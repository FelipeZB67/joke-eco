package co.edu.unbosque.chistesneco.controller;

import co.edu.unbosque.chistesneco.dto.LoginDTO;
import co.edu.unbosque.chistesneco.dto.TokenDTO;
import co.edu.unbosque.chistesneco.dto.UsuarioDTO;
import co.edu.unbosque.chistesneco.entity.Usuario;
//import co.edu.unbosque.chistesneco.security.JwtUtil;
import co.edu.unbosque.chistesneco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody UsuarioDTO dto) {
        int resultado = usuarioService.create(dto);
        switch (resultado) {
            case 0: return ResponseEntity.ok("Usuario registrado exitosamente.");
            case 1: return ResponseEntity.badRequest().body("ERROR: El username ya está en uso.");
            default: return ResponseEntity.internalServerError().body("ERROR: No se pudo registrar.");
        }
    }

    //@PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
    //     try {
    //      authManager.authenticate(
    //          new UsernamePasswordAuthenticationToken(
    //              dto.getUsername(), dto.getContrasena()
    //          )
    //      );

    //     Usuario usuario = usuarioService.buscarPorUsername(dto.getUsername())
    //              .orElseThrow();

            //            String token = jwtUtil.generarToken(
            //   usuario.getUsername(),
            //  usuario.getUserType().name()
            // );

            //return ResponseEntity.ok(new TokenDTO(token, usuario.getUserType().name()));

            //     } catch (Exception e) {
            //return ResponseEntity.status(401).body("Credenciales incorrectas.");
            //}
            //}
}