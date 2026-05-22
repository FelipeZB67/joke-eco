package co.edu.unbosque.chistesneco.controller;

import co.edu.unbosque.chistesneco.entity.HistorialChiste;
import co.edu.unbosque.chistesneco.entity.Usuario;
import co.edu.unbosque.chistesneco.repository.UsuarioRepository;
import co.edu.unbosque.chistesneco.service.ChisteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ChisteController {

    @Autowired
    private ChisteService chisteService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/chiste/seguro")
    public ResponseEntity<String> chisteSeguro(Authentication auth) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName())
                .orElseThrow();
        return ResponseEntity.ok(chisteService.obtenerChisteSeguro(usuario));
    }

    @GetMapping("/chiste/todo")
    public ResponseEntity<String> chisteTodo(Authentication auth) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName())
                .orElseThrow();
        return ResponseEntity.ok(chisteService.obtenerChisteTodo(usuario));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<HistorialChiste>> historial() {
        return ResponseEntity.ok(chisteService.obtenerHistorial());
    }
}