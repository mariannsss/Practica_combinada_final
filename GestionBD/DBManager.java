package GestionBD;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Esta clase se encarga de interactuar con la Base de Datos
 * 
 * @author lionel
 * @version 1.0
 */
public class DBManager 
{
    // Conexion a la base de datos
    private static Connection conn = null;

    // Configuracion de la conexion a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "tienda";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "minamino";
    
    private static final String DB_MSQ_CONN_OK = "CONEXION CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXION";

    // Configuracion de la tabla Clientes
    private static final String DB_CLI = "clientes";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
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
     *  @author lionel
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
            ex.printStackTrace();
            return false;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @author lionel
     * @version 1.0
     * @return true si pudo conectarse, false en caso contrario
     * @throws SQLException. Error en la conexion
     */
    public static boolean connect() 
    {
        try 
        {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexion y muestra su estado por pantalla
     *
     * @author lionel
     * @version 1.0
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
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexion con la base de datos
     * 
     * @author lionel
     * @version 1.0
     * @throws SQLException. Error en la conexion
     */
    public static void close() 
    {
        try 
        {
            System.out.print("Cerrando la conexion...");
            conn.close();
            System.out.println("OK!");
        } 
        catch (SQLException ex) {
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
     * @author lionel
     * @version 1.0
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     * @throws SQLException.
     */
    public static ResultSet getTablaClientes(int resultSetType, int resultSetConcurrency) 
    {
        try 
        {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_CLI_SELECT);
            //stmt.close();
            return rs;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene toda la tabla clientes de la base de datos
     *
     * @author lionel
     * @version 1.0
     * @return ResultSet (por defecto) con la tabla, null en caso de error
     * @throws SQLException.
     */
    public static ResultSet getTablaClientes() 
    {
        return getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Imprime por pantalla el contenido de la tabla clientes
     * 
     * @author lionel
     * @version 1.0
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
     * @author lionel
     * @version 1.0  
     * @param id. id del cliente
     * @return ResultSet con el resultado de la consulta, null en caso de error
     * @throws SQLException
     */
    public static ResultSet getCliente(int id) 
    {
        try 
        {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = DB_CLI_SELECT + " WHERE " + DB_CLI_ID + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();
            
            // Si no hay primer registro entonces no existe el cliente
            if (!rs.first()) 
            {
                return null;
            }

            // Todo bien, devolvemos el cliente
            return rs;

        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe el cliente con id indicado
     *
     * @author lionel
     * @version 1.0
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
                return false;
            }

            // Si no existe primer registro
            if (!rs.first()) 
            {
                rs.close();
                return false;
            }

            // Todo bien, existe el cliente
            rs.close();
            return true;

        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Imprime los datos del cliente con id indicado
     *
     * @author lionel
     * @version 1.0
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
            System.out.println("Error al solicitar cliente " + id);
            ex.printStackTrace();
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro cliente
     *
     * @author lionel
     * @version 1.0
     * @param nombre nombre del cliente
     * @param direccion direccion del cliente
     * @return verdadero si pudo insertarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean insertCliente(String nombre, String direccion) 
    {
        try 
        {
            // Obtenemos la tabla clientes
            System.out.print("Insertando cliente " + nombre + "...");
            ResultSet rs = getTablaClientes(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CLI_NOM, nombre);
            rs.updateString(DB_CLI_DIR, direccion);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de un cliente
     *
     * @author lionel
     * @version 1.0
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
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar un cliente
     *
     * @author lionel
     * @version 1.0
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
            ex.printStackTrace();
            return false;
        }
    }
}
