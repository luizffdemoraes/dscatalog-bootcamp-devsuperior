package com.devsuperior.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId;
	private long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception{
		exintingId = 1L;
		nonExistingId = 1000L;
		
	}
	

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		// Ação
		repository.deleteById(exintingId);
		Optional<Product> result = repository.findById(exintingId);
		
		// Verificação
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccesExceptionWhenIdDoesNotExist() {
		
		// Verificação
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
		
	}
	
	
	
	

}
