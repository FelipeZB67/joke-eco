package co.edu.unbosque.chistesneco.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3419274481795946604L;
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(unique = true)
	private String username;
	private String contrasena;
	private LocalDate fechaNacimiento;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	public Usuario() {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.userType = UserType.NINO;
	}

	public Usuario(String username, String contrasena, LocalDate fechaNacimiento) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Usuario(String username, String contrasena, LocalDate fechaNacimiento, UserType userType) {
		super();
		this.username = username;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.userType = userType;
	}

	public enum UserType {
		ADMINISTRADOR, NINO, ADULTO
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

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public @Nullable String getPassword() {
		return contrasena;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()));
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", contrasena=" + contrasena + ", fechaNacimiento="
				+ fechaNacimiento + ", userType=" + userType + ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNonExpired, accountNonLocked, contrasena, credentialsNonExpired, enabled,
				fechaNacimiento, id, userType, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return accountNonExpired == other.accountNonExpired && accountNonLocked == other.accountNonLocked
				&& Objects.equals(contrasena, other.contrasena) && credentialsNonExpired == other.credentialsNonExpired
				&& enabled == other.enabled && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(id, other.id) && userType == other.userType
				&& Objects.equals(username, other.username);
	}
}