package v2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

public class ConfigDialog extends JDialog {

    // Campos do formulário 
    private JTextField fieldImagePath  = new JTextField("img_0.png", 50);
    private JTextField fieldStartX     = new JTextField("0",3);
    private JTextField fieldStartY     = new JTextField("0",3);
    private JTextField fieldScale      = new JTextField("1",3);
    private JTextField fieldDelay      = new JTextField("10",3);
    private JTextField fieldFrameSkip  = new JTextField("10",3);
    private JButton    btnColor        = new JButton("   Escolher   ");
    private JComboBox<String> comboMethod = new JComboBox<>(new String[]{"Pilha (Stack)", "Fila (Queue)"});

    //  Resultado 
    private Color selectedColor = Color.MAGENTA;
    private boolean confirmed   = false;

    public ConfigDialog(JFrame parent) {
        super(parent, "Configurações do Flood Fill", true);
        buildUI();
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //  Painel: Imagem 
        JPanel panelImg = titledPanel("Imagem");
        panelImg.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelImg.add(new JLabel("Arquivo (PNG):"));
        panelImg.add(fieldImagePath);
        JButton btnBrowse = new JButton("...");
        btnBrowse.addActionListener(e -> browseFile());
        panelImg.add(btnBrowse);

        //  Painel: Ponto inicial 
        JPanel panelPoint = titledPanel("Ponto Inicial");
        panelPoint.add(new JLabel("X (coluna):"));
        panelPoint.add(fieldStartX);
        panelPoint.add(Box.createHorizontalStrut(15));
        panelPoint.add(new JLabel("Y (linha):"));
        panelPoint.add(fieldStartY);

        //  Painel: Visualização 
        JPanel panelView = titledPanel("Visualização");
        panelView.add(new JLabel("Escala (px por pixel):"));
        panelView.add(fieldScale);
        panelView.add(Box.createHorizontalStrut(15));
        panelView.add(new JLabel("Frame Skip:"));
        panelView.add(fieldFrameSkip);
        JLabel labelFS = new JLabel("  (1 = toda atualização, 100 = mais rápido)");
        labelFS.setForeground(Color.GRAY);
        labelFS.setFont(labelFS.getFont().deriveFont(10f));
        panelView.add(labelFS);

        //  Painel: Animação 
        JPanel panelAnim = titledPanel("Animação");
        panelAnim.add(new JLabel("Delay por frame (ms):"));
        panelAnim.add(fieldDelay);
        panelAnim.add(Box.createHorizontalStrut(15));
        panelAnim.add(new JLabel("Método:"));
        panelAnim.add(comboMethod);

        //  Painel: Cor 
        JPanel panelColor = titledPanel("Cor de Preenchimento");
        btnColor.setBackground(selectedColor);
        btnColor.setOpaque(true);
        btnColor.addActionListener(e -> chooseColor());
        panelColor.add(new JLabel("Cor escolhida:"));
        panelColor.add(btnColor);

        //  Layout central (empilha os painéis) 
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(panelImg);
        center.add(Box.createVerticalStrut(5));
        center.add(panelPoint);
        center.add(Box.createVerticalStrut(5));
        center.add(panelView);
        center.add(Box.createVerticalStrut(5));
        center.add(panelAnim);
        center.add(Box.createVerticalStrut(5));
        center.add(panelColor);

        //  Botões OK / Cancelar 
        JButton btnOk     = new JButton("▶  Iniciar");
        JButton btnCancel = new JButton("Cancelar");

        btnOk.setBackground(new Color(50, 150, 50));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(btnOk.getFont().deriveFont(Font.BOLD, 13f));

        btnOk.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            }
        });
        btnCancel.addActionListener(e -> dispose());

        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtns.add(btnCancel);
        panelBtns.add(btnOk);

        root.add(center,    BorderLayout.CENTER);
        root.add(panelBtns, BorderLayout.SOUTH);
        setContentPane(root);
    }

    //  Abre seletor de arquivo 
    private void browseFile() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens PNG", "png"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fieldImagePath.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    //  Abre seletor de cor 
    private void chooseColor() {
        Color c = JColorChooser.showDialog(this, "Escolha a cor de preenchimento", selectedColor);
        if (c != null) {
            selectedColor = c;
            btnColor.setBackground(c);
            // Ajusta texto do botão para contraste
            int brightness = (c.getRed() * 299 + c.getGreen() * 587 + c.getBlue() * 114) / 1000;
            btnColor.setForeground(brightness > 128 ? Color.BLACK : Color.WHITE);
        }
    }

    //  Valida todos os campos 
    private boolean validateFields() {
        try {
            File f = new File(fieldImagePath.getText().trim());
            if (!f.exists()) {
                erro("Arquivo de imagem não encontrado:\n" + f.getAbsolutePath());
                return false;
            }
            int x     = Integer.parseInt(fieldStartX.getText().trim());
            int y     = Integer.parseInt(fieldStartY.getText().trim());
            int scale = Integer.parseInt(fieldScale.getText().trim());
            int delay = Integer.parseInt(fieldDelay.getText().trim());
            int fs    = Integer.parseInt(fieldFrameSkip.getText().trim());

            if (x < 0 || y < 0)   { erro("Coordenadas não podem ser negativas.");   return false; }
            if (scale < 1)         { erro("Escala mínima é 1.");                     return false; }
            if (delay < 0)         { erro("Delay não pode ser negativo.");            return false; }
            if (fs < 1)            { erro("Frame Skip mínimo é 1.");                 return false; }

        } catch (NumberFormatException ex) {
            erro("Preencha todos os campos numéricos corretamente.");
            return false;
        }
        return true;
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro de validação", JOptionPane.ERROR_MESSAGE);
    }

    //  Helper: painel com borda e título 
    private JPanel titledPanel(String title) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), title,
                TitledBorder.LEFT, TitledBorder.TOP
        ));
        return p;
    }

    //  Getters para o Main usar
    public boolean isConfirmed()  { return confirmed; }
    public String  getImagePath() { return fieldImagePath.getText().trim(); }
    public int     getStartX()    { return Integer.parseInt(fieldStartX.getText().trim()); }
    public int     getStartY()    { return Integer.parseInt(fieldStartY.getText().trim()); }
    public int     getScale()     { return Integer.parseInt(fieldScale.getText().trim()); }
    public int     getDelay()     { return Integer.parseInt(fieldDelay.getText().trim()); }
    public int     getFrameSkip() { return Integer.parseInt(fieldFrameSkip.getText().trim()); }
    public Color   getColor()     { return selectedColor; }
    public boolean useStack()     { return comboMethod.getSelectedIndex() == 0; }
}