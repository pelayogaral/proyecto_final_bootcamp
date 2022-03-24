package com.formacionspringboot.gestor.service;

import java.util.List;

import com.formacionspringboot.gestor.entity.Empleado;



public interface EmpleadoService {
	public List<Empleado> findAll();
	public Empleado findById(Long id);
	public Empleado save(Empleado empleado);
	public void delete (Long id);
	
	List<Empleado> findByProyecto_Id(Long id);
}
