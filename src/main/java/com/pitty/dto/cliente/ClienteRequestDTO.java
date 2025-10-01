package com.pitty.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no debe exceder 120 caracteres")
    private String nombre;

    @Size(max = 30, message = "El teléfono no debe exceder 30 caracteres")
    private String telefono;

    private String notas;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}