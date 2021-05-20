package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	/*
	 * Garante que o metodo vai executar uma transação com banco de dados e não vai
	 * travar evita um lock no banco de dados
	 */

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		
		/* Implementação com expressão Lambda
		 * stream converter sua coleção, stream um recurso para trabalhar com
		 * funções de alta ordem inclusive lambda para aplicar transformações
		 * map aplica um função transforma cada elemento da lista em outra coisa
		 * ela aplica uma função a cada elemento.
		 * collec transforma uma stream em uma lista
		 */
		
		return list.stream()
				.map(x -> new CategoryDTO(x))
				.collect(Collectors.toList());
		
		//Uma forma de converter a lista de category
//		List<CategoryDTO> listDto = new ArrayList<>();
//		for(Category cat : list) {
//			listDto.add(new CategoryDTO(cat));
//		}
//		return listDto;
	}

}
