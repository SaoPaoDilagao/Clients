package com.nttdata.clients.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nttdata.clients.dto.request.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Client object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private int type;
  private int profile;
  private boolean active;

  /**
   * Return client from an ClientRequest.
   *
   * @param request ClientRequest object
   */
  public Client(ClientRequest request) {
    documentNumber = request.getDocumentNumber();
    firstName = request.getFirstName();
    lastName = request.getLastName();
    type = request.getType();
    profile = request.getProfile();
    active = true;
  }
}
