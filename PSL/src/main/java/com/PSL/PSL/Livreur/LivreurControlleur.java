package com.PSL.PSL.Livreur;


import com.PSL.PSL.commandes.CommandeService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livreur")
@RequiredArgsConstructor
public class LivreurControlleur {

    private final LivreurService livreurService;


    @PostMapping("/commandes/{commandeId}/signaler-probleme")
    public void signalerProbleme(
            @PathVariable Long commandeId,
            @RequestParam TypeProbleme typeProbleme,
            @RequestParam String description) throws MessagingException {
        livreurService.signalerProbleme(commandeId, typeProbleme, description);
    }
}
