package com.PSL.PSL.commandes;

import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Livreur.ProblemeCommande;
import com.PSL.PSL.Pharmacie.Pharmacie;
import com.PSL.PSL.grossiste.Grossiste;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "code_commande", nullable = false, unique = true)
    private String codeCommande;

    @Column(nullable = false)
    private String produit;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private double prixTotal;

    @Enumerated(EnumType.STRING)
    private EtatCommande etat = EtatCommande.EN_PREPARATION;

    private String adresseLivraison;

    private String commentaires;

    private LocalDateTime dateCreation = LocalDateTime.now();


    @Column(nullable = true)
    private LocalDateTime dateLivraisonPrevue;
    @Column(nullable = true)
    private LocalDateTime dateLivraisonReelle;

    @ManyToOne
    @JoinColumn(name = "grossiste_id", nullable = false)
    @JsonBackReference
    private Grossiste grossiste;

    @ManyToOne
    @JoinColumn(name = "livreur_id")
    @JsonBackReference
    private Livreur livreur;

    @ManyToOne
    @JoinColumn(name = "pharmacie_id", nullable = false)
    @JsonBackReference
    private Pharmacie pharmacie;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    private List<ProblemeCommande> problemes;


}

