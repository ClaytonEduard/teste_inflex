import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.Funcionario;

public class App {
    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Funcionario> funcionarios = new ArrayList<>();
        // 3.1 Inserindo funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"),
                "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"),
                "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"),
                "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"),
                "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.64"),
                "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"),
                "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"),
                "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"),
                "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"),
                "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"),
                "Gerente"));

        // 3.2 removendo o funcionario "joão" da Lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 Imprimir todos os funcionários
        // 3.3.1 informação de data deve ser exibido no formato dd/mm/aaaa;
        // 3.3.2 informação de valor numérico deve ser exibida no formatado com
        // separador de milhar como ponto e decimal como vírgula.
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s%-20s%-15s%-15s\n", "|Nome ", "|Data Nascimento ", "|Salário ", "|Função ");
        System.out.println("-------------------------------------------------------------");
        funcionarios.forEach(funcionario -> {
            String nome = funcionario.getNome();
            String dataNascimento = funcionario.getDataNascimento().format(formatter);
            String salario = "R$ " + String.format("%,.2f", funcionario.getSalario());
            String funcao = funcionario.getFuncao();
            System.out.printf("%-15s%-20s%-15s%-15s\n", nome, dataNascimento, salario, funcao);
        });

        System.out.println(""); // pular linha

        // 3.4 Funcionários recebem 10% de aumento
        funcionarios.forEach(funcionario -> {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        });

        // 3.5 Agrupar funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 Imprimir funcionários agrupados por função
        System.out.println("3.6 Imprimir funcionários agrupados por função: \n");
        System.out.printf("%-15s%s\n", "|Função", "|Nomes dos Funcionários");
        System.out.println("----------------------------------");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.printf("%-15s", "| " + funcao);
            lista.forEach(f -> System.out.print("| " + f.getNome() + ", "));
            System.out.println();
        });

        System.out.println(""); // pular linha

        System.out.println("3.8 Imprimir funcionários que fazem aniversário nos meses 10 e 12 \n");
        // 3.8 Imprimir funcionários que fazem aniversário nos meses 10 e 12
        int[] mesesAniversario = { 10, 12 };
        funcionarios.stream()
                .filter(funcionario -> Arrays.stream(mesesAniversario)
                        .anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes))
                .forEach(funcionario -> System.out.println("Aniversariante: " + funcionario.getNome()));

        System.out.println(""); // pular linha

        // 3.9 Imprimir funcionário mais velho
        System.out.println("3.9 Imprimir funcionário mais velho: \n");
        Funcionario maisVelho = Collections.max(funcionarios,
                Comparator.comparing(f -> f.getDataNascimento().until(LocalDate.now()).toTotalMonths()));
        System.out.println("Funcionário mais velho: " + maisVelho.getNome() +
                ", Idade: " + maisVelho.getDataNascimento().until(LocalDate.now()).toTotalMonths() / 12 + " anos");

        System.out.println(""); // pular linha

        // 3.10 Imprimir lista de funcionários por ordem alfabética
        System.out.println("3.10 Imprimir lista de funcionários por ordem alfabética: \n");
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        funcionarios.forEach(f -> System.out.println("   " + f.getNome()));

        System.out.println(""); // pular linha

        // 3.11 Imprimir total dos salários dos funcionários
        System.out.println("3.11 Imprimir total dos salários dos funcionários: \n");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String totalSalariosFormatado = String.format("%,.2f", totalSalarios);
        System.out.println("Total dos salários: R$" + totalSalariosFormatado + "\n");

        System.out.println(""); // pular linha

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("3.12 Imprimir quantos salários mínimos ganha cada funcionário: \n");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioEmSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2,
                    RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salarioEmSalariosMinimos + " salários mínimos.");
        });

    }
}
