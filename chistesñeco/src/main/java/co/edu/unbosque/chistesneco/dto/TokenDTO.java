package co.edu.unbosque.chistesneco.dto;

import java.util.Objects;

public class TokenDTO {

    private String token;
    private String rol;

    public TokenDTO() {}

    public TokenDTO(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "TokenDTO [rol=" + rol + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(rol, token);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TokenDTO other = (TokenDTO) obj;
        return Objects.equals(rol, other.rol)
                && Objects.equals(token, other.token);
    }
}