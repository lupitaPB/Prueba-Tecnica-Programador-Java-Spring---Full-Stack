package com.mx.EnvioDeCorreos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.EnvioDeCorreos.dto.Respuesta;
import com.mx.EnvioDeCorreos.entity.Contactos;
import com.mx.EnvioDeCorreos.service.ContactoService;

@RestController
@RequestMapping("contactos")
@CrossOrigin
public class ContactoController {

	@Autowired
	ContactoService service;
	
	@PostMapping("guardar")
	public Respuesta guardar(@RequestBody Contactos contacto) {
		return service.guardar(contacto);
	}
	
	@PostMapping("editar")
	public Respuesta editar(@RequestBody Contactos contacto) {
		return service.editar(contacto);
	}
	
	@PostMapping("eliminar")
	public Respuesta eliminar(@RequestBody Contactos contacto) {
		return service.eliminar(contacto);
	}
	
	@PostMapping("buscar")
	public ResponseEntity<Contactos> buscar(@RequestParam String nombre,
			@RequestParam String apellido,
			@RequestParam String email,
			@RequestParam String sexo){
		return service.buscar(nombre, apellido, email, sexo);
	}
	@GetMapping("buscarId/{id}")
	public ResponseEntity<Contactos> buscarId(@PathVariable int id){
		return service.buscarPorId(id);
	}
	@GetMapping("listar")
	public ResponseEntity<List<Contactos>> listar(){
		return service.listar();
	}
	
	
}
