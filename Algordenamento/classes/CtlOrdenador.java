package classes;

/**
 * Classe intermediária que controla o fluxo de dados dos métodos de ordenação
 **/
public class CtlOrdenador extends BaseT
{
    private Thread t;
    
    public enum ordenador
    {
        Bubble_sort,
        Heap_sort,
        Insertion_sort,
    }
    
    public CtlOrdenador(String[] nomes, ordenador algoritimo) throws Exception
    {
        BaseT algo = null;
        this.a_ser_processado = nomes.length;
        
        switch (algoritimo)
        {
        case Bubble_sort:
            algo = new Bubble(nomes);
            break;
            
        case Heap_sort:
            algo = new Heap(nomes);
            break;
            
        case Insertion_sort:
            algo = new Insertion(nomes);
            break;
            
        default:
            throw new Exception("Erro");
        }

        t = new Thread((Runnable) algo);
    }
    
    public void iniciar()
    {
        t.start();
    }
}
