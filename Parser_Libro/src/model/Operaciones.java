package model;
/**
 * CLASE PARA EXAMEN
 * 
 * @author marta
 *
 */
public class Operaciones {
	private String tipo;
	private int cantidad;
	private double precio;
	
	/**
	 * Constructor 
	 * 
	 * @param tipo
	 * @param cantidad
	 * @param precio
	 */
	public Operaciones(String tipo, int cantidad, double precio) {
		super();
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	/**
	 * Método toString()
	 */
	public String toString() {
		return "\nTipo: " + tipo + "\nCantidad: " + cantidad + "\nPrecio: " + precio + "\n"; 
	}
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	

}
