package com.PSL.PSL.grossiste;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrossisteDTO {

    private Integer id;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères.")
    private String nom;

    @NotBlank(message = "L'adresse ne peut pas être vide.")
    @Size(max = 255, message = "L'adresse ne peut pas dépasser 255 caractères.")
    private String addresse;

    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "Veuillez fournir une adresse email valide.")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;

    private List<@NotBlank(message = "Chaque rôle doit être valide.") String> roles;


    private List<@NotBlank(message = "Chaque livreur doit être valide.") String> livreurs;

    public GrossisteDTO(Integer id, String nom, String addresse, String email, List<String> roles, List<String> livreurs) {
        this.id = id;
        this.nom = nom;
        this.addresse = addresse;
        this.email = email;
        this.roles = roles;
        this.livreurs = livreurs;
    }
}
