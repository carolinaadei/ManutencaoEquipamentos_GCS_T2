import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppFuncionario {
    static Scanner in = new Scanner(System.in);
    public static final List<Funcionario> funcionarios = new ArrayList<>();

    public AppFuncionario() {
    }

    public void executar() {
        int opcao;
        do {
            menu();
            System.out.print("Opção desejada: ");
            opcao = in.nextInt();
            in.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    renomearFuncionario();
                    break;
                case 3:
                    alterarEmail();
                    break;
                case 4:
                    menuLocalizarFuncionario();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    private void menu() {
        System.out.println("Menu dos Funcionários: ");
        System.out.println("1 - Cadastrar funcionário");
        System.out.println("2 - Renomear um funcionário");
        System.out.println("3 - Alterar email de funcionário");
        System.out.println("4 - Localizar funcionário");
        System.out.println("0 - Sair");
    }

    private void cadastrarFuncionario() {
        System.out.println("Informe o nome do funcionário: ");
        String nome = in.nextLine();
        System.out.println("Informe o email do funcionário: ");
        String email = in.nextLine();
        Funcionario f = new Funcionario(nome, email);
        funcionarios.add(f);
        System.out.println("Funcionário cadastrado com sucesso! Matrícula: " + f.getMatricula());
    }

    private void renomearFuncionario() {
        System.out.print("Informe a matrícula do funcionário que deseja renomear: ");
        int matricula = in.nextInt();
        in.nextLine();
        Funcionario f = localizarFuncionarioPorMatricula(matricula);
        if (f == null) {
            System.out.println("Funcionário não encontrado!");
        } else {
            System.out.print("Informe o novo nome: ");
            String novoNome = in.nextLine();
            f.setNome(novoNome);
            System.out.println("Nome atualizado com sucesso.");
            exibirFuncionarioComEquipamentos(f);
        }
    }

    private void alterarEmail() {
        System.out.print("Informe a matrícula do funcionário que deseja alterar o email: ");
        int matricula = in.nextInt();
        in.nextLine();
        Funcionario f = localizarFuncionarioPorMatricula(matricula);
        if (f == null) {
            System.out.println("Funcionário não encontrado!");
        } else {
            System.out.print("Informe o novo email: ");
            String novoEmail = in.nextLine();
            f.setEmail(novoEmail);
            System.out.println("Email atualizado com sucesso.");
            exibirFuncionarioComEquipamentos(f);
        }
    }

    private void menuLocalizarFuncionario() {
        System.out.println("Escolha o tipo de busca:");
        System.out.println("1 - Por nome");
        System.out.println("2 - Por email");
        System.out.println("3 - Por matrícula");
        System.out.println("0 - Voltar");
        System.out.print("Opção: ");
        int opcao = in.nextInt();
        in.nextLine();
        switch (opcao) {
            case 1:
                localizarFuncionarioPeloNome();
                break;
            case 2:
                localizarFuncionarioPeloEmail();
                break;
            case 3:
                localizarFuncionarioParcialPorMatricula();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public static Funcionario localizarFuncionarioPeloNome() {
        System.out.print("Digite o nome do funcionário: ");
        String busca = in.nextLine().toLowerCase();
        List<Funcionario> encontrados = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            if (f.getNome().toLowerCase().contains(busca)) {
                exibirFuncionarioSimples(f);
                encontrados.add(f);
            }
        }
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
            return null;
        } else {
            return confirmarFuncionarioPorMatricula(encontrados);
        }
    }

    private void localizarFuncionarioPeloEmail() {
        System.out.print("Digite parte do email: ");
        String busca = in.nextLine().toLowerCase();
        List<Funcionario> encontrados = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            if (f.getEmail().toLowerCase().contains(busca)) {
                exibirFuncionarioSimples(f);
                encontrados.add(f);
            }
        }
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            confirmarFuncionarioPorMatricula(encontrados);
        }
    }

    private void localizarFuncionarioParcialPorMatricula() {
        System.out.print("Digite parte da matrícula: ");
        String termo = in.nextLine();
        List<Funcionario> encontrados = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            if (String.valueOf(f.getMatricula()).contains(termo)) {
                exibirFuncionarioSimples(f);
                encontrados.add(f);
            }
        }
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            confirmarFuncionarioPorMatricula(encontrados);
        }
    }

    private static Funcionario confirmarFuncionarioPorMatricula(List<Funcionario> lista) {
        System.out.print("Confirme o funcionário desejado pela matrícula: ");
        int confirmacao = in.nextInt();
        in.nextLine();
        Funcionario escolhido = localizarFuncionarioPorMatricula(confirmacao);
        if (escolhido != null && lista.contains(escolhido)) {
            return escolhido;
        } else {
            System.out.println("Matrícula não corresponde a nenhum dos funcionários listados.");
            return null;
        }
    }

    public static Funcionario localizarFuncionarioPorMatricula(int matricula) {
        for (Funcionario f : funcionarios) {
            if (f.getMatricula() == matricula) {
                return f;
            }
        }
        return null;
    }

    private static void exibirFuncionarioComEquipamentos(Funcionario f) {
        System.out.println("Funcionário: " + f.getNome() +
                " | Matrícula: " + f.getMatricula() +
                " | Email: " + f.getEmail());
        System.out.println("→ Histórico de nomes: " + f.getHistoricoNomes());
        System.out.println("→ Histórico de emails: " + f.getHistoricoEmails());
        List<Equipamento> equipamentos = AppEquipamento.getEquipamento(f.getNome());
        if (equipamentos.isEmpty()) {
            System.out.println("→ Nenhum equipamento associado.");
        } else {
            System.out.println("→ Equipamentos associados:");
            for (Equipamento e : equipamentos) {
                System.out.println("  - " + e.getNomeCurto() +
                        " | Tipo: " + e.getTipoEquipamento() +
                        " | Status: " + e.getDisponibilidade());
            }
        }
    }

    private static void exibirFuncionarioSimples(Funcionario f) {
        System.out.println("Funcionário: " + f.getNome() +
                " | Matrícula: " + f.getMatricula() +
                " | Email: " + f.getEmail());
    }
}