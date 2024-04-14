package com.carros.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	@Autowired
	private CarroService service;

	private final MeterRegistry meterRegistry;

	public CarrosController(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@GetMapping()
	public ResponseEntity<List<CarroDTO>> get() {
		return ResponseEntity.ok(service.getCarros());
		//return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<CarroDTO> get(@PathVariable("id") Long id){
		Optional<CarroDTO> carro = service.getCarrosById(id);

		Counter counter = Counter.builder("quantidade_de_carros_pesquisados")
				.tag("carros_pesquisados", "carros")
				.description("Quantidade de carros pesquisados")
				.register(meterRegistry);

		counter.increment();


		return carro
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		
	/*	return carro.map(c -> ResponseEntity.ok(c))
				.orElse(ResponseEntity.notFound().build());*/
		
		// -- primeira forma
		/*return carro.isPresent() ?
				ResponseEntity.ok(carro.get()) :
				ResponseEntity.notFound().build();*/
		
		// -- segunda forma
		/*if(carro.isPresent()) {
			return ResponseEntity.ok(carro.get());
			
		}else {
			return ResponseEntity.notFound().build();	
		}*/
	}

	@GetMapping("/km")
	public ResponseEntity kmCarros(){
		Gauge.builder("km_rodado", () -> new Random().nextInt(100))
				.description("Mede a km do carro")
				.register(meterRegistry);
		return ResponseEntity.ok("Ok");
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getCarrosByTipo(@PathVariable("tipo") String tipo){
		List<CarroDTO> carros = service.getCarrosByTipo(tipo);
		 
		return carros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
	}
	
	@PostMapping
	public ResponseEntity<CarroDTO> post(@RequestBody Carro carro) {
		
		try{
			CarroDTO c = service.insert(carro);
			
			return c != null ?
					ResponseEntity.created(getUri(c.getId())).build() :
				    ResponseEntity.badRequest().build();
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
		//Carro c = service.insert(carro);
	
		//return "Carro salvo com sucesso: " + c.getId();
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CarroDTO> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		
		carro.setId(id);
		CarroDTO c = service.update(carro, id);
		
		return c != null ?
				ResponseEntity.ok(c) :
				ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<List<CarroDTO>> delete(@PathVariable("id") Long id) {
		
		return service.delete(id) ?
				ResponseEntity.ok(service.getCarros()) :
					ResponseEntity.notFound().build();
	}
	
}
