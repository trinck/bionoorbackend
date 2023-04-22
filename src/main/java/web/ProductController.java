package web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ProductController {

	
	@RequestMapping(value = "users")
	public String users() {
		
		return "vous communiquez bien avec bionoor api/users";
	}
	
}

