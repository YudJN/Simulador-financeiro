import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Tela de simulação de financiamento de veículos.
 *
 * LayoutManagers utilizados:
 *  - BorderLayout  : janela principal (título no topo, conteúdo no centro)
 *  - BoxLayout     : empilha os painéis verticalmente
 *  - GridBagLayout : painéis com pares "rótulo + campo" (Veículo e Financiamento)
 *  - GridLayout    : painel do veículo usado e painel de resultado
 *  - FlowLayout    : seleção Novo/Usado e botões
 */
public class TelaFinanciamento extends JFrame {

    private static final NumberFormat MOEDA =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    // ----- Dados do veículo -----
    private final JComboBox<String> cbMarca = new JComboBox<>(new String[]{
            "Selecione...", "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai",
            "Jeep", "Nissan", "Renault", "Toyota", "Volkswagen"});
    private final JTextField txtModelo = new JTextField(15);
    private final JComboBox<Integer> cbAno = new JComboBox<>();
    private final JTextField txtValor = new JTextField(15);

    // ----- Novo / Usado -----
    private final JRadioButton rbNovo = new JRadioButton("Novo", true);
    private final JRadioButton rbUsado = new JRadioButton("Usado");

    // ----- Dados do veículo usado -----
    private final JPanel painelUsado = new JPanel(new GridLayout(2, 2, 8, 8));
    private final JTextField txtKm = new JTextField(15);
    private final JTextField txtProprietarios = new JTextField(15);

    // ----- Financiamento -----
    private final JCheckBox chkEntrada = new JCheckBox("Possui entrada?");
    private final JLabel lblEntrada = new JLabel("Entrada:");
    private final JTextField txtEntrada = new JTextField(15);
    private final JComboBox<Integer> cbParcelas = new JComboBox<>(new Integer[]{12, 24, 36, 48, 60});

    // ----- Resultado -----
    private final JPanel painelResultado = new JPanel(new GridLayout(3, 2, 8, 8));
    private final JLabel lblValorFinanciado = new JLabel();
    private final JLabel lblValorParcela = new JLabel();
    private final JLabel lblTotalPagar = new JLabel();

    public TelaFinanciamento() {
        super("Financiamento de Carros");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Anos de 2026 até 2000 (ordem decrescente)
        for (int ano = 2026; ano >= 2000; ano--) {
            cbAno.addItem(ano);
        }

        // Título
        JLabel titulo = new JLabel("Financiamento de Carros", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        titulo.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(titulo, BorderLayout.NORTH);

        // Conteúdo: painéis empilhados verticalmente
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(new EmptyBorder(5, 15, 15, 15));

        conteudo.add(criarPainelVeiculo());
        conteudo.add(criarPainelTipo());
        conteudo.add(criarPainelUsado());
        conteudo.add(criarPainelFinanciamento());
        conteudo.add(criarPainelBotoes());
        conteudo.add(criarPainelResultado());

        add(conteudo, BorderLayout.CENTER);

        // Estado inicial: painel do usado, campo de entrada e resultado ocultos
        painelUsado.setVisible(false);
        exibirEntrada(false);
        painelResultado.setVisible(false);

        pack();
        setLocationRelativeTo(null);
    }

    // ================= Criação dos painéis =================

    private JPanel criarPainelVeiculo() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("Dados do Veículo"));

