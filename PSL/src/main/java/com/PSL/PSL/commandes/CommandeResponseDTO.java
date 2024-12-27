package com.PSL.PSL.commandes;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class CommandeResponseDTO {

    private Integer id;
    private String codeCommande;
    private String produit;
    private int quantite;
    private double prixTotal;
    private LocalDateTime dateLivraisonPrevue;
    private LocalDateTime dateLivraisonReelle;
    private LocalDateTime dateCreation;
    private String etat;
    private String pharmacieId;
    private String  livreurId;


}
