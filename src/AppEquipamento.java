import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppEquipamento {
    private static ArrayList<Equipamento> equipamentos = new ArrayList<>();
    private AppManutencao appManutencao;
    private AppFuncionario appFuncionario;
    private Funcionario funcionario;
    Scanner in = new Scanner(System.in);

    public AppEquipamento() {
        this.appFuncionario = new AppFuncionario();
        this.appManutencao = new AppManutencao();
        this.funcionario = funcionario;
    }

    public static List<Equipamento> getEquipamento(String nomeCurto) {
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getNomeCurto().equalsIgnoreCase(nomeCurto)) {
                encontrados.add(equipamento);
            }
        }
        return encontrados;
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
                    cadastrarEquipamento();
                    break;
                case 2:
                    editarDescricao();
                    break;
                case 3:
                    marcarDisponibilidade();
                    break;
                case 4:
                    localizarEquipamento();
                    break;
                case 5:
                    relatorioEquipamento();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    private void menu() {
        System.out.println("Menu dos Equipamentos");
        System.out.println("1 - Cadastrar equipamento");
        System.out.println("2 - Editar descrição do equipamento");
        System.out.println("3 - Marcar disponibilidade do equipamento");
        System.out.println("4 - Localizar equipamento");
        System.out.println("5 - Criar relatório do equipamento");
        System.out.println("6 - Equipamentos inativos: "); // Samantha
        System.out.println("0 - Sair");
    }

    private void cadastrarEquipamento() {
        System.out.print("Informe o nome do equipamento: ");
        String nomeCurto = in.nextLine();
        System.out.print("Informe a descrição do equipamento: ");
        String descricao = in.nextLine();
        System.out.print("Informe a data de aquisição (00/00/0000 ou 00.00.0000): ");
        String dataAquisicao = in.nextLine();
        System.out.print("Informe o valor de aquisição: ");
        double valorAquisicao = in.nextDouble();
        in.nextLine();

        Funcionario funcionarioResponsavel = appFuncionario.localizarFuncionarioPeloNome();
        if (funcionarioResponsavel == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.println("Informe o tipo do equipamento (fixo ou móvel): ");
        String tipoEquipamento = in.nextLine();
        System.out.println("Informe a disponibilidade do equipamento: ");
        String disponibilidade = in.nextLine();

        try {
            Equipamento e = new Equipamento(nomeCurto, descricao, dataAquisicao, valorAquisicao, funcionarioResponsavel,
                    tipoEquipamento);
            e.setDisponibilidade(disponibilidade);
            equipamentos.add(e);
            System.out.println("Equipamento cadastrado com sucesso! Identificador: " + e.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar equipamento: " + e.getMessage());
        }
    }

    static Equipamento buscarEquipamentoPorId(int id) {
        for (Equipamento e : equipamentos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    private void editarDescricao() {
        System.out.print("Informe o ID do equipamento que deseja editar: ");
        int id = in.nextInt();
        in.nextLine();
        Equipamento equipamento = buscarEquipamentoPorId(id);
        if (equipamento == null) {
            System.out.println("Equipamento não encontrado!");
            return;
        }
        System.out.print("Informe a nova descrição: ");
        String novaDescricao = in.nextLine();
        equipamento.getDescricao();
        System.out.println("Descrição atualizada com sucesso!");
    }

    private void marcarDisponibilidade() {
        System.out.print("Informe o ID do equipamento que deseja marcar a disponibilidade: ");
        int id = in.nextInt();
        in.nextLine();
        Equipamento equipamento = buscarEquipamentoPorId(id);
        if (equipamento == null) {
            System.out.println("Equipamento não encontrado!");
            return;
        }
        System.out.println("Informe a nova disponibilidade (Disponível ou Indisponível): ");
        String disponibilidade = in.nextLine();
        equipamento.setDisponibilidade(disponibilidade);
        System.out.println("Disponibilidade do equipamento alterada com sucesso!");
    }

    public void relatorioEquipamento() {
        for (Equipamento eq : equipamentos) {
            int manutencoesRealizadas = 0;
            for (Manutencao m : appManutencao.getManutencoes()) {
                if (m.getEquipamento().getId() == eq.getId()) {
                    manutencoesRealizadas++;
                }
            }
            System.out.println("Equipamento: " + eq.getNomeCurto());
            System.out.println("Funcionário Responsável: " + eq.getFuncionarioResponsavel().getNome());
            System.out.println("Total de manutenções: " + manutencoesRealizadas);
            appManutencao.isEmManutencao(eq);
        }
    }

    private void localizarEquipamentoPeloId() {
        System.out.print("Informe o ID do equipamento: ");
        int id = in.nextInt();
        in.nextLine();
        Equipamento equipamento = buscarEquipamentoPorId(id);
        if (equipamento != null) {
            System.out.println("→ ID: " + equipamento.getId() + " | Nome: " + equipamento.getNomeCurto());
        } else {
            System.out.println("Nenhum equipamento encontrado com esse ID.");
        }
    }

    private void localizarEquipamentoPeloNome() {
        System.out.print("Digite parte do nome do equipamento: ");
        String termo = in.nextLine().toLowerCase();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getNomeCurto().toLowerCase().contains(termo)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPelaDescricao() {
        System.out.print("Informe parte da descrição: ");
        String termo = in.nextLine().toLowerCase();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getDescricao().toLowerCase().contains(termo)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPelaDataAquisicao() {
        System.out.print("Informe a data de aquisição (00/00/0000 ou 00.00.0000): ");
        String data = in.nextLine();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getDataAquisicao().equals(data)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPeloValor() {
        System.out.print("Informe o valor de aquisição: ");
        double valor = in.nextDouble();
        in.nextLine();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getValorAquisicao() == valor) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPeloFuncionario() {
        System.out.print("Informe parte do nome do funcionário responsável: ");
        String nome = in.nextLine().toLowerCase();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getFuncionarioResponsavel().getNome().toLowerCase().contains(nome)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPeloTipo() {
        System.out.print("Informe o tipo do equipamento (fixo ou móvel): ");
        String tipo = in.nextLine().toLowerCase();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getTipoEquipamento().toLowerCase().equals(tipo)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void localizarEquipamentoPelaDisponibilidade() {
        System.out.print("Informe a disponibilidade (Disponível ou Indisponível): ");
        String disp = in.nextLine().toLowerCase();
        List<Equipamento> encontrados = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getDisponibilidade().toLowerCase().equals(disp)) {
                encontrados.add(e);
                System.out.println("→ ID: " + e.getId() + " | Nome: " + e.getNomeCurto());
            }
        }
        confirmarEquipamentoPorId(encontrados);
    }

    private void confirmarEquipamentoPorId(List<Equipamento> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum equipamento encontrado.");
            return;
        }
        System.out.print("Confirme o equipamento desejado pelo ID: ");
        int id = in.nextInt();
        in.nextLine();
        for (Equipamento e : lista) {
            if (e.getId() == id) {
                System.out.println("Equipamento confirmado: " + e.getNomeCurto());
                return;
            }
        }
        System.out.println("ID não corresponde a nenhum equipamento listado.");
    }

    private void registrarEquipamentoEstragado() {
        System.out.print("Informe o ID do equipamento que está estragado: ");
        int id = in.nextInt();
        in.nextLine();
        Equipamento equipamento = buscarEquipamentoPorId(id);
        if (equipamento == null) {
            System.out.println("Equipamento não encontrado!");
            return;
        }else

            equipamento.setEstragado(true);
        equipamento.setDisponibilidade("Indisponível");
        System.out.println("Equipamento registrado como estragado.");
    }

    private void localizarEquipamento() {
        int selecionar;
        do {
            menuLocalizar();
            System.out.println("Como você deseja localizar o equipamento?");
            selecionar = in.nextInt();
            in.nextLine();
            switch (selecionar) {
                case 1:
                    localizarEquipamentoPeloId();
                    break;
                case 2:
                    localizarEquipamentoPeloNome();
                    break;
                case 3:
                    localizarEquipamentoPelaDescricao();
                    break;
                case 4:
                    localizarEquipamentoPelaDataAquisicao();
                    break;
                case 5:
                    localizarEquipamentoPeloValor();
                    break;
                case 6:
                    localizarEquipamentoPeloFuncionario();
                    break;
                case 7:
                    localizarEquipamentoPeloTipo();
                    break;
                case 8:
                    localizarEquipamentoPelaDisponibilidade();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (selecionar != 0);
    }

    private void menuLocalizar() {
        System.out.println("Menu para localização de Equipamentos: ");
        System.out.println("1 - Localizar um equipamento pelo ID: ");
        System.out.println("2 - Localizar um equipamento pelo nome: ");
        System.out.println("3 - Localizar um equipamento pela descrição: ");
        System.out.println("4 - Localizar um equipamento pela data de aquisição: ");
        System.out.println("5 - Localizar um equipamento pelo valor de aquisição: ");
        System.out.println("6 - Localizar equipamento pelo funcionário responsável: ");
        System.out.println("7 - Localizar equipamento pelo tipo de equipamento: ");
        System.out.println("8 - Localizar equipamento pela disponibilidade: ");
        System.out.println("0 - Sair");
    }
}