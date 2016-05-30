package classes;

/**
 * Classe base para a medição e funcionamento dos métodos das classes herdadas
 **/
public class BaseT
{
    //o array que guarda os nomes, por algum motivo tem memory leak
    protected String nomes[];

    // operações realizadas. ao invez de porcentagem, que é mais lento
    protected int processado;
    
    // à processar
    protected int a_ser_processado;
    
    //se ja está tudo tranquilo e favorável
    protected boolean concluido;
    
    /**
     * getter do processado
     **/
    public int getProcessado()
    {
        return processado;
    }
    
    /**
     * getter do a_ser_processado
     **/
    public int getASerProcessado()
    {
        return a_ser_processado;
    }
    
}
