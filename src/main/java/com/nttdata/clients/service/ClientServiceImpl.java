package com.nttdata.clients.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.clients.entity.Client;
import com.nttdata.clients.repository.ClientRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public Mono<Client> createClient(Client client) {
		
		Mono<Client> dummy = clientRepository.findByDocumentNumber(client.getDocumentNumber());
		
		if(dummy != null)
			return null;
		
		client.setActive(true);
		
		return clientRepository.save(client);
	}

	@Override
	public Mono<Client> searchClientById(Integer id) {
		return clientRepository.findById(id);
	}

	@Override
	public Flux<Client> searchClientByLastName(String lastName) {
		
		return clientRepository.findByLastName(lastName);
	}

	@Override
	public Mono<Client> searchClientByDocument(String documentNumber) {
		
		return clientRepository.findByDocumentNumber(documentNumber);
	}

	@Override
	public Mono<Client> updateClient(Client client) {
		
		Mono<Client> dummy = clientRepository.findById(client.getId());
		
		if(dummy == null)
			return null;
		
		return clientRepository.save(client);
	}

	@Override
	public Mono<Void> deleteClient(Integer id) {
		
//		Mono<Client> dummy = clientRepository.findById(id);
		
//		if(dummy == null)
//			return dummy;
//		
//		Client client = dummy.block();
//		client.setActive(false);
//		
//		return clientRepository.save(client);
		
		return clientRepository.deleteById(id);
	}

}
