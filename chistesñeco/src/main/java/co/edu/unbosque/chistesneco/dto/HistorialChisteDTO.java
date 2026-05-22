package co.edu.unbosque.chistesneco.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class HistorialChisteDTO {

    private Long id;
    private String nombreUsuario;
    private String rol;
    private String chiste;
    private String tipoPeticion;
    private LocalDateTime fechaSolicitud;

    public HistorialChisteDTO() {}

    public HistorialChisteDTO(String nombreUsuario, String rol, String chiste,
            String tipoPeticion, LocalDateTime fechaSolicitud) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.chiste = chiste;
        this.tipoPeticion = tipoPeticion;
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getChiste() { return chiste; }
    public void setChiste(String chiste) { this.chiste = chiste; }
    public String getTipoPeticion() { return tipoPeticion; }
    public void setTipoPeticion(String tipoPeticion) { this.tipoPeticion = tipoPeticion; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    @Override
    public String toString() {
        return "HistorialChisteDTO [id=" + id + ", nombreUsuario=" + nombreUsuario
                + ", rol=" + rol + ", tipoPeticion=" + tipoPeticion
                + ", fechaSolicitud=" + fechaSolicitud + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(chiste, fechaSolicitud, id, nombreUsuario, rol, tipoPeticion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistorialChisteDTO other = (HistorialChisteDTO) obj;
        return Objects.equals(chiste, other.chiste)
                && Objects.equals(fechaSolicitud, other.fechaSolicitud)
                && Objects.equals(id, other.id)
                && Objects.equals(nombreUsuario, other.nombreUsuario)
                && Objects.equals(rol, other.rol)
                && Objects.equals(tipoPeticion, other.tipoPeticion);
    }
}