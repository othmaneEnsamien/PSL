package com.PSL.PSL.grossiste;

import com.PSL.PSL.Livreur.ProblemeCommande;
import com.PSL.PSL.Livreur.ProblemeCommandeRepository;
import com.PSL.PSL.commandes.Commande;
import com.PSL.PSL.commandes.CommandeRepository;
import com.PSL.PSL.Livreur.LivreurRepository;
import com.PSL.PSL.commandes.EtatCommande;
import com.PSL.PSL.grossiste.GrossisteRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RapportService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private LivreurRepository livreurRepository;
    @Autowired
    private GrossisteRepository grossisteRepository;
    @Autowired
    private ProblemeCommandeRepository problemeCommandeRepository;

    // Générer un rapport hebdomadaire ou mensuel
    public Report generateReport(LocalDate startDate, LocalDate endDate) {

        List<Commande> commandesLivrees = commandeRepository.findByDateLivraisonReelleBetweenAndEtat(
                startDate.atStartOfDay(), endDate.atTime(23, 59), EtatCommande.LIVREE);

        List<Commande> commandesEnRetard = commandeRepository.findByDateLivraisonPrevueBeforeAndDateLivraisonReelleIsNull(
                endDate.atStartOfDay());

        List<ProblemeCommande> problemesSignales = problemeCommandeRepository.findByDateSignaleeBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59));

        return new Report(commandesLivrees, commandesEnRetard, problemesSignales);
    }
    public String exportToCSV(Report report) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "rapport_structure_" + timestamp + ".csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"", "Livraisons effectuées par livreur"});
            writer.writeNext(new String[]{"Commande ID", "Produit", "Quantité", "Statut", "Livreur", "Date Livraison Réelle"});
            for (Commande commande : report.getCommandesLivrees()) {
                String[] record = {
                        String.valueOf(commande.getId()),
                        commande.getProduit(),
                        String.valueOf(commande.getQuantite()),
                        String.valueOf(commande.getEtat()),
                        commande.getLivreur() != null ? commande.getLivreur().getNom() : "",
                        commande.getDateLivraisonReelle() != null ? commande.getDateLivraisonReelle().toString() : ""
                };
                writer.writeNext(record);
            }
            writer.writeNext(new String[]{""});

            // Commandes en retard
            writer.writeNext(new String[]{"", "Commandes en retard"});
            writer.writeNext(new String[]{"Commande ID", "Produit", "Quantité", "Date Livraison Prévue", "Statut"});
            for (Commande commande : report.getCommandesEnRetard()) {
                String[] record = {
                        String.valueOf(commande.getId()),
                        commande.getProduit(),
                        String.valueOf(commande.getQuantite()),
                        commande.getDateLivraisonPrevue() != null ? commande.getDateLivraisonPrevue().toString() : "",
                        String.valueOf(commande.getEtat())
                };
                writer.writeNext(record);
            }
            writer.writeNext(new String[]{""});

            // Problèmes signalés
            writer.writeNext(new String[]{"", "Problèmes signalés"});
            writer.writeNext(new String[]{"Commande ID", "Produit", "Quantité", "Type de Problème", "Description", "Date Signalée"});
            for (ProblemeCommande probleme : report.getProblemesSignales()) {
                String[] record = {
                        String.valueOf(probleme.getCommande().getId()),
                        probleme.getCommande().getProduit(),
                        String.valueOf(probleme.getCommande().getQuantite()),
                        String.valueOf(probleme.getTypeProbleme()),
                        probleme.getDescription() != null ? probleme.getDescription() : "N/A",
                        probleme.getDateSignalee() != null ? probleme.getDateSignalee().toString() : ""
                };
                writer.writeNext(record);
            }

            return "Fichier CSV structuré généré avec succès : " + filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors de la génération du fichier CSV.";
        }

}
}
