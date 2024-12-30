package com.PSL.PSL.commandes;



import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommandeDTO {

    @NotBlank(message = "Le code de commande est obligatoire.")
    private String codeCommande;

    @NotBlank(message = "Le produit est obligatoire.")
    private String produit;

    @Positive(message = "La quantité doit être un nombre positif.")
    private int quantite;

    @Positive(message = "Le prix total doit être un nombre positif.")
    private double prixTotal;

    @FutureOrPresent(message = "La date de livraison prévue doit être dans le futur ou le présent.")
    private LocalDateTime dateLivraisonPrevue;

    @NotNull(message = "L'ID de la pharmacie est obligatoire.")
    private Integer pharmacieId;

    @NotNull(message = "L'ID du livreur est obligatoire.")
    private Integer livreurId;
}