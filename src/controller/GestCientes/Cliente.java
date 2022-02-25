package controller.GestCientes;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int nif;
    private String email;
    private List<Integer> servicos;

    public Cliente(int nif, String email) {
        this.nif = nif;
        this.email = email;
        this.servicos = new ArrayList<>();
    }

    public String getEmail() {
        return this.email;
    }

    public void addServico(int idServico) {
        this.servicos.add(idServico);
    }

    public List<Integer> getServicos() {
        return new ArrayList<>(servicos);
    }

    public void removeServico(int idServico){
        this.servicos.remove(idServico);
    }
}
