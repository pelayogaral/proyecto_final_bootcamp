package com.formacionspringboot.gestor.contoller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionspringboot.gestor.entity.Empleado;
import com.formacionspringboot.gestor.service.EmpleadoService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class EmpleadoRestController 
{
	@Autowired
	private EmpleadoService servicio;
	@GetMapping({"/empleados"})
	public List<Empleado> index(){
		return servicio.findAll();
	}

	@GetMapping("/empleado/{id}")
	public ResponseEntity<?> findEmpleadoById(@PathVariable Long id)
	{
		Empleado empleado = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			empleado = servicio.findById(id);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(empleado == null)
		{
			response.put("mensaje", "El empleado con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleado")
	public ResponseEntity<?> saveEmpleado(@RequestBody Empleado empleado)
	{
		Empleado empleadoNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try
		{
			empleadoNew = servicio.save(empleado);		
		}
		catch(DataAccessException e) 
		{
			response.put("mensaje", "Error al realizar una insert a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El empleado ".concat(empleadoNew.getNombre()).concat(" ha sido creado con éxito"));
		response.put("empleado", empleadoNew);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/empleado/{id}")
	public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado, @PathVariable Long id)
	{
		Empleado empleadoActual = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(empleadoActual == null)
		{
			response.put("mensaje", "No se puede editar el empleado, el ID ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setApellidos(empleado.getApellidos());
			empleadoActual.setEmail(empleado.getEmail());
			empleadoActual.setTelefono(empleado.getTelefono());
			empleadoActual.setActivo(empleado.getActivo());			
			empleadoActual.setProyecto(empleado.getProyecto());			
			
			servicio.save(empleadoActual);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un update a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ".concat(empleadoActual.getNombre()).concat(" ha sido actualizado con éxito"));
		response.put("empleado", empleadoActual);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/empleado/{id}")
	public ResponseEntity<?> deleteEmpleado(@PathVariable Long id)
	{
		Empleado empleadoEliminado = servicio.findById(id);	
		Map<String,Object> response = new HashMap<>();
		if(empleadoEliminado == null)
		{
			response.put("mensaje", "No se puede eliminar el empleado, el ID ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}		
		try 
		{
			String fotoAnterior = empleadoEliminado.getImagen();		
			if(fotoAnterior != null && fotoAnterior.length() > 0) 
			{
				Path rutaAnterior = Paths.get("uploads").resolve(fotoAnterior).toAbsolutePath();
				File archivoAnterior = rutaAnterior.toFile();
				if(archivoAnterior.exists() && archivoAnterior.canRead())
				{
					archivoAnterior.delete();
				}
			}	
			servicio.delete(id);
		}
		catch(DataAccessException e)
		{
			response.put("mensaje", "Error al realizar un delete a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado ".concat(empleadoEliminado.getNombre()).concat(" ha sido eliminado con éxito"));
		response.put("empleado", empleadoEliminado);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("empleado/upload")
	public ResponseEntity<?> uploadImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id)
	{
		Map<String,Object> response = new HashMap<>();
		
		Empleado empleado = servicio.findById(id);
		if(!archivo.isEmpty()) 
		{
			String nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();	
			try 
			{			
				Files.copy(archivo.getInputStream(), rutaArchivo);
									
			} catch (IOException e) {
				
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String fotoAnterior = empleado.getImagen();
			
			if(fotoAnterior != null && fotoAnterior.length() > 0) 
			{
				Path rutaAnterior = Paths.get("uploads").resolve(fotoAnterior).toAbsolutePath();
				File archivoAnterior = rutaAnterior.toFile();
				if(archivoAnterior.exists() && archivoAnterior.canRead())
				{
					archivoAnterior.delete();
				}
			}		
			
			empleado.setImagen(nombreArchivo);
			servicio.save(empleado);
			response.put("mensaje", "La imagen "+ nombreArchivo +" ha sido subida con exito");
			response.put("empleado", empleado);
		}	
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	@GetMapping("/empleado/imagen/{nombreImagen:.+}")
	public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen)
	{
		Path rutaImagen = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		Resource recurso = null;
		try {
			recurso = new UrlResource(rutaImagen.toUri());
		} 
		catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		if(!recurso.exists() && !recurso.isReadable())
		{
			throw new RuntimeException("Error no se puede cargar la imagen solicitada: "+nombreImagen);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\" "+recurso.getFilename()+"\"");
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}	
	@GetMapping("/empleados/proyecto/{id}")
	public List<Empleado> findEmpleadoByProyectoId(@PathVariable Long id)
	{
		return servicio.findByProyecto_Id(id);
	}
}
