package com.autogest.main;

import com.autogest.dao.ClienteDAO;
import com.autogest.dao.VehiculoDAO;
import com.autogest.database.DatabaseConnection;
import com.autogest.models.Cliente;
import com.autogest.models.Vehiculo;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal del sistema AutoGest Taller Pro
 * @author AutoGest Taller Pro
 * @version 1.0
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  AUTO-GEST TALLER PRO                        ║");
        System.out.println("║                   Sistema de Gestión                         ║");
        System.out.println("║                  Módulo de Recepción                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        // Probar conexión a la base de datos
        if (!DatabaseConnection.testConnection()) {
            System.err.println("❌ Error: No se pudo conectar a la base de datos.");
            System.err.println("   Verifique que MySQL esté ejecutándose y la configuración sea correcta.");
            System.exit(1);
        }
        
        System.out.println("\n✅ Conexión a la base de datos exitosa!\n");
        
        // Menú principal
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuVehiculos();
                    break;
                case 3:
                    System.out.println("\n👋 ¡Gracias por usar AutoGest Taller Pro!");
                    System.out.println("   Saliendo del sistema...\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        MENÚ PRINCIPAL                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Clientes                                      ║");
        System.out.println("║  2. Gestión de Vehículos                                    ║");
        System.out.println("║  3. Salir                                                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // ==================== MENÚ CLIENTES ====================
    
    private static void menuClientes() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    GESTIÓN DE CLIENTES                       ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Listar todos los clientes                                ║");
            System.out.println("║  2. Buscar cliente por ID                                    ║");
            System.out.println("║  3. Buscar cliente por NIT                                   ║");
            System.out.println("║  4. Crear nuevo cliente                                     ║");
            System.out.println("║  5. Actualizar cliente                                      ║");
            System.out.println("║  6. Eliminar cliente                                        ║");
            System.out.println("║  7. Volver al menú principal                                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.print("👉 Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    buscarClientePorId();
                    break;
                case 3:
                    buscarClientePorNit();
                    break;
                case 4:
                    crearCliente();
                    break;
                case 5:
                    actualizarCliente();
                    break;
                case 6:
                    eliminarCliente();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
    }
    
    private static void listarClientes() {
        System.out.println("\n📋 LISTADO DE CLIENTES:\n");
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("   No hay clientes registrados.\n");
            return;
        }
        
        System.out.println("┌────┬──────────────────┬────────────────────────────────────────┬──────────────────────────┬─────────────┐");
        System.out.println("│ ID │ NIT              │ Nombre                                 │ Representante            │ Teléfono    │");
        System.out.println("├────┼──────────────────┼────────────────────────────────────────┼──────────────────────────┼─────────────┤");
        
        for (Cliente c : clientes) {
            System.out.printf("│ %2d │ %-16s │ %-38s │ %-24s │ %-11s │\n",
                    c.getIdCliente(),
                    c.getNit(),
                    truncar(c.getNombre(), 38),
                    truncar(c.getRepresentanteLegal(), 24),
                    c.getTelefono());
        }
        
        System.out.println("└────┴──────────────────┴────────────────────────────────────────┴──────────────────────────┴─────────────┘\n");
    }
    
    private static void buscarClientePorId() {
        System.out.print("\n🔍 Ingrese el ID del cliente: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        
        if (cliente == null) {
            System.out.println("❌ No se encontró un cliente con ID: " + id);
            return;
        }
        
        mostrarDetalleCliente(cliente);
    }
    
    private static void buscarClientePorNit() {
        System.out.print("\n🔍 Ingrese el NIT del cliente (formato: 123456789-1): ");
        String nit = scanner.nextLine();
        
        Cliente cliente = clienteDAO.obtenerPorNit(nit);
        
        if (cliente == null) {
            System.out.println("❌ No se encontró un cliente con NIT: " + nit);
            return;
        }
        
        mostrarDetalleCliente(cliente);
    }
    
    private static void mostrarDetalleCliente(Cliente cliente) {
        System.out.println("\n📄 DETALLE DEL CLIENTE:\n");
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.printf("│ ID                    : %-37d │\n", cliente.getIdCliente());
        System.out.printf("│ NIT                   : %-37s │\n", cliente.getNit());
        System.out.printf("│ Nombre                : %-37s │\n", truncar(cliente.getNombre(), 37));
        System.out.printf("│ Representante Legal   : %-37s │\n", truncar(cliente.getRepresentanteLegal(), 37));
        System.out.printf("│ Teléfono              : %-37s │\n", cliente.getTelefono());
        System.out.printf("│ Email                 : %-37s │\n", cliente.getEmail() != null ? cliente.getEmail() : "N/A");
        System.out.printf("│ Dirección             : %-37s │\n", cliente.getDireccion() != null ? cliente.getDireccion() : "N/A");
        System.out.printf("│ Tipo Entidad          : %-37s │\n", cliente.getTipoEntidad());
        System.out.printf("│ Fecha Registro        : %-37s │\n", cliente.getFechaRegistro());
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
    }
    
    private static void crearCliente() {
        System.out.println("\n➕ CREAR NUEVO CLIENTE:\n");
        
        System.out.print("   NIT (formato: 123456789-1): ");
        String nit = scanner.nextLine();
        
        // Verificar si ya existe
        if (clienteDAO.obtenerPorNit(nit) != null) {
            System.out.println("❌ Ya existe un cliente con ese NIT.");
            return;
        }
        
        System.out.print("   Nombre de la entidad: ");
        String nombre = scanner.nextLine();
        
        System.out.print("   Representante Legal: ");
        String representante = scanner.nextLine();
        
        System.out.print("   Teléfono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("   Email (opcional): ");
        String email = scanner.nextLine();
        if (email.isEmpty()) email = null;
        
        System.out.print("   Dirección (opcional): ");
        String direccion = scanner.nextLine();
        if (direccion.isEmpty()) direccion = null;
        
        System.out.print("   Tipo de Entidad (Alcaldía/Gobernación/Ministerio/Instituto/Empresa): ");
        String tipoEntidad = scanner.nextLine();
        
        Cliente cliente = new Cliente(nit, nombre, representante, telefono, tipoEntidad);
        cliente.setEmail(email);
        cliente.setDireccion(direccion);
        
        if (clienteDAO.insertar(cliente)) {
            System.out.println("✅ Cliente creado exitosamente con ID: " + cliente.getIdCliente());
        } else {
            System.out.println("❌ Error al crear el cliente.");
        }
    }
    
    private static void actualizarCliente() {
        System.out.print("\n✏️ Ingrese el ID del cliente a actualizar: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        
        if (cliente == null) {
            System.out.println("❌ No se encontró un cliente con ID: " + id);
            return;
        }
        
        System.out.println("\n📄 Datos actuales del cliente:");
        mostrarDetalleCliente(cliente);
        
        System.out.println("📝 Ingrese los nuevos datos (deje en blanco para mantener el valor actual):\n");
        
        System.out.print("   Nuevo NIT [" + cliente.getNit() + "]: ");
        String nit = scanner.nextLine();
        if (!nit.isEmpty()) cliente.setNit(nit);
        
        System.out.print("   Nuevo nombre [" + cliente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);
        
        System.out.print("   Nuevo representante [" + cliente.getRepresentanteLegal() + "]: ");
        String representante = scanner.nextLine();
        if (!representante.isEmpty()) cliente.setRepresentanteLegal(representante);
        
        System.out.print("   Nuevo teléfono [" + cliente.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);
        
        System.out.print("   Nuevo email [" + (cliente.getEmail() != null ? cliente.getEmail() : "N/A") + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);
        
        System.out.print("   Nueva dirección [" + (cliente.getDireccion() != null ? cliente.getDireccion() : "N/A") + "]: ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) cliente.setDireccion(direccion);
        
        System.out.print("   Nuevo tipo entidad [" + cliente.getTipoEntidad() + "]: ");
        String tipoEntidad = scanner.nextLine();
        if (!tipoEntidad.isEmpty()) cliente.setTipoEntidad(tipoEntidad);
        
        if (clienteDAO.actualizar(cliente)) {
            System.out.println("✅ Cliente actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar el cliente.");
        }
    }
    
    private static void eliminarCliente() {
        System.out.print("\n🗑️ Ingrese el ID del cliente a eliminar: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        
        if (cliente == null) {
            System.out.println("❌ No se encontró un cliente con ID: " + id);
            return;
        }
        
        System.out.println("\n📄 Cliente a eliminar:");
        mostrarDetalleCliente(cliente);
        
        System.out.print("⚠️ ¿Está seguro de eliminar este cliente? (s/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            if (clienteDAO.eliminar(id)) {
                System.out.println("✅ Cliente eliminado correctamente.");
            } else {
                System.out.println("❌ Error al eliminar el cliente. Verifique que no tenga vehículos asociados.");
            }
        } else {
            System.out.println("❌ Eliminación cancelada.");
        }
    }
    
    // ==================== MENÚ VEHÍCULOS ====================
    
    private static void menuVehiculos() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   GESTIÓN DE VEHÍCULOS                        ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Listar todos los vehículos                               ║");
            System.out.println("║  2. Buscar vehículo por ID                                   ║");
            System.out.println("║  3. Buscar vehículo por placa                                ║");
            System.out.println("║  4. Buscar vehículos por cliente                             ║");
            System.out.println("║  5. Crear nuevo vehículo                                     ║");
            System.out.println("║  6. Actualizar vehículo                                      ║");
            System.out.println("║  7. Eliminar vehículo                                        ║");
            System.out.println("║  8. Volver al menú principal                                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.print("👉 Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    listarVehiculos();
                    break;
                case 2:
                    buscarVehiculoPorId();
                    break;
                case 3:
                    buscarVehiculoPorPlaca();
                    break;
                case 4:
                    buscarVehiculosPorCliente();
                    break;
                case 5:
                    crearVehiculo();
                    break;
                case 6:
                    actualizarVehiculo();
                    break;
                case 7:
                    eliminarVehiculo();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
    }
    
    private static void listarVehiculos() {
        System.out.println("\n📋 LISTADO DE VEHÍCULOS:\n");
        List<Vehiculo> vehiculos = vehiculoDAO.obtenerTodos();
        
        if (vehiculos.isEmpty()) {
            System.out.println("   No hay vehículos registrados.\n");
            return;
        }
        
        System.out.println("┌────┬──────────┬──────────────────┬──────────────────┬──────┬────────────────────────────┐");
        System.out.println("│ ID │ Placa    │ Marca            │ Modelo           │ Año  │ Cliente                     │");
        System.out.println("├────┼──────────┼──────────────────┼──────────────────┼──────┼────────────────────────────┤");
        
        for (Vehiculo v : vehiculos) {
            System.out.printf("│ %2d │ %-8s │ %-16s │ %-16s │ %4d │ %-26s │\n",
                    v.getIdVehiculo(),
                    v.getPlaca(),
                    truncar(v.getMarca(), 16),
                    truncar(v.getModelo(), 16),
                    v.getAño(),
                    truncar(v.getNombreCliente(), 26));
        }
        
        System.out.println("└────┴──────────┴──────────────────┴──────────────────┴──────┴────────────────────────────┘\n");
    }
    
    private static void buscarVehiculoPorId() {
        System.out.print("\n🔍 Ingrese el ID del vehículo: ");
        int id = leerOpcion();
        
        Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
        
        if (vehiculo == null) {
            System.out.println("❌ No se encontró un vehículo con ID: " + id);
            return;
        }
        
        mostrarDetalleVehiculo(vehiculo);
    }
    
    private static void buscarVehiculoPorPlaca() {
        System.out.print("\n🔍 Ingrese la placa del vehículo (formato: ABC-123): ");
        String placa = scanner.nextLine();
        
        Vehiculo vehiculo = vehiculoDAO.obtenerPorPlaca(placa);
        
        if (vehiculo == null) {
            System.out.println("❌ No se encontró un vehículo con placa: " + placa);
            return;
        }
        
        mostrarDetalleVehiculo(vehiculo);
    }
    
    private static void buscarVehiculosPorCliente() {
        System.out.print("\n🔍 Ingrese el ID del cliente: ");
        int idCliente = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(idCliente);
        
        if (cliente == null) {
            System.out.println("❌ No se encontró un cliente con ID: " + idCliente);
            return;
        }
        
        System.out.println("\n🚗 VEHÍCULOS DEL CLIENTE: " + cliente.getNombre() + "\n");
        List<Vehiculo> vehiculos = vehiculoDAO.obtenerPorCliente(idCliente);
        
        if (vehiculos.isEmpty()) {
            System.out.println("   Este cliente no tiene vehículos registrados.\n");
            return;
        }
        
        System.out.println("┌────┬──────────┬──────────────────┬──────────────────┬──────┐");
        System.out.println("│ ID │ Placa    │ Marca            │ Modelo           │ Año  │");
        System.out.println("├────┼──────────┼──────────────────┼──────────────────┼──────┤");
        
        for (Vehiculo v : vehiculos) {
            System.out.printf("│ %2d │ %-8s │ %-16s │ %-16s │ %4d │\n",
                    v.getIdVehiculo(),
                    v.getPlaca(),
                    truncar(v.getMarca(), 16),
                    truncar(v.getModelo(), 16),
                    v.getAño());
        }
        
        System.out.println("└────┴──────────┴──────────────────┴──────────────────┴──────┘\n");
    }
    
    private static void mostrarDetalleVehiculo(Vehiculo vehiculo) {
        System.out.println("\n📄 DETALLE DEL VEHÍCULO:\n");
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.printf("│ ID                    : %-37d │\n", vehiculo.getIdVehiculo());
        System.out.printf("│ Placa                 : %-37s │\n", vehiculo.getPlaca());
        System.out.printf("│ Marca                 : %-37s │\n", vehiculo.getMarca());
        System.out.printf("│ Modelo                : %-37s │\n", vehiculo.getModelo());
        System.out.printf("│ Año                   : %-37d │\n", vehiculo.getAño());
        System.out.printf("│ Color                 : %-37s │\n", vehiculo.getColor() != null ? vehiculo.getColor() : "N/A");
        System.out.printf("│ Kilometraje           : %-37d │\n", vehiculo.getKilometraje());
        System.out.printf("│ Cliente               : %-37s │\n", vehiculo.getNombreCliente());
        System.out.printf("│ Fecha Registro        : %-37s │\n", vehiculo.getFechaRegistro());
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
    }
    
    private static void crearVehiculo() {
        System.out.println("\n➕ CREAR NUEVO VEHÍCULO:\n");
        
        // Primero listar clientes para seleccionar
        System.out.println("📋 CLIENTES DISPONIBLES:\n");
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("❌ No hay clientes registrados. Primero debe crear un cliente.\n");
            return;
        }
        
        System.out.println("┌────┬──────────────────┬────────────────────────────────────────┐");
        System.out.println("│ ID │ NIT              │ Nombre                                 │");
        System.out.println("├────┼──────────────────┼────────────────────────────────────────┤");
        
        for (Cliente c : clientes) {
            System.out.printf("│ %2d │ %-16s │ %-38s │\n",
                    c.getIdCliente(),
                    c.getNit(),
                    truncar(c.getNombre(), 38));
        }
        System.out.println("└────┴──────────────────┴────────────────────────────────────────┘\n");
        
        System.out.print("   ID del cliente propietario: ");
        int idCliente = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(idCliente);
        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado.");
            return;
        }
        
        System.out.print("   Placa (formato: ABC-123): ");
        String placa = scanner.nextLine();
        
        // Verificar si ya existe
        if (vehiculoDAO.obtenerPorPlaca(placa) != null) {
            System.out.println("❌ Ya existe un vehículo con esa placa.");
            return;
        }
        
        System.out.print("   Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("   Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("   Año: ");
        int año = leerOpcion();
        
        System.out.print("   Color (opcional): ");
        String color = scanner.nextLine();
        if (color.isEmpty()) color = null;
        
        System.out.print("   Kilometraje (opcional): ");
        String kmStr = scanner.nextLine();
        int kilometraje = kmStr.isEmpty() ? 0 : Integer.parseInt(kmStr);
        
        Vehiculo vehiculo = new Vehiculo(placa, marca, modelo, año, idCliente);
        vehiculo.setColor(color);
        vehiculo.setKilometraje(kilometraje);
        
        if (vehiculoDAO.insertar(vehiculo)) {
            System.out.println("✅ Vehículo creado exitosamente con ID: " + vehiculo.getIdVehiculo());
        } else {
            System.out.println("❌ Error al crear el vehículo.");
        }
    }
    
    private static void actualizarVehiculo() {
        System.out.print("\n✏️ Ingrese el ID del vehículo a actualizar: ");
        int id = leerOpcion();
        
        Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
        
        if (vehiculo == null) {
            System.out.println("❌ No se encontró un vehículo con ID: " + id);
            return;
        }
        
        System.out.println("\n📄 Datos actuales del vehículo:");
        mostrarDetalleVehiculo(vehiculo);
        
        System.out.println("📝 Ingrese los nuevos datos (deje en blanco para mantener el valor actual):\n");
        
        System.out.print("   Nueva placa [" + vehiculo.getPlaca() + "]: ");
        String placa = scanner.nextLine();
        if (!placa.isEmpty()) vehiculo.setPlaca(placa);
        
        System.out.print("   Nueva marca [" + vehiculo.getMarca() + "]: ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) vehiculo.setMarca(marca);
        
        System.out.print("   Nuevo modelo [" + vehiculo.getModelo() + "]: ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) vehiculo.setModelo(modelo);
        
        System.out.print("   Nuevo año [" + vehiculo.getAño() + "]: ");
        String añoStr = scanner.nextLine();
        if (!añoStr.isEmpty()) vehiculo.setAño(Integer.parseInt(añoStr));
        
        System.out.print("   Nuevo color [" + (vehiculo.getColor() != null ? vehiculo.getColor() : "N/A") + "]: ");
        String color = scanner.nextLine();
        if (!color.isEmpty()) vehiculo.setColor(color);
        
        System.out.print("   Nuevo kilometraje [" + vehiculo.getKilometraje() + "]: ");
        String kmStr = scanner.nextLine();
        if (!kmStr.isEmpty()) vehiculo.setKilometraje(Integer.parseInt(kmStr));
        
        // Mostrar clientes para seleccionar nuevo
        System.out.println("\n📋 CLIENTES DISPONIBLES:\n");
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        
        System.out.println("┌────┬──────────────────┬────────────────────────────────────────┐");
        System.out.println("│ ID │ NIT              │ Nombre                                 │");
        System.out.println("├────┼──────────────────┼────────────────────────────────────────┤");
        
        for (Cliente c : clientes) {
            System.out.printf("│ %2d │ %-16s │ %-38s │\n",
                    c.getIdCliente(),
                    c.getNit(),
                    truncar(c.getNombre(), 38));
        }
        System.out.println("└────┴──────────────────┴────────────────────────────────────────┘\n");
        
        System.out.print("   Nuevo ID cliente [" + vehiculo.getIdCliente() + "]: ");
        String idClienteStr = scanner.nextLine();
        if (!idClienteStr.isEmpty()) {
            vehiculo.setIdCliente(Integer.parseInt(idClienteStr));
        }
        
        if (vehiculoDAO.actualizar(vehiculo)) {
            System.out.println("✅ Vehículo actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar el vehículo.");
        }
    }
    
    private static void eliminarVehiculo() {
        System.out.print("\n🗑️ Ingrese el ID del vehículo a eliminar: ");
        int id = leerOpcion();
        
        Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
        
        if (vehiculo == null) {
            System.out.println("❌ No se encontró un vehículo con ID: " + id);
            return;
        }
        
        System.out.println("\n📄 Vehículo a eliminar:");
        mostrarDetalleVehiculo(vehiculo);
        
        System.out.print("⚠️ ¿Está seguro de eliminar este vehículo? (s/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            if (vehiculoDAO.eliminar(id)) {
                System.out.println("✅ Vehículo eliminado correctamente.");
            } else {
                System.out.println("❌ Error al eliminar el vehículo. Verifique que no tenga órdenes asociadas.");
            }
        } else {
            System.out.println("❌ Eliminación cancelada.");
        }
    }
    
    // ==================== MÉTODO AUXILIAR ====================
    
    private static String truncar(String texto, int longitud) {
        if (texto == null) return "N/A";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 3) + "...";
    }
}