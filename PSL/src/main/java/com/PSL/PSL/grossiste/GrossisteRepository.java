package com.PSL.PSL.grossiste;

import com.PSL.PSL.Livreur.Livreur;
//import com.PSL.PSL.commandes.Commande;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GrossisteRepository extends JpaRepository<Grossiste, Long> {

    Optional<Grossiste> findByEmail(String username);


    Optional<Grossiste> findById(Integer grossisteId);



    @Modifying
    @Transactional // Assurez-vous que la transaction est bien gérée pour les requêtes modifiantes
    @Query("DELETE FROM Grossiste g WHERE g.id = :id")
    void deleteById(@Param("id") Integer id);
}
