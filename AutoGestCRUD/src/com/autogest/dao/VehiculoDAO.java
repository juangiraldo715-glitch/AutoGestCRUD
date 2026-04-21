package com.autogest.dao;

import com.autogest.database.DatabaseConnection;
import com.autogest.models.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para la entidad Vehículo
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class VehiculoDAO {
    
    // ==================== QUERIES SQL ====================
    private static final String SQL_INSERT = 
            "INSERT INTO VEHICULO (placa, marca, modelo, año, color, kilometraje, id_cliente) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT = 
            "SELECT v.*, c.nombre as nombre_cliente "
            + "FROM VEHICULO v "
            + "JOIN CLIENTE c ON v.id_cliente = c.id_cliente "
            + "ORDER BY v.id_vehiculo";
    
    private static final String SQL_SELECT_BY_ID = 
            "SELECT v.*, c.nombre as nombre_cliente "
            + "FROM VEHICULO v "
            + "JOIN CLIENTE c ON v.id_cliente = c.id_cliente "
            + "WHERE v.id_vehiculo = ?";
    
    private static final String SQL_SELECT_BY_PLACA = 
            "SELECT v.*, c.nombre as nombre_cliente "
            + "FROM VEHICULO v "
            + "JOIN CLIENTE c ON v.id_cliente = c.id_cliente "
            + "WHERE v.placa = ?";
    
    private static final String SQL_SELECT_BY_CLIENTE = 
            "SELECT v.*, c.nombre as nombre_cliente "
            + "FROM VEHICULO v "
            + "JOIN CLIENTE c ON v.id_cliente = c.id_cliente "
            + "WHERE v.id_cliente = ?";
    
    private static final String SQL_UPDATE = 
            "UPDATE VEHICULO SET placa = ?, marca = ?, modelo = ?, año = ?, "
            + "color = ?, kilometraje = ?, id_cliente = ? "
            + "WHERE id_vehiculo = ?";
    
    private static final String SQL_DELETE = 
            "DELETE FROM VEHICULO WHERE id_vehiculo = ?";
    
    private static final String SQL_COUNT_ORDENES = 
            "SELECT COUNT(*) FROM ORDEN_SERVICIO WHERE id_vehiculo = ?";
    
    // ==================== CREATE ====================
    
    public boolean insertar(Vehiculo vehiculo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getAño());
            ps.setString(5, vehiculo.getColor());
            ps.setInt(6, vehiculo.getKilometraje());
            ps.setInt(7, vehiculo.getIdCliente());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        vehiculo.setIdVehiculo(rs.getInt(1));
                    }
                }
                System.out.println("✅ Vehículo insertado correctamente. ID: " + vehiculo.getIdVehiculo());
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al insertar vehículo: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== READ ====================
    
    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                vehiculos.add(rsToVehiculo(rs));
            }
            
            System.out.println("📋 Se encontraron " + vehiculos.size() + " vehículos");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al obtener vehículos: " + e.getMessage());
        }
        
        return vehiculos;
    }
    
    public Vehiculo obtenerPorId(int idVehiculo) {
        Vehiculo vehiculo = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setInt(1, idVehiculo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = rsToVehiculo(rs);
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al buscar vehículo por ID: " + e.getMessage());
        }
        
        return vehiculo;
    }
    
    public Vehiculo obtenerPorPlaca(String placa) {
        Vehiculo vehiculo = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_PLACA)) {
            
            ps.setString(1, placa);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = rsToVehiculo(rs);
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al buscar vehículo por placa: " + e.getMessage());
        }
        
        return vehiculo;
    }
    
    public List<Vehiculo> obtenerPorCliente(int idCliente) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CLIENTE)) {
            
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehiculos.add(rsToVehiculo(rs));
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al buscar vehículos por cliente: " + e.getMessage());
        }
        
        return vehiculos;
    }
    
    // ==================== UPDATE ====================
    
    public boolean actualizar(Vehiculo vehiculo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getAño());
            ps.setString(5, vehiculo.getColor());
            ps.setInt(6, vehiculo.getKilometraje());
            ps.setInt(7, vehiculo.getIdCliente());
            ps.setInt(8, vehiculo.getIdVehiculo());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Vehículo actualizado correctamente. ID: " + vehiculo.getIdVehiculo());
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== DELETE ====================
    
    public boolean eliminar(int idVehiculo) {
        if (tieneOrdenesAsociadas(idVehiculo)) {
            System.err.println("❌ No se puede eliminar el vehículo porque tiene órdenes de servicio asociadas");
            return false;
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, idVehiculo);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Vehículo eliminado correctamente. ID: " + idVehiculo);
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    private Vehiculo rsToVehiculo(ResultSet rs) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
        vehiculo.setPlaca(rs.getString("placa"));
        vehiculo.setMarca(rs.getString("marca"));
        vehiculo.setModelo(rs.getString("modelo"));
        vehiculo.setAño(rs.getInt("año"));
        vehiculo.setColor(rs.getString("color"));
        vehiculo.setKilometraje(rs.getInt("kilometraje"));
        vehiculo.setIdCliente(rs.getInt("id_cliente"));
        vehiculo.setNombreCliente(rs.getString("nombre_cliente"));
        vehiculo.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return vehiculo;
    }
    
    private boolean tieneOrdenesAsociadas(int idVehiculo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_ORDENES)) {
            
            ps.setInt(1, idVehiculo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al verificar órdenes: " + e.getMessage());
        }
        return false;
    }
}