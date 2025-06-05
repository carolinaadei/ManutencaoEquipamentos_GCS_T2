import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int opcao;

        AppFuncionario appFuncionario = new AppFuncionario();
        AppManutencao appManutencao = new AppManutencao();
        AppEquipamento appEquipamento = new AppEquipamento();

        do {
            System.out.println("Menu Principal");
            System.out.println("1 - Acesso ao menu de Funcionários");
            System.out.println("2 - Acesso ao menu de Equipamentos");
            System.out.println("3 - Acesso ao menu de Manutenções");
            System.out.println("0 - Sair");
            System.out.print("Digite a opção desejada: ");
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1:
                    appFuncionario.executar();
                    break;
                case 2:
                    appEquipamento.executar();
                    break;
                case 3:
                    appManutencao.executar();
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }
}