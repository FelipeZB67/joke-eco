package co.edu.unbosque.chistesneco.configuration;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.chistesneco.entity.Usuario;
import co.edu.unbosque.chistesneco.entity.Usuario.UserType;
import co.edu.unbosque.chistesneco.repository.UsuarioRepository;

@Configuration
public class LoadDataBase {
	private static final Logger Log = org.slf4j.LoggerFactory.getLogger(LoadDataBase.class);

	@Bean
	CommandLineRunner initDataBase(UsuarioRepository usuarioRepo, PasswordEncoder encoder) {
		return args -> {
			Optional<Usuario> foundAdmin = usuarioRepo.findByUsername("admin");

			if (foundAdmin.isPresent()) {
				Log.info("El admin ya existe, omitiendo creacion ...");
			} else {
				usuarioRepo.save(new Usuario(
						"admin",
						encoder.encode("admin.clave"),
						LocalDate.of(2005, 5, 15),
						UserType.ADMINISTRADOR));
				Log.info("Admin precargado");
			}

			Optional<Usuario> foundAdulto = usuarioRepo.findByUsername("adulto");

			if (foundAdulto.isPresent()) {
				Log.info("El adulto ya existe, omitiendo creacion ...");
			} else {
				usuarioRepo.save(new Usuario(
						"adulto",
						encoder.encode("adulto.clave"),
						LocalDate.of(1995, 8, 20),
						UserType.ADULTO));
				Log.info("Adulto precargado");
			}

			Optional<Usuario> foundNino = usuarioRepo.findByUsername("nino");

			if (foundNino.isPresent()) {
				Log.info("El nino ya existe, omitiendo creacion ...");
			} else {
				usuarioRepo.save(new Usuario(
						"nino",
						encoder.encode("nino.clave"),
						LocalDate.of(2015, 3, 10),
						UserType.NINO));
				Log.info("Nino precargado");
			}
		};
	}
}