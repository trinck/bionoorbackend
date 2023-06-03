package com.bionoor.api.web;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.models.Media;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")

public class RestMedia {

	
	
	
	
	
	
	
	
	
	@Data
	@NoArgsConstructor
	public class OutputMedia{
		
		    private Long id;

		    private String name;
		 
			  
		    private String url;
		    
		    private Long size;
		    
		    private String dimensions;
		    
		public  OutputMedia(Media media) {
			
			this.id = media.getId();
			this.name = media.getName();
			this.url = media.getUrl();
			this.size = media.getSize();
			this.dimensions = media.getDimensions();
			
		}
	}
}
