package com.PSL.PSL.grossiste;

import com.PSL.PSL.auth.RegistrationRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grossistes")
@RequiredArgsConstructor
public class GrossisteController {

    private final GrossisteService grossisteService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllGrossistes() {
        try {
            List<GrossisteDTO> grossistes = grossisteService.findAll();
            return ResponseEntity.ok(grossistes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des grossistes.");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createGrossiste(@RequestBody @Valid GrossisteDTO grossisteDTO) {
        try {
            GrossisteDTO createdGrossiste = grossisteService.save(grossisteDTO);
            return ResponseEntity.ok("Grossiste créé avec succès : " + createdGrossiste.getNom());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la création du grossiste : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateGrossiste(@PathVariable Integer id,
                                             @RequestBody @Valid Grossiste grossiste,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Erreur(s) de validation : ");
            bindingResult.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
        }

        try {
            GrossisteDTO updatedGrossiste = grossisteService.update(id, grossiste);
            return ResponseEntity.ok("Grossiste mis à jour avec succès : " + updatedGrossiste.getNom());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Grossiste introuvable pour l'ID : " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la mise à jour du grossiste.");
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteGrossiste(@PathVariable Integer id) {
        try {
            grossisteService.delete(id);
            return ResponseEntity.ok("Grossiste supprimé avec succès, ID : " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Grossiste introuvable pour l'ID : " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression du grossiste.");
        }
    }

    @PostMapping("/livreur")
    @PreAuthorize("hasRole('GROSSISTE')")
    public ResponseEntity<?> createLivreurAssigneAGrossiste(
            @RequestBody @Valid RegistrationRequest request
    ) {
        try {
            LivreurResponseDTO response = grossisteService.CLAG(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la création du livreur.");
        }
    }

    @PutMapping("/livreur/{livreurId}")
    @PreAuthorize("hasRole('GROSSISTE')")
    public ResponseEntity<?> updateLivreur(
            @PathVariable Long livreurId,
            @RequestBody @Valid RegistrationRequest request
    ) {
        try {
            grossisteService.updateLivreur(livreurId, request);
            return ResponseEntity.ok("Livreur mis à jour avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livreur introuvable pour l'ID : " + livreurId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la mise à jour du livreur.");
        }
    }

    @DeleteMapping("/livreur/{livreurId}")
    @PreAuthorize("hasRole('GROSSISTE')")
    public ResponseEntity<?> deleteLivreur(@PathVariable Long livreurId) {
        try {
            grossisteService.deleteLivreur(livreurId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livreur introuvable pour l'ID : " + livreurId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression du livreur.");
        }
    }

    @PutMapping("/{livreurId}/disable")
    @PreAuthorize("hasRole('GROSSISTE')")
    public ResponseEntity<?> disableLivreur(@PathVariable Long livreurId) {
        try {
            grossisteService.disableLivreur(livreurId);
            return ResponseEntity.ok("Livreur désactivé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livreur introuvable pour l'ID : " + livreurId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la désactivation du livreur.");
        }
    }

    @PutMapping("/{livreurId}/enable")
    @PreAuthorize("hasRole('GROSSISTE')")
    public ResponseEntity<?> enableLivreur(@PathVariable Long livreurId) {
        try {
            grossisteService.enableLivreur(livreurId);
            return ResponseEntity.ok("Livreur activé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livreur introuvable pour l'ID : " + livreurId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'activation du livreur.");
        }
    }
}
