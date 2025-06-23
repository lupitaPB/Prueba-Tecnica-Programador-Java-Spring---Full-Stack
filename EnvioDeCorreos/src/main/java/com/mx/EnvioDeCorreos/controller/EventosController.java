package com.mx.EnvioDeCorreos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.EnvioDeCorreos.dto.Respuesta;
import com.mx.EnvioDeCorreos.entity.EventoCorreo;
import com.mx.EnvioDeCorreos.service.EventoCorreoService;

@RestController
@RequestMapping("eventos")
@CrossOrigin
public class EventosController {

	@Autowired
	EventoCorreoService service;
	
	@PostMapping("crear")
	public Respuesta guardar(@RequestBody EventoCorreo evento) {
		return service.crear(evento);
	}
	@PostMapping("editarEvento")
	public Respuesta editar(@RequestBody EventoCorreo evento) {
		return service.editar(evento);
	}
	@PostMapping("eliminarEvento")
	public Respuesta eliminar(@RequestBody EventoCorreo evento) {
		return service.eliminar(evento);
	}
	@PostMapping("cancelarEvento")
	public Respuesta cancelar(@RequestBody EventoCorreo evento) {
		return service.cancelar(evento);
	}
	@GetMapping("listarEvento")
	public List<EventoCorreo>listar(){
		return service.listar();
	}
}
