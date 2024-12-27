package com.PSL.PSL.commandes;

import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Livreur.LivreurRepository;
import com.PSL.PSL.Pharmacie.Pharmacie;
import com.PSL.PSL.Pharmacie.PharmacieRepository;
import com.PSL.PSL.grossiste.GrossisteRepository;
import com.PSL.PSL.notification.NotificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommandeService {


    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private GrossisteRepository grossisteRepository;

    @Autowired
    private PharmacieRepository pharmacieRepository;

    @Autowired
    private LivreurRepository livreurRepository;

    @Autowired
    private CommandeMapper commandeMapper;

    @Autowired
    private NotificationService notificationService;


    public List<CommandeResponseDTO> getCommandesForLivreur() {

        String currentLivreurEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasLivreurRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_LIVREUR"));

        if (!hasLivreurRole) {
            throw new AccessDeniedException("Access denied: Only users with the role 'LIVREUR' can view their commandes.");
        }


        List<Commande> commandes = commandeRepository.findByLivreurEmail(currentLivreurEmail);


        return commandes.stream()
                .map(CommandeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CommandeResponseDTO updateCommandeStatus(Integer commandeId, String newStatus) throws MessagingException {

        String currentLivreurEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasLivreurRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_LIVREUR"));

        if (!hasLivreurRole) {
            throw new AccessDeniedException("Access denied: Only users with the role 'LIVREUR' can update commandes.");
        }

        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée."));

        if (!commande.getLivreur().getEmail().equals(currentLivreurEmail)) {
            throw new AccessDeniedException("Access denied: This commande is not assigned to the current livreur.");
        }

        EtatCommande etatCommande;
        try {
            etatCommande = EtatCommande.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide. Les statuts valides sont : "
                    + Arrays.toString(EtatCommande.values()));
        }

        switch (etatCommande) {
            case LIVREE:
                commande.setDateLivraisonReelle(LocalDateTime.now());
                notificationService.notifierLivraisonPharmacie(commande);
                break;
            case RETARD:
                commande.setDateLivraisonReelle(null);
                notificationService.notifierRetardPharmacie(commande);
                break;
            default:
                commande.setDateLivraisonReelle(null);
                break;
        }

        commande.setEtat(etatCommande);

        commande = commandeRepository.save(commande);

        return CommandeMapper.toResponseDTO(commande);
    }


    public CommandeResponseDTO createCommande(CommandeDTO commandeDTO) {
        String currentGrossisteEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var currentGrossiste = grossisteRepository.findByEmail(currentGrossisteEmail)
                .orElseThrow(() -> new IllegalStateException("Grossiste not found for the connected user"));

        Pharmacie pharmacie = pharmacieRepository.findById(commandeDTO.getPharmacieId())
                .orElseThrow(() -> new IllegalArgumentException("Pharmacie non trouvée."));
        Livreur livreur = livreurRepository.findById(commandeDTO.getLivreurId())
                .orElseThrow(() -> new IllegalArgumentException("Livreur non trouvé."));

        Commande commande = commandeMapper.toEntity(commandeDTO, currentGrossiste, pharmacie, livreur);
        commande = commandeRepository.save(commande);

        return commandeMapper.toResponseDTO(commande);
    }
    // Méthode pour mettre à jour une commande
    public CommandeResponseDTO updateCommande(Integer id, CommandeDTO commandeDTO) {
        Commande existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée avec l'ID : " + id));

        Pharmacie pharmacie = pharmacieRepository.findById(commandeDTO.getPharmacieId())
                .orElseThrow(() -> new IllegalArgumentException("Pharmacie non trouvée."));
        Livreur livreur = livreurRepository.findById(commandeDTO.getLivreurId())
                .orElseThrow(() -> new IllegalArgumentException("Livreur non trouvé."));

        existingCommande.setCodeCommande(commandeDTO.getCodeCommande());
        existingCommande.setProduit(commandeDTO.getProduit());
        existingCommande.setQuantite(commandeDTO.getQuantite());
        existingCommande.setPrixTotal(commandeDTO.getPrixTotal());
        existingCommande.setDateLivraisonPrevue(commandeDTO.getDateLivraisonPrevue());
        existingCommande.setPharmacie(pharmacie);
        existingCommande.setLivreur(livreur);


        existingCommande = commandeRepository.save(existingCommande);

        return commandeMapper.toResponseDTO(existingCommande);
    }

    // Méthode pour supprimer une commande
    public void deleteCommande(Integer id) {
        Commande existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée avec l'ID : " + id));
        commandeRepository.delete(existingCommande);
    }


}
