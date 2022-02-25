package controller.GestReparacoes;

public class Reparacao {
    private int estado; // estado 0 nao reparada, 1 reparada, -1 iterrompida
    private int idServico;
    private int idTecnicoRep;

    public Reparacao(int idServico) {
        this.estado = 0;
        this.idServico = idServico;
    }

    public Reparacao(Reparacao r) {
        this.estado = r.estado;
        this.idServico = r.idServico;
        this.idTecnicoRep = r.idTecnicoRep;
    }

    public int getIdServico() {
        return idServico;
    }

    public void iniciarReparacao(int idTecnicoRep) {
        this.idTecnicoRep = idTecnicoRep;
    }

    public int getIdTecnicoRep() {
        return this.idTecnicoRep;
    }

    public void interromperReparacao() {
        this.estado = -1;
    }

    public void terminarReparacao() {
        this.estado = 1;
    }

    public void retomarReparacao() {
        this.estado = 0;
    }

    public boolean isFinished() {
        return estado == 1;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("idServico: ").append(this.estado);
        sb.append(", idTecnicoRep:").append(this.idTecnicoRep);

        return sb.toString();

    }

    @Override
    public Reparacao clone() {
        return new Reparacao(this);
    }
}

