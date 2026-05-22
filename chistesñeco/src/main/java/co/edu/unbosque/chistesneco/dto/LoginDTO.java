package co.edu.unbosque.chistesneco.dto;

import java.util.Objects;

public class LoginDTO {

    private String username;
    private String contrasena;

    public LoginDTO() {}

    public LoginDTO(String username, String contrasena) {
        this.username = username;
        this.contrasena = contrasena;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "LoginDTO [username=" + username + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(contrasena, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LoginDTO other = (LoginDTO) obj;
        return Objects.equals(contrasena, other.contrasena)
                && Objects.equals(username, other.username);
    }
}