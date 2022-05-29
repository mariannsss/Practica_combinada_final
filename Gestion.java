import GestionBD.DBManager;
import GestionBD.BBDD;
import java.util.Scanner;

/**
 *Esta clase se encarga de interactuar con el usuario y llamar a los metodos de la clase DBManager
 *
 * @author marian
 * @version 2.1
 */
public class Gestion 
{
	/**
	 * Clase principal. Establece la conexion y llama al menu
	 * 
	 * @author Marian
	 * @version 2.2
	 * @param args
	 */
    public static void main(String[] args) 
    {
    	// Menu para seleccionar la BBDD
        boolean salir = false;
        Menu menu = new Menu();
        
        do 
        {
            salir = menu.getMenuPrincipal();
            
        } while (!salir);

        DBManager.close(); //cierra la conexion
    }
    
    /**
     * Pide un numero (opcion del menu)
     * 
     * @author marian
     * @version 1.1
     * @param mensaje
     * @return valor. devuelve el numero de la opcion elegida
     * @throws Exception. No se ha introducido un entero
     */
    public static int pideInt (String mensaje)
    {        
        while (true) 
        {
            try 
            {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                int valor = in.nextInt();
                //in.nextLine();
                return valor;
            } 
            catch (Exception e) {
                System.out.println("No has introducido un numero entero. Vuelve a intentarlo.");
            }
        }
    }
    
    /**
     * pide un String (cadena de texto)
     * 
     * @author marian
     * @version 1.1
     * @return linea. devuelve un String
     * @throws Exception. No se ha introducido un String
     */
    public static String pideLinea (String mensaje)
    {        
        while(true) 
        {
            try 
            {
                System.out.print(mensaje);
                Scanner in = new Scanner(System.in);
                String linea = in.nextLine();
                return linea;
            } 
            catch (Exception e) {
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }

    /**
     * Llama a printTabla y muestra la tabla
     * 
     * @author marian
     * @version 1.2
     */
    public static void opcionMostrarTabla(String SELECT_TABLA) 
    {
        System.out.println();
    	System.out.println("Listado de Clientes:");
        DBManager.printTabla(SELECT_TABLA);
    }

    /**
     * Pide los datos para crear un nuevo registro en la tabla
     * llama a pideLinea para obtener los parametros necesarios
     * llama a insertCliente para insertar el nuevo registro
     * 
     * @author marian
     * @version 1.2
     */
    public static void opcionNuevoRegistro(String INSERT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("Introduce los datos del nuevo registro:");
        String col2 = pideLinea("Columna 2: ");
        String col3 = pideLinea("columna 3: ");

        DBManager.insertTupla(col2, col3, INSERT_TABLA);
    }

    /**
     * Modifica un registro de la tabla
     * llama a pideInt para obtener el id del registro a modificar
     * llama a pideLinea para obtener los los nuevos valores
     * llama a updateCliente para actualizar los datos
     * 
     * @author marian
     * @version 1.2
     */
    public static void opcionModificarRegistro(String SELECT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del registro a modificar: ");
        System.out.println();

        // Comprobamos si existe el registro
        if (!DBManager.existsTupla(id, SELECT_TABLA)) 
        {
            System.out.println(id + " no existe.");
            return;
        }

        // Mostramos datos del registro a modificar
        DBManager.printTupla(id, SELECT_TABLA);

        // Solicitamos los nuevos datos
        String col2 = pideLinea("Nuevo campo de la columna 2: ");
        String col3 = pideLinea("Nuevo campo de la columna 3: ");

        // Registramos los cambios
        DBManager.updateTupla(id, col2, col3, SELECT_TABLA);
    }

    /**
     * Elimina un registro de la tabla
     * llama a pideInt para obtener el id del registro a borrar
     * llama a deleteTupla para borrar el registro indicado
     * 
     * @author marian
     * @version 1.2
     */
    public static void opcionEliminarRegistro(String SELECT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");
        System.out.println();

        // Comprobamos si existe el cliente
        if (!DBManager.existsTupla(id, SELECT_TABLA)) 
        {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Eliminamos el cliente
        DBManager.deleteTupla(id, SELECT_TABLA);
    }
    
    /**
     * Llama a primerosClientes y muestra  los 5 primeros clientes de la tabla clientes
     * 
     * @authormarian
     * @version 1.1
     */
    public static void opcionPrimerosClientes() 
    {
    	System.out.println();
    	System.out.println("Listado de los 5 primeros Clientes:");
    	DBManager.primerosClientes();
    }
    
    /**
     * Añade una nueva tabla a la base de datos
     * llama a pideLinea para obtener el nombre de la nueva tabla
     * llama a crearTabla y crea la tabla con el nombre solicitado
     * 
     * @author marian
     * @version 1.0
     */
    public static void opcionCrearTabla ()
    {
    	Scanner in = new Scanner(System.in);

        System.out.println();
        String nombreTab = pideLinea("Nombre de la nueva tabla: ");
        
        DBManager.crearTabla(nombreTab);
    }
    
    /**
     * Filtra filas de una tabla, Muestra la fila indicada
     * llama a pideInt para obtener el numero de fila
     * llama a pideLinea para obtener le nombre de la tabla
     * llama a filtrarFilas para buscar la fila
     * 
     * @author marian
     * @version 1.1 
     */
    public static void opcionFiltrarFilas ()
    {
    	Scanner in = new Scanner(System.in);

        System.out.println();
        String nombreTab = pideLinea("Nombre de la tabla que quiere filtrar: ");
        int numero = pideInt("Numero de fila que desea ver: ");
        
        DBManager.filtrarFilas(nombreTab, numero);
    }
    
    /**
     * Vuelca los datos de una tabla en un fichero
     * Llama a volcarEnFichero para volcar el fichero
     * 
     * @author marian
     * @version 1.1 
     * @param bbdd. Base de datos
     */
    public static void opcionVolcarEnFichero(BBDD bbdd)
    {
    	DBManager.volcarEnFichero (bbdd);
    }
    
    /**
     * Inserta los datos de un fichero en una tabla
     * Llama a insertardeFichero para insertar los datos
     * 
     * @author marian
     * @version 1.1 
     */
    public static void opcionInsertardeFichero()
    {
    	DBManager.insertardeFichero();
    }
    
    /**
     * Actualiza los datos de una tabla con los datos de un fichero
     * Llama a actualizarFichero para actualizar los datos
     * 
     * @author marian
     * @version 1.1 
     */
    public static void opcionActualizarFichero()
    {
    	DBManager.actualizarFichero();
    }
    
    /**
     * Borra los datos de una tabla con los datos de un fichero
     * Llama a borrarFichero para borrar los datos
     * 
     * @author marian
     * @version 1.1 
     */
    public static void opcionBorrarFichero()
    {
    	DBManager.borrarFichero();
    }
}
