package com.mx.EnvioDeCorreos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mx.EnvioDeCorreos.dao.ContactoDao;
import com.mx.EnvioDeCorreos.dto.Respuesta;
import com.mx.EnvioDeCorreos.entity.Contactos;

@Service
public class ContactoService {

	@Autowired
	ContactoDao dao;

	public Respuesta guardar(Contactos contacto) {
		Respuesta rs = new Respuesta();
		try {
			if (dao.existsById(contacto.getIdContacto())) {
				rs.setMensaje("No se lograra guardar, por que el id ya existe");
				rs.setSuccess(false);
				rs.setObj(contacto.getIdContacto());
				return rs;
			}
			for (Contactos cont : dao.findAll()) {
				if (cont.getEmail().equalsIgnoreCase(contacto.getEmail())) {
					rs.setMensaje("No se logro guardar, por que el email ya existe");
					rs.setSuccess(false);
					rs.setObj(contacto.getEmail());
					return rs;
				}
			}
			dao.save(contacto);
			rs.setMensaje("Se guardo con exito el contacto");
			rs.setSuccess(true);
			rs.setObj(contacto);
			return rs;
		} catch (Exception e) {
			System.out.println("Errror insesperado : " + e);
			rs.setMensaje("Sucedio un error inesperado..");
			rs.setSuccess(false);
			rs.setObj(contacto);
			return rs;
		}
	}

	public Respuesta editar(Contactos contacto) {
		Respuesta rs = new Respuesta();
		try {
			if(dao.existsById(contacto.getIdContacto())) {
				for(Contactos cont:dao.findAll()) {
					if(cont.getEmail().equalsIgnoreCase(contacto.getEmail())) {
						rs.setMensaje("No se puede editar por que ese email ya existe");
						rs.setSuccess(false);
						rs.setObj(contacto.getEmail());
						return rs;
					}
				}
				dao.save(contacto);
				rs.setMensaje("Se edito con exito el contacto.");
				rs.setSuccess(true);
				rs.setObj(contacto);
				return rs;
			}
			rs.setMensaje("No se logro editar por que su id no existe.");
			rs.setSuccess(true);
			rs.setObj(contacto.getIdContacto());
			return rs;
		} catch (Exception e) {
			System.out.println("Error inesperado : "+e);
			rs.setMensaje("Sucedio un error inesperado..");
			rs.setSuccess(false);
			rs.setObj(contacto);
			return rs;
		}
	}
	
	public Respuesta eliminar(Contactos contacto) {
		Respuesta rs = new Respuesta();
		try {
			if(dao.existsById(contacto.getIdContacto())) {
				for(Contactos cont:dao.findAll()) {
					if(cont.getEmail().equalsIgnoreCase(contacto.getEmail())) {
						rs.setMensaje("No se puede eliminar por que ese email ya existe");
						rs.setSuccess(false);
						rs.setObj(contacto.getEmail());
						return rs;
					}
				}
				dao.delete(contacto);
				rs.setMensaje("Se elimino con exito el contacto.");
				rs.setSuccess(true);
				rs.setObj(contacto);
				return rs;
			}
			rs.setMensaje("No se logro eliminar por que su id no existe.");
			rs.setSuccess(true);
			rs.setObj(contacto.getIdContacto());
			return rs;
		} catch (Exception e) {
			System.out.println("Error inesperado : "+e);
			rs.setMensaje("Sucedio un error inesperado..");
			rs.setSuccess(false);
			rs.setObj(contacto);
			return rs;
		}
	}
	
	public ResponseEntity<Contactos> buscar(String nombre, String apellido, String email, String sexo){	
		System.out.println("CONTACNTO : NOMBRE : "+nombre+"APELLIDO : "+apellido+"EMAIL : "+email+"SEXO: "+sexo);
		if(dao.findAll().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		for(Contactos cont : dao.findAll()) {
			if(cont.getNombre().equalsIgnoreCase(nombre) && cont.getApellido().equalsIgnoreCase(apellido) && cont.getEmail().equalsIgnoreCase(email) && cont.getSexo().equalsIgnoreCase(sexo)) {
				return ResponseEntity.status(HttpStatus.OK).body(cont);
			}
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	public ResponseEntity<Contactos> buscarPorId(int id){	
		Contactos contacto = dao.findById(id).orElse(null);
		if(contacto == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(contacto);
	}
	
	public ResponseEntity<List<Contactos>> listar(){
		if(dao.findAll().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(dao.findAll());
	}
}
