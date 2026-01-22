package com.example.hakaton_janvier2026_backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class User {
    private int  id;

    //Data
    private String username;
    private String password;
}
