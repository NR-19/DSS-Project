package controller.GestCientes;

import interfaces.IGestClientes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestClientes implements IGestClientes {
    private Map<Integer, Cliente> clientes;

    public GestClientes() {
        this.clientes = new HashMap<>();
    }

    @Override
    public void addClient(int nif, String email) {
        Cliente c = new Cliente(nif, email);
        this.clientes.putIfAbsent(nif, c);
    }

    @Override
    public void addServico(int nif, int idEquipamento, int idServico) {
        Cliente c = this.clientes.get(nif);
        c.addServico(idServico);
    }

    public void removeServico(int nif, int idServico){
        Cliente c = this.clientes.get(nif);
        c.removeServico(idServico);

    }

    public List<Integer> getServicos(int nif) {
        return this.clientes.get(nif).getServicos();
    }
}
