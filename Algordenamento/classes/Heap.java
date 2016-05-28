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
        int count = nomes.length;

        //primeiro colocar a na ordem do max-heap
        heapify(count);

        int end = count - 1;
        while (end > 0)
        {
            //trocar o root(maximum value) da heap com o ultimo elemento da heap
            String tmp = nomes[end];
            nomes[end] = nomes[0];
            nomes[0] = tmp;
            //colocar o heap de volta na ordem do max-heap
            siftDown(0, end - 1);
            // diminuir o tamanho da heap, assim os valores maximos anteriores
            //continuarão no seu devido lugar
            processado++;
            end--;
        }
    }

    private void heapify(int count)
    {
        //começar definindo o indice no a do ultimo nodo pai
        int start = (count - 2) / 2; // heap binario

        while (start >= 0)
        {
            // sift down the node at index start to the proper place
            // such that all nodes below the start index are in heap
            // order
            siftDown(start, count - 1);
            start--;
        }
        // after sifting down the root all nodes/elements are in heap order
    }

    private void siftDown(int start, int end)
    {
        // end represents the limit of how far down the heap to sift
        int root = start;

        while ((root * 2 + 1) <= end)
        { // While the root has at least one child
            int child = root * 2 + 1; // root*2+1 points to the left child
            // if the child has a sibling and the child's value is less than its
            // sibling's...
            if (child + 1 <= end && nomes[child].compareTo(nomes[child + 1]) < 0)
                child = child + 1; // ... then point to the right child instead
            if (nomes[root].compareTo(nomes[child]) < 0)
            { // out of max-heap order
                String tmp = nomes[root];
                nomes[root] = nomes[child];
                nomes[child] = tmp;
                root = child; // repeat to continue sifting down the child now
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
