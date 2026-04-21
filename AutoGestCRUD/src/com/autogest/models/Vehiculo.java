package com.autogest.models;

import java.sql.Timestamp;

/**
 * Modelo de la entidad Vehículo
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class Vehiculo {
    
    // Atributos
    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private int año;
    private String color;
    private int kilometraje;
    private int idCliente;
    private String nombreCliente; // Para mostrar en vistas
    private Timestamp fechaRegistro;
    
    // Constructores
    public Vehiculo() {}
    
    public Vehiculo(String placa, String marca, String modelo, int año, int idCliente) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.idCliente = idCliente;
    }
    
    // Getters y Setters
    public int getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getAño() {
        return año;
    }
    
    public void setAño(int año) {
        this.año = año;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public int getKilometraje() {
        return kilometraje;
    }
    
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", año=" + año +
                ", clienteId=" + idCliente +
                '}';
    }
}