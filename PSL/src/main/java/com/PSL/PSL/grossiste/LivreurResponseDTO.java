package com.PSL.PSL.grossiste;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LivreurResponseDTO {
    private String nom;
    private String email;
    private String hashedPassword;
    private String grossisteNom;
    private String grossisteEmail;
}