package interfaces;

import controller.GestServicos.Servico;

public interface IGestServicos {
    // repensar a maneira como associamos um preco a um servicoExpresso
    int criarServicoNormal(int nif, String email, int idEquipamento, String descricao);

    int criarServicoExpresso(int numTelefonico, int preco, String descricao, int idEquipamento);

    void removeServico(int idServico);

    void criarOrcamento(int idServico, int preco);

    void aceitarOrcamento(int idServico);

    void rejeitarOrcamento(int idServico);

    void entregarEquipamento(int idServico);

    Servico getServico(int idServico);

    boolean isEntregue(int idServico);
}
