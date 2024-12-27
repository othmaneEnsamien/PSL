package com.PSL.PSL.auth;


import com.PSL.PSL.Livreur.Livreur;
import com.PSL.PSL.Livreur.LivreurRepository;
import com.PSL.PSL.grossiste.Grossiste;
import com.PSL.PSL.grossiste.GrossisteRepository;
import com.PSL.PSL.role.RoleRepository;
import com.PSL.PSL.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthentificationService {

    private final GrossisteRepository grossisteRepository;
    private final LivreurRepository livreurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;





    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        Object principal = auth.getPrincipal();

        if (principal instanceof Grossiste) {
            Grossiste grossiste = (Grossiste) principal;
            claims.put("Nom", grossiste.getNom());
            var jwtToken = jwtService.generateToken(claims, grossiste);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else if (principal instanceof Livreur) {
            Livreur livreur = (Livreur) principal;
            claims.put("Nom", livreur.getNom());
            var jwtToken = jwtService.generateToken(claims, livreur);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }




}
