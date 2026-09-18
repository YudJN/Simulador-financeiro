/**
 * Métodos auxiliares para converter o texto digitado nos campos em números.
 * Retornam null quando o texto é inválido.
 */
public final class Validador {

    private Validador() {
        // classe utilitária: não deve ser instanciada
    }

    /** Aceita formatos como 45000, 45000,50 ou 45.000,50. */
    public static Double lerNumero(String texto) {
        String limpo = texto.trim().replace("R$", "").replace(" ", "")
                .replace(".", "").replace(",", ".");
        if (!limpo.matches("\\d+(\\.\\d{1,2})?")) {
            return null;
        }
        return Double.parseDouble(limpo);
    }

    /** Aceita apenas dígitos (pontos de milhar são ignorados). */
    public static Integer lerInteiro(String texto) {
        String limpo = texto.trim().replace(".", "");
        if (!limpo.matches("\\d{1,9}")) {
            return null;
        }
        return Integer.parseInt(limpo);
    }
}
