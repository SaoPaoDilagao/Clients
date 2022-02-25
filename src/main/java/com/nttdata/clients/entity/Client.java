package com.nttdata.clients.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Client {
	int id;
	String firstName;
	String lastName;
	String documentNumber;
	int type;
	boolean active;
	

}
