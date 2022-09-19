package com.xptorg.crud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xptorg.crud.dto.ClientDTO;
import com.xptorg.crud.entities.Client;
import com.xptorg.crud.repository.ClientRepository;
import com.xptorg.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = clientRepository.findAll(pageRequest);

		return clients.map(client -> new ClientDTO(client));

	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optional = clientRepository.findById(id);
		Client entity = optional.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client(); 
		toEntity(dto, entity);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {

		try {
			Client entity = clientRepository.getReferenceById(id);
			toEntity(dto, entity);

			entity = clientRepository.save(entity);

			return new ClientDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente com o id " + id + " não foi encontrado");
		}

	}

	@Transactional
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente com o id " + id  + " não encontrado.");
		}

	}

	private Client toEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
		entity.setBirthDate(dto.getBirthDate());
		return entity;
	}

}
