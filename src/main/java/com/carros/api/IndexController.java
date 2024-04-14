package com.carros.api;


import com.carros.domain.dto.CarroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class IndexController {
	
	@GetMapping()
	public ResponseEntity<String> get() {
		return ResponseEntity.ok("Api de carros");
	}
	
	
}
