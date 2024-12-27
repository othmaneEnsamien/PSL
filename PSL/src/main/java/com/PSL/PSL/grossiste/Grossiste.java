package com.PSL.PSL.grossiste;


import com.PSL.PSL.Livreur.Livreur;
//import com.PSL.PSL.commandes.Commande;
import com.PSL.PSL.commandes.Commande;
import com.PSL.PSL.role.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "grossiste")
@EntityListeners(AuditingEntityListener.class)
public class Grossiste implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Le nom ne peut pas être vide.")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères.")
    private String nom;

    @NotBlank(message = "L'adresse ne peut pas être vide.")
    @Size(max = 255, message = "L'adresse ne peut pas dépasser 255 caractères.")
    private String addresse;

    @Email(message = "Veuillez fournir une adresse email valide.")
    @NotBlank(message = "L'email ne peut pas être vide.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    private String password;
    @ManyToMany(fetch = EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "grossiste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Livreur> livreurs = new ArrayList<>();

    @OneToMany(mappedBy = "grossiste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Commande> commandes = new ArrayList<>();

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
}
