package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;

import classes.Gerador;
import classes.Gerador.modos;
import classes.Log.estilos;
import classes.Profiler;
import classes.Log;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JEditorPane;
import javax.swing.DefaultComboBoxModel;

import classes.CtlOrdenador;
import classes.CtlOrdenador.ordenador;

public class frmPrincipal extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JLabel          lblOperaes;
    private JLabel          lblAleatoriedade;
    private JLabel          lblPsfixo;
    private JLabel          lblAlgoritimo;
    private JLabel          lblPorcento;
    private JLabel          lblNitens;
    private JLabel          lblPrefixo;
    private JButton         btnMostrarLista;
    private JButton         btnOrdenar;
    private JButton         btnGerar;
    private JTextField      txtPrefix;
    private JTextField      txtPostfix;
    private JEditorPane     txtLog;
    private JRadioButton    rbRandom;
    private JRadioButton    rbSemiRandom;
    private JRadioButton    rbLowVariation;
    private JRadioButton    rbInverse;
    private JCheckBox       cbRepeat;
    private JCheckBox       chckbxTamanhoFixo;
    private JSpinner        spNumItems;
    private JPanel          pnOrdenar;
    private JLayeredPane    panel;
    private JProgressBar    pbProgresso;
    private JComboBox<ordenador> cbAlgoritimo;
    private JScrollPane     scrNomes;
    private JScrollPane     scrLog;
    private JList<String>   lstNomes;
    private ButtonGroup     bg;
    private Gerador         ger;
    private Timer           tim;
    private Log             log;
    private Profiler        contador;
    private Thread          piao;
    private CtlOrdenador    algor;
    private JButton         btnSalvarLista;
    private JButton         btnCarregarLista;
    private boolean         isListaSalva;
    private String[]        lista_salva;     
    
    /**
     * escreve um texto formatado na caixa de texto jtp
     */
    private void logEvt(String msg,JEditorPane jtp,estilos Estilo)
    {
        log.msg(msg ,Estilo);
        jtp.setText(log.getLog());
    }
    
    /**
     * modifica todos os controles do usuário de uma só vez
     */
    private void TravarControles(boolean estado)
    {
        btnGerar.setEnabled(!estado);
        btnMostrarLista.setEnabled(!estado);
        btnOrdenar.setEnabled(!estado);
        cbAlgoritimo.setEnabled(!estado);
        btnSalvarLista.setEnabled(!estado);
        if (isListaSalva && !estado)
            btnCarregarLista.setEnabled(true);
        else
            btnCarregarLista.setEnabled(false);
    }
    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    frmPrincipal frame = new frmPrincipal();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public frmPrincipal()
    {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 624, 559);
        getContentPane().setLayout(null);
        
        log = new Log();
        
        panel = new JLayeredPane();
        panel.setBounds(12, 12, 327, 373);
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        getContentPane().add(panel);
        panel.setLayout(new MigLayout("", "[155.00px][94.00px,grow]", "[20px][23px][25.00px][25.00][20.00][25.00][25.00][25.00][25.00][25.00][25.00][30.00]"));

        lblNitens = new JLabel("Número de itens:");
        panel.add(lblNitens, "cell 0 0,alignx left,aligny center");

        spNumItems = new JSpinner();
        spNumItems.setModel(new SpinnerNumberModel(new Integer(1000000), null, null, new Integer(100000)));
        panel.add(spNumItems, "cell 1 0,growx,aligny top");

        bg = new ButtonGroup();

        cbRepeat = new JCheckBox("Permitir repetições");
        cbRepeat.setToolTipText("Nomes podem aparecer mais de uma vez");
        cbRepeat.setSelected(true);
        panel.add(cbRepeat, "cell 0 1,alignx left,aligny top");

        chckbxTamanhoFixo = new JCheckBox("Tamanho fixo");
        chckbxTamanhoFixo.setToolTipText("O numero de caracteres em cada nome permanece o mesmo");
        chckbxTamanhoFixo.setSelected(true);
        panel.add(chckbxTamanhoFixo, "cell 0 2");

        lblAleatoriedade = new JLabel("Aleatoriedade:");
        panel.add(lblAleatoriedade, "cell 0 3");

        rbRandom = new JRadioButton("Aleatória");
        rbRandom.setToolTipText("Totalmente aleatório");
        panel.add(rbRandom, "cell 0 4");
        rbRandom.setSelected(true);
        bg.add(rbRandom);

        rbSemiRandom = new JRadioButton("Semi-aleatória");
        rbSemiRandom.setToolTipText("Sequencia com alguns nomes fora de ordem");
        panel.add(rbSemiRandom, "cell 0 5");
        bg.add(rbSemiRandom);

        rbInverse = new JRadioButton("Inversa");
        rbInverse.setToolTipText("Do maior número ao menor");

        panel.add(rbInverse, "cell 0 6");
        bg.add(rbInverse);

        rbLowVariation = new JRadioButton("Pouca variação");
        rbLowVariation.setToolTipText("Muitos nomes repetidos. A opção \"Permitir repetições\" é obrigatória");
        panel.add(rbLowVariation, "cell 0 7");
        bg.add(rbLowVariation);
        lblPrefixo = new JLabel("Texto prefixo:");
        lblPrefixo.setToolTipText("Texto que vem antes da numeração");
        panel.add(lblPrefixo, "cell 0 8,alignx left");

        txtPrefix = new JTextField();
        txtPrefix.setText("foto_");
        panel.add(txtPrefix, "cell 1 8,grow");
        txtPrefix.setColumns(10);

        lblPsfixo = new JLabel("Texto pósfixo:");
        lblPsfixo.setToolTipText("Texto que vem após a numeração");
        panel.add(lblPsfixo, "cell 0 9,alignx left");

        txtPostfix = new JTextField();
        txtPostfix.setText(".jpg");
        txtPostfix.setColumns(10);
        panel.add(txtPostfix, "cell 1 9,grow");
        
                btnGerar = new JButton("Gerar nova lista");
                btnGerar.setToolTipText("Produzir nomes de acordo com as especificações oferecidas");
                panel.add(btnGerar, "cell 0 10,growx,aligny baseline");
                
                btnSalvarLista = new JButton("Salvar lista");
                btnSalvarLista.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent arg0) 
                    {
                        try
                        {
                            lista_salva =  Arrays.copyOf(ger.getNomes(), ger.getNomes().length); 
                            logEvt("Lista salva",txtLog,estilos.ITALICO);
                        }
                        catch (Exception er)
                        {
                            logEvt("Erro: "+er.getMessage(),txtLog,estilos.ERRO);
                        }
                        isListaSalva = true;
                        TravarControles(false);
                    }
                });
                btnSalvarLista.setEnabled(false);
                panel.add(btnSalvarLista, "cell 0 11,growx");
                
                btnCarregarLista = new JButton("Carregar lista");
                btnCarregarLista.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                        try
                        {
                            if (!isListaSalva) throw new Exception("Tentativa de carregar sem estar salvo");
                            ger.setNomes(lista_salva);
                            logEvt("Lista carregada",txtLog,estilos.ITALICO);
                        }
                        catch (Exception er)
                        {
                            logEvt("Erro: "+er.getMessage(),txtLog,estilos.ERRO);
                        }
                    }
                });
                btnCarregarLista.setEnabled(false);
                panel.add(btnCarregarLista, "cell 1 11,growx");
                btnGerar.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent arg0)
                    {
                        //inicia contagem, antes de tudo
                        contador = new Profiler();
                        
                        int tamanho = Integer.valueOf(spNumItems.getValue().toString());

                        modos Modo = modos.aleatoria;
                        if (rbRandom.isSelected())          Modo = modos.aleatoria;
                        if (rbInverse.isSelected())         Modo = modos.inversa;
                        if (rbLowVariation.isSelected())    Modo = modos.pouca_variacao;
                        if (rbSemiRandom.isSelected())      Modo = modos.semi_aleatoria;
                        
                        try {
                            if (tamanho < 100) throw new Exception("Número abaixo do mínimo permitido");
                            
                            ger = new Gerador(cbRepeat.isSelected(),    // permitir repetições
                                    chckbxTamanhoFixo.isSelected(),     // tamanho fixo
                                    Modo,                               // modo
                                    tamanho,                            // numero total de itens
                                    txtPrefix.getText(),                // texto prefixo
                                    txtPostfix.getText());              // texto posfixo
                            
                            logEvt("Gerando lista de nomes com formato \""+ Modo.toString()+"\"",txtLog, estilos.NORMAL);
                            
                            //definir o numero de operações a ser processado
                            pbProgresso.setMaximum(ger.getASerProcessado());
                            
                            // talvez isso não mude muita coisa pro garbage collector..
                            ger.LimparNomes();
                            
                            TravarControles(true);
                            // gera sempre um novo timer, não sei como otimizar isso em java
                            tim = new Timer(16, new ActionListener()
                            {
                                public void actionPerformed(ActionEvent arg0)
                                {
                                    int processado = ger.getProcessado();
                                    lblPorcento.setText(String.valueOf( processado ));
                                    pbProgresso.setValue(processado);
    
                                    // se não parar, novos timers vão persistir
                                    if (ger.getConcluido())
                                    {
                                        tim.stop();
                                        TravarControles(false);
                                        logEvt("Geração de nomes concluída em "+ contador.Tempo_Final(false) , txtLog, estilos.NEGRITO);
                                    } 
                                    else 
                                        TravarControles(true);
                                }
                            });
    
                            tim.start();
                            piao = new Thread(ger);
                            piao.start();
                        } 
                        catch (Exception er)
                        {
                            logEvt("Erro: "+er.getMessage(),txtLog,estilos.ERRO);
                        }
                    }
                });

        pnOrdenar = new JPanel();
        pnOrdenar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        pnOrdenar.setBounds(351, 12, 259, 373);
        getContentPane().add(pnOrdenar);
        pnOrdenar.setLayout(null);

        btnMostrarLista = new JButton("Mostrar");
        btnMostrarLista.setEnabled(false);
        btnMostrarLista.setToolTipText("Mostrar nomes gerados na lista.");
        
        btnMostrarLista.setBounds(133, 34, 114, 25);
        pnOrdenar.add(btnMostrarLista);
        btnOrdenar = new JButton("Ordenar");

        btnOrdenar.setEnabled(false);
        btnOrdenar.setToolTipText("Ordenar e mostrar nomes na lista");
        btnOrdenar.setBounds(133, 66, 114, 25);
        pnOrdenar.add(btnOrdenar);

        lblOperaes = new JLabel("Operações:");
        lblOperaes.setBounds(12, 39, 100, 15);
        pnOrdenar.add(lblOperaes);

        lblAlgoritimo = new JLabel("Algoritimo:");
        lblAlgoritimo.setBounds(12, 12, 100, 15);
        pnOrdenar.add(lblAlgoritimo);

        cbAlgoritimo = new JComboBox<ordenador>();
        cbAlgoritimo.setModel(new DefaultComboBoxModel<ordenador>(ordenador.values()));
        cbAlgoritimo.setToolTipText("Algoritimo de ordenação a ser usado");
        cbAlgoritimo.setBounds(133, 7, 114, 24);
        pnOrdenar.add(cbAlgoritimo);

        scrNomes = new JScrollPane();
        scrNomes.setBounds(12, 98, 235, 263);
        pnOrdenar.add(scrNomes);

        lstNomes = new JList<String>();
        lstNomes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrNomes.setViewportView(lstNomes);

        pbProgresso = new JProgressBar();
        pbProgresso.setToolTipText("Progresso da operação atual");
        pbProgresso.setBounds(12, 397, 528, 13);
        getContentPane().add(pbProgresso);

        lblPorcento = new JLabel("0");
        lblPorcento.setToolTipText("Número de operações realizadas na lista de nomes virtual");
        lblPorcento.setHorizontalAlignment(SwingConstants.CENTER);
        lblPorcento.setBounds(538, 395, 72, 15);
        getContentPane().add(lblPorcento);
        
        scrLog = new JScrollPane();
        scrLog.setBounds(12, 422, 598, 99);
        getContentPane().add(scrLog);
        
        txtLog = new JEditorPane();
        txtLog.setContentType("text/html");
        txtLog.setEditable(false);
        scrLog.setViewportView(txtLog);

        btnOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    
                    logEvt("Iniciando ordenação com o algoritimo "+ cbAlgoritimo.getSelectedItem().toString(), txtLog, estilos.NORMAL);
                    
                    contador = new Profiler();
                    
                    //instanciar usando item da lista com cast do ordenador
                    algor = new CtlOrdenador(ger.getNomes(),(ordenador)cbAlgoritimo.getSelectedItem());
                   
                    //isso é ambiguo, sim eu sei, a quantidade não muda
                    //mas são boas maneiras
                    pbProgresso.setMaximum(algor.getASerProcessado());
                    
                    TravarControles(true);
                    tim = new Timer(100, new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            pbProgresso.setValue(algor.getProcessado());
                            lblPorcento.setText(String.valueOf(algor.getProcessado()));
                            if (algor.getConcluido())
                            {
                                tim.stop();
                                TravarControles(false);
                                logEvt("Ordenação finalizada em " + contador.Tempo_Final(false)
                                , txtLog, estilos.NOTICIA);
                                
                            }
                            else
                                TravarControles(true);
                        }
                    });
                    
                    tim.start();
                    algor.iniciar();
                } 
                catch (Exception e1)
                {
                    logEvt(e1.getMessage(), txtLog, estilos.ERRO);
                    e1.printStackTrace();
                }
            }
        });
        
        /**
         * desmarcar a caixa pra permitir repetições
         ***/
        rbSemiRandom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rbSemiRandom.isSelected())
                {
                    cbRepeat.setEnabled(false);
                    cbRepeat.setSelected(false);
                }
                else
                    cbRepeat.setEnabled(true);
            }
        });
        
        /**
         * marcar a caixa pra permitir repetições
         ***/
        rbLowVariation.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rbLowVariation.isSelected())
                    cbRepeat.setSelected(true);
            }
        });
        
        /**
         * se desativar as repetições, isso muda de opção se for "pouca variação".
         * se vai ter baixa variação, é porque existem itens repetidos, duh
         ***/
        cbRepeat.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent arg0)
            {
                if (!cbRepeat.isSelected())
                    if (rbLowVariation.isSelected())
                        rbRandom.setSelected(true);
            }
        });
        
        /**
         * desativa o checkbox de repetição
         ***/
        rbInverse.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent arg0)
            {
                // não dá pra ser aleatorio e inverso..
                if (rbInverse.isSelected()){
                    cbRepeat.setEnabled(false);
                    cbRepeat.setSelected(false);
                }
                else
                    cbRepeat.setEnabled(true);
            }
        });
        
        /**
         * óh botão que gera os nomes
         ***/

        /**
         * mostrar as coisas na lista
         ***/
        btnMostrarLista.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                // modifiquei o codigo gerado pelo eclipse, assim ele insere na 
                // lista os nomes gerados
                logEvt("Itens carregados na lista",txtLog,estilos.ITALICO);
                
                lstNomes.setModel(new AbstractListModel<String>()
                {
                    private static final long serialVersionUID = 1L;
                    String[] values = ger.getNomes();

                    public int getSize()
                    {
                        return values.length;
                    }

                    public String getElementAt(int index)
                    {
                        return values[index];
                    }
                });
                
            }
        });

    }
}
