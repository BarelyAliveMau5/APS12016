package classes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Log
{
    private ByteArrayOutputStream slog;
    
    public enum estilos
    {
        NORMAL,
        NEGRITO,
        ITALICO,
        NOTICIA,
        ERRO
    }
    
    /**
     * redireciona o StdOut para slog. futuramente será Static
     **/
    public Log()
    {
        slog = new ByteArrayOutputStream();
        PrintStream t = new PrintStream(slog);

        System.setErr(t);
        System.setOut(t);
    }

    /**
     * formata uma mensagem usando estilos pré-definidos
     **/
    public void msg(String message, estilos estilo)
    {
        switch (estilo){
        case NEGRITO:
            message = "<a style='font-weight: bold;'>" + message + "</a><br/>";
            break;
        case ITALICO:
            message = "<a style='font-style: italic;'>" + message + "</a><br/>";
            break;
        case NOTICIA:
            message = "<a style='text-decoration: underline;font-weight: bold;'>" + message + "</a><br/>";
            break;
        case ERRO:
            message = "<a style='font-weight: bold;"  + 
                      "background-color: rgb(255,0,0);"+ 
                      "color: rgb(255,255,255);'>" + message + "</a><br/>";
            break;
        default:
            message = "<a>" + message + "</a><br/>";
            break;
        }
        
        System.out.println(message);
    }
    
    /**
     * retorna o texto do log.
     **/
    public String getLog()
    {
        return slog.toString();
    }
}
