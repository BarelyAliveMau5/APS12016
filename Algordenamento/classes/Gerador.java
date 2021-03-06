package classes;

import java.util.Arrays;

/**
 * essa classe gera os nomes aleatorios pros supostos arquivos.
 **/
public class Gerador extends BaseT implements Runnable
{
    public enum modos
    {
        aleatoria, 
        semi_aleatoria, 
        inversa, 
        pouca_variacao
    }
    private final int proporcao_semi_aleatoria = 100;
    
    // se é ou não permitido repetir nomes
    private boolean repetir;
    private modos Modo;

    // numero de iterações na verdade
    private int tamanho;
    private String prefixo;

    // pad é uma String com zeros usados pra alinhar, se fixo for true
    private String pad;
    private String posfixo;
    private boolean fixo;
    private Exception er;
    private boolean erro;

    /**
     * prepara o worker
     **/
    public Gerador(boolean repetir, boolean fixo, modos Modo, int tamanho, String prefixo, String posfixo)
    {
        this.repetir = repetir;
        this.Modo = Modo;
        this.tamanho = tamanho;

        pad = new String("");
        // cria X numeros de zeros
        if (fixo)
            pad = new String(new char[String.valueOf(tamanho-1).length()]).replace('\0', '0');

        this.prefixo = prefixo;
        this.posfixo = posfixo;
        this.fixo = fixo;
        
        //quando não há repetições, acontece o dobro de operações
        if (!repetir && Modo != modos.inversa && Modo != modos.semi_aleatoria)
            a_ser_processado = tamanho *2;
        //em semi_aleatoria, 10% é repetido (proporcao_semi_aleatoria /10)
        else if(Modo == modos.semi_aleatoria)
            a_ser_processado = tamanho + (tamanho / proporcao_semi_aleatoria);
        else
            a_ser_processado = tamanho;
    }

    /**
     * retorna a lista de nomes
     **/
    public String[] getNomes()
    {
        return this.nomes;
    }
    
    public void setNomes(String[] nomes)
    {
        this.nomes = Arrays.copyOf(nomes, nomes.length);
        
    }

    /**
     * isso muda alguma coisa?
     **/
    public void LimparNomes()
    {
        nomes = null;
    }
    
    /**
     * gera numeros aleatorios, somewhat otimizado. inspirado no Randy Marsh
     **/
    private int Randy(int[] preset)
    {
        //essa função seria uma lambda se java7 tivesse isso -_-
        //é crucial que seja otimizado, como é usado em loops.
        if (preset == null)
            return ((int) (Math.random() * tamanho));
        else
        {
            //lista limitada de numeros usada no semi-aleatoria
            return preset[(int) (Math.random() * preset.length)];
        }
    }
    
    /**
     * gera a lista de nomes usando os atributos inicializados no Constructor
     **/
    public void Gerar()
    {
        try 
        {
            nomes = new String[tamanho];
            concluido = false;
            processado = 0;
            int[] buffer = null; //TEM QUE INICIAR NULO
            
            switch (Modo)
            {
            case pouca_variacao:
                //o divisor controla a proporção de repetições.
                //quanto maior o divisor, mais repetições.
                 buffer = new int[tamanho/proporcao_semi_aleatoria];
                
                for (int i=0;i<buffer.length;i++)
                    buffer[i] = Randy(null);
                    
            // FALL THROUGHT INTENCIONAL!!
            // buffer TEM QUE SER nulo se não for passar pelo codigo acima.
            // Randy(buffer) com buffer null é intencional para esta logica
            // só assim tudo acontece como deve, sem eu precisar reescrever codigo
            case semi_aleatoria:
            // FALL THROUGHT INTENCIONAL!!
            // a variavel uTamanho lida com isso, pra evitar reescrever
            // o mesmo codigo. leia com cautela o código, ele é autoexplicativo
            case aleatoria:
    
                // aqui os numeros aleatorios são gerados on-the-fly
                if (repetir)
                {
                    // porquê do codigo repetido:
                    // é mais simples que contornar um bug onde o pad tem uma length
                    // menor que o temp, e também usa menos processamento se não for
                    // fixo, usar IFs dentro de loops é lento.
                    if (fixo)
                    {
                        String temp;
                        for (int i = 0; i < tamanho; i++) {
                            temp = String.valueOf(Randy(buffer));
                            nomes[i] = prefixo + pad.substring(temp.length()) +temp+ posfixo;
                            processado++;
                        }
                    } 
                    else 
                    {
                        for (int i = 0; i < tamanho; i++) {
                            nomes[i] = prefixo + Randy(buffer) + posfixo;
                            processado++;
                        }
                    }
                }
                // aqui uma lista é gerada e então embaralhada, leva o dobro de operações
                else
                {
                    if (fixo) 
                    {
                        for (int i = 0; i < tamanho; i++){
                            nomes[i] = prefixo + pad.substring(String.valueOf(i).length()) + i + posfixo;
                            processado++;
                        }
                    } 
                    else
                    {
                        for (int i = 0; i < tamanho; i++) {
                            nomes[i] = prefixo + i + posfixo;
                            processado++;
                        }
                    }
                    
                    // trocar indices dos lugares
                    // caso seja semi-aleatoria, embaralhar parcialmente
                    String strTroca;
                    int idx1, idx2;
                    int uTamanho = tamanho;
                    
                    //evitar merda de usuario
                    if (tamanho > 10)
                        if (Modo == modos.semi_aleatoria)
                            uTamanho = uTamanho / 10;
                    
                    for (int i = 0; i < uTamanho; i++)
                    {
                        idx1 = Randy(buffer);
                        idx2 = Randy(buffer);
                        // inverte com o valor da posição aleatoria
                        strTroca = nomes[idx2];
                        nomes[idx2] = nomes[idx1];
                        nomes[idx1] = strTroca;
                        processado++;
                    }
                }
                break;
    
            case inversa:
                // mesmo esquema dito acima sobre o bug do pad e otimização extra
                if (fixo)
                {
                    for (int i = tamanho-1; i >= 0; i--){
                        nomes[tamanho-1-i] = prefixo + pad.substring(String.valueOf(i).length()) + i + posfixo;
                        processado++;
                    }
                } else
                {
                    for (int i = tamanho; i > 0; i--){
                        nomes[tamanho-i] = prefixo + i + posfixo;
                        processado++;
                    }
                }
                break;
    
            default:
                break;
            }
            
            erro = false;
        }
        catch (Exception err )
        {
            er = err;
            erro = true;
        }
        catch(OutOfMemoryError outch)
        {
            er = new Exception(outch.getMessage());
            erro = true;
        }
        concluido = true;
    }
    
    /**
     * só é usado pra rodar a thread de criação
     ***/
    @Override
    public void run()
    {
        Gerar();
    }

    public Exception getException()
    {
        return er;
    }
    
    public boolean getErro()
    {
        return erro;
    }

}
