package com.mx.EnvioDeCorreos.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.mx.EnvioDeCorreos.dto.EstadoCorreo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EVENTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoCorreo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvento;
	private LocalDateTime fecha;
	private String titulo;
	private String contenido;
	@Enumerated(EnumType.STRING)
	private EstadoCorreo estado;
	@ManyToMany
	private List<Contactos> listaContactos;
}
