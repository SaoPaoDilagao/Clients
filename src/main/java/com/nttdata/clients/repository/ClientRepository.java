package com.nttdata.clients.repository;

import com.nttdata.clients.entity.Client;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Client repository.
 */
@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, ObjectId> {

  Flux<Client> findByLastName(String lastName);

  Mono<Client> findByDocumentNumber(String document);

}
