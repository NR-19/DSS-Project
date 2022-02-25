package controller.GestReparacoes;

public class ReparacaoExpresso extends Reparacao {

    public ReparacaoExpresso(int idServico) {
        super(idServico);
    }

    public ReparacaoExpresso clone() {
        return (ReparacaoExpresso) super.clone();
    }



}
