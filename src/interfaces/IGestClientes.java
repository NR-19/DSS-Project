package interfaces;

import java.util.List;

public interface IGestClientes {
    void addClient(int nif, String email);

    void addServico(int nif, int idEquipamento, int idServico);

    List<Integer> getServicos(int nif);

    void removeServico(int nif, int idServico);
}
