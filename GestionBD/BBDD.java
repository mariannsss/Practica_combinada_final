package GestionBD;

import java.util.HashMap;

public class BBDD {
	
	private String nombre;
	//private HashMap<String,?> listaCampos;

	public BBDD(String nombre) {
		this.nombre = nombre;
	}	
	
	/*
	public BBDD(String nombre, HashMap<String, ?> listaCampos) {
		super();
		this.nombre = nombre;
		this.listaCampos = listaCampos;
	}


	public HashMap<String, ?> getListaCampos() {
		return listaCampos;
	}

	public void setListaCampos(HashMap<String, ?> listaCampos) {
		this.listaCampos = listaCampos;
	}	*/

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

	@Override
	public String toString() {
		return "BBDD [nombre=" + nombre + "]";
	}

	
}
