
import java.util.Scanner;

import GestionBD.DBManager;

/**
 *Esta clase se encarga de interactuar con el cliente y llamar a los metodos de la clase DBManager
 *
 * @author marian
 * @version 1.1
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
        //DBManager.loadDriver(); esta obsoleto
        DBManager.connect();

        boolean salir = false;
        do 
        {
            salir = menuPrincipal();
            
        } while (!salir);

        DBManager.close(); //cierra la conexion
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
        
        Scanner in = new Scanner(System.in);
            
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
        System.out.println("Listado de Clientes:");
        DBManager.printTablaClientes();
    }

    /**
     * Pide los datos para crear un nuevo registro en la tabla clientes
     * llama a pideLinea para obtener los parametros necesarios
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionNuevoCliente() 
    {
        Scanner in = new Scanner(System.in);

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
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionModificarCliente() 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");

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
     * 
     * @author marian
     * @version 1.1
     */
    public static void opcionEliminarCliente() 
    {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a eliminar: ");

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
     * marian
     * @version 1.1
     */
    public static void opcionPrimerosClientes() 
    {
    	System.out.println("Listado de los 5 primeros Clientes:");
    	DBManager.primerosClientes();
    }
}
