package com.packt.cardatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;

@RestController
public class CarController {
	private final CarRepository repository;

	public CarController(CarRepository repository)
	{
		this.repository = repository;
	}

	@GetMapping("/cars")
	public Iterable<Car> getCars() {
		return repository.findAll();
	}

	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cars")
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		Car savedCar = repository.save(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
	}

	@PutMapping("/cars/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		updatedCar.setId(id);
		Car savedCar = repository.save(updatedCar);
		return ResponseEntity.ok(savedCar);
	}

	@DeleteMapping("/cars/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
