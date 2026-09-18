# Simulador de Financiamento de Veículos (Java Swing)

Aplicação desktop em Java Swing para simular o financiamento de veículos.

## Estrutura
- `Main.java` – inicia a aplicação
- `TelaFinanciamento.java` – interface gráfica (Swing)
- `Financiamento.java` – regra de cálculo do financiamento
- `Validador.java` – conversão e validação dos campos numéricos

## Como executar
1. Abra a pasta `src` em um projeto Java (JDK 8 ou superior).
2. Execute a classe `Main`.

## Cálculo
```
valor_financiado = valor_veiculo - entrada
valor_total      = valor_financiado * (1 + taxa)
valor_parcela    = valor_total / numero_parcelas
total_a_pagar    = valor_parcela * numero_parcelas
```
Taxa utilizada: 32% (constante `TAXA` em `Financiamento.java`).
