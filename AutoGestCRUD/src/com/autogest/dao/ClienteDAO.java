package com.autogest.dao;

import com.autogest.database.DatabaseConnection;
import com.autogest.models.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para la entidad Cliente
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class ClienteDAO {
    
    // ==================== QUERIES SQL ====================
    private static final String SQL_INSERT = 
            "INSERT INTO CLIENTE (nit, nombre, representante_legal, telefono, email, direccion, tipo_entidad) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT = 
            "SELECT * FROM CLIENTE";
    
    private static final String SQL_SELECT_BY_ID = 
            "SELECT * FROM CLIENTE WHERE id_cliente = ?";
    
    private static final String SQL_SELECT_BY_NIT = 
            "SELECT * FROM CLIENTE WHERE nit = ?";
    
    private static final String SQL_UPDATE = 
            "UPDATE CLIENTE SET nit = ?, nombre = ?, representante_legal = ?, "
            + "telefono = ?, email = ?, direccion = ?, tipo_entidad = ? "
            + "WHERE id_cliente = ?";
    
    private static final String SQL_DELETE = 
            "DELETE FROM CLIENTE WHERE id_cliente = ?";
    
    private static final String SQL_COUNT_VEHICULOS = 
            "SELECT COUNT(*) FROM VEHICULO WHERE id_cliente = ?";
    
    // ==================== CREATE (Insertar) ====================
    
    /**
     * Inserta un nuevo cliente en la base de datos
     * @param cliente Objeto Cliente con los datos a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Cliente cliente) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getRepresentanteLegal());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getTipoEntidad());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                // Obtener el ID generado automáticamente
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setIdCliente(rs.getInt(1));
                    }
                }
                System.out.println("✅ Cliente insertado correctamente. ID: " + cliente.getIdCliente());
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== READ (Consultar) ====================
    
    /**
     * Obtiene todos los clientes de la base de datos
     * @return Lista de todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(rsToCliente(rs));
            }
            
            System.out.println("📋 Se encontraron " + clientes.size() + " clientes");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al obtener clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por su ID
     * @param idCliente ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente obtenerPorId(int idCliente) {
        Cliente cliente = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = rsToCliente(rs);
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al buscar cliente por ID: " + e.getMessage());
        }
        
        return cliente;
    }
    
    /**
     * Busca un cliente por su NIT
     * @param nit NIT del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente obtenerPorNit(String nit) {
        Cliente cliente = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_NIT)) {
            
            ps.setString(1, nit);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = rsToCliente(rs);
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al buscar cliente por NIT: " + e.getMessage());
        }
        
        return cliente;
    }
    
    // ==================== UPDATE (Actualizar) ====================
    
    /**
     * Actualiza los datos de un cliente existente
     * @param cliente Objeto Cliente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Cliente cliente) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getRepresentanteLegal());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getTipoEntidad());
            ps.setInt(8, cliente.getIdCliente());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Cliente actualizado correctamente. ID: " + cliente.getIdCliente());
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== DELETE (Eliminar) ====================
    
    /**
     * Elimina un cliente de la base de datos
     * @param idCliente ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int idCliente) {
        // Primero verificar si el cliente tiene vehículos asociados
        if (tieneVehiculosAsociados(idCliente)) {
            System.err.println("❌ No se puede eliminar el cliente porque tiene vehículos asociados");
            return false;
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, idCliente);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Cliente eliminado correctamente. ID: " + idCliente);
                return true;
            }
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Convierte un ResultSet en un objeto Cliente
     */
    private Cliente rsToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNit(rs.getString("nit"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setRepresentanteLegal(rs.getString("representante_legal"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setTipoEntidad(rs.getString("tipo_entidad"));
        cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return cliente;
    }
    
    /**
     * Verifica si un cliente tiene vehículos asociados
     * @param idCliente ID del cliente
     * @return true si tiene vehículos, false en caso contrario
     */
    private boolean tieneVehiculosAsociados(int idCliente) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_VEHICULOS)) {
            
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error al verificar vehículos: " + e.getMessage());
        }
        return false;
    }
}