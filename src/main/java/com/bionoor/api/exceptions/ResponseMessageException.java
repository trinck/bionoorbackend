package com.bionoor.api.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMessageException  implements Serializable{

	
	private String message;
	
	private LocalDateTime dateTime;
	
	private int  Status;
}
