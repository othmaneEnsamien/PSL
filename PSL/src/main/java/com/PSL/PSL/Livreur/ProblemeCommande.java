package com.PSL.PSL.Livreur;


import com.PSL.PSL.commandes.Commande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor

public class ProblemeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Commande commande;

    @Enumerated(EnumType.STRING)
    private TypeProbleme typeProbleme;

    private String description;

    private LocalDateTime dateSignalee;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public TypeProbleme getTypeProbleme() {
        return typeProbleme;
    }

    public void setTypeProbleme(TypeProbleme typeProbleme) {
        this.typeProbleme = typeProbleme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateSignalee() {
        return dateSignalee;
    }

    public void setDateSignalee(LocalDateTime dateSignalee) {
        this.dateSignalee = dateSignalee;
    }
}
