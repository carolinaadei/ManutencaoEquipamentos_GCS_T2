import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private static int contadorMatricula = 101;
    private final int matricula;
    private String nome;
    private String email;
    private final List<String> historicoNomes = new ArrayList<>();
    private final List<String> historicoEmails = new ArrayList<>();

    public Funcionario(String nome, String email) {
        this.matricula = contadorMatricula++;
        this.nome = nome;
        this.email = email;
        historicoNomes.add(nome);
        historicoEmails.add(email);
    }

    public void setNome(String nome) {
        if (!this.nome.equals(nome)) {
            this.nome = nome;
            historicoNomes.add(nome);
        }
    }

    public void setEmail(String email) {
        if (!this.email.equals(email)) {
            this.email = email;
            historicoEmails.add(email);
        }
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getHistoricoNomes() {
        return new ArrayList<>(historicoNomes);
    }

    public List<String> getHistoricoEmails() {
        return new ArrayList<>(historicoEmails);
    }
}