package classes;

public class gerador implements Runnable{
    private String nomes[];
    private int processado;
    
    public enum modos
    {
        aleatoria,
        semi_aleatoria,
        inversa,
        pouca_variacao
    }
    
    private boolean repetir;
    private modos Modo; 
    private int tamanho; 
    private String prefixo; 
    private String posfixo;
    private boolean concluido;
    
    
    public gerador(boolean repetir, modos Modo, int tamanho, String prefixo, String posfixo) {
        this.repetir = repetir;
        this.Modo = Modo;
        this.tamanho = tamanho;
        this.prefixo = prefixo;
        this.posfixo = posfixo;
        processado = 0;
    }
    
    public String[] getNomes(){
        return this.nomes;
    }
    
    public boolean getConcluido(){
        return this.concluido;
    }
    
    public void LimparNomes() {
        nomes = null;
    }
    
    public int Processado(){
        return processado;
    }

    @Override
    public void run() {
        nomes = new String[tamanho];
        concluido = false;
        switch (Modo){
        case aleatoria:
            if (repetir) {
                for (int i=0; i<tamanho;i++) {
                    nomes[i] = prefixo + ((int)(Math.random() * tamanho)) + posfixo;
                    
                    processado++;
                }
            } 
            else {
                for (int i=0; i<tamanho;i++) {
                    nomes[i] = prefixo + i + posfixo;
                    processado++;
                }
                String temp;
                int indice;
                for (int i=0; i<tamanho;i++){
                    indice = ((int)(Math.random() * tamanho)) % tamanho;
                    temp = nomes[i];
                    nomes[i] = nomes[indice];
                    nomes[indice] = temp;
                    
                    processado++;
                }
            }
            break;
        
        case semi_aleatoria:
            break;
        case inversa:
            break;
        case pouca_variacao:
            break;
        default:
            break;
        }
        concluido = true;
    }
}
