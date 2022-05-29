package GestionBD;

/**
 * Clase que interactua con la BBDD
 * 
 * @author Mariannsss
 * @version 1.2
 */
public class BBDD 
{	
	// Configuracion de la tabla Clientes
    public static final String DB_CLI_ID = "id";
    public static final String DB_CLI_NOM = "nombre";
    public static final String DB_CLI_DIR = "direccion";
	
	private String nombreBBDD;
	private String nombreTabla;
	
	//constructor
	public BBDD(String nombreBBDD, String nombreTabla) 
	{
		this.nombreBBDD = nombreBBDD;
		this.nombreTabla = nombreTabla;
	}

	public String getNombreBBDD() {
		return nombreBBDD;
	}

	public void setNombreBBDD(String nombreBBDD) {
		this.nombreBBDD = nombreBBDD;
	}
	
	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
		
//////////////////////////////////////////////////
// METODOS DE LA BASE DE DATOS
//////////////////////////////////////////////////
;

	/**
	 * Imprime el nombre de la BBDD y la tabla
	 * 
	 * @author Mariannsss
	 * @version 1.1
	 */
	@Override
	public String toString() 
	{
		return "nombre de la BBDD: " + nombreBBDD + ", nombre de la Tabla: " + nombreTabla;
	}
	
	/**
	 * Consusltar
	 * @return devuelve la consulta
	 * 
	 * @author Mariannsss
	 * @version 1.1
	 */
	public String INSERT_TABLA() 
	{		
		return "INSERT INTO " + this.nombreTabla + "(nombre, direccion) VALUES (?, ?)";		
	}
	
	public String SELECT_TABLA() 
	{		
		return "SELECT * FROM " + this.nombreTabla;		
	}
}