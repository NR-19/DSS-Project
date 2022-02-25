package controller.GestReparacoes;

import java.time.Duration;
import java.util.List;

public class ReparacaoNormal extends Reparacao {
    private int idTecnicoPt;
    private PlanoTrabalhos planoTrabalhos;

    public ReparacaoNormal(int idServico, int idTecnicoPt) {
        super(idServico);
        this.idTecnicoPt = idTecnicoPt;
        this.planoTrabalhos = new PlanoTrabalhos();
    }

    public ReparacaoNormal(ReparacaoNormal r) {
        super(r);
        this.idTecnicoPt = r.idTecnicoPt;
        this.planoTrabalhos = r.planoTrabalhos.clone();
    }

    public void createPasso(String descricao) {
        this.planoTrabalhos.createPasso(descricao);
    }

    public void addSubPasso(int custo, Duration duracao, String descricao) {
        this.planoTrabalhos.addSubPasso(custo, duracao, descricao);
    }

    public int getCustoTotal(){
        return this.planoTrabalhos.calculaCustoTotal();
    }

    public List<String> getReparacaoToTecnico() {
        return this.planoTrabalhos.toStringList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String ret = super.toString();
        sb.append(ret);
        sb.append(", idTecnicoPt: ").append(this.idTecnicoPt);
        sb.append(this.planoTrabalhos.toString());

        return sb.toString();
    }


    @Override
    public ReparacaoNormal clone() {
        ReparacaoNormal r = (ReparacaoNormal) super.clone();
        r.idTecnicoPt = this.idTecnicoPt;
        r.planoTrabalhos = this.planoTrabalhos.clone();
        return new ReparacaoNormal(this);
    }
}
