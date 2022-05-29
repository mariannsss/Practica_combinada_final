import GestionBD.DBManager;
import GestionBD.BBDD;
import java.util.Scanner;

/**
 *Esta clase se encarga de interactuar con el cliente y llamar a los metodos de la clase DBManager
 *
 * @author marian
 * @version 2.1
 */
public class GestionClientes 
{
	/**
	 * Clase principal. Establece la conexion y llama al menu
	 * 
	 * @author Marian
	 * @version 1.1
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
     * Llama a printTablaClientes y muestra la tabla clientes
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionMostrarClientes(String SELECT_TABLA) 
    {
        System.out.println();
    	System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes(SELECT_TABLA);
    }

    /**
     * Pide los datos para crear un nuevo registro en la tabla clientes
     * llama a pideLinea para obtener los parametros necesarios
     * llama a insertCliente para insertar el nuevo cliente
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionNuevoCliente(String INSERT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String direccion = pideLinea("Direccion: ");

        boolean res = DBManager.insertCliente(nombre, direccion, INSERT_TABLA);

        if (res) 
        {
            System.out.println("Cliente registrado correctamente");
        } 
        else 
        {
            System.out.println("Error :(");
        }
    }

    /**
     * Modifica un cliente de la tabla clientes
     * llama a pideInt para obtener el id del cliente a modificar
     * llama a pideLinea para obtener los los nuevos valores
     * llama a updateCliente para actualizar los datos del cliente
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionModificarCliente(String SELECT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");
        System.out.println();

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id, SELECT_TABLA)) 
        {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Mostramos datos del cliente a modificar
        DBManager.printCliente(id, SELECT_TABLA);

        // Solicitamos los nuevos datos
        String nombre = pideLinea("Nuevo nombre: ");
        String direccion = pideLinea("Nueva direccion: ");

        // Registramos los cambios
        boolean res = DBManager.updateCliente(id, nombre, direccion, SELECT_TABLA);

        if (res) 
        {
            System.out.println("Cliente modificado correctamente");
        } 
        else 
        {
            System.out.println("Error :(");
        }
    }

    /**
     * Elimina un cliente de la tabla clientes
     * llama a pideInt para obtener el id del cliente a borrar
     * llama a deleteCliente para borrar el cliente indicado
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionEliminarCliente(String SELECT_TABLA) 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");
        System.out.println();

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id, SELECT_TABLA)) 
        {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Eliminamos el cliente
        boolean res = DBManager.deleteCliente(id, SELECT_TABLA);

        if (res) 
        {
            System.out.println("Cliente eliminado correctamente");
        } 
        else 
        {
            System.out.println("Error :(");
        }
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
     * 
     * @author marian
     * @version 1.1 
     * @param bbdd
     */
    public static void opcionVolcarEnFichero(BBDD bbdd)
    {
    	DBManager.volcarEnFichero (bbdd);
    }
}
