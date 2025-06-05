import java.text.Normalizer;
import java.util.Scanner;

public class AtualizaManutencao {

    private Scanner in = new Scanner(System.in);
    private AppManutencao appManutencao;

    public AtualizaManutencao(AppManutencao appManutencao) {
        this.appManutencao = appManutencao;
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }

    public void alterarDisponibilidadeEquipamento() {
        System.out.println("Alteração de Disponibilidade do Equipamento");
        int idEquipamento = -1;

        while (true) {
            System.out.print("Informe o ID do equipamento: ");
            if (in.hasNextInt()) {
                idEquipamento = in.nextInt();
                in.nextLine();
                break;
            } else {
                System.out.println("Digite um número válido!");
                in.nextLine();
            }
        }

        Equipamento equipamento = AppEquipamento.buscarEquipamentoPorId(idEquipamento);
        if (equipamento == null) {
            System.out.println("Equipamento não encontrado!");
            return;
        }

        if (equipamento.isDisponibilidadeAtualizada()) {
            System.out.println(
                    "A disponibilidade deste equipamento já foi atualizada uma vez. Não é possível alterar novamente.");
            return;
        }

        System.out.println("Equipamento: " + equipamento.getNomeCurto());
        System.out.println("Disponibilidade atual: " + equipamento.getDisponibilidade());

        System.out.print("Digite a nova disponibilidade (Disponível ou Indisponível): ");
        String entradaUsuario = in.nextLine().trim();
        String novaDisponibilidade = removerAcentos(entradaUsuario);

        Manutencao manutencaoMaisRecente = null;
        for (Manutencao m : appManutencao.getManutencoes()) {
            if (m.getEquipamento().getId() == idEquipamento) {
                if (manutencaoMaisRecente == null
                        || m.getDataEntrada().isAfter(manutencaoMaisRecente.getDataEntrada())) {
                    manutencaoMaisRecente = m;
                }
            }
        }

        if (manutencaoMaisRecente == null) {
            System.out.println("Nenhuma manutenção encontrada para este equipamento.");
            return;
        }

        String statusManutencao = removerAcentos(manutencaoMaisRecente.getStatus());

        switch (novaDisponibilidade) {
            case "disponivel":
                if (statusManutencao.contains("concluida")) {
                    equipamento.setDisponibilidade("Disponível");
                    equipamento.setEmManutencao(false);
                    equipamento.setDisponibilidadeAtualizada(true);
                    System.out.println("Equipamento marcado como Disponível.");
                } else {
                    System.out.println("A manutenção deve estar 'Concluída' para marcar como Disponível.");
                }
                break;

            case "indisponivel":
                if (statusManutencao.contains("em andamento")) {
                    equipamento.setDisponibilidade("Indisponível");
                    equipamento.setEmManutencao(true);
                    equipamento.setDisponibilidadeAtualizada(true);
                    System.out.println("Equipamento marcado como Indisponível.");
                } else {
                    System.out.println("A manutenção deve estar 'Em andamento' para marcar como Indisponível.");
                }
                break;

            default:
                System.out.println("Opção inválida. Use apenas 'Disponível' ou 'Indisponível'.");
                break;
        }
    }
}