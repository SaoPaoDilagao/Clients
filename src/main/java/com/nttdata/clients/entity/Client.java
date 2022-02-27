package com.nttdata.clients.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
	//@Id
	//private ObjectId id;
	private int id;
	private String firstName;
	private String lastName;
	private String documentNumber;
	private int type;
	private boolean active;
}
