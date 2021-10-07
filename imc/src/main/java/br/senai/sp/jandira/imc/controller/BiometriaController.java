package br.senai.sp.jandira.imc.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senai.sp.jandira.imc.model.Biometria;
import br.senai.sp.jandira.imc.model.Usuario;
import br.senai.sp.jandira.imc.repository.BiometriaRepository;

@RestController
@RequestMapping("/imc/biometrias")
public class BiometriaController {
	
	@Autowired
	private BiometriaRepository biometriaRepository;
	
	@GetMapping
	public List<Biometria> listarBiometria() {
		return biometriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Biometria> buscarPeloId(@PathVariable Long id) {
		
		Optional<Biometria> biometria = biometriaRepository.findById(id);
		Biometria biometriaEncontrada = new Biometria();
		
		if (biometria.isPresent()) {
			LocalDate x = biometria.get().getDataPesagem();
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			System.out.println(x.format(df));
			biometriaEncontrada = biometria.get();
			return ResponseEntity.ok(biometriaEncontrada);
		} else {
			return ResponseEntity.notFound().build();
		}
		
//		return biometria.isPresent() ? 
//				ResponseEntity.notFound().build() : 
//				ResponseEntity.ok(biometria.get());
	}
	
	@PostMapping
	public ResponseEntity<Biometria> salvarBiometria(@RequestBody Biometria biometria) {
		
		Biometria biometriaSalva = biometriaRepository.save(biometria);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(biometriaSalva.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(biometriaSalva);
		
	}
	
	@GetMapping("/usuarios/{id}")
	public List<Biometria> listarBiometriaPorUsuario(@PathVariable Long id) {
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		return biometriaRepository.findByUsuario(usuario);
		
	}
	
	// ***************************************
	// ***************** MELHORAR A URL **********
	// ****************************************************
	@GetMapping("/usuarios/{id}/bio")
	public ResponseEntity<Biometria> listar(@PathVariable Long id) {
		List<Biometria> bio = biometriaRepository.findLastPeso(id);
		if (!bio.isEmpty()) {
			return ResponseEntity.ok(bio.get(bio.size() - 1));
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}

}
