package com.example.hakaton_janvier2026_backend.application.utils;

public interface IQueryHandler <I,O>{
    O handle(I input);
}
