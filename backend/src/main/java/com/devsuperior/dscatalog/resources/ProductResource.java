package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	/*
	 * Consulta geral Alteração de retorno List para Page
	 * 
	 * @RequestParam significa que é um parametro opcional - pagina, quantidade de
	 * registros, nome de ordenação e direção ASC OU DESC
	 */

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
		
		// PARAMETROS: page, size, sort

		Page<ProductDTO> list = service.findAllPaged(pageable);

		return ResponseEntity.ok().body(list);

	}

	// consulta por ID, anotação @PathVariable reconhecer o parametro de argumento
	// da rota.
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody @Valid  ProductDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid  ProductDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
