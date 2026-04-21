package com.autogest.models;

import java.sql.Timestamp;

/**
 * Modelo de la entidad Orden de Servicio
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class OrdenServicio {
    
    // Atributos
    private int idOrden;
    private String numeroOrden;
    private Timestamp fechaRecepcion;
    private String estado;
    private String descripcion;
    private String observaciones;
    private int idVehiculo;
    private String placaVehiculo;    // Para mostrar en vistas
    private String clienteVehiculo;   // Para mostrar en vistas
    
    // Constructores
    public OrdenServicio() {}
    
    public OrdenServicio(String numeroOrden, int idVehiculo, String descripcion) {
        this.numeroOrden = numeroOrden;
        this.idVehiculo = idVehiculo;
        this.descripcion = descripcion;
        this.estado = "Recibido";
    }
    
    // Getters y Setters
    public int getIdOrden() {
        return idOrden;
    }
    
    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }
    
    public String getNumeroOrden() {
        return numeroOrden;
    }
    
    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }
    
    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }
    
    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public int getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }
    
    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }
    
    public String getClienteVehiculo() {
        return clienteVehiculo;
    }
    
    public void setClienteVehiculo(String clienteVehiculo) {
        this.clienteVehiculo = clienteVehiculo;
    }
    
    @Override
    public String toString() {
        return "OrdenServicio{" +
                "idOrden=" + idOrden +
                ", numeroOrden='" + numeroOrden + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}