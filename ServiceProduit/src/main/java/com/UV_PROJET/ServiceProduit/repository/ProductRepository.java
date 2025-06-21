package com.UV_PROJET.ServiceProduit.repository;

import com.UV_PROJET.ServiceProduit.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNomContaining(String nom);
    List<Product> findByCategorie(String categorie);
}
