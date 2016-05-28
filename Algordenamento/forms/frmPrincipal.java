package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.border.EtchedBorder;

import classes.gerador;
import classes.gerador.modos;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class frmPrincipal extends JFrame {
    private JTextField txtPrefix;
    private JTextField txtPostfix;
    private ButtonGroup bg;
    private JSpinner spNumItems;
    private JRadioButton rbRandom;
    private JRadioButton rbSemiRandom;
    private JRadioButton rbLowVariation;
    private JLabel lblPrefixo;
    private JCheckBox cbRepeat;
    private JLabel lblNitens;
    private JLayeredPane panel;
    private JCheckBox chckbxTamanhoFixo;
    private JRadioButton rbInverse;
    private JButton btnGerar;
    private Timer tim;
    private gerador ger;
    private JButton btnMostrarLista;
    private JButton btnOrdenar;
    private JLabel lblAlgoritimo;
    private JLabel lblPorcento;
    private JProgressBar pbProgresso;
    private JComboBox<String> cbAlgoritimo;
    private JLabel lblOperaes;
    private JLabel lblAleatoriedade;
    private JLabel lblPsfixo;
    private JPanel pnOrdenar;
    private JScrollPane scrollPane;
    private JList<String> lstNomes;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmPrincipal frame = new frmPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public frmPrincipal() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 574, 560);
        getContentPane().setLayout(null);
        
        panel = new JLayeredPane();
        panel.setBounds(12, 12, 277, 373);
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
        cbRepeat.setSelected(true);
        panel.add(cbRepeat, "cell 0 1,alignx left,aligny top");
        
        cbRepeat.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if (!cbRepeat.isSelected()) {
                    if (rbLowVariation.isSelected())
                        rbRandom.setSelected(true);
                    rbLowVariation.setEnabled(false);
                } else {
                    rbLowVariation.setEnabled(true);
                }
            }
        });
        
        chckbxTamanhoFixo = new JCheckBox("Tamanho fixo");
        chckbxTamanhoFixo.setSelected(true);
        panel.add(chckbxTamanhoFixo, "cell 0 2");
        
        lblAleatoriedade = new JLabel("Aleatoriedade:");
        panel.add(lblAleatoriedade, "cell 0 3");
        
        rbRandom = new JRadioButton("Aleatória");
        panel.add(rbRandom, "cell 0 4");
        rbRandom.setSelected(true);
        bg.add(rbRandom);
        
        rbSemiRandom = new JRadioButton("Semi-aleatória");
        panel.add(rbSemiRandom, "cell 0 5");
        bg.add(rbSemiRandom);
        
        rbInverse = new JRadioButton("Inversa");
        rbInverse.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                
                //não dá pra ser aleatorio e inverso..
                if (rbInverse.isSelected())
                    cbRepeat.setEnabled(false);
                
                else
                    cbRepeat.setEnabled(true);
            }
        });
        panel.add(rbInverse, "cell 0 6");
        bg.add(rbInverse);
        
        rbLowVariation = new JRadioButton("Pouca variação");
        panel.add(rbLowVariation, "cell 0 7");
        bg.add(rbLowVariation);
        lblPrefixo = new JLabel("Texto prefixo:");
        panel.add(lblPrefixo, "cell 0 8,alignx left");
        
        txtPrefix = new JTextField();
        txtPrefix.setText("foto_");
        panel.add(txtPrefix, "cell 1 8,grow");
        txtPrefix.setColumns(10);
        
        lblPsfixo = new JLabel("Texto pósfixo:");
        panel.add(lblPsfixo, "cell 0 9,alignx left");
        
        
        btnGerar = new JButton("Gerar lista");
        btnGerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int tamanho = Integer.valueOf(spNumItems.getValue().toString());
                
                //quando não é permitido repetições, acontece o dobro de operações.
                pbProgresso.setMaximum(cbRepeat.isSelected() ? tamanho : tamanho * 2);
                ger = new gerador(cbRepeat.isSelected(), 
                        chckbxTamanhoFixo.isSelected(),
                        modos.aleatoria, 
                        tamanho, 
                        txtPrefix.getText(),
                        txtPostfix.getText());
                
                //talvez isso não mude muita coisa pro garbage collector..
                ger.LimparNomes();
                
                //gera sempre um novo timer, não sei como otimizar isso em java
                tim = new Timer(16, new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                       int processado = ger.Processado();
                       lblPorcento.setText(String.valueOf(processado));
                       pbProgresso.setValue(processado);
                       
                       //se não parar, novos timers vão persistir
                       if (ger.getConcluido()) {
                           tim.stop();
                           btnGerar.setEnabled(true);
                       }
                       else 
                           //caso algum infeliz decida estragar as threads, NÉ >_>
                           btnGerar.setEnabled(false);
                       
                    }
                });
                
                tim.start();
                new Thread(ger).start();
            }
        });
        
        txtPostfix = new JTextField();
        txtPostfix.setText(".jpg");
        txtPostfix.setColumns(10);
        panel.add(txtPostfix, "cell 1 9,grow");
        panel.add(btnGerar, "cell 0 11,aligny baseline");
        
        pnOrdenar = new JPanel();
        pnOrdenar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        pnOrdenar.setBounds(301, 12, 259, 373);
        getContentPane().add(pnOrdenar);
        pnOrdenar.setLayout(null);
        
        btnMostrarLista = new JButton("Mostrar");
        btnMostrarLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //a unica forma que eu consegui fazer isso adicionar coisas na lista
                //o eclipse gerou esse codigo, eu não sei ao certo como funciona.
                //parece uma chamada implicita nos metodos getSize e getElementAt
                lstNomes.setModel(new AbstractListModel<String>() {
                    String[] values = ger.getNomes();
                    public int getSize() {
                        return values.length;
                    }
                    public String getElementAt(int index) {
                        return values[index];
                    }
                });
            }
        });
        btnMostrarLista.setBounds(133, 34, 114, 25);
        pnOrdenar.add(btnMostrarLista);
        
        btnOrdenar = new JButton("Ordenar");
        btnOrdenar.setBounds(133, 66, 114, 25);
        pnOrdenar.add(btnOrdenar);
        
        lblOperaes = new JLabel("Operações:");
        lblOperaes.setBounds(12, 39, 100, 15);
        pnOrdenar.add(lblOperaes);
        
        lblAlgoritimo = new JLabel("Algoritimo:");
        lblAlgoritimo.setBounds(12, 12, 100, 15);
        pnOrdenar.add(lblAlgoritimo);
        
        cbAlgoritimo = new JComboBox<String>();
        cbAlgoritimo.setBounds(133, 7, 114, 24);
        pnOrdenar.add(cbAlgoritimo);
        
        cbAlgoritimo.setBounds(133, 7, 114, 24);
        pnOrdenar.add(cbAlgoritimo);
        
        
        cbAlgoritimo = new JComboBox<String>();
        cbAlgoritimo.setBounds(133, 7, 114, 24);
        pnOrdenar.add(cbAlgoritimo);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 98, 235, 263);
        pnOrdenar.add(scrollPane);
        
        lstNomes = new JList<String>();
        lstNomes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(lstNomes);
        
        
        pbProgresso = new JProgressBar();
        pbProgresso.setBounds(12, 397, 441, 13);
        getContentPane().add(pbProgresso);
        
        lblPorcento = new JLabel("0");
        lblPorcento.setHorizontalAlignment(SwingConstants.CENTER);
        lblPorcento.setBounds(471, 397, 89, 15);
        getContentPane().add(lblPorcento);

    }
}
