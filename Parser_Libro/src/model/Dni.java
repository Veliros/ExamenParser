package model;

import java.util.ArrayList;
/**
 * 
 * @author marta
 *
 */
public class Dni {
	private String banco;
	private ArrayList<Operaciones> op;
	
	/**
	 * Constructor
	 * 
	 * @param banco
	 * @param op
	 */
	public Dni(String banco, ArrayList<Operaciones> op) {
		super();
		this.banco = banco;
		this.op = op;
	}
	
	/**
	 * Método toString()
	 */
	public String toString() {
		return "\tAcción: " + banco + "\n" + "Operaciones:\n " + mostrarArrayList(op);
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public StringBuilder mostrarArrayList(ArrayList<Operaciones> ope) {
		StringBuilder sb = new StringBuilder();
	    
	    for(Operaciones o : ope) {
	    	sb.append(o.toString());
	    }
		
		return sb;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public ArrayList<Operaciones> getOp() {
		return op;
	}

	public void setOp(ArrayList<Operaciones> op) {
		this.op = op;
	}	
	

}
