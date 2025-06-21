package com.UV_PROJET.ServiceProduit.service;

import com.UV_PROJET.ServiceProduit.dto.ProductDTO;
import com.UV_PROJET.ServiceProduit.model.Product;
import com.UV_PROJET.ServiceProduit.repository.ProductRepository;
import com.UV_PROJET.ServiceProduit.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productDTO.getPrix() == null || productDTO.getPrix().doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prix ne doit pas être négatif");
        }
        Product product = productMapper.toEntity(productDTO);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDTO);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé");
        }
        productRepository.deleteById(id);
    }

    public List<ProductDTO> findByNom(String nom) {
        return productRepository.findByNomContaining(nom).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findByCategorie(String categorie) {
        return productRepository.findByCategorie(categorie).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (productDTO.getPrix() == null || productDTO.getPrix().doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prix ne doit pas être négatif");
        }
        return productRepository.findById(id)
                .map(existing -> {
                    Product updated = productMapper.toEntity(productDTO);
                    updated.setId(existing.getId());
                    return productMapper.toDTO(productRepository.save(updated));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
    }
}
