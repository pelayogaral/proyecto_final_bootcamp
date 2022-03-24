package com.formacionspringboot.gestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspringboot.gestor.dao.EmpleadoDAO;
import com.formacionspringboot.gestor.entity.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoDAO empleadoDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Empleado> findAll() {
		
		return (List<Empleado>) empleadoDAO.findAll();
	}

	@Override
	public Empleado findById(Long id) {
		return empleadoDAO.findById(id).orElse(null);
	}

	@Override
	public Empleado save(Empleado empleado) {
		
		return empleadoDAO.save(empleado);
	}

	@Override
	public void delete(Long id) {
		empleadoDAO.deleteById(id);
		
	}
	@Override
	public List<Empleado> findByProyecto_Id(Long id) {
		
		return empleadoDAO.findByProyecto_Id(id);
	}

}
