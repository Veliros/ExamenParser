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

public class Parser {	
	private Document dom = null;
	private ArrayList<Libro> libro = null;

	/**
	 * CONSTRUCTOR
	 */
	public Parser() {
	   libro = new ArrayList<Libro>();
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
	    NodeList nl = doc.getElementsByTagName("libro");
	      
	    if (nl != null && nl.getLength() > 0) {
	      for (int i = 0; i < nl.getLength(); i++) {        
	        // Elemento de libro.
	        Element el = (Element) nl.item(i);
	        // Objeto de libro añadido al Array.
	        Libro p = getLibro(el);
	        libro.add(p);
	      }
	    }
	  }
	  
	  
	 /**
	  * 
	  * Recuperar libro. 
	  * @param libro
	  * @return
	  */
	 private Libro getLibro(Element libro){
		// Llamamos al método para sacar varios valores de ahí:
	    String editor = getTextValue(libro,"editor"),
	    	   titulo = getTextValue(libro,"titulo");
	   // ArrayList<Autor> autores = getAutoresCambiado(libro, "autor");
	    //autoresF(libro, "autores");
	    ArrayList<Autor> autores = autores(libro, "autores");
	    
	    // Código anterior:
	    /*
	    // Pueden ser varios autores, con lo cual necesita otra operación:
	    NodeList autores = libro.getElementsByTagName("nombre");
	    // Está inicializado para evitar que salga "nullNombre".
	    String lista = "";
	    // Recorre los nodos dentro de otro nodo, "child".
	    for (int i = 0; i < autores.getLength(); i++) { 
	    	// Coge el autor actual:
	    	Element e = (Element) autores.item(i);
	    	// Es un string que coge el valor del nodo hijo.
	    	lista += "- " + e.getFirstChild().getNodeValue() + " -";        	
	    } // Al sólo haber 1 no hice método aparte.*/
	    
	    // Los ints:
	    int paginas = getIntValue(libro,"paginas"),
	    	anyo = Integer.parseInt(getAtributeValue(libro,"titulo"));  
	    // A año tenía que sacar el atributo.
	    
	    // Creamos un objeto libro con estos datos y lo devolvemos:
	    Libro lib = new Libro(titulo, autores, editor, paginas, anyo);
	    return lib; 
		 
		 }
	 

	 /**
	  * Coge los autores. Método anterior sin modificar de XML.
	  * 
	  * @param ele
	  * @param tagName
	  * @return autores
	  */
	/* private ArrayList<String> getAutores(Element ele, String tagName) {
			ArrayList<String> autores = new ArrayList<String>();
			NodeList nl = ele.getElementsByTagName(tagName);
			
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				NodeList nlNombres = el.getElementsByTagName("nombre");
				
				if (nlNombres != null && nlNombres.getLength() > 0) {
					for (int i=0; i<nlNombres.getLength(); i++) {
						Element eNom = (Element) nlNombres.item(i);
						autores.add(eNom.getFirstChild().getTextContent());
					}
				}
			}
			
			return autores;
	}*/
	
	 /**
	  *  Coge Apellidos.
	  *  
	  * @param ele
	  * @param tagName
	  * @return
	  */
	 private ArrayList<String> getApe(Element ele, String tagName) {
			NodeList nl = ele.getElementsByTagName(tagName);
			ArrayList<String> apellidos = new ArrayList<>();
			String autor = "";
			
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				
				NodeList nlApellidos = el.getElementsByTagName("apellidos");
				
				if (nlApellidos != null && nlApellidos.getLength() > 0) {
					for (int i=0; i < nlApellidos.getLength(); i++) {
							Element eApe = (Element) nlApellidos.item(i);
							
							autor = eApe.getFirstChild().getTextContent();
							apellidos.add(autor);
						}
						
					
				}
			}
			
			return apellidos;			
			
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
			ArrayList<String> nombres = new ArrayList<>();
			String autor = "";
			
			if (nl != null && nl.getLength() > 0) {
				Element el = (Element) nl.item(0);
				
				NodeList nlNombre = el.getElementsByTagName("nombre");
				
				if (nlNombre != null && nlNombre.getLength() > 0) {
					for (int i=0; i < nlNombre.getLength(); i++) {
							Element eNom = (Element) nlNombre.item(i);
							
							autor = eNom.getFirstChild().getTextContent();
							nombres.add(autor);
						}
						
					
				}
			}
			
			return nombres;
			
			
	}
	
	 /**
	  * Junta nombres y apellidos devolviendolos.
	  * 
	  * @param ele
	  * @param tagName
	  * @return
	  */
	 public ArrayList<Autor> autores(Element ele, String tagName){
		 ArrayList<Autor> autores = new ArrayList<>();
		 ArrayList<String> nombres = new ArrayList<>(), apellidos = new ArrayList<>();
		 Autor a;
		 String nom, ape;
		 
		 nombres = getNom(ele, tagName);
		 apellidos = getApe(ele, tagName);
		 
		 for(int i = 0; i < nombres.size(); i++) {
			 nom = nombres.get(i);
			 ape = apellidos.get(i);
			 a = new Autor(nom, ape);
			
			 autores.add(a);
			 
		 }
		 
		 return autores;
	 }

	  
	  
	  /**
	   *  Valor dentro de la respectiva etiqueta.
	   *  
	   * @param ele
	   * @param tagName
	   * @return
	   */
	  private String getTextValue(Element ele, String tagName) {
	    String textVal = null;
	    NodeList nl = ele.getElementsByTagName(tagName);
	    
	    // Si el nodo tiene nombre o contenido:
	    if(nl != null && nl.getLength() > 0) {
	      Element el = (Element)nl.item(0);
	      textVal = el.getFirstChild().getNodeValue();
	    }  
	    
	    return textVal;
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
	      textVal = el.getAttribute("anyo");
	    }    
	    
	    return textVal;
	  }
	  
	  /**
	   *  Obtención del valor en formato de Integer para las páginas.
	   *  
	   * @param ele
	   * @param tagName
	   * @return
	   */
	  private int getIntValue(Element ele, String tagName) {  
	    return Integer.parseInt(getTextValue(ele,tagName));
	  }
	  
	
	  /**
	   * Print de libros
	   */
	  public void printLibro() {
		Iterator<Libro> it = libro.iterator();
		StringBuilder sb = new StringBuilder();
		
		while(it.hasNext()) {
		 Libro l = it.next();
		 // Llama al método toString() de libro para sacar el formato deseado:
	     sb.append(l.toString() + "\n\n");
	     
	    }
		
		System.out.println(sb);;	
	  }


}

