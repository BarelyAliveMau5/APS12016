# APS12016


##alguns pontos bons do projeto:

- texto prefixo (e.g.: foto_100) e posfixo  (e.g.: 100.jpg) na geração de nomes
- permitir que se repita nomes ou não
- tamanho fixo de nomes opcional (e.g.: foto_0001.jpg .. foto_9999.jpg) o comprimento vai de acordo com o numero de casas.
- a geração de nomes roda numa thread separada, assim dá pra ver o progresso sem travar o programa
- log dos eventos ocorridos com texto formatado
- salvar e carregar lista previamente gerada


##pontos ruins:

- o código tá meio mal organizado, ainda estou arrumando onde posso e colocando muitos comentarios em tudo de util.
- gerar 1000000 itens leva em torno de 5 segundos mas consome aproximadamente 50mb
- usei muito codigo em closures, isso reduz o tamanho do frmPrincipal porém fica mais dificil de ler, 
- existem aguns bugs ainda não descobertos

![geracao](http://i.imgur.com/VRH7Q8o.png)
