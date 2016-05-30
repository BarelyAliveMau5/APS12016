package classes;

public class Insertion extends BaseT implements Runnable
{

    public Insertion(String[] nomes)
    {
        this.nomes = nomes;
        processado = 0;
    }
    
    public void insertion_sort()
    {
        int i, j, total = nomes.length;
        
        concluido = false;
        
        String temp;
        for (i = 1; i < total; i++)
        {
            j = i;
            temp = nomes[i];
            while ((j > 0) && (nomes[j - 1].compareTo(temp) > 0))
            {
                nomes[j] = nomes[j - 1];
                j--;
            }
            nomes[j] = temp;
            processado++;
        }
        processado++;
        concluido = true;
    }

    @Override
    public void run()
    {
        insertion_sort();
    }
}
