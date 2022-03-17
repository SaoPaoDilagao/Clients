package com.nttdata.clients.controller;

import com.nttdata.clients.dto.request.ClientRequest;
import com.nttdata.clients.entity.Client;
import com.nttdata.clients.service.ClientService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

/**
 * RestController for client service.
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @PostMapping
  public Mono<Client> createClient(@Valid @RequestBody ClientRequest request) {
    Client client = new Client(request);
    return clientService.createClient(client);
  }

  @GetMapping("/id/{id}")
  public Mono<Client> searchClientById(@PathVariable("id") String id) {
    return clientService.searchClientById(id);
  }

  @GetMapping("/lname/{lastName}")
  public Flux<Client> searchClientByLastName(@PathVariable("lastName") String lastName) {
    return clientService.searchClientByLastName(lastName);
  }

  @GetMapping("/document/{documentNumber}")
  public Mono<Client> searchClientByDocumentNumber(
      @PathVariable("documentNumber") String documentNumber) {
    return clientService.searchClientByDocument(documentNumber);
  }

  /**
   * Update client information.
   */
  @PutMapping("/id/{id}")
  public Mono<Client> updateClient(@PathVariable String id,
                                                   @RequestBody ClientRequest request) {
    Client client = new Client(request);
    client.setId(new ObjectId(id));
    return clientService.updateClient(client);
  }

  /**
   * Delete client by id.
   */
  @DeleteMapping("/{id}")
  public Mono<Client> deleteClient(@PathVariable("id") String id) {
    return clientService.deleteClient(id);
  }
}
