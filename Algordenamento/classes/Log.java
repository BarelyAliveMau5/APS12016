package classes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Log
{
    private ByteArrayOutputStream slog;
    
    public enum estilos
    {
        normal,
        negrito,
        italico,
        underline
    }
    
    public Log()
    {
        //redirecionar o StdOut para slog
        slog = new ByteArrayOutputStream();
        System.setOut(new PrintStream(slog));
    }
    
    //se java tivesse parametros pre-definidos, eu poderia usa-los
    //ao inves de reescrever os metodos permitindo que eles sendo nulos..
    
    public void msg(String message, estilos estilo)
    {
        if (estilo != null){
        switch (estilo){
        case normal:
            message = "<a>" + message + "</a><br/>";
            break;
        case negrito:
            message = "<a style='font-weight: bold;'>" + message + "</a><br/>";
            break;
        case italico:
            message = "<a style='font-weight: italic;'>" + message + "</a><br/>";
            break;
        case underline:
            message = "<a style='text-decoration: underline;'>" + message + "</a><br/>";
            break;
            
        default:
            break;
        }
        }
        System.out.println(message);
    }
    
    public String getLog()
    {
        return slog.toString();
    }
}
