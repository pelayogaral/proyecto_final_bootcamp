package com.formacionspringboot.gestor.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspringboot.gestor.entity.Proyecto;
import com.formacionspringboot.gestor.service.ProyectoService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProyectoRestController {
	@Autowired
	private ProyectoService servicio;
	@GetMapping({"/proyectos"})
	public List<Proyecto> index(){
		return servicio.findAll();
	}

	@GetMapping("/proyecto/{id}")
	public ResponseEntity<?> findProyectoById(@PathVariable Long id)
	{
		Proyecto proyecto = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			proyecto = servicio.findById(id);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(proyecto == null)
		{
			response.put("mensaje", "El proyecto con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
	}
	
	@PostMapping("/proyecto")
	public ResponseEntity<?> saveProyecto(@RequestBody Proyecto proyecto)
	{
		Proyecto proyectoNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			proyectoNew = servicio.save(proyecto);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El proyecto ".concat(proyectoNew.getNombre()).concat(" ha sido creado con éxito"));
		response.put("proyecto", proyectoNew);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/proyecto/{id}")
	public ResponseEntity<?> updateProyecto(@RequestBody Proyecto proyecto, @PathVariable Long id)
	{
		Proyecto proyectoActual = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(proyectoActual == null)
		{
			response.put("mensaje", "No se puede editar el proyecto, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{
			proyectoActual.setNombre(proyecto.getNombre());
			proyectoActual.setDescripcion(proyecto.getDescripcion());
			proyectoActual.setFechaInicio(proyecto.getFechaInicio());
			proyectoActual.setFechaFin(proyecto.getFechaFin());
			proyectoActual.setActivo(proyecto.getActivo());
						
			servicio.save(proyectoActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El proyecto ".concat(proyectoActual.getNombre()).concat(" ha sido actualizado con éxito"));
		response.put("proyecto", proyectoActual);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/proyecto/{id}")
	public ResponseEntity<?> deleteProyecto(@PathVariable Long id)
	{
		Proyecto proyectoEliminado = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(proyectoEliminado == null)
		{
			response.put("mensaje", "No se puede eliminar el proyecto, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{
			servicio.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El proyecto ".concat(proyectoEliminado.getNombre()).concat(" ha sido eliminado con éxito"));
		response.put("proyecto", proyectoEliminado);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

}
