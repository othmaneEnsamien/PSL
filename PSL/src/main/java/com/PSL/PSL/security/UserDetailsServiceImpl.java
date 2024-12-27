package com.PSL.PSL.security;

import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Livreur.LivreurRepository;
import com.PSL.PSL.grossiste.Grossiste;
import com.PSL.PSL.grossiste.GrossisteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final GrossisteRepository grossisteRepository;
    private final LivreurRepository livreurRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche d'un utilisateur par email dans le Grossiste
        Optional<Grossiste> grossiste = grossisteRepository.findByEmail(email);
        if (grossiste.isPresent()) {
            return grossiste.get();
        }

        // Recherche d'un utilisateur par email dans le Livreur
        Optional<Livreur> livreur = livreurRepository.findByEmail(email);
        if (livreur.isPresent()) {
            return livreur.get();
        }

        // Si aucun utilisateur n'est trouvé
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}