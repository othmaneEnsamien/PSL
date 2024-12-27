package com.PSL.PSL.grossiste;

import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Livreur.LivreurRepository;
import com.PSL.PSL.auth.RegistrationRequest;
import com.PSL.PSL.role.Role;
import com.PSL.PSL.role.RoleRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrossisteService {

    private final GrossisteRepository grossisteRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LivreurRepository livreurRepository;



    public List<GrossisteDTO> findAll() {
        List<Grossiste> grossistes = grossisteRepository.findAll();
        return grossistes.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private GrossisteDTO convertToDTO(Grossiste grossiste) {
        List<String> roles = grossiste.getRoles().stream()
                .map(Role::getName)
                .toList();

        List<String> livreurs = grossiste.getLivreurs().stream()
                .map(Livreur::getNom)
                .toList();

        return new GrossisteDTO(
                grossiste.getId(),
                grossiste.getNom(),
                grossiste.getAddresse(),
                grossiste.getEmail(),
                roles,
                livreurs
        );
    }

    private Grossiste convertToEntity(GrossisteDTO grossisteDTO) {
        Grossiste grossiste = new Grossiste();
        grossiste.setId(grossisteDTO.getId());
        grossiste.setNom(grossisteDTO.getNom());
        grossiste.setAddresse(grossisteDTO.getAddresse());
        grossiste.setEmail(grossisteDTO.getEmail());
        grossiste.setLivreurs(grossisteDTO.getLivreurs().stream()
                .map(nom -> {
                    Livreur livreur = new Livreur();
                    livreur.setNom(nom);
                    return livreur;
                })
                .toList());
        return grossiste;
    }

    public GrossisteDTO save(GrossisteDTO grossisteDTO) {
        Role grossisteRole = roleRepository.findByName("ROLE_GROSSISTE")
                .orElseThrow(() -> new RuntimeException("Role 'ROLE_GROSSISTE' not found"));

        Grossiste grossiste = convertToEntity(grossisteDTO);

        String encodedPassword = passwordEncoder.encode(grossisteDTO.getPassword());
        grossiste.setPassword(encodedPassword);

        grossiste.setRoles(List.of(grossisteRole));

        Grossiste savedGrossiste = grossisteRepository.save(grossiste);

        return convertToDTO(savedGrossiste);
    }

    public GrossisteDTO update(Integer id, Grossiste grossiste) {
        return grossisteRepository.findById(id)
                .map(existing -> {
                    existing.setNom(grossiste.getNom());
                    existing.setAddresse(grossiste.getAddresse());
                    existing.setEmail(grossiste.getEmail());
                    existing.setPassword(grossiste.getPassword());
                    return grossisteRepository.save(existing);
                })
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Grossiste non trouvé"));
    }



    public void delete(Integer id) {
        grossisteRepository.deleteById(id);
    }

    //creer un livreur assigner a un grossiste
    public LivreurResponseDTO CLAG(RegistrationRequest request) throws MessagingException {

        String currentGrossisteEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var currentGrossiste = grossisteRepository.findByEmail(currentGrossisteEmail)
                .orElseThrow(() -> new IllegalStateException("Grossiste not found for the connected user"));

        if (livreurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("A livreur with this email already exists");
        }

        var livreurRole = roleRepository.findByName("ROLE_LIVREUR")
                .orElseThrow(() -> new IllegalStateException("Role 'Livreur' not found"));

        var hashedPassword = passwordEncoder.encode(request.getPassword());
        var livreur = Livreur.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .password(hashedPassword)
                .grossiste(currentGrossiste)
                .enabled(true)
                .build();
        livreur.setRoles(List.of(livreurRole));

        livreurRepository.save(livreur);

        return LivreurResponseDTO.builder()
                .nom(livreur.getNom())
                .email(livreur.getEmail())
                .hashedPassword(livreur.getPassword())
                .grossisteNom(currentGrossiste.getNom())
                .grossisteEmail(currentGrossiste.getEmail())
                .build();
    }

    public void disableLivreur(Long livreurId) {

        String currentGrossisteEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var currentGrossiste = grossisteRepository.findByEmail(currentGrossisteEmail)
                .orElseThrow(() -> new IllegalStateException("Grossiste not found for the connected user"));

        var livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new IllegalStateException("Livreur not found"));

        if (!livreur.getGrossiste().getId().equals(currentGrossiste.getId())) {
            throw new IllegalStateException("You are not authorized to disable this livreur");
        }
        livreur.setEnabled(false);

        livreurRepository.save(livreur);
    }

    public void enableLivreur(Long livreurId) {
        String currentGrossisteEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var currentGrossiste = grossisteRepository.findByEmail(currentGrossisteEmail)
                .orElseThrow(() -> new IllegalStateException("Grossiste not found for the connected user"));

        var livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new IllegalStateException("Livreur not found"));

        if (!livreur.getGrossiste().getId().equals(currentGrossiste.getId())) {
            throw new IllegalStateException("You are not authorized to enable this livreur");
        }

        livreur.setEnabled(true);
        livreurRepository.save(livreur);
    }
    public void updateLivreur(Long livreurId, RegistrationRequest request) {
        // Récupérer le livreur existant
        var livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new IllegalStateException("Livreur not found"));

        // Vérifier si l'email est unique
        if (livreurRepository.existsByEmail(request.getEmail()) &&
                !livreur.getEmail().equals(request.getEmail())) {
            throw new IllegalStateException("A livreur with this email already exists");
        }


        livreur.setNom(request.getNom());
        livreur.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            livreur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        livreurRepository.save(livreur);
    }

    public void deleteLivreur(Long livreurId) {
        // Vérifier si le livreur existe
        var livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new IllegalStateException("Livreur not found"));

        // Vérifier s'il est assigné à un grossiste et dissocier
        if (livreur.getGrossiste() != null) {
            livreur.getGrossiste().getLivreurs().remove(livreur);  // Supposons que 'getLivreurs' retourne une liste de livreurs
        }

        // Supprimer le livreur
        livreurRepository.delete(livreur);
    }
}