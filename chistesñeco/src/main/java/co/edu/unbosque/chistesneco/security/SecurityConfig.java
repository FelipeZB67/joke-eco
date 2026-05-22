package co.edu.unbosque.chistesneco.security;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad de la aplicación.
 * Define las reglas de autorización por roles, la política de sesiones sin estado,
 * la configuración CORS, el filtro JWT y los beans de autenticación y cifrado.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;

	/**
	 * Constructor que inyecta el filtro JWT y el servicio de detalles de usuario.
	 *
	 * @param jwtAuthFilter      filtro de autenticación JWT
	 * @param userDetailsService servicio para cargar los detalles del usuario
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Configura la cadena de filtros de seguridad HTTP.
	 * Define los endpoints públicos, las reglas de acceso por rol,
	 * la política de sesiones sin estado y el filtro JWT.
	 *
	 * @param http objeto de configuración de seguridad HTTP
	 * @return la cadena de filtros de seguridad construida
	 * @throws Exception si ocurre un error durante la configuración
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/persona/create", "/persona/createjson").permitAll()
						.requestMatchers("/persona/**").hasRole("ADMINISTRADOR")
						.requestMatchers("/noticia/create", "/noticia/createjson", "/horoscopo/create",
								"/horoscopo/createjson")
						.hasRole("ADMINISTRADOR")
						.requestMatchers("/noticia/update/**", "/noticia/updatejson", "/noticia/delete/**",
								"/horoscopo/update/**", "/horoscopo/updatejson", "/horoscopo/delete/**")
						.hasAnyRole("ADMINISTRADOR", "EDITOR")
						.requestMatchers("/noticia/comentar/**")
						.hasAnyRole("COMENTARISTA", "ADMINISTRADOR", "EDITOR")
						.requestMatchers("/noticia/getall", "/noticia/getbyid/**", "/horoscopo/getall",
								"/horoscopo/getbyid/**")
						.hasAnyRole("USUARIO", "EDITOR", "COMENTARISTA", "ADMINISTRADOR")
						.anyRequest().authenticated())
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configura y registra la fuente de configuración CORS para toda la aplicación.
	 * Permite cualquier origen, los métodos GET, POST, PUT, DELETE y OPTIONS,
	 * y cualquier encabezado.
	 *
	 * @return fuente de configuración CORS registrada para todos los endpoints
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	/**
	 * Configura el proveedor de autenticación basado en DAO,
	 * asociando el servicio de detalles de usuario y el codificador de contraseñas.
	 *
	 * @return proveedor de autenticación configurado
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * Expone el manejador de autenticación de Spring Security como bean.
	 *
	 * @param config configuración de autenticación de Spring
	 * @return manejador de autenticación obtenido desde la configuración
	 * @throws Exception si ocurre un error al obtener el manejador
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Registra el codificador de contraseñas BCrypt como bean.
	 *
	 * @return instancia de BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}