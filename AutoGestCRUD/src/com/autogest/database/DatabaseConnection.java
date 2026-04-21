package com.autogest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos MySQL
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class DatabaseConnection {
    
    // Constantes de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/autogest_crud";
    private static final String USER = "root";
    private static final String PASSWORD = "ProgramA-56*38"; 
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    
    /**
     * Constructor privado para evitar instanciación
     */
    private DatabaseConnection() {}
    
    /**
     * Obtiene la conexión a la base de datos (Singleton)
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     * @throws ClassNotFoundException si no se encuentra el driver
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     * @throws SQLException si hay error al cerrar
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    /**
     * Prueba la conexión a la base de datos
     * @return true si la conexión es exitosa
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}