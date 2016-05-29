package classes;

public class Bubble extends BaseT implements Runnable
{
    public Bubble(String[] nomes)
    {
        this.nomes = nomes;
        processado = 0;
    }
    
    public void bubble_srt()
    {
        int n = nomes.length;
        String temp;
        for (int j = n; j >= 0; j--)
        {
            for (int i = 0; i < n - 1; i++)
            {
                if (nomes[i].compareTo(nomes[j]) < 0)
                {
                    temp = nomes[i];
                    nomes[i] = nomes[j];
                    nomes[j] = temp;
                }
            }
            processado++;
        }
    }

    @Override
    public void run()
    {
        bubble_srt();
    }
}
