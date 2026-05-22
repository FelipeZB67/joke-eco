package co.edu.unbosque.chistesneco.service;

import co.edu.unbosque.chistesneco.dto.JokeApiDTO;
import co.edu.unbosque.chistesneco.entity.HistorialChiste;
import co.edu.unbosque.chistesneco.entity.Usuario;
import co.edu.unbosque.chistesneco.repository.HistorialChisteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChisteService {

    @Autowired
    private HistorialChisteRepository historialRepo;

    @Value("${jokeapi.url.segura}")
    private String urlSegura;

    @Value("${jokeapi.url.todo}")
    private String urlTodo;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerChisteSeguro(Usuario usuario) {
        JokeApiDTO respuesta = llamarApi(urlSegura);
        String chiste = extraerTexto(respuesta);
        guardarHistorial(usuario, chiste, "SEGURO");
        return chiste;
    }

    public String obtenerChisteTodo(Usuario usuario) {
        JokeApiDTO respuesta = llamarApi(urlTodo);
        String chiste = extraerTexto(respuesta);
        guardarHistorial(usuario, chiste, "CUALQUIER_TIPO");
        return chiste;
    }

    public List<HistorialChiste> obtenerHistorial() {
        return historialRepo.findAll();
    }

    private JokeApiDTO llamarApi(String url) {
        try {
            return restTemplate.getForObject(url, JokeApiDTO.class);
        } catch (Exception e) {
            JokeApiDTO error = new JokeApiDTO();
            error.setError(true);
            error.setJoke("Error al obtener chiste: " + e.getMessage());
            return error;
        }
    }

    private String extraerTexto(JokeApiDTO dto) {
        if (dto == null || dto.isError()) return "No se pudo obtener el chiste.";
        if ("single".equals(dto.getType())) return dto.getJoke();
        if ("twopart".equals(dto.getType())) 
            return dto.getSetup() + " ... " + dto.getDelivery();
        return "Chiste no disponible.";
    }

    private void guardarHistorial(Usuario usuario, String chiste, String tipoPeticion) {
        HistorialChiste historial = new HistorialChiste();
        historial.setNombreUsuario(usuario.getUsername());
        historial.setRol(usuario.getUserType().name());
        historial.setChiste(chiste);
        historial.setTipoPeticion(tipoPeticion);
        historial.setFechaSolicitud(LocalDateTime.now());
        historialRepo.save(historial);
    }
}