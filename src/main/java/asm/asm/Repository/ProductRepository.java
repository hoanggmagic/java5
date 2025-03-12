package asm.asm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import asm.asm.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);
}