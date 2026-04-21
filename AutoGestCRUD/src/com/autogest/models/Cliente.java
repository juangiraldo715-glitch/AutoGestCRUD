package com.autogest.models;

import java.sql.Timestamp;

/**
 * Modelo de la entidad Cliente
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class Cliente {
    
    // Atributos
    private int idCliente;
    private String nit;
    private String nombre;
    private String representanteLegal;
    private String telefono;
    private String email;
    private String direccion;
    private String tipoEntidad;
    private Timestamp fechaRegistro;
    
    // Constructores
    public Cliente() {}
    
    public Cliente(String nit, String nombre, String representanteLegal, 
                   String telefono, String tipoEntidad) {
        this.nit = nit;
        this.nombre = nombre;
        this.representanteLegal = representanteLegal;
        this.telefono = telefono;
        this.tipoEntidad = tipoEntidad;
    }
    
    public Cliente(int idCliente, String nit, String nombre, String representanteLegal,
                   String telefono, String email, String direccion, String tipoEntidad) {
        this.idCliente = idCliente;
        this.nit = nit;
        this.nombre = nombre;
        this.representanteLegal = representanteLegal;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipoEntidad = tipoEntidad;
    }
    
    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getRepresentanteLegal() {
        return representanteLegal;
    }
    
    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTipoEntidad() {
        return tipoEntidad;
    }
    
    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nit='" + nit + '\'' +
                ", nombre='" + nombre + '\'' +
                ", representanteLegal='" + representanteLegal + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipoEntidad='" + tipoEntidad + '\'' +
                '}';
    }
}