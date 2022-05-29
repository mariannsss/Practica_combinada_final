package GestionBD;
import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Esta clase se encarga de interactuar con la Base de Datos
 * 
 * @author marian
 * @version 2.2
 */
public class DBManager {

	 // Conexion a la base de datos
    private static Connection conn = null;

    // Configuracion de la conexion a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    
    private static final String DB_USER = "root";
    private static final String DB_PASS = "minamino";

    private static final String DB_SELECT_GENERICO = "SELECT * FROM ";    
   
    private static final String DB_ID = "id";
    private static final String DB_CLI_NOM = "nombre";
    private static final String DB_CLI_DIR = "direccion";

    //////////////////////////////////////////////////
    // METODOS DE CONEXION A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;       

    /**
     * Intenta conectar con la base de datos.
     *
     * @author marian
     * @version 2.1
     * @return true si pudo conectarse, false en caso contrario
     * @throws SQLException. Error en la conexion
     */
    public static boolean connect(String nombre) 
    {
    	String DB_NAME = nombre;
        String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    	
    	try 
        {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } 
        catch (SQLException ex) 
    	{
        	System.out.println("No se pudo conectar con la base de datos"); 
        	System.out.println("Compruebe el nombre del base de datos, usuario y contraseña del servidor");
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
        catch (SQLException ex) 
        {
        	System.out.println("Error al cerrar la conexion");
        }
    }

    
    //////////////////////////////////////////////////
    // METODOS DE TABLA CLIENTES
    //////////////////////////////////////////////////
    ;
    
    /**
     * Obtiene toda la tabla de la base de datos
     * 
     * @author marian
     * @version 2.2
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     * @throws SQLException.
     */
    public static ResultSet getTabla(int resultSetType, int resultSetConcurrency, String SELECT_TABLA) 
    {
        try 
        {
        	//Realizamos la consulta y devolvemos el resultado
        	PreparedStatement pstmt = conn.prepareStatement(SELECT_TABLA, resultSetType, resultSetConcurrency);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } 
        catch (SQLException ex) 
        {
        	System.out.println("Tabla no encontrada");
            return null;
        }
    }
    
    /**
     * Imprime por pantalla el contenido de la tabla
     * 
     * @author marian
     * @version 2.2
     * @throws SQLException
     */
    public static void printTabla(String SELECT_TABLA) 
    {
        try 
        {
            ResultSet rs = getTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,SELECT_TABLA);
            while (rs.next()) //imorime el resultado
            {
                int col1id = rs.getInt(1);
                String col2 = rs.getString(2);
                String col3 = rs.getString(3);
                System.out.println(col1id + "\t" + col2 + "\t" + col3);
            }
            rs.close(); //cierra el resultSet
        } 
        catch (SQLException ex) 
        {
        	System.out.println("Hubo un error al mostrar la tabla");
        }
    }

    //////////////////////////////////////////////////
    // METODOS DE UN SOLO CLIENTE
    //////////////////////////////////////////////////
    ;
    
