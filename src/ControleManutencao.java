import java.util.List;

public class ControleManutencao {

    private List<Manutencao> manutencoes;

    public ControleManutencao(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

    public void listarEquipamentosEmManutencao() {
        System.out.println("=== Equipamentos em Manutenção ===");
        for (Manutencao manutencao : manutencoes) {
            if (manutencao.isEmManutencao()) {
                Equipamento equipamento = manutencao.getEquipamento();
                Funcionario responsavel = equipamento.getFuncionarioResponsavel();

                System.out.println("Equipamento: " + equipamento.getNomeCurto());
                System.out.println("Status da Manutenção: " + manutencao.getStatus());
                System.out.println("Funcionário Responsável: " + responsavel.getNome());
                System.out.println("--------------------------------------");
            }
        }

        boolean encontrou = false;

        for (Manutencao manutencao : manutencoes) {
            if (manutencao.isEmManutencao()) {
                Equipamento equipamento = manutencao.getEquipamento();
                Funcionario responsavel = equipamento.getFuncionarioResponsavel();

                System.out.println("Equipamento: " + equipamento.getNomeCurto());
                System.out.println("Status da Manutenção: " + manutencao.getStatus());
                System.out.println("Funcionário Responsável: " + responsavel.getNome());
                System.out.println("--------------------------------------");

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum equipamento está em manutenção no momento.\n");
        }
    }
}