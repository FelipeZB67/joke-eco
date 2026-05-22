package co.edu.unbosque.chistesneco.dto;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UsuarioDTO {
	
	private Long id;
	@Column(unique = true)
	private String username;
	private String contrasena;
	private LocalDate fechaNacimiento;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public UsuarioDTO(String username, String contrasena, LocalDate fechaNacimiento, UserType userType) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.setFechaNacimiento(fechaNacimiento);
		this.userType = userType;
	}


	public UsuarioDTO(String username, String contrasena, UserType userType) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.userType = userType;
	}


	public enum UserType {
		ADMINISTRADOR,
		NINO,
		ADULTO
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	


	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", username=" + username + ", contrasena=" + contrasena + ", fechaNacimiento="
				+ fechaNacimiento + ", userType=" + userType + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(contrasena, fechaNacimiento, id, userType, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(id, other.id) && userType == other.userType
				&& Objects.equals(username, other.username);
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	
	
	
}