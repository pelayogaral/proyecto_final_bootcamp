package com.formacionspringboot.gestor.service;

import java.util.List;

import com.formacionspringboot.gestor.entity.Empleado;
import com.formacionspringboot.gestor.entity.Proyecto;

public interface ProyectoService 
{
	public List<Proyecto> findAll();
	public Proyecto findById(Long id);
	public Proyecto save(Proyecto proyecto);
	public void delete (Long id);
	
	
}
