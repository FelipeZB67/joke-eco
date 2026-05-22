package co.edu.unbosque.chistesneco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.chistesneco.dto.UsuarioDTO;
import co.edu.unbosque.chistesneco.entity.Usuario;
import co.edu.unbosque.chistesneco.security.JwtUtil;
import co.edu.unbosque.chistesneco.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:8081", "*" })
@Tag(name = "Autenticacion", description = "Endpoints para login y registro de usuarios")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UsuarioService usuarioService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UsuarioService usuarioService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioService = usuarioService;
	}

	@Operation(summary = "Iniciar sesion", description = "Autentica un usuario y retorna un token JWT.")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getContrasena()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			String userType = null;

			if (userDetails instanceof Usuario) {
				Usuario usuario = (Usuario) userDetails;
				userType = usuario.getUserType().name();
			}

			return ResponseEntity.ok(new AuthResponse(jwt, userType));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contrasena invalidos");
		}
	}

	@Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario. El tipo se asigna automaticamente segun la edad.")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {

		if (usuarioService.findUsernameAlreadyTaken(registerRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
		}

		int result = usuarioService.create(registerRequest);

		switch (result) {
		case 0:
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
		case 1:
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
		default:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el usuario");
		}
	}

	private static class AuthResponse {

		private final String token;
		private final String userType;

		public AuthResponse(String token, String userType) {
			this.token = token;
			this.userType = userType;
		}

		public String getToken() {
			return token;
		}

		public String getUserType() {
			return userType;
		}
	}
}