package com.nttdata.clients.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String firstName;
	private String lastName;
	private String documentNumber;
	private int profile;
	private int type;
	private boolean active;
}
