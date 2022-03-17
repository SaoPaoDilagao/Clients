package com.nttdata.clients;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nttdata.clients.controller.ClientController;
import com.nttdata.clients.dto.request.ClientRequest;
import com.nttdata.clients.entity.Client;
import com.nttdata.clients.service.ClientService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)
class ClientsApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ClientService clientService;

	@Test
	void testListByLastName() {
		Client client = new Client();
		client.setFirstName("Juan");
		client.setLastName("Perez");

		Client client1 = new Client();
		client1.setFirstName("Maria");
		client1.setLastName("Perez");

		var fluxClient = Flux.just(client, client1);
		when(clientService.searchClientByLastName("Perez")).thenReturn(fluxClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/lname/Perez")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Client.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.expectNext(client)
				.expectNext(client1)
				.verifyComplete();
	}

	@Test
	void testGetById() {
		ObjectId id = new ObjectId();

		Client client = new Client();
		client.setId(id);
		client.setFirstName("Juan");
		client.setLastName("Perez");

		var monoClient = Mono.just(client);
		when(clientService.searchClientById(id.toString())).thenReturn(monoClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/id/" + id)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Client.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.expectNextMatches(x -> x.getFirstName().equals("Juan"))
				.verifyComplete();
	}

	@Test
	void testGetByClientByDocumentNumber() {
		Client client = new Client();
		client.setDocumentNumber("00000001");
		client.setFirstName("Juan");
		client.setLastName("Perez");

		var monoClient = Mono.just(client);
		when(clientService.searchClientByDocument("00000001")).thenReturn(monoClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/document/00000001")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Client.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.expectNextMatches(x -> x.getFirstName().equals("Juan"))
				.verifyComplete();
	}

	@Test
	void testCreate() {
		Client client = new Client();
		client.setDocumentNumber("71489282");
		client.setFirstName("Juan");
		client.setLastName("Perez");
		client.setType(1);
		client.setProfile(1);

		var monoAccount = Mono.just(client);
		when(clientService.createClient(client)).thenReturn(monoAccount);

		var responseBody = webTestClient
				.post()
				.uri("/clients")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(Mono.just(client), Client.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void testUpdate() {
		ObjectId id = new ObjectId();

		Client client = new Client();
		client.setId(id);
		client.setDocumentNumber("71489282");
		client.setFirstName("Juan");
		client.setLastName("Perez");
		client.setType(1);
		client.setProfile(1);

		var monoClient = Mono.just(client);
		when(clientService.updateClient(client)).thenReturn(monoClient);

		var responseBody = webTestClient
				.put()
				.uri("/clients/id/" + id)
				.contentType(APPLICATION_JSON)
				.body(Mono.just(client), Client.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void testDelete() {
		ObjectId id = new ObjectId();

		Client client = new Client();
		client.setId(id);
		client.setDocumentNumber("71489282");
		client.setFirstName("Juan");
		client.setLastName("Perez");
		client.setType(1);
		client.setProfile(1);

		var monoClient = Mono.just(client);
		when(clientService.updateClient(client)).thenReturn(monoClient);

		var responseBody = webTestClient
				.delete()
				.uri("/clients/" + id)
				.exchange()
				.expectStatus().isOk();
	}
}
