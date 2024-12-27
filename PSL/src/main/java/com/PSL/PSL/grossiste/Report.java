package com.PSL.PSL.grossiste;

import com.PSL.PSL.commandes.Commande;
import com.PSL.PSL.Livreur.ProblemeCommande;

import java.util.List;

public class Report {
    private List<Commande> commandesLivrees;
    private List<Commande> commandesEnRetard;
    private List<ProblemeCommande> problemesSignales;

    public Report(List<Commande> commandesLivrees, List<Commande> commandesEnRetard, List<ProblemeCommande> problemesSignales) {
        this.commandesLivrees = commandesLivrees;
        this.commandesEnRetard = commandesEnRetard;
        this.problemesSignales = problemesSignales;
    }

    public List<Commande> getCommandesLivrees() {
        return commandesLivrees;
    }

    public List<Commande> getCommandesEnRetard() {
        return commandesEnRetard;
    }

    public List<ProblemeCommande> getProblemesSignales() {
        return problemesSignales;
    }
}
