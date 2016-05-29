package classes;

//fonte: http://rosettacode.org/wiki/Sorting_algorithms/Heapsort
//modificado para aceitar String[], e traduzido do ingles

public class Heap extends BaseT implements Runnable
{
    public Heap(String[] nomes)
    {
        this.nomes = nomes;
        processado = 0;
    }

    private void heapSort()
    {
        int total = nomes.length;

        concluido = false;
        //primeiro colocar a na ordem do max-heap
        heapificar(total);

        int fim = total - 1;
        a_ser_processado = fim;
        while (fim > 0)
        {
            //trocar o root(maximum value) da heap com o ultimo elemento da heap
            String tmp = nomes[fim];
            nomes[fim] = nomes[0];
            nomes[0] = tmp;
            //colocar o heap de volta na ordem do max-heap
            siftDown(0, fim - 1);
            // diminuir o tamanho da heap, assim os valores maximos anteriores
            //continuarão no seu devido lugar
            processado++;
            fim--;
        }
        concluido = true;
    }

    private void heapificar(int total)
    {
        //começar definindo o indice no a do ultimo nodo pai
        int inicio = (total - 2) / 2; // heap binario

        while (inicio >= 0)
        {
            // sift down the node at index start to the proper place
            // such that all nodes below the start index are in heap
            // order
            siftDown(inicio, total - 1);
            inicio--;
        }
        // after sifting down the root all nodes/elements are in heap order
    }

    private void siftDown(int inicio, int fim)
    {
        // end represents the limit of how far down the heap to sift
        int raiz = inicio;

        while ((raiz * 2 + 1) <= fim)
        { // While the root has at least one child
            int filho = raiz * 2 + 1; // root*2+1 points to the left child
            // if the child has a sibling and the child's value is less than its
            // sibling's...
            if (filho + 1 <= fim && nomes[filho].compareTo(nomes[filho + 1]) < 0)
                filho = filho + 1; // ... then point to the right child instead
            if (nomes[raiz].compareTo(nomes[filho]) < 0)
            { // out of max-heap order
                String tmp = nomes[raiz];
                nomes[raiz] = nomes[filho];
                nomes[filho] = tmp;
                raiz = filho; // repeat to continue sifting down the child now
            } else
                return;
        }
    }

    @Override
    public void run()
    {
        heapSort();
    }
}
