package com.devsuperior.dscatalog.repositories;


import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception{
		exintingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	/*
	@Test
	public void returnOptionalNonEmptyWhenIdExists(){
		//Ação
		Optional<Product> possible = repository.findById(exintingId);
		
		//Verificação
		assertThat(possible).isPresent();
		assertThat(possible.get().getId().equals(1L));
		Assertions.assertNotNull(possible);
		Assertions.assertTrue(possible.isPresent());
		Assertions.assertEquals(possible.get().getId(), 1L);
	}
	
	@Test
	public void returnOptionalEmptyWhenIdDoesNotExist(){
		//Ação
		Optional<Product> possible = repository.findById(nonExistingId);
		
		//Verificação
		Assertions.assertFalse(possible.isPresent());
		Assertions.assertTrue(possible.isEmpty());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		//Cenário
		Product product = Factory.createProduct();
		product.setId(null);
		//Ação
		product = repository.save(product);
		//Verificação
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
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
	 */
}