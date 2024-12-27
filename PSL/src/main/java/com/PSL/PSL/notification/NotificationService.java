package com.PSL.PSL.notification;

import com.PSL.PSL.Livreur.ProblemeCommandeRepository;
import com.PSL.PSL.commandes.Commande;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProblemeCommandeRepository problemeCommandeRepository;

    public void notifierRetardPharmacie(Commande commande) throws MessagingException {
        if (commande.getDateLivraisonReelle() == null ||
                commande.getDateLivraisonReelle().isAfter(commande.getDateLivraisonPrevue())) {

            String message = "Votre commande a été retardée. Nous nous excusons pour le désagrément.";
            emailService.sendPharmacyNotification(
                    commande.getPharmacie().getEmail(),
                    commande.getPharmacie().getNom(),
                    message
            );
        }
    }


    public void notifierLivraisonPharmacie(Commande commande) throws MessagingException {
        if (commande.getDateLivraisonReelle() != null &&
                commande.getDateLivraisonReelle().isBefore(commande.getDateLivraisonPrevue())) {

            String message = "Votre commande a été livrée avec succès.";
            emailService.sendPharmacyNotification(
                    commande.getPharmacie().getEmail(),
                    commande.getPharmacie().getNom(),
                    message
            );
        }
    }

    public void notifierProblemeGrossiste(Commande commande) throws MessagingException {

        boolean problemeExistant = problemeCommandeRepository.existsByCommande(commande);

        if (commande.getLivreur() != null && problemeExistant) {
            String message = "Un problème a été signalé par votre livreur pour la commande " + commande.getCodeCommande() + ".";
            emailService.sendGrossisteNotification(
                    commande.getGrossiste().getEmail(),
                    commande.getGrossiste().getNom(),
                    message
            );
        }
    }
}
