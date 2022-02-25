package controller.GestServicos;

public class ServicoExpresso extends Servico {
    int preco;
    int numeroTelefonico;

    public ServicoExpresso(int idServico, int idEquipamento, String descricao, int preco, int numeroTelefonico) {
        super(idServico, idEquipamento, descricao);
        this.preco = preco;
        this.numeroTelefonico = numeroTelefonico;
    }

}
