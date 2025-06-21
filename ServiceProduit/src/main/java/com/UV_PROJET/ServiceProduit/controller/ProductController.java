package com.UV_PROJET.ServiceProduit.controller;

import com.UV_PROJET.ServiceProduit.dto.ProductDTO;
import com.UV_PROJET.ServiceProduit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * Crée un nouveau produit
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    /**
     * Récupère tous les produits
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Récupère un produit par son id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime un produit par son id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recherche des produits par nom ou catégorie
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam(required = false) String nom,
                                                          @RequestParam(required = false) String categorie) {
        if (nom != null) {
            return ResponseEntity.ok(productService.findByNom(nom));
        } else if (categorie != null) {
            return ResponseEntity.ok(productService.findByCategorie(categorie));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Met à jour un produit existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }
}
