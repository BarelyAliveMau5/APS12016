package classes;

/**
 * essa classe gera os nomes aleatorios pros arquivos; eutinha em mente criar
 * mais opções, como lista de caracteres e tals, mas ia dar trabalho demais, e
 * com o tempo escasso isso iria atrapalhar mais do que dar nota.
 **/
public class gerador implements Runnable
{
    private String nomes[];

    // operações realizadas. ao invez de porcentagem, que é mais lento
    private int processado;

    public enum modos
    {
        aleatoria, semi_aleatoria, inversa, pouca_variacao
    }

    // se é ou não permitido repetir (no caso, usar somente numeros aleatorios,
    // porque podem repetir)
    private boolean repetir;
    private modos Modo;

    // numero de iterações na verdade
    private int tamanho;
    private String prefixo;

    // pad é uma String com zeros (do tamanho dos digitos+length do prefixo)
    // usados pra alinhar
    private String pad;
    private String posfixo;
    private boolean concluido;
    private boolean fixo;

    /**
     * prepara o worker
     **/
    public gerador(boolean repetir, boolean fixo, modos Modo, int tamanho, String prefixo, String posfixo)
    {
        this.repetir = repetir;
        this.Modo = Modo;
        this.tamanho = tamanho;

        pad = new String("");
        ;
        if (fixo)
            // cria X numeros de zeros
            pad = new String(new char[prefixo.length() + String.valueOf(tamanho).length() - 1]).replace('\0', '0');

        this.prefixo = prefixo;
        this.posfixo = posfixo;
        this.fixo = fixo;
    }

    /**
     * retorna a lista de nomes
     **/
    public String[] getNomes()
    {
        return this.nomes;
    }

    /**
     * pra finalizar os timers
     **/
    public boolean getConcluido()
    {
        return this.concluido;
    }

    /**
     * isso muda alguma coisa?
     **/
    public void LimparNomes()
    {
        nomes = null;
    }

    /**
     * getter do processado
     **/
    public int Processado()
    {
        return processado;
    }

    /**
     * só é usado pra rodar a thread de criação
     ***/
    @Override
    public void run()
    {
        nomes = new String[tamanho];
        concluido = false;
        processado = 0;
        switch (Modo)
        {
        case aleatoria:

            // aqui os numeros aleatorios são gerados on-the-fly
            if (repetir)
            {

                // porquê do codigo repetido:
                // é mais simples que contornar um bug onde o pad tem uma length
                // menor que o temp, e também usa menos processamento se não for
                // fixo
                if (fixo)
                {
                    String temp;
                    for (int i = 0; i < tamanho; i++)
                    {
                        temp = ((int) (Math.random() * tamanho)) + posfixo;

                        // o metodo substring exclui os primeiros temp.length()
                        // caracteres
                        nomes[i] = prefixo + pad.substring(temp.length()) + temp;

                        processado++;
                    }
                } else
                {
                    // metodo original, antes da frescura do tamanho fixo..
                    for (int i = 0; i < tamanho; i++)
                    {
                        nomes[i] = prefixo + ((int) (Math.random() * tamanho)) + posfixo;

                        processado++;
                    }
                }
            }
            // aqui uma lista é gerada e então embaralhada, leva o dobro de
            // operações
            else
            {
                if (fixo)
                {
                    for (int i = 0; i < tamanho; i++)
                    {
                        // tecnicamente isso não pode ser simplificado,
                        // adicionaria mais processamento e complexidade
                        nomes[i] = prefixo + pad.substring(String.valueOf(i).length() + posfixo.length()) + i + posfixo;
                        processado++;
                    }
                } 
                else
                {
                    for (int i = 0; i < tamanho; i++)
                    {
                        nomes[i] = prefixo + i + posfixo;
                        processado++;
                    }
                }
                // só usado pra trocar indices dos lugares
                String temp;
                int indice;
                for (int i = 0; i < tamanho; i++)
                {
                    // pega um indice valido aleatorio
                    indice = ((int) (Math.random() * tamanho)) % tamanho;

                    // inverte com o valor da posição aleatoria
                    temp = nomes[i];
                    nomes[i] = nomes[indice];
                    nomes[indice] = temp;
                    processado++;
                }
            }
            break;

        case semi_aleatoria:
            break;

        case inversa:
            // mesmo esquema dito acima sobre o bug do pad e otimização extra
            if (fixo)
            {
                for (int i = tamanho; i > 0; i--)
                {
                    nomes[tamanho - i] = prefixo + pad.substring(String.valueOf(i).length() + posfixo.length()) + i
                            + posfixo;
                    processado++;
                }
            } else
            {
                for (int i = tamanho; i > 0; i--)
                {
                    nomes[tamanho - i] = prefixo + i + posfixo;
                    processado++;
                }
            }
            break;

        case pouca_variacao:
            break;
        default:
            break;
        }
        concluido = true;
    }
}
