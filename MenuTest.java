import GestionBD.BBDD;
import GestionBD.DBManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

/**
 * Clase de pruebas JUnit
 * 
 * @author Mariannsss
 * @version 1.0
 */
class MenuTest 
{
	private BBDD bbdd;
	
	
	@BeforeEach
	public void init() 
	{
		bbdd = new BBDD("tienda", "clientes");
		DBManager.connect(bbdd.getNombreBBDD());
	}	
	
	@AfterEach
	public void cierre() 
	{
		DBManager.close();
	}
	
	@Test
	public void test_opcionMostrarClientes() 
	{
		GestionClientes.opcionMostrarClientes(bbdd.SELECT_TABLA());	
	} 

	@Test
	public void test_opcionNuevoCliente() 
	{		
		DBManager.insertCliente("Jose", "Malaga",bbdd.INSERT_TABLA());		
	}
	
	@Test
	public void test_opcionModificarCliente() 
	{		
		DBManager.updateCliente(1, bbdd.SELECT_TABLA(),"Ana", "Sevilla");		
	}
	
	@Test
	public void test_opcionEliminarCliente() //si se lanza mas de una vez falla el test
	{		
		DBManager.deleteCliente(4, bbdd.SELECT_TABLA());		
	}

	@Test
	public void test_opcionPrimerosClientes() 
	{		
		GestionClientes.opcionPrimerosClientes();
	}	
	
	@Test
	public void test_opcionCrearTabla() //si se lanza mas de una vez falla el test
	{		
		 DBManager.crearTabla("empleados");
	}
	
	@Test
	public void test_opcionFiltrarFilas() 
	{
		DBManager.filtrarFilas("clientes", 5);
	}
	
	@Test
	public void test_eligeBBDD() 
	{
		bbdd = new BBDD("tienda", "empleados");
	}	
}