        addLinha(p, 0, new JLabel("Marca:"), cbMarca);
        addLinha(p, 1, new JLabel("Modelo:"), txtModelo);
        addLinha(p, 2, new JLabel("Ano:"), cbAno);
        addLinha(p, 3, new JLabel("Valor:"), txtValor);
        return p;
    }

    private JPanel criarPainelTipo() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbNovo);
        grupo.add(rbUsado);
        p.add(new JLabel("Tipo:"));
        p.add(rbNovo);
        p.add(rbUsado);

        // Mostra/oculta o painel do veículo usado
        rbNovo.addActionListener(e -> atualizarVisibilidade());
        rbUsado.addActionListener(e -> atualizarVisibilidade());
        return p;
    }

    private JPanel criarPainelUsado() {
        painelUsado.setBorder(new TitledBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 2, 2, false),
                "Dados do Veículo Usado"));
        painelUsado.add(new JLabel("Quilometragem:"));
        painelUsado.add(txtKm);
        painelUsado.add(new JLabel("Proprietários:"));
        painelUsado.add(txtProprietarios);
        return painelUsado;
    }

    private JPanel criarPainelFinanciamento() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("Financiamento"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.LINE_START;
        p.add(chkEntrada, g);

        addLinha(p, 1, lblEntrada, txtEntrada);
        addLinha(p, 2, new JLabel("Parcelas:"), cbParcelas);

        // Mostra/oculta o campo de entrada
        chkEntrada.addActionListener(e -> atualizarVisibilidade());
        return p;
    }

    private JPanel criarPainelBotoes() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCalcular = new JButton("Calcular");
        btnLimpar.addActionListener(e -> limpar());
        btnCalcular.addActionListener(e -> calcular());
        p.add(btnCalcular);
        p.add(btnLimpar);
        return p;
    }

    private JPanel criarPainelResultado() {
        painelResultado.setBorder(new TitledBorder("Resultado"));
        painelResultado.add(new JLabel("Valor financiado:"));
        painelResultado.add(lblValorFinanciado);
        painelResultado.add(new JLabel("Valor da parcela:"));
        painelResultado.add(lblValorParcela);
        painelResultado.add(new JLabel("Total a pagar:"));
        painelResultado.add(lblTotalPagar);
        return painelResultado;
    }

    /** Adiciona uma linha "rótulo + campo" em um painel com GridBagLayout. */
    private void addLinha(JPanel painel, int linha, JComponent rotulo, JComponent campo) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.gridy = linha;

        g.gridx = 0;
        g.anchor = GridBagConstraints.LINE_START;
        painel.add(rotulo, g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.LINE_START;
        painel.add(campo, g);
    }

    // ================= Visibilidade =================

    private void atualizarVisibilidade() {
        painelUsado.setVisible(rbUsado.isSelected());
        exibirEntrada(chkEntrada.isSelected());
        pack();
    }

    private void exibirEntrada(boolean exibir) {
        lblEntrada.setVisible(exibir);
        txtEntrada.setVisible(exibir);
    }

    // ================= Ações dos botões =================

    private void limpar() {
        cbMarca.setSelectedIndex(0);
        txtModelo.setText("");
        cbAno.setSelectedIndex(0);
        txtValor.setText("");

        rbNovo.setSelected(true);
        txtKm.setText("");
        txtProprietarios.setText("");

        chkEntrada.setSelected(false);
        txtEntrada.setText("");
        cbParcelas.setSelectedIndex(0);

        painelResultado.setVisible(false);
        atualizarVisibilidade();
    }

    private void calcular() {
        // ----- Validações -----
        if (cbMarca.getSelectedIndex() == 0) {
            erro("Selecione a marca do veículo.", cbMarca);
            return;
        }
        if (txtModelo.getText().trim().isEmpty()) {
            erro("Informe o modelo do veículo.", txtModelo);
            return;
        }

        Double valorVeiculo = Validador.lerNumero(txtValor.getText());
        if (valorVeiculo == null || valorVeiculo <= 0) {
            erro("Informe um valor válido para o veículo (ex.: 45000 ou 45.000,00).", txtValor);
            return;
        }

        if (rbUsado.isSelected()) {
            Integer km = Validador.lerInteiro(txtKm.getText());
            if (km == null) {
                erro("Informe uma quilometragem válida (somente números).", txtKm);
                return;
            }
            Integer proprietarios = Validador.lerInteiro(txtProprietarios.getText());
            if (proprietarios == null || proprietarios < 1) {
                erro("Informe uma quantidade válida de proprietários (mínimo 1).", txtProprietarios);
                return;
            }
        }

        double entrada = 0;
        if (chkEntrada.isSelected()) {
            Double valorEntrada = Validador.lerNumero(txtEntrada.getText());
            if (valorEntrada == null || valorEntrada <= 0) {
                erro("Informe um valor de entrada válido.", txtEntrada);
                return;
            }
            if (valorEntrada >= valorVeiculo) {
                erro("A entrada deve ser menor que o valor do veículo.", txtEntrada);
                return;
            }
            entrada = valorEntrada;
        }

        int parcelas = (Integer) cbParcelas.getSelectedItem();

        // ----- Cálculo (regra de negócio na classe Financiamento) -----
        Financiamento financiamento = new Financiamento(valorVeiculo, entrada, parcelas);

        // ----- Resultado -----
        lblValorFinanciado.setText(MOEDA.format(financiamento.getValorFinanciado()));
        lblValorParcela.setText(MOEDA.format(financiamento.getValorParcela()));
        lblTotalPagar.setText(MOEDA.format(financiamento.getTotalPagar()));

        painelResultado.setVisible(true);
        pack();
    }

    private void erro(String mensagem, JComponent campo) {
        JOptionPane.showMessageDialog(this, mensagem, "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        campo.requestFocusInWindow();
    }
}
