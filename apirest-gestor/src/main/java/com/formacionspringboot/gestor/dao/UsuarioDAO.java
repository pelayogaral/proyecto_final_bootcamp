package com.formacionspringboot.gestor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspringboot.gestor.entity.Usuario;



@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long>
{
	public Usuario findByUsername(String username);
	
}
