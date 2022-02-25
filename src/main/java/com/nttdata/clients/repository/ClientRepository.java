package com.nttdata.clients.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.clients.entity.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Integer>{
	
	Flux<Client> findByLastName(String lastName);
	Mono<Client> findByDocumentNumber(String Document);

}
