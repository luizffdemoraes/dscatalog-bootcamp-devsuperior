package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * Método para retornar todos registros do banco de dados. Garante que o metodo
	 * vai executar uma transação com banco de dados e não vai travar evita um lock
	 * no banco de dados Implementação com expressão Lambda stream converter sua
	 * coleção, stream um recurso para trabalhar com funções de alta ordem inclusive
	 * lambda para aplicar transformações map aplica um função transforma cada
	 * elemento da lista em outra coisa ela aplica uma função a cada elemento.
	 * collec transforma uma stream em uma lista alteração do metodo de listagem
	 * para paginação o page já é um stream
	 */

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);
		return list.map(x -> new ProductDTO(x));
	}

// Conversão de lista de Categoria para uma Lista de Categoria Dto com função Lambda
// return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
//	
//		Uma forma de converter a lista de category
//		List<ProductDTO> listDto = new ArrayList<>();
//		for(Product cat : list) {
//			listDto.add(new ProductDTO(cat));
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
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	/*
	 * Diferenda de findById e getOne. findById: Efetiva o acesso ao banco de dados
	 * ele traz do banco de dados. getOne: Ele não toca no banco de dados ele
	 * instancia um objeto provisorio e com o id do objeto so quando mandar salvar
	 * ele acessa o banco.
	 */

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	

	// captura de exception para isso não colocamos @Transactional
	// captura exception de integridade quando deletamos algo que não podemos
	public void delete(Long id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
	}

}
