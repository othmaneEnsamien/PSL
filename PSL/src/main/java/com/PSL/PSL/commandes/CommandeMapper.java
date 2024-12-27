package com.PSL.PSL.commandes;


import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Pharmacie.Pharmacie;
import com.PSL.PSL.grossiste.Grossiste;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommandeMapper {


    public Commande toEntity(CommandeDTO commandeDTO, Grossiste grossiste, Pharmacie pharmacie, Livreur livreur) {
        return Commande.builder()
                .codeCommande(commandeDTO.getCodeCommande())
                .produit(commandeDTO.getProduit())
                .quantite(commandeDTO.getQuantite())
                .prixTotal(commandeDTO.getPrixTotal())
                .dateLivraisonPrevue(commandeDTO.getDateLivraisonPrevue())
                .grossiste(grossiste)
                .pharmacie(pharmacie)
                .livreur(livreur)
                .dateCreation(LocalDateTime.now())
                .etat(EtatCommande.EN_PREPARATION)
                .build();
    }

    // Convertir Commande en CommandeResponseDTO
    public static CommandeResponseDTO toResponseDTO(Commande commande) {
        return CommandeResponseDTO.builder()
                .id(commande.getId())
                .codeCommande(commande.getCodeCommande())
                .produit(commande.getProduit())
                .quantite(commande.getQuantite())
                .prixTotal(commande.getPrixTotal())
                .dateLivraisonPrevue(commande.getDateLivraisonPrevue())
                .dateLivraisonReelle(commande.getDateLivraisonReelle())
                .dateCreation(commande.getDateCreation())
                .etat(commande.getEtat().name())
                .livreurId(commande.getLivreur().getNom())
                .pharmacieId(commande.getPharmacie().getNom())
                .build();
    }
}