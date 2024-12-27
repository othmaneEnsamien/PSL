package com.PSL.PSL.Livreur;

import com.PSL.PSL.commandes.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ProblemeCommandeRepository extends JpaRepository<ProblemeCommande, Long> {
    // Trouver les problèmes signalés entre deux dates
    List<ProblemeCommande> findByDateSignaleeBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByCommande(Commande commande);

    ProblemeCommande findByCommande(Commande commande);
}
