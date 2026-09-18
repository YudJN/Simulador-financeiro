/**
 * Regra de negócio: cálculo simples do financiamento.
 *
 * valor_financiado = valor_veiculo - entrada
 * valor_total      = valor_financiado * (1 + taxa)
 * valor_parcela    = valor_total / numero_parcelas
 * total_a_pagar    = valor_parcela * numero_parcelas
 */
public class Financiamento {

    // A atividade não informa a taxa, mas o exemplo do protótipo (financiado R$ 45.000,00 em 36x
    // = parcela de R$ 1.650,00 = total de R$ 59.400,00) corresponde a 32% (45000 * 1,32 = 59400).
    public static final double TAXA = 0.32;

    private final double valorFinanciado;
    private final double valorParcela;
    private final int numeroParcelas;

    public Financiamento(double valorVeiculo, double entrada, int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
        this.valorFinanciado = valorVeiculo - entrada;

        double valorTotal = valorFinanciado * (1 + TAXA);
        this.valorParcela = valorTotal / numeroParcelas;
    }

    public double getValorFinanciado() {
        return valorFinanciado;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public double getTotalPagar() {
        return valorParcela * numeroParcelas;
    }
}
