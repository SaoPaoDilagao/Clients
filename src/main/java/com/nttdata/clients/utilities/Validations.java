package com.nttdata.clients.utilities;

import com.nttdata.clients.entity.Client;
import com.nttdata.clients.exceptions.customs.CustomInformationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Validation class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
  /**
   * Validate fields of client.
   *
   * @param client Account object
   */
  public static Mono<Client> validateCreateClient(Client client) {
    if (client.getType() == Constants.ClientType.PERSONAL) {
      if (client.getProfile() == Constants.ClientProfile.PYME) {
        throw new CustomInformationException("A personal type customer cannot have this profile");
      }
    } else {
      if (client.getProfile() == Constants.ClientProfile.VIP) {
        throw new CustomInformationException("A business type customer cannot have this profile");
      }
    }

    return Mono.just(client);
  }
}
