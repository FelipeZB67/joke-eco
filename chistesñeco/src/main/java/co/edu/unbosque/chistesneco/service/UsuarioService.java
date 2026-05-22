package co.edu.unbosque.chistesneco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.chistesneco.dto.UsuarioDTO;
import co.edu.unbosque.chistesneco.entity.Usuario;
import co.edu.unbosque.chistesneco.repository.UsuarioRepository;
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO>{
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int create(UsuarioDTO data) {
		try {
			Usuario entity = mapper.map(data, Usuario.class);
			if (findUsernameAlreadyTaken(entity)) {
				return 1;
			}		
			if (data.getUserType() != null) {
				data.setUserType(data.getUserType());
			}
			entity.setContrasena(passwordEncoder.encode(entity.getContrasena()));
			usuarioRepo.save(entity);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}
	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long id, UsuarioDTO data) {
		try {
			Optional<Usuario> found = usuarioRepo.findById(id);
			Optional<Usuario> foundUser = usuarioRepo.findByUsername(data.getUsername());
			
			if(found.isPresent()&& !foundUser.isPresent()) {
				Usuario temp = found.get();
				temp.setUsername(data.getUsername());
				temp.setContrasena(passwordEncoder.encode(data.getContrasena()));
				if (data.getUserType() != null) {
					temp.setUserType(data.getUserType());
				}
				usuarioRepo.save(temp);
				return 0;
			}
			if(found.isPresent()&&foundUser.isPresent()) {
				return 1;
			}
			if(!found.isPresent()) {
				return 2;
			}
			return 3;
		}catch(Exception e) {
			return 3;
		}
	}

	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = (List<Usuario>) usuarioRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			UsuarioDTO dto = mapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	@Override
	public long count() {
		return usuarioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioRepo.existsById(id);
	}
	
	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = usuarioRepo.findByUsername(username);
		return found.isPresent();
	}
	
	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		Optional<Usuario> found = usuarioRepo.findByUsername(newUser.getUsername());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int validateCredentials(String username, String password) {
		Optional<Usuario> personaOpt = usuarioRepo.findByUsername(username);
		if (personaOpt.isPresent()) {
			Usuario user = personaOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0;
			}
		}
		return 1;
	}


}
