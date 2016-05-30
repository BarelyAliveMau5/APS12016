package classes;

public class Bubble extends BaseT implements Runnable
{
    public Bubble(String[] nomes)
    {
        this.nomes = nomes;
        processado = 0;
    }
    
    //fonte:
    //http://stackoverflow.com/questions/16195092/optimized-bubble-sort-java
    public void bubble_sort()
    {
        int total = nomes.length;
        concluido = false;
        String temp;
        boolean ordenado;
        for(int i=1; i<total; i++) 
        {
            ordenado = true;
            for(int j=0; j < total - i; j++) 
            { 
                if(nomes[j].compareTo(nomes[j+1]) >0 ) 
                {
                    temp = nomes[j];
                    nomes[j] = nomes[j+1];
                    nomes[j+1] = temp;
                    ordenado = false;
                }
            }
            processado++;
            if(ordenado) break;
        }
        processado = total;
        concluido = true;
          
    }

    @Override
    public void run()
    {
        bubble_sort();
    }
}
