import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import model.Parser;
import model.ParserDNI;

/**
 * CLASE PRINCIPAL
 * 
 * @author marta
 *
 */
public class Main {
	
	private static Parser p;
	private static ParserDNI pd;
	/**
	 * MÉTODO PRINCIPAL.
	 * @param args
	 */
	public static void main(String[] args) {
		p = new Parser();
		pd = new ParserDNI();
		
		//Intenta leerlo y parsearlo.
		try {
			parsearFich(p);
			parsearDNI(pd);
			
			
		//Captura las excepciones:
		} catch (ParserConfigurationException e) {
			System.err.println("Hubo un error de configuración.\n");
			e.printStackTrace();
		} catch(SAXException s) {
			System.err.println("Hubo un error al parsear.\n");
			s.printStackTrace();
		} catch(IOException ex) {
			System.err.println("Hubo un error de I/O, es probable que no se encuentre el fichero o haya un error en él.\n");
			ex.printStackTrace();
		}

	}

	/**
	 * Parsear fichero.
	 * 
	 * @param par
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void parsearFich(Parser par) throws ParserConfigurationException, SAXException, IOException {
		par.parseFicheroXml("biblioteca.xml");
		par.parseDocument();
		par.printLibro();
		
		
	}
	

	/**
	 * Parseo de DNI: exámen.
	 * 
	 * @param par
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void parsearDNI(ParserDNI par) throws ParserConfigurationException, SAXException, IOException {
		par.parseFicheroXml("examen.xml");
		par.parseDocument();
		par.printDnis();
		
	}
	


}
