package com.nttdata.clients.service;

import com.nttdata.clients.entity.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
	
	Mono<Client> createClient(Client client);
	Mono<Client> searchClientById(String id);
	Flux<Client> searchClientByLastName(String lastName);
	Mono<Client> searchClientByDocument(String documentNumber);
	Mono<Client> updateClient(Client e);
	Mono<Client> deleteClient(String id);

}
