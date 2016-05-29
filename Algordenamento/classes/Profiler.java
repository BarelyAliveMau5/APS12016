package classes;

//inspiração goo.gl/SsAhv
public class Profiler
{
    private long init;
    private boolean running;
    private long ending;

    /**
     * inicia a "contagem"
     **/
    public Profiler()
    {
        if (!running)
        {
            init = System.currentTimeMillis();
            ending = 0;
            running = true;
        }
    }
    public void Finalizar()
    {
        if (running)
            ending = System.currentTimeMillis();
        running = false;
    }

    /****************************************
     * mede o tempo total até agora, só o numero em ms
     ****************************************/
    public long tempo_atual_puro()
    {
        if (running)
            return System.currentTimeMillis() - init;
        else
            return ending - init;
    }

    /****************************************
     * mede o tempo total até agora, formatado bonetenho
     ****************************************/
    public String Tempo_atual_bonetenho()
    {
        long total = tempo_atual_puro();
        if (total >= 0)
        {
            return  String.format("%.2g%n", Double.valueOf((double)total / 1000)) + " segundo(s) (" + total + "ms).";
        } else
            return "erro? lol";
    }

    /***************************************
     * "para de contar" e mede o tempo total
     ****************************************/
    public String Tempo_Final(boolean puro)
    {
        Finalizar();
        if (puro)
            return String.valueOf(tempo_atual_puro());
        else
            return Tempo_atual_bonetenho();
    }

}
