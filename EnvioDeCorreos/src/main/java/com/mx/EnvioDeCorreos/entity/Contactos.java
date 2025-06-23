package com.mx.EnvioDeCorreos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name ="CONTACTOS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contactos {

	@Id
	private int idContacto;
	private String nombre;
	private String apellido;
	private String email;
	private String sexo;
	private String direccion;
}
