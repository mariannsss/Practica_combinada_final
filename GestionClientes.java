import GestionBD.DBManager;
import GestionBD.BBDD;
import java.util.Scanner;

/**
 *Esta clase se encarga de interactuar con el cliente y llamar a los metodos de la clase DBManager
 *
 * @author marian
 * @version 1.1
 */
public class GestionClientes 
{
	//private BBDD bbdd;
	
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
        do 
        {
            salir = menuBBDD();

        } while (!salir);

        salir = false;

        do 
        {
            salir = menuPrincipal();
            
        } while (!salir);

        DBManager.close(); //cierra la conexion
    }
    
    /**
     * Clase principal BBDD. Establece el nombre de la BBDD
     * @author Marian
	 * @version 1.1
     * @return
     */
    public static boolean menuBBDD() 
    {
    	 System.out.println("");
         System.out.println("MENU BBDD");
         System.out.println("0. Salir");
         System.out.println("1. nombre BBDD");
         
         Scanner in = new Scanner(System.in);
         
         System.out.println();
         int opcion = pideInt("Elige una opcion (número): ");
         
         switch (opcion) 
         {
 	        case 0:
 	            return true;
             case 1:
                DBManager.connect(nombreBBDD());
                return false;
             default:
                 System.out.println("Opcion elegida incorrecta");
                 return false;
         }
    }
    
    
    public static String nombreBBDD() {
    	
        System.out.print("Indique el nombre de la BBDD: ");
        Scanner in = new Scanner(System.in);
        String nombre = in.nextLine();
        return nombre;    	
    }

    /**
     * Muestra el menu al usuario
     * llama a pideInt para obtener la opcion elegida
     * 
     * @author Marian
     * @version 1.2
     * @return varia en cada opcion
     */
    public static boolean menuPrincipal() 
    {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("0. Salir");
        System.out.println("1. Listar clientes");
        System.out.println("2. Nuevo cliente");
        System.out.println("3. Modificar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Muestra los primeros 5 clientes"); //usa CallableStatement
        System.out.println("6. Crear una Tabla nueva");
        System.out.println("7. Filtrar filas de una tabla");
        
        Scanner in = new Scanner(System.in);
            
        System.out.println();
        int opcion = pideInt("Elige una opcion (número): ");
        
        switch (opcion) 
        {
	        case 0:
	            return true;
            case 1:
                opcionMostrarClientes();
                return false;
            case 2:
                opcionNuevoCliente();
                return false;
            case 3:
                opcionModificarCliente();
                return false;
            case 4:
                opcionEliminarCliente();
                return false;
            case 5:
            	opcionPrimerosClientes();
            	return false;
            case 6:
            	opcionCrearTabla();
            	return false;
            case 7:
            	opcionFiltrarFilas();
            	return false;
            default:
                System.out.println("Opcion elegida incorrecta");
                return false;
        }        
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
    public static void opcionMostrarClientes() 
    {
        System.out.println();
    	System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

    /**
     * Pide los datos para crear un nuevo registro en la tabla clientes
     * llama a pideLinea para obtener los parametros necesarios
     * llama a insertCliente para insertar el nuevo cliente
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionNuevoCliente() 
    {
        Scanner in = new Scanner(System.in);

        System.out.println();
        System.out.println("Introduce los datos del nuevo cliente:");
        String nombre = pideLinea("Nombre: ");
        String direccion = pideLinea("Direccion: ");

        boolean res = DBManager.insertCliente(nombre, direccion);

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
    public static void opcionModificarCliente() 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");
        System.out.println();

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) 
        {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Mostramos datos del cliente a modificar
        DBManager.printCliente(id);

        // Solicitamos los nuevos datos
        String nombre = pideLinea("Nuevo nombre: ");
        String direccion = pideLinea("Nueva direccion: ");

        // Registramos los cambios
        boolean res = DBManager.updateCliente(id, nombre, direccion);

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
    public static void opcionEliminarCliente() 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");
        System.out.println();

        // Comprobamos si existe el cliente
        if (!DBManager.existsCliente(id)) 
        {
            System.out.println("El cliente " + id + " no existe.");
            return;
        }

        // Eliminamos el cliente
        boolean res = DBManager.deleteCliente(id);

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
     * @version 1.1     * 
     */
    public static void opcionFiltrarFilas ()
    {
    	Scanner in = new Scanner(System.in);

        System.out.println();
        String nombreTab = pideLinea("Nombre de la tabla que quiere filtrar: ");
        int numero = pideInt("Numero de fila que desea ver: ");
        
        DBManager.filtrarFilas(nombreTab, numero);
    }
}
