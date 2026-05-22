package co.edu.unbosque.chistesneco.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unbosque.chistesneco.repository.UsuarioRepository;

/**
 * Implementación del servicio UserDetailsService de Spring Security.
 * Se encarga de cargar los detalles de un usuario desde la base de datos
 * a partir de su nombre de usuario para el proceso de autenticación.
 */
@Service
public class UserDetailsServicelmpl implements UserDetailsService {

	private final UsuarioRepository personaRepository;

	/**
	 * Constructor que inyecta el repositorio de personas.
	 *
	 * @param personaRepository repositorio para consultar personas en la base de datos
	 */
	public UserDetailsServicelmpl(UsuarioRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

	/**
	 * Carga los detalles de un usuario a partir de su nombre de usuario.
	 *
	 * @param username nombre de usuario a buscar
	 * @return detalles del usuario encontrado
	 * @throws UsernameNotFoundException si no existe un usuario con el nombre dado
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return personaRepository
				.findByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}