import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AppManutencao {
    private ArrayList<Manutencao> manutencoes = new ArrayList<>();
    private Equipamento equipamento;
    private Funcionario funcionario;
    private AppEquipamento appEquipamento;
    private AtualizaManutencao atualizaManutencao;

    Scanner in = new Scanner(System.in);

    public AppManutencao() {
        this.appEquipamento = appEquipamento;
        this.atualizaManutencao = new AtualizaManutencao(this);
    }

    public ArrayList<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void executar() {
        int opcao;
        do {
            menuManutencao();
            System.out.println("Opção desejada: ");
            opcao = in.nextInt();
            in.nextLine();
            switch (opcao) {
                case 1:
                    novaManutencao();
                    break;
                case 2:
                    atualizaManutencao.alterarDisponibilidadeEquipamento();
                    break;
                case 3:
                    exibirManutencaoMaisRecente();
                    break;
                case 4:
                    listarEquipamentosEmManutencao();
                    break;
                case 5:
                    //deshabilitarEquipamento();
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    private void menuManutencao() {
        System.out.println("Menu da Manutenção");
        System.out.println("1 - Criar uma nova manutenção");
        System.out.println("2 - Alterar status de uma manutenção");
        System.out.println("3 - Exibir a última manutenção de um equipamento");
        System.out.println("4 - Listar Equipamentos em manutenção");
        System.out.println("5 - Deshabilitar um equipamento");
        System.out.println("0 - Sair");
    }

    public boolean isEmManutencao(Equipamento equipamento) {
        Manutencao manutencaoMaisRecente = null;

        for (Manutencao m : manutencoes) {
            System.out.println("Comparando ID do Equipamento: " + equipamento.getId() + " com ID da Manutenção: "
                    + m.getEquipamento().getId());

            if (m.getEquipamento().getId() == equipamento.getId()) {
                System.out.println("Encontrado equipamento com ID: " + equipamento.getId());
                if (manutencaoMaisRecente == null
                        || m.getDataEntrada().isAfter(manutencaoMaisRecente.getDataEntrada())) {
                    manutencaoMaisRecente = m;
                }
            }
        }

        if (manutencaoMaisRecente != null) {
            String status = manutencaoMaisRecente.getStatus();
            if (!status.equalsIgnoreCase("concluída")) {
                System.out.println("O equipamento está em manutenção - status: " + status);
                return true;
            } else {
                System.out.println("O equipamento não está em manutenção - última manutenção realizada "
                        + manutencaoMaisRecente.getDataEntrada());
            }
        } else {
            System.out.println("O equipamento não está em manutenção - nenhuma manutenção registrada.");
        }

        return false;
    }

    private LocalDate gerarDataAtual() {
        return LocalDate.now();
    }

    private LocalDate gerarDataSaida() {
        return LocalDate.now();
    }

    private void novaManutencao() {

        LocalDate dataEntrada = gerarDataAtual();
        LocalDate dataSaida = gerarDataSaida();
        String estado = "Solicitada";
        boolean isEmManutencao = true;

        System.out.println("Novo Registro de Manutenção");
        System.out.print("Informe o ID do equipamento: ");
        int idEquipamento = in.nextInt();
        in.nextLine();

        Equipamento equipamento = AppEquipamento.buscarEquipamentoPorId(idEquipamento);
        if (equipamento == null) {
            System.out.println("Equipamento não encontrado!");
            return;
        }

        System.out.println("Equipamento selecionado: " + equipamento.getNomeCurto());
        equipamento.setDisponibilidade("Indisponível");
        System.out.print("Descrição do Problema: ");
        String descricaoProblema = in.nextLine();

        Funcionario funcionario = AppFuncionario.localizarFuncionarioPeloNome();
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.println("Funcionário: " + funcionario.getNome());

        Manutencao nova = new Manutencao(
                equipamento,
                estado,
                isEmManutencao,
                descricaoProblema,
                dataEntrada,
                dataSaida,
                funcionario);

        manutencoes.add(nova);
        System.out.println("Manutenção registrada com sucesso!");
        System.out.println("→ Equipamento agora está com disponibilidade: " + equipamento.getDisponibilidade());
    }

    public void exibirManutencaoMaisRecente() {
        System.out.print("Informe o ID do equipamento: ");
        int idEquipamento = in.nextInt();
        in.nextLine();

        Manutencao manutencaoMaisRecente = null;

        for (Manutencao m : manutencoes) {
            if (m.getEquipamento().getId() == idEquipamento) {
                if (manutencaoMaisRecente == null ||
                        m.getDataEntrada().isAfter(manutencaoMaisRecente.getDataEntrada())) {
                    manutencaoMaisRecente = m;
                }
            }
        }

        if (manutencaoMaisRecente != null) {
            System.out.println("Última manutenção: " + manutencaoMaisRecente.getDescricaoProblema() +
                    " em " + manutencaoMaisRecente.getDataEntrada());
        } else {
            System.out.println("Nenhuma manutenção registrada para este equipamento.");
        }
    }

    public void exibirUltimaManutencao(int idEquipamento) {
        Manutencao manutencaoMaisRecente = null;

        for (Manutencao m : manutencoes) {
            if (m.getEquipamento().getId() == idEquipamento) {
                if (manutencaoMaisRecente == null ||
                        m.getDataEntrada().isAfter(manutencaoMaisRecente.getDataEntrada())) {
                    manutencaoMaisRecente = m;
                }
            }
        }

        if (manutencaoMaisRecente != null) {
            System.out.println("Última manutenção: " + manutencaoMaisRecente.getDescricaoProblema() +
                    " em " + manutencaoMaisRecente.getDataEntrada());
        } else {
            System.out.println("Nenhuma manutenção registrada para este equipamento.");
        }
    }
    public void listarEquipamentosEmManutencao() {
        ControleManutencao controleManutencao = new ControleManutencao(manutencoes);
        controleManutencao.listarEquipamentosEmManutencao();
    }
}