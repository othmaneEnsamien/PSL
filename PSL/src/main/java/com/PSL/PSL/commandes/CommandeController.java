package com.PSL.PSL.commandes;


import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PreAuthorize("hasRole('GROSSISTE')")
    @PostMapping("/create")
    public CommandeResponseDTO createCommande(@RequestBody CommandeDTO commandeDTO) {
        return commandeService.createCommande(commandeDTO);
    }

    @PreAuthorize("hasRole('GROSSISTE')")
    @PutMapping("/update/{id}")
    public CommandeResponseDTO updateCommande(@PathVariable Integer id, @RequestBody CommandeDTO commandeDTO) {
        return commandeService.updateCommande(id, commandeDTO);
    }

    @PreAuthorize("hasRole('GROSSISTE')")
    @DeleteMapping("/delete/{id}")
    public void deleteCommande(@PathVariable Integer id) {
        commandeService.deleteCommande(id);
    }

    @GetMapping("/livreur/assigned")
    public List<CommandeResponseDTO> getAssignedCommandes() {
        return commandeService.getCommandesForLivreur();
    }

    @PutMapping("/{commandeId}/status")
    public CommandeResponseDTO updateCommandeStatus(
            @PathVariable Integer commandeId,
            @RequestParam String newStatus) throws MessagingException {
        return commandeService.updateCommandeStatus(commandeId, newStatus);
    }
}
