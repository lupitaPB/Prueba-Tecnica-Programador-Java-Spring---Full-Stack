package com.mx.EnvioDeCorreos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mx.EnvioDeCorreos.dto.EstadoCorreo;
import com.mx.EnvioDeCorreos.entity.EventoCorreo;

import java.time.LocalDateTime;
import java.util.List;


public interface EventoCorreoDao extends JpaRepository<EventoCorreo, Integer>{

	@Query("SELECT e FROM EventoCorreo e JOIN FETCH e.listaContactos WHERE e.estado = :estado AND e.fecha <= :fecha")
	List<EventoCorreo> findEventosConContactosByEstadoAndFechaBefore(@Param("estado") EstadoCorreo estado, @Param("fecha") LocalDateTime fecha);
}
