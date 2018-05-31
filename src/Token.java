import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que incluye los posibles tipos de Tokens que el lenguage TL es capaz de aceptar
 * El match de token se hacen en orden de definicion por lo tanto los menos generales deben ir al final
 * @author Diego
 *
 */
public enum Token {
	EOF ("<EOF>"),
	OBRACE ("\\{"),
	CBRACE ("\\}"),
	TOKEN_COM ("#"),
	OKEY ("\\["),
	CKEY ("\\]"),
	OPAR ("\\("),
	CPAR ("\\)"),
	GTEQ (">="),
	LTEQ ("<="),
	GT (">"),
	LT ("<"),
	IN ("in"),
	EQ ("=="),
	DOT ("\\."),
	NEQ ("!="),
	AND ("&&"),
	OR ("\\|\\|"),
	NOT ("!"),
	TOKEN_DOS_PUNTOS (":"),
	PLUS ("\\+"), 
    MINUS ("-"), 
    MULT ("\\*"), 
    DIV ("/"), 
    MOD ("%"),
    POW ("\\^"),
    ASSIGN ("="),
    COMMA (","), 
    IF ("if"), 
    ELSE ("else"), 
    NEWLINE ("\n"),
    LOG ("log"),
    FALSE ("false"),
    TRUE ("true"),
    FOR ("for"),
    WHILE ("while"),
    FUNCION ("funcion"),
    RETORNO ("retorno"),
    NIL ("nil"),
    END ("end"),
    IMPORT ("importar"),
    FROM ("from"),
    STRING ("\"[^\"]+\""),
    FLOAT ("(\\d+)\\.\\d+"),
    INT ("\\d+"), 
    
    ID ("\\w+");
    private final Pattern pattern;


    /**
     * Se encarga de hacer coincidencias desde el comienzo de la cadena que se le pasa para que
     * asi reciba la cadena mas larga posible que pueda armar
     * @param regex
     */
    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    /**
     * Se encarga de coincidir con el patron dado 
     * @param s cadena que se encarga de coincidir con algunos de los posibles token expuestos
     * @return La posicion hasta donde coincide el lexema de acuerdo al token, -1 en caso de que no encuentre coincidencia
     */
    int coincidirToken(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
   
}