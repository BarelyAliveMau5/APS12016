package classes;

public abstract class BaseT
{
    //o array que guarda os nomes, por algum motivo tem memory leak
    protected String nomes[];

    // operações realizadas. ao invez de porcentagem, que é mais lento
    protected int processado;
    // à processar
    protected int a_ser_processado;
    protected boolean concluido;
    
    /**
     * getter do processado
     **/
    public int getProcessado()
    {
        return processado;
    }
    public int getASerProcessado()
    {
        return a_ser_processado;
    }
    
}
