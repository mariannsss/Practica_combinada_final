import GestionBD.BBDD;
import GestionBD.DBManager;
import java.sql.ResultSet;
import java.util.Scanner;

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
    	opcionEligirBBDDyTabla();
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
        System.out.println("0.  Salir");
        System.out.println("1.  Listar tabla");
        System.out.println("2.  Nuevo registro");
        System.out.println("3.  Modificar registro");
        System.out.println("4.  Eliminar registro");
        System.out.println("5.  Muestra los primeros 5 clientes"); //usa CallableStatement
        System.out.println("6.  Crear una Tabla nueva");
        System.out.println("7.  Filtrar filas de una tabla");
        System.out.println("8.  Elige BBDD");
        System.out.println("9.  Volcar en un fichero");
        System.out.println("10. insertar desde fichero");
        System.out.println("11. Actualizar desde fichero");
        System.out.println("12. Borrar desde fichero");
        
        Scanner in = new Scanner(System.in);
            
        System.out.println();
        int opcion = Gestion.pideInt("Elige una opcion (número): ");
        
        switch (opcion) 
        {
	        case 0:
	            return true;
            case 1:
            	Gestion.opcionMostrarTabla(bbdd.SELECT_TABLA());
                return false;
            case 2:
            	Gestion.opcionNuevoRegistro(bbdd.INSERT_TABLA());
                return false;
            case 3:
            	Gestion.opcionModificarRegistro(bbdd.SELECT_TABLA());
                return false;
            case 4:
            	Gestion.opcionEliminarRegistro(bbdd.SELECT_TABLA());
                return false;
            case 5:
            	Gestion.opcionPrimerosClientes();
            	return false;
            case 6:
            	Gestion.opcionCrearTabla();
            	return false;
            case 7:
            	Gestion.opcionFiltrarFilas();
            	return false;
            case 8:
            	opcionEligirBBDDyTabla();
            	return false;
            case 9:
            	Gestion.opcionVolcarEnFichero(bbdd);
            	return false;
            case 10:
            	Gestion.opcionInsertardeFichero();
            	return false;
            case 11:
            	Gestion.opcionActualizarFichero();
            	return false;
            case 12:
            	Gestion.opcionBorrarFichero();
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
    public void opcionEligirBBDDyTabla() 
	{    	
    	Scanner in = new Scanner(System.in);
    	String nombreBBDD, nombreTabla;
    	
        System.out.print("Indique el nombre de la BBDD: ");        
        nombreBBDD = in.nextLine();
      
    	System.out.print("Indique el nombre de la tabla: ");
        nombreTabla = in.nextLine();
        
        bbdd = new BBDD(nombreBBDD, nombreTabla);          
       if ( DBManager.connect(nombreBBDD)== false) //comprueba la conecta con la base de datos
       {
    	   this.opcionEligirBBDDyTabla();
       } 
        
        //controla que la tabla exista, sino vuelve a pedir los datos
       if (DBManager.getTabla(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,bbdd.SELECT_TABLA())==null) 
       {
    	   DBManager.close(); //cierra la base de datos
    	   this.opcionEligirBBDDyTabla();
       }
    }
}