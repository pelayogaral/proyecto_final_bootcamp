package com.formacionspringboot.gestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspringboot.gestor.dao.ProyectoDAO;
import com.formacionspringboot.gestor.entity.Empleado;
import com.formacionspringboot.gestor.entity.Proyecto;

@Service
public class ProyectoServiceImpl implements ProyectoService{

	@Autowired
	private ProyectoDAO proyectoDAO;
	@Override
	public List<Proyecto> findAll() {
	
		return (List<Proyecto>) proyectoDAO.findAll();
	}

	@Override
	public Proyecto findById(Long id) {
		
		return proyectoDAO.findById(id).orElse(null);
	}

	@Override
	public Proyecto save(Proyecto proyecto) {
		
		return proyectoDAO.save(proyecto);
	}

	@Override
	public void delete(Long id) {
		proyectoDAO.deleteById(id);
	}

	

}
