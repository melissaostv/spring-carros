package com.carros.domain;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.domain.dto.CarroDTO;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
		
		return rep.findAll().stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());
		
		// List<CarroDTO> list = new ArrayList<>();
		// for(Carro c : carros) {
		//		list.add(new CarroDTO(c));
		// }
		
	}
	
	public Optional<CarroDTO> getCarrosById(Long id) {
		return rep.findById(id).map(CarroDTO::create);
		
		//Optional<Carro> carro = rep.findById(id);
		//if(carro.isPresent()){
		//	return Optional.of(new CarroDTO(carro.get()));
		//}else{
		//	return null;
		//}
	}
	
	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo).stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());
	}
	
	

	public CarroDTO insert(Carro carro) {
		// TODO Auto-generated method stub
		Assert.isNull(carro.getId(), "Não foi possível inserir registro");
		return CarroDTO.create(rep.save(carro));
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Não foi possível atualizar o registro.");
		
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id: " + db.getId());
			
			rep.save(db);
			return CarroDTO.create(db);
		}else {
			return null;
		}
		
		
		/*getCarrosById(id).map(db -> {
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			rep.save(db);
			return db;
		}).orElseThrow(() -> new RuntimeException("Não foi possível atualizar o registro."));*/
	}

	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		
		return getCarrosById(id).map(c -> {
			rep.deleteById(id);
			return true;
		}).orElse(false);
		
//		if(getCarrosById(id).isPresent()) {
//			rep.deleteById(id);
//		}
		
	}

}
