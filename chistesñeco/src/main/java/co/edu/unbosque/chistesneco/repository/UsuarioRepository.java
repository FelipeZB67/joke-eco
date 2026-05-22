package co.edu.unbosque.chistesneco.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.chistesneco.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	Optional<Usuario> findByUsername(String usuario);
}
