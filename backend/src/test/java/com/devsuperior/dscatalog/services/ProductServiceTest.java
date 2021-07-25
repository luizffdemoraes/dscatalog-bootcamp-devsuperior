package com.devsuperior.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;
	/*
	 * Usar quando a classe de teste não carrega o contexto da aplicação.
	 *  É mais rápido e enxuto.@ExtendWith
	 */
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page; //Tipo concreto que representa uma pagina de dados
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		// Ao criar um mock e necessário configurar o comportamento simulado dele.
		//Simular o comportamento de uma pagina
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		//Simular o comportamento de save
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		//Simular a busca por id existente e inexistente
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product)); // .of recebe um objeto
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty()); // empty instancia um Optional vazio
		
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		assertNotNull(result);
		verify(repository, Mockito.times(1)).findAll(pageable);
		
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExists() {
		
		
		//Verificação
		Assertions.assertThrows(DataBaseException.class, () -> {
			//Ação
			service.delete(dependentId);
		});
		
		//Verificação
		verify(repository, times(1)).deleteById(dependentId);
		
	}
			
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		
		//Verificação
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			//Ação
			service.delete(nonExistingId);
		});
		
		//Verificação
		verify(repository, times(1)).deleteById(nonExistingId);
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		
		//Verificação
		Assertions.assertDoesNotThrow(() -> {
			//Ação
			service.delete(existingId);
		});
		
		//Verificação
		verify(repository, times(1)).deleteById(existingId);
		
	}
	
}
