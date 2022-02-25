package com.nttdata.clients.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.clients.entity.Client;


@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Integer>{

}
