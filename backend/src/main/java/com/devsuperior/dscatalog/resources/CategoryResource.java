package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

/*
 * Pré processamento ao compilar fornecendo a estrutura para funcionar corretamente.
 * Definição de rota.
 * No java alguns tipos são interfaces exemplo List. 
 * Não pode ser instanciada uma interface, no caso 
 * devemos instanciar uma Classe que implemente a interface exemplo ArrayList.
 * ResponseEntity.ok() Resposta 200
 * .body defini o corpo da resposta
 * 
 */

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);

	}
	
	//consulta por ID, anotação @PathVariable reconhecer o parametro de argumento da rota. 
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}


}