    /**
     * Solicita a la BD el tupla/fila con id indicado
     * 
     * @author marian
     * @version 2.2
     * @param id. id de la tupla
     * @return ResultSet con el resultado de la consulta, null en caso de error
     * @throws SQLException
     */
    public static ResultSet getTupla(int id, String SELECT_TABLA) 
    {
        try 
        {
            // Realizamos la consulta SQL
        	String sql = SELECT_TABLA + " WHERE " + DB_ID + "='" + id + "';";
        	PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            ResultSet rs = pstmt.executeQuery();            
           
            if (!rs.first())  // Si no hay primer registro entonces no existe
            {
            	System.out.println(id + " no registrado");
            	return null;
            }            
            return rs; // Todo bien, devolvemos resultado
        } 
        catch (SQLException ex) 
        {
        	System.out.println(id + " no existe");
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe la tupla/fila con id indicado
     *
     * @author marian
     * @version 2.2
     * @param id id de la tupla
     * @return verdadero si existe, false en caso contrario
     * @throws SQLException
     */
    public static boolean existsTupla(int id, String SELECT_TABLA) 
    {
        try 
        {            
            ResultSet rs = getTupla(id, SELECT_TABLA); // Obtenemos el cliente
           
            if (rs == null || !rs.first())  // no existe
            {
            	System.out.println("no existe");
            	return false;
            }
           
            rs.close();
            return true;  // Todo bien, existe
        } 
        catch (SQLException ex) 
        {
        	System.out.println("Error al comprobar si el id existe");
            return false;
        }
    }

    /**
     * Imprime los datos de la tupla/fila con id indicado
     *
     * @author marian
     * @version 2.2
     * @param id id de la tupla
     * @throws SQLException
     */
    public static void printTupla(int id, String SELECT_TABLA) 
    {
        try 
        {           
            ResultSet rs = getTupla(id, SELECT_TABLA); // Obtenemos  la tupla
                       
            if (rs == null || !rs.first())  //si no esta
            {
                System.out.println(id + " no existe");

            } else {
            
            // Imprimimos su informacion por pantalla
            int col1id = rs.getInt(DB_ID);
            String col2 = rs.getString(2);
            String col3 = rs.getString(3);
            System.out.println(col1id + "\t" + col2 + "\t" + col3);
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error al solicitar " + id);
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro en una tabla
     *
     * @author marian
     * @version 2.2
     * @param campo2. Primer valor a insertar (en la columna 2)
     * @param campo3. Segundo valor a insertar (en la columna 3)
     * @return verdadero si pudo insertardo, false en caso contrario
     * @throws SQLException
     */
    public static boolean insertTupla(String campo2, String campo3, String INSERT_TABLA) 
    {
        try 
        {
            // Obtenemos la tabla clientes
            System.out.print("Insertando...");            
            PreparedStatement pstmt = conn.prepareStatement(INSERT_TABLA, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            
            //Insertamos el nuevo cliente
            pstmt.setString(1, campo2);
            pstmt.setString(2,campo3);            
            int row = pstmt.executeUpdate();

            System.out.println(row);
            System.out.println("OK!");
            return true;
        } 
        catch (SQLException ex) 
        {
        	System.out.println("Fallo al insertar");
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de una tupla/fila
     *
     * @author marian
     * @version 2.2 
     * @param id. id de la tupla a modificar
     * @param nuevoCampo2. nuevo valor del campo 2
     * @param nuevoCampo3. nuevo valor del campo 3
     * @return verdadero si pudo modificarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean updateTupla(int id, String nuevoCampo2, String nuevoCampo3, String SELECT_TABLA) 
    {
        try 
        {
            // Obtenemos la tupla
            System.out.print("Actualizando " + id + "... ");
            ResultSet rs = getTupla(id, SELECT_TABLA);
           
            if (rs == null)  // Si no existe el Resultset
            {
                System.out.println(id + " no existe");
                return false;
            }
            
            if (rs.first()) // Si tiene un primer registro, lo eliminamos
            {
                rs.updateString(DB_CLI_NOM, nuevoCampo2);
                rs.updateString(DB_CLI_DIR, nuevoCampo3);
                rs.updateRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } 
            else 
            {
                System.out.println("No se encontro resultado.");
                return false;
            }
        } 
        catch (SQLException ex) 
        {
        	System.out.println("Fallo al actualizar");
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar una tupla/fila
     *
     * @author marian
     * @version 2.2 
     * @param id. id de la tupla a eliminar
     * @return verdadero si pudo eliminarlo, false en caso contrario
     * @throws SQLException
     */
    public static boolean deleteTupla(int id, String SELECT_TABLA) 
    {
        try 
        {
            System.out.print("Eliminando " + id + "... ");

            // Obtenemos el cliente
            ResultSet rs = getTupla(id, SELECT_TABLA);
            
            if (rs == null) // Si no existe el Resultset
            {
                System.out.println(id + " no existe");
                return false;
            }
            
            if (rs.first()) // Si existe y tiene primer registro, lo eliminamos
            {
                rs.deleteRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } 
            else 
            {
                System.out.println("No se encontro resultado");
                return false;
            }
        } 
        catch (SQLException ex) {
        	System.out.println("Fallo al borrar");
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
			
			
			while (rs.next()) //Imprimimos su informacion por pantalla
			{
				int cid = rs.getInt(DB_ID);
				String nombre = rs.getString(DB_CLI_NOM);
				String direccion = rs.getString(DB_CLI_DIR);
				System.out.println("Cliente " + cid + "\t" + nombre + "\t" + direccion);
			}
			rs.close();
		} 
    	catch (SQLException ex) 
    	{
    		System.out.println("Fallo al llamar al procedimiento almacenado");
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
    		//Realizamos la consulta sql
    		System.out.print("Insertando tabla " + nombreTabla + "... ");     
        	String sql = "CREATE TABLE " + nombreTabla + "(`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT);";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		int row = pstmt.executeUpdate();
            System.out.println("ok");
           
        } 
        catch (SQLException ex) {
        	System.out.println("Error al crear la tabla");
        }    			
    }
    
    /**
     * Busca una fila concreta en una tabla y la muestra
     * 
     * @author marian
     * @version 1.2
     * @param nombreTabla. nombre de la tabla en la que buscar la fila
     * @param numero. numero de la fila que buscar
     * @throws SQLException
     */
    public static void filtrarFilas(String nombreTabla, int numero)
    {
    	try 
        {
    		System.out.println("Filtrar filas de la tabla " + nombreTabla + "... ");     
        	String sql = DB_SELECT_GENERICO + nombreTabla;
    		PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if (rs == null ) //Si no encuentra la fila
    		{
                System.out.println("Fila no encontrada");
                return;
            }
    		
    		if (rs.absolute(numero)) //imprime los campos
    		{
    			int col1id = rs.getInt(DB_ID);
                String col2 = rs.getString(2);
                String col3 = rs.getString(3);
                System.out.println("Cliente " + col1id + "\t" + col2 + "\t" + col3);
            } 
        } 
        catch (SQLException ex) {
        	System.out.println("Error al filtrar las filas");
        }    			
    }
    
    /**
     * Vuelca los datos del fichero en la base de datos
     * 
     * @author marian
     * @version 1.2
     * @param bbdd. Base de datos
     * @throws SQLException
     * @throws IOException
     */
    public static void volcarEnFichero (BBDD bbdd)
    {    	
    	try 
        {
    		File destino = new File("DatosTabla.txt"); //crear la ruta donde guardar los datos
    		FileWriter escribir = new FileWriter(destino); //abre el escritor
    		
            ResultSet rs = getTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,bbdd.SELECT_TABLA());
            
            escribir.write(bbdd.getNombreBBDD() + "," + bbdd.getNombreTabla() +"\n");
            escribir.write(BBDD.DB_CLI_ID + "," + BBDD.DB_CLI_NOM  + "," + BBDD.DB_CLI_DIR +"\n");
            while (rs.next()) 
            {
            	escribir.write(rs.getInt(DB_ID) + "," + 
            			rs.getString(2) + "," + 
            			rs.getString(3) + "\n");
            }
            escribir.close(); //cierro el escritor
            rs.close();
            System.out.println("Fichero DatosTabla.txt creado");
        } 
        catch (SQLException ex) 
    	{
        	System.out.println("Error al mostrar la tabla");
        }
    	catch (IOException e)
		{
			System.err.println("E/S fallidas o interrumpidas");
		} 
    }
    
    /**
     * Inserta datos de un fichero en una tabla de la base de datos
     * 
     * @author marian
     * @version 1.2
     * @throws FileNotFoundException
     */
    public static void insertardeFichero()
    {	
		try 
		{
			File leer = new File ("Archivos/archivoInsertar.csv"); //instancio un archivo con la ruta que va a leer			
			Scanner lector = new Scanner(leer); //abro el lector
			
			ArrayList <String> archivo = new ArrayList <String>(); //ArrayList para guardar lo leido
			
			while (lector.hasNext()) //leo el archivo, lo guardo en un ArrayList y cierro el lector
			{
				String archivoLeido = lector.nextLine().replaceAll("\"", "");
				archivo.add(archivoLeido); 			
			} 
			lector.close(); //cierro el lector
	    	
			//conectarse a bbdd y a la tabla
			BBDD bbdd = new BBDD(archivo.get(0),archivo.get(1));
			DBManager.connect(bbdd.getNombreBBDD());
			
			for (int i = 3; i<archivo.size(); i++) 
			{
				String[] parte = archivo.get(i).split(","); 			
				DBManager.insertTupla(parte[0],parte[1],bbdd.INSERT_TABLA());
			}		
		} 
		catch (FileNotFoundException e)
		{
			System.err.println("Archivo no encontrado");
		}     	
    }
    
    /**
     * actualiza con los datos de un fichero, una tabla de la base de datos
     * 
     * @author marian
     * @version 1.2
     * @throws FileNotFoundException
     */
    public static void actualizarFichero()
    {		
		try 
		{
			File leer = new File ("Archivos/archivoActualizar.csv"); //instancio un archivo con la ruta que va a leer
			Scanner lector = new Scanner(leer);  //abro el lector
			
			ArrayList <String> archivo = new ArrayList <String>(); //ArrayList para guardar lo leido
			
			while (lector.hasNext()) //leo el archivo, lo guardo en un ArrayList y cierro el lector
			{
				String archivoLeido = lector.nextLine().replaceAll("\"", "");
				archivo.add(archivoLeido); 			
			} 
			lector.close();
			
			//conectarse a bbdd y a la tabla
			BBDD bbdd = new BBDD(archivo.get(0),archivo.get(1));
			DBManager.connect(bbdd.getNombreBBDD());		
			
			for (int i = 3; i<archivo.size();i++) 
			{
				String[] parte = archivo.get(i).split(","); 			
				DBManager.updateTupla(Integer.parseInt(parte[0]),parte[1],parte[2],bbdd.SELECT_TABLA());
			}	
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("Archivo no encontrado");
		}	
				    	
    }
    
    /**
     * borra con los datos de un fichero, datos de una tabla de la base de datos
     * 
     * @author marian
     * @version 1.2
     * @throws FileNotFoundException
     */
    public static void borrarFichero()
    {	
		try 
		{
			File leer = new File ("Archivos/archivoBorrar.csv"); //instancio un archivo con la ruta que va a leer			
			Scanner lector = new Scanner(leer); //abro el lector
			
			ArrayList <String> archivo = new ArrayList <String>(); //ArrayList para guardar lo leido
			
			while (lector.hasNext()) //leo el archivo, lo guardo en un ArrayList y cierro el lector
			{
				String archivoLeido = lector.nextLine().replaceAll("\"", "");
				archivo.add(archivoLeido); 			
			} 
			lector.close();
	    	
			//conectarse a bbdd y a la tabla
			BBDD bbdd = new BBDD(archivo.get(0),archivo.get(1));
			DBManager.connect(bbdd.getNombreBBDD());	
			
			String[] parte = archivo.get(2).split(","); 	
			
			for (int i = 0;i<parte.length;i++) 
			{			
				DBManager.deleteTupla(Integer.parseInt(parte[i]), bbdd.SELECT_TABLA());
			}			
		} 
		catch (FileNotFoundException e)
		{
			System.err.println("Archivo no encontrado");
		}	
    }
}