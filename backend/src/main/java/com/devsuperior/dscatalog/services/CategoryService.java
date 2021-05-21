package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	/*
	 * Garante que o metodo vai executar uma transação com banco de dados e não vai
	 * travar evita um lock no banco de dados
	 * Implementação com expressão Lambda stream converter sua coleção, stream um
	 * recurso para trabalhar com funções de alta ordem inclusive lambda para
	 * aplicar transformações map aplica um função transforma cada elemento da lista
	 * em outra coisa ela aplica uma função a cada elemento. collec transforma uma
	 * stream em uma lista
	 */
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

//		Uma forma de converter a lista de category
//		List<CategoryDTO> listDto = new ArrayList<>();
//		for(Category cat : list) {
//			listDto.add(new CategoryDTO(cat));
//		}
//		return listDto;

	/*
	 * criar metodo para busca de id -> Optional foi criado para não trabalhar com
	 * valor nulo a partir do Java 8 -> .get do Optional obtem o objeto dentro do
	 * Optional -> .orElse Tentar obter o objeto que seria o get, se o objeto não
	 * estiver presente no Optional você pode retornar outra coisa um objeto
	 * alternativo, nulo exception -> orElseThrow() permite definir uma chamada de
	 * exception uma função lambda
	 */

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

}
