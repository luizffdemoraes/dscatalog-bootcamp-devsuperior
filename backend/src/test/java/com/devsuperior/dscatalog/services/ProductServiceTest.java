package com.devsuperior.dscatalog.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

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
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		
		
		// Ao criar um mock e necessário configurar o comportamento simulado dele.
		doNothing().when(repository).deleteById(existingId);
	
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
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
