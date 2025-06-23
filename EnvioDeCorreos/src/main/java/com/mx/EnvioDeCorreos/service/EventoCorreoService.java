package com.mx.EnvioDeCorreos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.EnvioDeCorreos.dao.EventoCorreoDao;
import com.mx.EnvioDeCorreos.dto.EstadoCorreo;
import com.mx.EnvioDeCorreos.dto.Respuesta;
import com.mx.EnvioDeCorreos.entity.Contactos;
import com.mx.EnvioDeCorreos.entity.EventoCorreo;

@Service
public class EventoCorreoService {

	@Autowired
	EventoCorreoDao dao;
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	RestTemplate restTemplate;
	
	
	
	
	public Respuesta crear(EventoCorreo evento) {
		Respuesta rs = new Respuesta();
		List<Contactos> listaNueva = new ArrayList<>();
			for (Contactos cont : evento.getListaContactos()) {
				Contactos contacto = restTemplate.getForObject(
						"http://localhost:9000/contactos/buscarId/" + cont.getIdContacto(), Contactos.class);
				if (contacto == null) {
					rs.setMensaje("No es posible crear el evento por que un contacto no existe");
					rs.setSuccess(false);
					rs.setObj(cont);
					return rs;
				}
				listaNueva.add(contacto);
			}
		
		evento.setListaContactos(listaNueva);
		dao.save(evento);
		rs.setMensaje("Evento guardado");
		rs.setSuccess(true);
		rs.setObj(evento);
		return rs;
	}

	public Respuesta editar(EventoCorreo evento) {
		Respuesta rs = new Respuesta();
		if (evento.getEstado() == EstadoCorreo.PENDIENTE) {
			List<Contactos> listaNueva = new ArrayList<>();
			for (Contactos cont : evento.getListaContactos()) {
				Contactos contacto = restTemplate.getForObject(
						"http://localhost:9000/contactos/buscarId/" + cont.getIdContacto(), Contactos.class);
				if (contacto == null) {
					rs.setMensaje("No es posible crear el evento por que un contacto no existe");
					rs.setSuccess(false);
					rs.setObj(cont);
					return rs;
				}
				listaNueva.add(contacto);
			}
		
		evento.setListaContactos(listaNueva);
			dao.save(evento);
			rs.setMensaje("Evento editado");
			rs.setSuccess(true);
			rs.setObj(evento);
			return rs;
		}
		rs.setMensaje("El evento no se pudo editar por que ya ha sido enviado");
		rs.setSuccess(false);
		rs.setObj(evento);
		return rs;
	}
	public Respuesta cancelar(EventoCorreo evento) {
		Respuesta rs = new Respuesta();
		if(evento.getEstado() == EstadoCorreo.PENDIENTE) {
			evento.setEstado(EstadoCorreo.CANCELADO);
			rs.setMensaje("Evento ha sido cancelado");
			rs.setSuccess(true);
			rs.setObj(evento);
			return rs;
		}
		rs.setMensaje("El evento no se pudo cancelar por que su estado no es pendiente");
		rs.setSuccess(false);
		rs.setObj(evento);
		return rs;
	}
	public Respuesta eliminar(EventoCorreo evento) {
		Respuesta rs = new Respuesta();
		if(evento.getEstado() == EstadoCorreo.FINALIZADO) {
			dao.delete(evento);
			rs.setMensaje("Evento eliminado");
			rs.setSuccess(true);
			rs.setObj(evento);
			return rs;
		}
		rs.setMensaje("El evento no se pudo eliminar por que no ha sido enviado");
		rs.setSuccess(false);
		rs.setObj(evento);
		return rs;
	}
	
	public List<EventoCorreo> listar(){
		List<EventoCorreo> listaFiltrada = new ArrayList<>();
		for(EventoCorreo evento : dao.findAll()) {
			if(evento.getEstado() == EstadoCorreo.PENDIENTE || evento.getEstado() == EstadoCorreo.FINALIZADO) {
				listaFiltrada.add(evento);
			}
		}
		return listaFiltrada;
	}
	
	@Scheduled(fixedRate = 60000) // cada minuto
	public void procesarEventosPendientes() {
	    List<EventoCorreo> pendientes = dao.findEventosConContactosByEstadoAndFechaBefore(EstadoCorreo.PENDIENTE,LocalDateTime.now());
	    for (EventoCorreo evento : pendientes) {
	        enviarCorreo(evento);
	        evento.setEstado(EstadoCorreo.FINALIZADO);
	        dao.save(evento);
	    }
	}
	
	public void enviarCorreo(EventoCorreo evento) {
	    for (Contactos contacto : evento.getListaContactos()) {
	        SimpleMailMessage mensaje = new SimpleMailMessage();
	        mensaje.setTo(contacto.getEmail());
	        mensaje.setSubject(evento.getTitulo());
	        mensaje.setText(evento.getContenido());
	        mensaje.setFrom("lupitapb07010@gmail.com"); // Remitente
	        mailSender.send(mensaje);
	        System.out.println("CORREO ENVIADO....");
	    }
	}
}
