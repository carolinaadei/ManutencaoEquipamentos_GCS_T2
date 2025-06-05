import java.time.LocalDate;

public class Manutencao {
    private final Equipamento equipamento;
    private String status;
    private boolean isEmManutencao = false;
    public String descricaoProblema;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private Funcionario funcionarioResponsavel;

    public Manutencao(Equipamento equipamento, String status, boolean isEmManutencao, String descricaoProblema,
                      LocalDate dataEntrada, LocalDate dataSaida, Funcionario funcionarioResponsavel) {
        this.equipamento = equipamento;
        this.status = status;
        this.isEmManutencao = isEmManutencao;
        setDescricaoProblema(descricaoProblema);
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEmManutencao() {
        return isEmManutencao;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public String toString() {
        return "Manutenção:" +
                " Equipamento: " + equipamento.getNomeCurto() +
                ". Status: " + status +
                ". Está agora em manutenção: " + isEmManutencao +
                ". Descrição: " + descricaoProblema +
                ". Data de Entrada: " + dataEntrada +
                ". Data de Saída: " + dataSaida +
                ". Funcionário Responsável pelo Equipamento: " + funcionarioResponsavel.getNome() +
                ".";
    }

}