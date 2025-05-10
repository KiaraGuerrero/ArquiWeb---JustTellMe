package pe.edu.upc.demo3798api.dtos;

import java.time.LocalDateTime;

public class DatosWearableDTO {
    public Long idUsuario;
    public LocalDateTime fechaRegistro;
    public Integer frecuenciaCardiaca;
    public String calidadSueno;
    public String nivelActividad;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getCalidadSueno() {
        return calidadSueno;
    }

    public void setCalidadSueno(String calidadSueno) {
        this.calidadSueno = calidadSueno;
    }

    public String getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(String nivelActividad) {
        this.nivelActividad = nivelActividad;
    }
}
