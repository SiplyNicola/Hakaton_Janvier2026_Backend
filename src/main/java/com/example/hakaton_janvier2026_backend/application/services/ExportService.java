package com.example.hakaton_janvier2026_backend.application.services;

import com.example.hakaton_janvier2026_backend.infrastructure.folders.DbFolder;
import com.example.hakaton_janvier2026_backend.infrastructure.folders.IFolderRepository;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.DbNote;
import com.example.hakaton_janvier2026_backend.infrastructure.notes.INoteRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ExportService {

    private final INoteRepository noteRepository;
    private final IFolderRepository folderRepository;

    public ExportService(INoteRepository noteRepository, IFolderRepository folderRepository) {
        this.noteRepository = noteRepository;
        this.folderRepository = folderRepository;
    }

    // EXPORT PDF

    public byte[] generateNotePdf(int noteId) throws Exception {
        DbNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Parchment not found"));

        Parser parser = Parser.builder().build();
        Node document = parser.parse(note.content_markdown != null ? note.content_markdown : "");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String bodyContent = renderer.render(document);

        String html = """
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; font-size: 12pt; line-height: 1.5; color: #000; background-color: #fff; }
                    h1 { text-align: center; border-bottom: 1px solid #ccc; padding-bottom: 10px; margin-bottom: 20px; }
                    a { color: blue; text-decoration: underline; }
                    * { background-color: transparent; }
                </style>
            </head>
            <body>
                <h1>%s</h1>
                <div>%s</div>
            </body>
            </html>
            """.formatted(note.title, bodyContent);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, "");
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }
    }

    // EXPORT ZIP

    public byte[] generateFolderZip(int folderId) throws IOException {
        DbFolder rootFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            addFolderToZip(rootFolder, "", zos);

            zos.finish();
            return baos.toByteArray();
        }
    }

    private void addFolderToZip(DbFolder folder, String parentPath, ZipOutputStream zos) throws IOException {
        String currentPath = parentPath.isEmpty() ? "" : parentPath + "/";

        List<DbNote> notes = noteRepository.findAllByFolderIdAndDeletedAtIsNull(folder.id);
        for (DbNote note : notes) {
            String cleanTitle = sanitizeFilename(note.title);
            ZipEntry zipEntry = new ZipEntry(currentPath + cleanTitle + ".md");
            zos.putNextEntry(zipEntry);

            String content = note.content_markdown != null ? note.content_markdown : "";
            zos.write(content.getBytes());
            zos.closeEntry();
        }

        List<DbFolder> subFolders = folderRepository.findAllByParentFolderIdAndDeletedAtIsNull(folder.id);
        for (DbFolder subFolder : subFolders) {
            String cleanFolderName = sanitizeFilename(subFolder.name);
            addFolderToZip(subFolder, currentPath + cleanFolderName, zos);
        }
    }

    private String sanitizeFilename(String input) {
        if (input == null || input.isBlank()) return "Untitled";
        return input.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}