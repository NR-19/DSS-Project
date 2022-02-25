package controller.GestServicos;

public class ServicoNormal extends Servico {
    private int nif;
    private String email;
    private Orcamento orcamento;

    public ServicoNormal(int idServico, int idEquipamento, String descricao, String email, int nif) {
        super(idServico, idEquipamento, descricao);
        this.nif = nif;
        this.email = email;
    }

    public void criarOrcamento(int preco){
        this.orcamento = new Orcamento(preco);
    }

    public void aceitarOrcamento(){
        orcamento.aceitarOrcamento();
    }

    public void rejeitarOrcamento(){
        orcamento.rejeitarOrcamento();
    }
}
