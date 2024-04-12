package com.carros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;



@SpringBootTest
class CarrosApplicationTests {
	
	@Autowired
	private CarroService service;

	@Test
	public void test1() {
		Carro carro = new Carro();
		
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.insert(carro);
		
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		//buscar o objeto
		
		Optional<CarroDTO> op = service.getCarrosById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		//deletar objeto
		service.delete(id);
		
		//verificar se deletou
		assertFalse(service.getCarrosById(id).isPresent());
	}

}
