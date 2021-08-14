package org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Tema;
import org.generation.blogpessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> GetById(@PathVariable Long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
		}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> GetByName(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(nome));
	
	}

	@PostMapping("/novotema")
	public ResponseEntity<Tema> post (@RequestBody Tema tema)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}
	
	@PutMapping("/alterartema")
	public ResponseEntity<Tema> put (@RequestBody Tema tema)
	{
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(tema));
	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> delete (@PathVariable Long id)
	{
		Optional<Tema> temaExistente = repository.findById(id);
		if(temaExistente.isPresent())
		{
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("ID DELETADO!!!");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body("ID NÃO ENCONTRADO!!!");
		}
	}

}


