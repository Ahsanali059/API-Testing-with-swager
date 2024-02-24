package com.packt.cardatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@RestController
public class OwnerController {

    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("/owners")
    public Iterable<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ownerRepository.findById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/owners")
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody Owner updatedOwner) {
        if (!ownerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedOwner.setOwnerid(id);
        Owner savedOwner = ownerRepository.save(updatedOwner);
        return ResponseEntity.ok(savedOwner);
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        if (!ownerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ownerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
