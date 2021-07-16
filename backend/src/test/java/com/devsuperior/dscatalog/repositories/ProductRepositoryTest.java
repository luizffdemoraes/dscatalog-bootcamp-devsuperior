package com.devsuperior.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		// Cenário
		long exitingId = 1L;
		
		// Ação
		repository.deleteById(exitingId);
		Optional<Product> result = repository.findById(exitingId);
		
		// Verificação
		Assertions.assertFalse(result.isPresent());
		
	}
	

}
