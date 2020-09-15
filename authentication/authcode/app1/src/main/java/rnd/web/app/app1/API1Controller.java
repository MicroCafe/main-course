package rnd.web.app.app1;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class API1Controller {

	@ResponseBody
	@GetMapping(value = "/api1")
	public ResponseEntity<String> hello() throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(new String("Hello API1"));
	}

}
