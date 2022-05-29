import java.util.Scanner;

import GestionBD.BBDD;
import GestionBD.DBManager;

/**
 * Clase con el menu
 * 
 * @author Mariannsss
 * @version 2.1
 */
public class Menu 
{
	private BBDD bbdd;
	
	/**
	 * Constructor de la clase
	 * para seleccionar la base de datos y la tabla
	 * 
	 * @author Mariannsss
	 * @version 1.0
	 */
    public Menu() 
    {
    	opEligirBBDDyTabla();
	}

    /**
     * Muestra el menu al usuario
     * llama a pideInt para obtener la opcion elegida
     * 
     * @author Marian
     * @version 2.1
     * @return varia en cada opcion
     */
    public boolean getMenuPrincipal() 
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
        System.out.println("8. Elige BBDD");
        System.out.println("9. Volcar en un fichero");
        
        Scanner in = new Scanner(System.in);
            
        System.out.println();
        int opcion = GestionClientes.pideInt("Elige una opcion (número): ");
        
        switch (opcion) 
        {
	        case 0:
	            return true;
            case 1:
            	GestionClientes.opcionMostrarClientes(bbdd.SELECT_TABLA());
                return false;
            case 2:
            	GestionClientes.opcionNuevoCliente(bbdd.INSERT_TABLA());
                return false;
            case 3:
            	GestionClientes.opcionModificarCliente(bbdd.INSERT_TABLA());
                return false;
            case 4:
            	GestionClientes.opcionEliminarCliente(bbdd.INSERT_TABLA());
                return false;
            case 5:
            	GestionClientes.opcionPrimerosClientes();
            	return false;
            case 6:
            	GestionClientes.opcionCrearTabla();
            	return false;
            case 7:
            	GestionClientes.opcionFiltrarFilas();
            	return false;
            case 8:
            	opEligirBBDDyTabla();
            	return false;
            case 9:
            	GestionClientes.opcionVolcarEnFichero(bbdd);
            	return false;
            default:
                System.out.println("Opcion elegida incorrecta");
                return false;
        }        
    }
    
    /**
     * Pide el nombre de la BBDD y de la tabla y llama al constructor
     * 
     * @author Marian
     * @version 1.0
     */
    public void opEligirBBDDyTabla() 
	{    	
    	Scanner in = new Scanner(System.in);
    	
        System.out.print("Indique el nombre de la BBDD: ");        
        String nombreBBDD = in.nextLine();
        System.out.print("Indique el nombre de la tabla: ");
        in = new Scanner(System.in);
        String nombreTabla = in.nextLine();
        
        bbdd = new BBDD(nombreBBDD, nombreTabla);
        
        //Pendiente comprobar si la tabla existe eb bbdd 
        //error diciendo que los datos introducidos no corresponden con ninguna bbdd existente
        DBManager.connect(nombreBBDD);
    }
	
}