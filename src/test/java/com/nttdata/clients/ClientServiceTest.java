package com.nttdata.clients;

import com.nttdata.clients.entity.Client;
import com.nttdata.clients.exceptions.customs.CustomInformationException;
import com.nttdata.clients.repository.ClientRepository;
import com.nttdata.clients.service.ClientServiceImpl;
import com.nttdata.clients.utilities.Constants;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ClientServiceTest {
  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientServiceImpl clientService;

  @Test
  void testListByLastName() {
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setLastName("Perez");

    Client client1 = new Client();
    client1.setDocumentNumber("00000002");
    client1.setFirstName("Juan");
    client1.setLastName("Perez");

    var fluxClient = Flux.just(client, client1);
    when(clientRepository.findByLastName("Perez")).thenReturn(fluxClient);

    var list = clientService.searchClientByLastName("Perez");
    StepVerifier
        .create(list)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("00000001", ac.getDocumentNumber());
        })
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("00000002", ac.getDocumentNumber());
        })
        .verifyComplete();
  }

  @Test
  void testGetById() {
    ObjectId id = new ObjectId();

    Client client = new Client();
    client.setId(id);
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setLastName("Perez");

    var monoClient = Mono.just(client);
    when(clientRepository.findById(id)).thenReturn(monoClient);

    var data = clientService.searchClientById(id.toString());
    StepVerifier
        .create(data)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("00000001", ac.getDocumentNumber());
        })
        .verifyComplete();
  }

  @Test
  void testGetByDocument() {
    Client client = new Client();
    client.setDocumentNumber("00000001");
    client.setFirstName("Juan");
    client.setLastName("Perez");

    var monoClient = Mono.just(client);
    when(clientRepository.findByDocumentNumber("00000001")).thenReturn(monoClient);

    var data = clientService.searchClientByDocument("00000001");
    StepVerifier
        .create(data)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("Juan", ac.getFirstName());
          Assertions.assertEquals("Perez", ac.getLastName());
        })
        .verifyComplete();
  }

  @Test
  void testCreateTypePersonalProfileRegular() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.PERSONAL, Constants.ClientProfile.REGULAR, true);

    var monoClient = Mono.just(client);
    when(clientRepository.findByDocumentNumber("71489282")).thenReturn(Mono.empty());
    when(clientRepository.save(client)).thenReturn(monoClient);

    var result = clientService.createClient(client);
    StepVerifier
        .create(result)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("71489282", ac.getDocumentNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateTypeBusinessProfilePyme() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.BUSINESS, Constants.ClientProfile.PYME, true);

    var monoClient = Mono.just(client);
    when(clientRepository.findByDocumentNumber("71489282")).thenReturn(Mono.empty());
    when(clientRepository.save(client)).thenReturn(monoClient);

    var result = clientService.createClient(client);
    StepVerifier
        .create(result)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("71489282", ac.getDocumentNumber());
        })
        .verifyComplete();
  }

  @Test
  void testCreateTypePersonalProfilePyme() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.PERSONAL, Constants.ClientProfile.PYME, true);

    var monoClient = Mono.just(client);
    when(clientRepository.findByDocumentNumber("71489282")).thenReturn(Mono.empty());
    when(clientRepository.save(client)).thenReturn(monoClient);

    var result = clientService.createClient(client);
    StepVerifier
        .create(result)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("A personal type customer cannot have this profile"))
        .verify();
  }

  @Test
  void testCreateTypeBusinessProfileVip() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.BUSINESS, Constants.ClientProfile.VIP, true);

    var monoClient = Mono.just(client);
    when(clientRepository.findByDocumentNumber("71489282")).thenReturn(Mono.empty());
    when(clientRepository.save(client)).thenReturn(monoClient);

    var result = clientService.createClient(client);
    StepVerifier
        .create(result)
        .expectErrorMatches(throwable -> throwable instanceof CustomInformationException &&
            throwable.getMessage().equals("A business type customer cannot have this profile"))
        .verify();
  }

  @Test
  void testUpdate() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.BUSINESS, Constants.ClientProfile.VIP, true);

    var monoClient = Mono.just(client);
    when(clientRepository.findById(id)).thenReturn(monoClient);
    when(clientRepository.save(client)).thenReturn(Mono.empty());

    var result = clientService.updateClient(client);
    StepVerifier
        .create(result)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("71489282", ac.getDocumentNumber());
        })
        .verifyComplete();
  }

  @Test
  void testDelete() {
    ObjectId id = new ObjectId();
    Client client = new Client(id, "71489282", "Juan",
        "Perez", Constants.ClientType.BUSINESS, Constants.ClientProfile.VIP, false);

    var monoClient = Mono.just(client);
    when(clientRepository.findById(id)).thenReturn(monoClient);
    when(clientRepository.save(client)).thenReturn(Mono.empty());

    var result = clientService.deleteClient(id.toString());
    StepVerifier
        .create(result)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertFalse(ac.isActive());
        })
        .verifyComplete();
  }
}
