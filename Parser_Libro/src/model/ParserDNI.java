package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 
 * CLASE PARA EXAMEN.
 * @author marta
 *
 */
public class ParserDNI {
	private Document dom = null;
	private ArrayList<Dni> dnis = null;

	/**
	 * CONSTRUCTOR
	 */
	public ParserDNI() {
	   dnis = new ArrayList<Dni>();
	}


	/**
	 * Método para parsear.
	 * 
	 * @param fichero
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parseFicheroXml(String fichero) throws ParserConfigurationException, SAXException, IOException {
	    // Creamos una factory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	      
	    //Forzar UTF-8 al fichero:
	    InputStream inputStream= new FileInputStream(fichero);
	    Reader rd = new InputStreamReader(inputStream, "UTF-8");
	      
	    InputSource is = new InputSource(rd);
	      
	    is.setEncoding("UTF-8");
	      
	     //Representación DOM del parseo de XML una vez forzado a UTF-8.
	    dom = db.parse(is);

	  }

	
	  /**
	   * Parseo del Documento XML.
	   */
	  public void parseDocument() {
	    // Elemento raíz:
	    Element doc = dom.getDocumentElement();
	    // Nodelist de elementos:
	    NodeList nl = doc.getElementsByTagName("accion");
	      
	    if (nl != null && nl.getLength() > 0) {
	      for (int i = 0; i < nl.getLength(); i++) {        
	        // Elemento de libro.
	        Element el = (Element) nl.item(i);
	        // Objeto de libro añadido al Array.
	        Dni p = getDni(el);
	        dnis.add(p);
	      }
	    }
	  }
	  
	  
	 /**
	  * 
	  * Recuperar DNI.
	  * @param dni
	  * @return d
	  */
	 private Dni getDni(Element dni){
		// Llamamos al método para sacar varios valores de ahí:
	    String banco = getAtributeValue(dni,"acciones");
	    ArrayList<Operaciones> op = operaciones(dni, "operaciones");
	    
	    Dni d = new Dni(banco, op);
	    return d;  
		 
	 }
	
	 /**
	  *  Operaciones.
	  *  
	  * @param dni
	  * @param string
	  * @return
	  */
	 private ArrayList<Operaciones> operaciones(Element dni, String string) {
		 ArrayList<Operaciones> ops = new ArrayList<>();
		 ArrayList<String> tipo = new ArrayList<>();
		 ArrayList<Integer> cantidad = new ArrayList<>();
		 ArrayList<Double> precio = new ArrayList<>();
		 
		 Operaciones o;
		
		 String t; 
		 int cant;
		 double p;
		 
		 tipo = getNom(dni, string);
		 cantidad = getCant(dni,string);
		 precio = getPrecio(dni,string);
		 
		 for(int i = 0; i < tipo.size(); i++) {
			 t = tipo.get(i);
			 cant = cantidad.get(i);
			 p = precio.get(i);
			 
			 o = new Operaciones(t, cant, p);		
			 ops.add(o);
 
		 }
		 
		 return ops;
	}
	
	 /**
	  * Devuelve el precio.
	  * 
	  * @param dni
	  * @param string
	  * @return
	  */
	 private ArrayList<Double> getPrecio(Element dni, String string) {
		 NodeList nl = dni.getElementsByTagName(string);
			ArrayList<Double> precios = new ArrayList<>();
			double p = 0;
			
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				
				NodeList nlPrecios = el.getElementsByTagName("precio");
				
				if (nlPrecios != null && nlPrecios.getLength() > 0) {
					for (int i=0; i < nlPrecios.getLength(); i++) {
							Element ePre = (Element) nlPrecios.item(i);
							
							p = Double.parseDouble(ePre.getFirstChild().getTextContent());
							precios.add(p);
						}								
				}
			}
			
			return precios;	
	}


	 /**
	  * Recoge las cantidades.
	  * 
	  * @param dni
	  * @param string
	  * @return
	  */
	private ArrayList<Integer> getCant(Element dni, String string) {
		NodeList nl = dni.getElementsByTagName(string);
		ArrayList<Integer> cantidades = new ArrayList<>();
		int c = 0;
		
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			
			NodeList nlCantidades = el.getElementsByTagName("cantidad");
			
			if (nlCantidades != null && nlCantidades.getLength() > 0) {
				for (int i=0; i < nlCantidades.getLength(); i++) {
						Element eCant = (Element) nlCantidades.item(i);
						
						c = Integer.parseInt(eCant.getFirstChild().getTextContent());
						cantidades.add(c);
					}								
			}
		}
		
		return cantidades;	
	}


	/**
	  *  Recoge nombres.
	  *  
	  * @param ele
	  * @param tagName
	  * @return
	  */
	 private ArrayList<String> getNom(Element ele, String tagName) {
			NodeList nl = ele.getElementsByTagName(tagName);
			ArrayList<String> tipos = new ArrayList<>();
			String dni = "";
			
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				
				NodeList nlTipos = el.getElementsByTagName("tipo");
				
				if (nlTipos != null && nlTipos.getLength() > 0) {
					for (int i=0; i < nlTipos.getLength(); i++) {
							Element eNom = (Element) nlTipos.item(i);
							
							dni = eNom.getFirstChild().getTextContent();
							tipos.add(dni);
						}			
					
				}
			}
			
			return tipos;				
	}
	  
	  /**
	   *  Obtener atributo.
	   *  
	   * @param ele
	   * @param tagName
	   * @return
	   */
	  private String getAtributeValue(Element ele, String tagName) {
	    String textVal = null;
	    NodeList nl = ele.getElementsByTagName(tagName);
	    
	    if(nl != null && nl.getLength() > 0) {
	      Element el = (Element)nl.item(0);
	      textVal = el.getAttribute("nombre");
	    }    
	    
	    return textVal;
	  }
	    
	
	  /**
	   * Print de DNIs
	   */
	  public void printDnis() {
		Iterator<Dni> it = dnis.iterator();
		StringBuilder sb = new StringBuilder();
		
		while(it.hasNext()) {
		 Dni l = it.next();
		 // Llama al método toString() de libro para sacar el formato deseado:
	     sb.append(l.toString() + "\n\n");
	     
	    }
		
		System.out.println(sb);;	
	  }


}



