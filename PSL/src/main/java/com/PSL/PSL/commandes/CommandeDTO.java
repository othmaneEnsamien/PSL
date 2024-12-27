package com.PSL.PSL.commandes;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommandeDTO {

    private String codeCommande;
    private String produit;
    private int quantite;
    private double prixTotal;
    private LocalDateTime dateLivraisonPrevue;
    private Integer pharmacieId;
    private Integer livreurId;

}