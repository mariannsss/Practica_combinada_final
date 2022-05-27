package GestionBD;
import java.sql.*;

/**
 * Esta clase se encarga de interactuar con la Base de Datos
 * 
 * @author marian
 * @version 1.2
 */
public class DBManager {

    // Conexion a la base de datos
    private static Connection conn = null;

    // Configuracion de la conexion a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    
    private static final String DB_USER = "root";
    private static final String DB_PASS = "minamino";
    
    private static final String DB_MSQ_CONN_OK = "CONEXION CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXION";

    // Configuracion de la tabla Clientes
    private static final String DB_CLI = "clientes";
    private static final String DB_CLI_INSERT = "INSERT INTO clientes (nombre, direccion) VALUES (?, ?)";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    private static final String DB_SELECT = "SELECT * FROM ";
    private static final String DB_CLI_WHERE = "WHERE nombre = ? AND direccion = ?";
    private static final String DB_CLI_ID = "id";
    private static final String DB_CLI_NOM = "nombre";
    private static final String DB_CLI_DIR = "direccion";

    //////////////////////////////////////////////////
    // METODOS DE CONEXION A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;
    
    /**
     * Intenta cargar el JDBC driver.
     * 
     * @author lionel
     * @version 1.0
     * @return true si pudo cargar el driver, false en caso contrario
     * @throws ClassNotFoundException
     * @throws Exception
     * @deprecated no es necesario cargar el driver
     */
    public static boolean loadDriver() 
    {
        try 
        {
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("OK!");
            return true;
        } 
        catch (ClassNotFoundException ex) {
        	System.out.println("Exception. fallo al con los parametros del driver");
            ex.printStackTrace();
            return false;
        } 
        catch (Exception ex) {
        	System.out.println("Exception. fallo al cargar el driver");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @author marian
     * @version 1.1
     * @return true si pudo conectarse, false en caso contrario
     * @throws SQLException. Error en la conexion
     */
    public static boolean connect(String nombre) {
    	String DB_NAME = nombre;
        String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    	
    	try 
        {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al conectar");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexion y muestra su estado por pantalla
     *
     * @author marian
     * @version 1.1
     * @return true si la conexion existe y es valida, false en caso contrario
     * @throws SQLException. Error en la conexion
     */
    public static boolean isConnected()
    {        
        try 
        {
            if (conn != null && conn.isValid(0)) //si la conexion es correcta
            {
                System.out.println(DB_MSQ_CONN_OK);
                return true;
            } 
            else 
            {
                return false;
            }
        } 
        catch (SQLException ex) {
            System.out.println("Exception." + DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexion con la base de datos
     * 
     * @author marian
     * @version 1.1
     * @throws SQLException. Error en la conexion
     */
    public static void close() 
    {
        try {
            System.out.print("Cerrando la conexion...");
            conn.close();
            System.out.println("OK!");
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al cerrar la conexion");
            ex.printStackTrace();
        }
    }

    
    //////////////////////////////////////////////////
    // METODOS DE TABLA CLIENTES
    //////////////////////////////////////////////////
    ;
    
    // Devuelve los argumentos indican el tipo de ResultSet deseado
    
    /**
     * Obtiene toda la tabla clientes de la base de datos
     * 
     * @author marian
     * @version 1.2
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     * @throws SQLException.
     */
    public static ResultSet getTablaClientes(int resultSetType, int resultSetConcurrency) 
    {
        try 
        {
        	PreparedStatement pstmt = conn.prepareStatement(DB_CLI_SELECT, resultSetType, resultSetConcurrency);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al solicitar la tabla");
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Imprime por pantalla el contenido de la tabla clientes
     * 
     * @author marian
     * @version 1.2
     * @throws SQLException
     */
    public static void printTablaClientes() 
    {
        try 
        {
            ResultSet rs = getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            while (rs.next()) 
            {
                int id = rs.getInt(DB_CLI_ID);
                String n = rs.getString(DB_CLI_NOM);
                String d = rs.getString(DB_CLI_DIR);
                System.out.println(id + "\t" + n + "\t" + d);
            }
            rs.close();
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al mostrar la tabla");
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // METODOS DE UN SOLO CLIENTE
    //////////////////////////////////////////////////
    ;
    
    /**
     * Solicita a la BD el cliente con id indicado
     * 
     * @author marian
     * @version 1.2
     * @param id. id del cliente
     * @return ResultSet con el resultado de la consulta, null en caso de error
     * @throws SQLException
     */
    public static ResultSet getCliente(int id) 
    {
        try 
        {
            // Realizamos la consulta SQL
        	String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "='" + id + "';";
        	PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            ResultSet rs = pstmt.executeQuery();
            
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) 
            {
            	System.out.println("cliente no registrado");
            	return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Hubo algun error al buscar al cliente");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe el cliente con id indicado
     *
     * @author marian
     * @version 1.2
     * @param id id del cliente
     * @return verdadero si existe, false en caso contrario
     * @throws SQLException
     */
    public static boolean existsCliente(int id) 
    {
        try 
        {
            // Obtenemos el cliente
            ResultSet rs = getCliente(id);

            // Si rs es null, se ha producido un error
            if (rs == null) 
            {
            	System.out.println("hubo algun error. vuelva a comprobar si el cliente existe");
            	return false;
            }

            // Si no existe primer registro
            if (!rs.first()) 
            {
            	System.out.println("el cliente no existe");
                rs.close();
                return false;
            }

            // Todo bien, existe el cliente
            rs.close();
            System.out.println("el cliente si existe");
            return true;

        } 
        catch (SQLException ex) {
        	System.out.println("Exception. hubo algun error al comprobar si el cliente existe");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Imprime los datos del cliente con id indicado
     *
     * @author marian
     * @version 1.1
     * @param id id del cliente
     * @throws SQLException
     */
    public static void printCliente(int id) 
    {
        try 
        {
            // Obtenemos el cliente
            ResultSet rs = getCliente(id);
            
            //si no esta el cliente
            if (rs == null || !rs.first()) 
            {
                System.out.println("Cliente " + id + " NO EXISTE");
                return;
            }
            
            // Imprimimos su informacion por pantalla
            int cid = rs.getInt(DB_CLI_ID);
            String nombre = rs.getString(DB_CLI_NOM);
            String direccion = rs.getString(DB_CLI_DIR);
            System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);

        } 
        catch (SQLException ex) {
            System.out.println("Exception. Error al solicitar cliente " + id);
            ex.printStackTrace();
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro cliente
     *
     * @author marian
     * @version 1.1
     * @param nombre. nombre del cliente
     * @param direccion. direccion del cliente
     * @return verdadero si pudo insertarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean insertCliente(String nombre, String direccion) 
    {
        try 
        {
            // Obtenemos la tabla clientes
            System.out.print("Insertando cliente " + nombre + "...");            
            PreparedStatement pstmt = conn.prepareStatement(DB_CLI_INSERT, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            
            //Insertamos el nuevo cliente
            pstmt.setString(1, nombre);
            pstmt.setString(2,direccion);            
            int row = pstmt.executeUpdate();

            System.out.println(row);
            System.out.println("OK!");
            return true;
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. fallo al insertar cliente");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de un cliente
     *
     * @author marian
     * @version 1.1
     *
     * @param id id del cliente a modificar
     * @param nombre nuevo nombre del cliente
     * @param direccion nueva direccion del cliente
     * @return verdadero si pudo modificarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean updateCliente(int id, String nuevoNombre, String nuevaDireccion) 
    {
        try 
        {
            // Obtenemos el cliente
            System.out.print("Actualizando cliente " + id + "... ");
            ResultSet rs = getCliente(id);

            // Si no existe el Resultset
            if (rs == null) 
            {
                System.out.println("Error. Resultado nulo");
                return false;
            }

            // Si tiene un primer registro, lo eliminamos
            if (rs.first()) 
            {
                rs.updateString(DB_CLI_NOM, nuevoNombre);
                rs.updateString(DB_CLI_DIR, nuevaDireccion);
                rs.updateRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } 
            else 
            {
                System.out.println("ERROR. Resultado vacio.");
                return false;
            }
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. fallo al actualizar cliente");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar un cliente
     *
     * @author marian
     * @version 1.1
     * 
     * @param id. id del cliente a eliminar
     * @return verdadero si pudo eliminarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean deleteCliente(int id) 
    {
        try 
        {
            System.out.print("Eliminando cliente " + id + "... ");

            // Obtenemos el cliente
            ResultSet rs = getCliente(id);

            // Si no existe el Resultset
            if (rs == null) 
            {
                System.out.println("ERROR. Resultado nulo");
                return false;
            }

            // Si existe y tiene primer registro, lo eliminamos
            if (rs.first()) 
            {
                rs.deleteRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } 
            else 
            {
                System.out.println("ERROR. Resultado vacio.");
                return false;
            }
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. fallo al borrar cliente");
            ex.printStackTrace();
            return false;
        }
    }
    
    
    //////////////////////////////////////////////////
    // OTROS  METODOS
    //////////////////////////////////////////////////
    
    /**
     * Solicita a la BD los 5 primeros clientes mediante un procedimiento almacenado
     * 
     * @author marian
     * @version 1.0
     * @throws SQLException
     */
    public static void primerosClientes()
    {
    	try 
    	{
    		//llamamos al procedimiento almacenado
			CallableStatement cstmt = conn.prepareCall("{CALL primeros_clientes()}");
			ResultSet rs = cstmt.executeQuery();
			
			// Imprimimos su informacion por pantalla
			while (rs.next())
			{
				int cid = rs.getInt(DB_CLI_ID);
				String nombre = rs.getString(DB_CLI_NOM);
				String direccion = rs.getString(DB_CLI_DIR);
				System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);
			}
			rs.close();
		} 
    	catch (SQLException ex) {
    		System.out.println("Exception. fallo al llamar al procedimiento almacenado");
			ex.printStackTrace();
		}
    }
    
    /**
     * Solicita a la BD crear una nueva tabla
     * 
     * @author marian
     * @version 1.0
     * @param nombreTabla. nombre de la nueva tabla
     * @throws SQLException
     */
    public static void crearTabla (String nombreTabla)
    {
    	try 
        {
    		System.out.print("Insertando tabla " + nombreTabla + "... ");     
        	String sql = "CREATE TABLE " + nombreTabla + "(`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT);";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		int row = pstmt.executeUpdate();
            System.out.println("ok");
           
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al crear la tabla");
            ex.printStackTrace();
        }    			
    }
    
    /**
     * Busca una fila concreta en una tabla y la muestra
     * 
     * @param nombreTabla. nombre de la tabla en la que buscar la fila
     * @param numero. numero de la fila que buscar
     */
    public static void filtrarFilas(String nombreTabla, int numero)
    {
    	try 
        {
    		System.out.println("Filtrar filas de la tabla " + nombreTabla + "... ");     
        	String sql = DB_SELECT + nombreTabla;
    		PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if (rs == null ) //Si no encuentra la fila
    		{
                System.out.println("Fila no encontrada");
                return;
            }
    		
    		if (rs.absolute(numero)) 
    		{
    			int cid = rs.getInt(DB_CLI_ID);
                String nombre = rs.getString(DB_CLI_NOM);
                String direccion = rs.getString(DB_CLI_DIR);
                System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);
            } 
        } 
        catch (SQLException ex) {
        	System.out.println("Exception. Ha habido un error al filtrar las filas");
            ex.printStackTrace();
        }    			
    }
}