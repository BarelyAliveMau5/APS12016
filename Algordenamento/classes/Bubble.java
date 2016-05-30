package classes;

public class Bubble extends BaseT implements Runnable
{
    public Bubble(String[] nomes)
    {
        this.nomes = nomes;
        processado = 0;
    }
    
    public void bubble_sort()
    {
        int total = nomes.length;
        concluido = false;
        String temp;
        for (int j = total; j >= 0; j--)
        {
            for (int i = 0; i < total - 1; i++)
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
        concluido = true;
    }

    @Override
    public void run()
    {
        bubble_sort();
    }
}
