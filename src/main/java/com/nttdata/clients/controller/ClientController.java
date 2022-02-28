package com.nttdata.clients.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.clients.entity.Client;
import com.nttdata.clients.service.ClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping
	public Mono<ResponseEntity<Client>> createClient(@RequestBody Client client){
		
		return clientService.createClient(client).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Mono<Client>> searchClientById(@PathVariable("id") Integer id){
	//public ResponseEntity<Mono<Client>> searchClientById(@PathVariable("id") String id){
		
		Mono<Client> client = clientService.searchClientById(id);
		
		return new ResponseEntity<Mono<Client>>(client,client != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
		
	}
	
	
	@GetMapping("/lname/{lastName}")
	public ResponseEntity<Flux<Client>> searchClientByLastName(@PathVariable("lastName") String lastName){
		
		Flux<Client> client = clientService.searchClientByLastName(lastName);
		
		return new ResponseEntity<Flux<Client>>(client,client != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/document/{documentNumber}")
	public ResponseEntity<Mono<Client>> searchClientByDocumentNumber(@PathVariable("documentNumber") String documentNumber){
		
		Mono<Client> client = clientService.searchClientByDocument(documentNumber);
		
		return new ResponseEntity<Mono<Client>>(client,client != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
		
	}
	
	@PutMapping
	public Mono<ResponseEntity<Client>> updateClient(@RequestBody Client client){
		
		return clientService.updateClient(client).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Client>> deleteClient(@PathVariable("id") Integer id){
	//public Mono<ResponseEntity<Client>> deleteClient(@PathVariable("id") String id){
		
		return clientService.deleteClient(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
                
	}
	
}
