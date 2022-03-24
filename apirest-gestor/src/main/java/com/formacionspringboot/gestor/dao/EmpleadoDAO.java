package com.formacionspringboot.gestor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.formacionspringboot.gestor.entity.Empleado;

public interface EmpleadoDAO extends CrudRepository <Empleado, Long>{
	List<Empleado> findByProyecto_Id(Long id);
}
