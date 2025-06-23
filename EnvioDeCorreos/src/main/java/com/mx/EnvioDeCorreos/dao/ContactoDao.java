package com.mx.EnvioDeCorreos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.EnvioDeCorreos.entity.Contactos;

public interface ContactoDao extends JpaRepository<Contactos, Integer>{

}
