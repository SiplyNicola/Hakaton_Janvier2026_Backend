package com.example.hakaton_janvier2026_backend.controller.export;

import com.example.hakaton_janvier2026_backend.application.services.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exports")
@CrossOrigin("*")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/note/{id}/pdf")
    public ResponseEntity<byte[]> exportNotePdf(@PathVariable int id) {
        try {
            byte[] pdfContent = exportService.generateNotePdf(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"note_" + id + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/folder/{id}/zip")
    public ResponseEntity<byte[]> exportFolderZip(@PathVariable int id) {
        try {
            byte[] zipContent = exportService.generateFolderZip(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archive_" + id + ".zip\"")
                    .contentType(MediaType.parseMediaType("application/zip"))
                    .body(zipContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}