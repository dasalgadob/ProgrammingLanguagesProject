import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
/**
 * 
 * @author Diego
 *Clase que se encarga de las operaciones de identicar cuales son los tokens, los lexemas que corresponden a cada token y determinar 
 *el estado en el que se encuentra el analisis
 *Tambien incluye posicion actual de linea y en el espacio  de esa linea
 *
 */
public class Analizador {
    private StringBuilder cadenaRestante = new StringBuilder();
    private int currentLine;
    private int currentPosition;
    private Token token;
    private String lexema;
    private boolean finalizo = false; //Se empieza considerando que se puede avanzar
    private String mensajeError = "";
    private Set<Character> caracteresIgnorar = new HashSet<Character>();

    /**
     * 
     * El constructor se encarga de la lectura linea a linea del archivo para incluirlo en el SringBuilder input parametro
     * en caso de alguna falla se indica que se finaliza la lectura 
     * Finalmente agrega caracteres en blanco a blankChars para evitar que se lean caracteres innecesarios
     * @param filePath Construye basado en la ruta del archivo que se indica
     */
    public Analizador(String filePath) {
    	currentLine=1;
        try (Stream<String> st = Files.lines(Paths.get(filePath))) {
        	Iterator<String> i = st.iterator();
        	while(i.hasNext()){
        		//System.out.println();
        		cadenaRestante.append(i.next()+ "\n");
        	}
        	//System.out.println(input);
        } catch (IOException ex) {
        	// En cado de problema al leer se indica que no se va a operar mas sobre los datos ingresados
            finalizo = true;
            mensajeError = "Could not read file: " + filePath;
            return;
        }
        //System.out.println("Input:"+ input);
        caracteresIgnorar.add('\r');
        //blankChars.add('\n');
        caracteresIgnorar.add((char) 8);
        caracteresIgnorar.add((char) 9);
        caracteresIgnorar.add((char) 11);
        caracteresIgnorar.add((char) 12);//Entre otros
        caracteresIgnorar.add((char) 32);//Espacio 

        avanzarAnalisis();
    }

    /**
     * 
     * Funcion que se encarga de avanzar el Scanner por la entrada dada
     */
    public void avanzarAnalisis() {
    	//Si hubo error de lectura de archivo no continuar
        if (finalizo) {
            return;
        }
        //Si no hay datos para leer se sale del programa
        if (cadenaRestante.length() == 0) {
            finalizo = true;
            return;
        }

        ignorarCaracteresEnBlanco();

        if (encontrarProximoToken()) {
        	currentPosition++;
            return;
        }
        

        finalizo = true;

        if (cadenaRestante.length() > 0) {
        	currentPosition++;
            mensajeError = "Error Lexico (Linea" +getCurrentLine()+ ", Posicion: "+ getCurrentPosition() + ") En caracter: "+ cadenaRestante.charAt(0);
        }
    }

    /**
     * 
     * Elimina distintos caracteres que no se van a tomar en cuenta para el analisis antes de la siguiente palabra
     */
    private void ignorarCaracteresEnBlanco() {
        int charsToDelete = 0;

        while (caracteresIgnorar.contains(cadenaRestante.charAt(charsToDelete))) {
            charsToDelete++;
        }

        if (charsToDelete > 0) {
            cadenaRestante.delete(0, charsToDelete);
        }
        //System.out.println("Input ignore:" + input);
    }

    /**
     * Funciona buscando hacer la maxima coincidencia de los tokens con la cadena que sigue, si lo encuentra asigna el token correspondiente
     * actual, ademas saca las substring del valor del lexema actual.
     * Cuando se encuentra un comentario se encarga de excluir hasta que alcanza fin de linea.
     * @return true si encuentra otro token mas en el StringBuilder, false en caso contrario
     */
    private boolean encontrarProximoToken() {
        for (Token t : Token.values()) {
        	//System.out.println("find next token:" + input.toString());
            int end = t.coincidirToken(cadenaRestante.toString());

            if (end != -1) {
                token = t;
                
                //System.out.println("TOken:" + token);
                lexema = cadenaRestante.substring(0, end);
                if (lexema.equals("#")){
                	//System.out.println("comentario");
                	end = cadenaRestante.indexOf("\n");	
                	//System.out.println("end:" + end);
                }
                cadenaRestante.delete(0, end);
                return true;
            }
        }

        return false;
    }
    
    public void resetCurrentPosition(){
    	setCurrentPosition(1);
    }
    
    
    public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getCurrentLine() {
		return currentLine;
	}

	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}

	/**
     * 
     * @return El token actual en el que se encuentra el analizador
     */
    public Token currentToken() {
        return token;
    }

    public String currentLexema() {
        return lexema;
    }
    /**
     * 
     * @return true si no hay mensajes en la cadena de errores
     */
    public boolean completoCorrectamente() {
        return mensajeError.isEmpty();
    }

    public String errorMessage() {
        return mensajeError;
    }

    public boolean haFinalizado() {
        return finalizo;
    }

	public void addCurrentLine() {
		currentLine++;
		// TODO Auto-generated method stub
		
	}
}