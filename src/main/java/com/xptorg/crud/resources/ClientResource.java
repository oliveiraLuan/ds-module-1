package com.xptorg.crud.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.xptorg.crud.dto.ClientDTO;
import com.xptorg.crud.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAllPaged(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "6") Integer linesPerPage,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction,
			@RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<ClientDTO> dto = clientService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {

		ClientDTO dto = clientService.findById(id);

		return ResponseEntity.ok().body(dto);

	}

	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto) {

		dto = clientService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO dto, @PathVariable Long id) {
		dto = clientService.update(dto, id);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);

		return ResponseEntity.noContent().build();

	}

}
