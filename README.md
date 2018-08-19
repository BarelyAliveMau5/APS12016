# APS12016
Este projeto tem o propósito de mostrar a eficiência de cada um dos métodos de ordenação implementados (bubble sort, heap sort e insertion sort), usando uma suposta lista de nomes de arquivos que é gerada no momento de execução.

## Caractersticas boas do projeto:

- texto prefixo (e.g.: foto_100) e sufixo  (e.g.: 100.jpg) na geração de nomes
- permitir que se repita nomes ou não
- tamanho fixo de nomes opcional (e.g.: foto_0001.jpg .. foto_9999.jpg) o comprimento vai de acordo com o numero de casas.
- a geração de nomes executa numa thread separada, assim dá pra ver o progresso da operação sem travar o programa
- log dos eventos ocorridos com texto formatado e colorido
- salvar e carregar lista previamente gerada


## Caractersticas ruins do projeto:

- o código está meio mal organizado.
- gerar 1000000 itens leva em torno de 5 segundos e consome aproximadamente 50mb
- foi usado muito codigo em closures, isso dificulta a legibilidade do código da janela principal 
- é muito provável que exista alguns bugs ainda não descobertos

![geracao](http://i.imgur.com/VRH7Q8o.png)
