package com.PSL.PSL.Livreur;

import com.PSL.PSL.commandes.Commande;
import com.PSL.PSL.commandes.CommandeRepository;
import com.PSL.PSL.notification.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LivreurService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LivreurRepository livreurRepository;

    @Autowired
    private ProblemeCommandeRepository problemeCommandeRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void signalerProbleme(Long commandeId, TypeProbleme typeProbleme, String description) throws MessagingException {

        String currentLivreurEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var livreur = livreurRepository.findByEmail(currentLivreurEmail)
                .orElseThrow(() -> new IllegalStateException("Livreur not found for the connected user"));

        Commande commande = commandeRepository.findById(Math.toIntExact(commandeId))
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

        if (!commande.getLivreur().equals(livreur)) {
            throw new IllegalStateException("Vous n'êtes pas autorisé à signaler un problème pour cette commande.");
        }

        ProblemeCommande probleme = new ProblemeCommande();
        probleme.setCommande(commande);
        probleme.setTypeProbleme(typeProbleme);
        probleme.setDescription(description);
        probleme.setDateSignalee(LocalDateTime.now());

        boolean problemeExistant = problemeCommandeRepository.existsByCommande(commande);
        if (problemeExistant) {
            notificationService.notifierProblemeGrossiste(commande);
        }
        problemeCommandeRepository.save(probleme);
    }
}
