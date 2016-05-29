# APS12016

**Ainda to tentando lidar com essa bagaça de github, paciencia**

######alguns pontos bons do projeto:

- texto prefixo (e.g.: foto_100) e posfixo  (e.g.: 100.jpg) na geração de nomes
- permitir que se repita nomes ou não
- tamanho fixo de nomes (e.g.: foto_0001.jpg .. foto_9999.jpg) o comprimento vai de acordo com o numero de casas.
- a geração de nomes roda numa thread separada, assim dá pra ver o progresso sem travar o programa (ou quase sem travar.. é java lol)
- log dos eventos ocorridos (*ainda não implementado*)


######pontos ruins:

- o código tá meio mal organizado, ainda estou arrumando onde posso e colocando muitos comentarios em tudo de util.
- gerar 1000000 itens leva em torno de 5 segundos mas consome aproximadamente 50mb, e isso continua a crescer se criar novamente. acho que é culpa do garbage collector do java né..
- usei muito codigo em closures, isso reduz o tamanho do frmPrincipal porém fica mais dificil de ler, 

>comentarios no código fonte salvam vidas de golfinhos :+1:


aqui talvez eu coloque uns resultados de testes no meu pc, junto da configuração dele e as opções do programa

| teste  | teste |
| ---- | ---- |
| teste  | teste  |
| teste  | teste  |

> lmao
