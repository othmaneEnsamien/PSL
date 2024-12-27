package com.PSL.PSL.Livreur;


import com.PSL.PSL.grossiste.Grossiste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivreurRepository extends JpaRepository<Livreur, Integer> {

    Optional<Livreur> findByEmail(String username);

    boolean existsByEmail(String email);

    Optional<Livreur> findById(Long livreurId);


}
