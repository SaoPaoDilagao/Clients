package com.nttdata.clients.service;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.clients.entity.Client;
import com.nttdata.clients.repository.ClientRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService{
	
	private static final Logger logger_consola = LoggerFactory.getLogger("consola");
	private static final Logger logger_file = LoggerFactory.getLogger("clients_log");
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public Mono<Client> createClient(Client client) {
		
		client.setActive(true);
		
		return clientRepository.findByDocumentNumber(client.getDocumentNumber())
				.switchIfEmpty(clientRepository.save(client)
						.map(x-> {
							logger_file.debug("Created a new id= {} for the client with document number= {}", client.getId(), client.getDocumentNumber());
							logger_consola.info("Created a new id= {} for the client with document number= {}", client.getId(), client.getDocumentNumber());
							return x;})
						);
		
	}

	@Override
	public Mono<Client> searchClientById(String id) {
		return clientRepository.findById(new ObjectId(id));
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

		return clientRepository.findById(client.getId())
                .map(data -> {
                    
                    clientRepository.save(client).subscribe();
                    
                    logger_file.debug("Updated the client with id= {}", client.getId());
            		logger_consola.info("Updated the client with id= {}", client.getId());
                    
                    return client;
                });

	}

	@Override
	public Mono<Client> deleteClient(String id) {
		
		return clientRepository.findById(new ObjectId(id))
                .map(client -> {
                    client.setActive(false);
                    clientRepository.save(client).subscribe();
                    
                    logger_file.debug("Deleted the client with id= {}", id);
                    logger_consola.info("Deleted the client with id= {}", id);
                    
                    return client;
                });
	}

}
