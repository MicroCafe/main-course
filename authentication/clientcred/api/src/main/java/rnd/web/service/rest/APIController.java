package rnd.web.service.rest;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

	@ResponseBody
	@GetMapping(value = "/hello")
	public ResponseEntity<String> hello() throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(new String("Hello World"));
	}

}
