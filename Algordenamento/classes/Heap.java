package classes;

// fonte: http://rosettacode.org/wiki/Sorting_algorithms/Heapsort
// modificado para aceitar String[], e traduzido do ingles

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
        // primeiro colocar a na ordem do max-heap
        heapificar(total);

        int fim = total - 1;
        while (fim > 0)
        {
            //trocar o root(maximum value) da heap com o ultimo elemento do monte
            String tmp = nomes[fim];
            nomes[fim] = nomes[0];
            nomes[0] = tmp;
            // colocar o heap de volta na ordem do max-heap
            siftDown(0, fim - 1);
            // diminuir o tamanho da heap, assim os valores maximos anteriores
            // continuarão no seu devido lugar
            processado++;
            fim--;
        }
        concluido = true;
    }

    private void heapificar(int total)
    {
        // começar definindo o indice no a do ultimo nodo pai
        int inicio = (total - 2) / 2; // heap binario

        while (inicio >= 0)
        {
            // processar o nodo no indice inicial para seu devido lugar,
            // assim todos os nodos abaixo do indice inicial estão em ordem
            siftDown(inicio, total - 1);
            inicio--;
        }
        //depois de processar a raiz, todos os nodos/elementos estão ordenados
    }

    private void siftDown(int inicio, int fim)
    {
        // fim representa o limite do quão longe o monte pode ser processado
        int raiz = inicio;

        while ((raiz * 2 + 1) <= fim)
        { // enquanto a raiz tem pelo menos um filho
            int filho = raiz * 2 + 1; // raiz*2+1 aponta para o filho da esquerda
            // se o filho tem um irmão e o valor do filho é menor que do irmão ...
            if (filho + 1 <= fim && nomes[filho].compareTo(nomes[filho + 1]) < 0)
                filho = filho + 1; // ... então apontar para o filho da direita como alternativa
            if (nomes[raiz].compareTo(nomes[filho]) < 0)
            { // não há odrem de max-heap
                String tmp = nomes[raiz];
                nomes[raiz] = nomes[filho];
                nomes[filho] = tmp;
                raiz = filho; // repetir para continuar processando abaixo do filho agora
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
