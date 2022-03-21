package com.nttdata.clients.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
  @NotBlank(message = "Field documentNumber must be required")
  private String documentNumber;
  @NotBlank(message = "Field firstName must be required")
  private String firstName;
  @NotBlank(message = "Field lastName must be required")
  private String lastName;
  @NotNull(message = "Field type must be required")
  @Min(value = 1, message = "Field type must be at least 1")
  @Max(value = 2, message = "Field type must be less than or equal to 2")
  private Integer type;
  @NotNull(message = "Field profile must be required")
  private Integer profile;
}
