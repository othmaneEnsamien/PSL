package com.PSL.PSL.commandes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    List<Commande> findByLivreurEmail(String email);

    // Trouver les commandes livrées entre deux dates et dans un état spécifique
    List<Commande> findByDateLivraisonReelleBetweenAndEtat(
            LocalDateTime startDate, LocalDateTime endDate, EtatCommande etat);

    // Trouver les commandes en retard (date livraison prévue dépassée sans livraison réelle)
    List<Commande> findByDateLivraisonPrevueBeforeAndDateLivraisonReelleIsNull(LocalDateTime endDate);

}