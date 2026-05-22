package co.edu.unbosque.chistesneco.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.chistesneco.dto.UsuarioDTO;
import co.edu.unbosque.chistesneco.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:8081", "*" })
@Tag(name = "Gestion de Usuarios", description = "Endpoints para administrar usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController() {
	}

	@Operation(summary = "Crear usuario (JSON)", description = "Crea un nuevo usuario enviando los datos en formato JSON.")
	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crearUsuarioJSON(@RequestBody UsuarioDTO nueva) {
		int status = usuarioService.create(nueva);

		switch (status) {
		case 0:
			return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
		case 1:
			return new ResponseEntity<>("Username duplicado", HttpStatus.CONFLICT);
		default:
			return new ResponseEntity<>("Error al crear usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Crear usuario (parametros)", description = "Crea un nuevo usuario usando parametros.")
	@PostMapping("/create")
	public ResponseEntity<String> crearUsuario(
			@RequestParam String username,
			@RequestParam String contrasena,
			@RequestParam LocalDate fechaNacimiento) {

		UsuarioDTO nueva = new UsuarioDTO();
		nueva.setUsername(username);
		nueva.setContrasena(contrasena);
		nueva.setFechaNacimiento(fechaNacimiento);

		int status = usuarioService.create(nueva);

		switch (status) {
		case 0:
			return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
		case 1:
			return new ResponseEntity<>("Username duplicado", HttpStatus.CONFLICT);
		default:
			return new ResponseEntity<>("Error al crear usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Obtener todos los usuarios", description = "Retorna todos los usuarios registrados.")
	@GetMapping("/getall")
	public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
		List<UsuarioDTO> lista = usuarioService.getAll();

		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Actualizar usuario (JSON)", description = "Actualiza un usuario enviando JSON.")
	@PutMapping(path = "/updatejson", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizarJSON(@RequestParam Long id, @RequestBody UsuarioDTO nueva) {
		int status = usuarioService.updateById(id, nueva);

		switch (status) {
		case 0:
			return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED);
		case 1:
			return new ResponseEntity<>("Username duplicado", HttpStatus.CONFLICT);
		case 2:
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<>("Error al actualizar usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Actualizar usuario (parametros)", description = "Actualiza un usuario usando parametros.")
	@PutMapping("/update")
	public ResponseEntity<String> actualizarUsuario(
			@RequestParam Long id,
			@RequestParam String username,
			@RequestParam String contrasena,
			@RequestParam LocalDate fechaNacimiento) {

		UsuarioDTO nueva = new UsuarioDTO();
		nueva.setUsername(username);
		nueva.setContrasena(contrasena);
		nueva.setFechaNacimiento(fechaNacimiento);

		int status = usuarioService.updateById(id, nueva);

		switch (status) {
		case 0:
			return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED);
		case 1:
			return new ResponseEntity<>("Username duplicado", HttpStatus.CONFLICT);
		case 2:
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<>("Error al actualizar usuario", HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID.")
	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
		int status = usuarioService.deleteById(id);

		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<>("Error al eliminar usuario", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Contar usuarios", description = "Cuenta el total de usuarios.")
	@GetMapping("/count")
	public ResponseEntity<Long> contar() {
		Long total = usuarioService.count();

		if (total == 0) {
			return new ResponseEntity<>(total, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(total, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Verificar existencia", description = "Verifica si existe un usuario.")
	@GetMapping("/exists/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		boolean found = usuarioService.exist(id);

		if (found) {
			return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
	}
}