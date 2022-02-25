package controller.GestServicos;

public abstract class Servico {
    int idServico;
    int idEquipamento;
    boolean entregue;
    String descricao;

    public Servico(int idServico, int idEquipamento, String descricao) {
        this.idServico = idServico;
        this.idEquipamento = idEquipamento;
        this.entregue = false;
        this.descricao = descricao;
    }

    public void entregarEquipamento() {
        this.entregue = true;
    }

    public int getIdEquipamento(){
        return this.idEquipamento;
    }

    public boolean isEntregue(){
        return this.entregue;
    };
}
