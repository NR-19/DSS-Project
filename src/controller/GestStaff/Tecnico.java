package controller.GestStaff;

public class Tecnico {
    private int idTecnico;
    private String nomeTecnico;
    private int idReparacaoAtual;
    private int passwordTecnico;

    public Tecnico(int idTecnico, String nomeTecnico,String password) {
        this.idTecnico = idTecnico;
        this.nomeTecnico = nomeTecnico;
        this.idReparacaoAtual = 0;
        this.passwordTecnico = hashPass(password);
    }

    public Tecnico(int idTecnico, String nomeTecnico, int idReparacaoAtual) {
        this.idTecnico = idTecnico;
        this.nomeTecnico = nomeTecnico;
        this.idReparacaoAtual = idReparacaoAtual;
    }

    public String getNomeTecnico() {
        return this.nomeTecnico;
    }

    public void setIdReparacaoAtual(int idReparacaoAtual) {
        this.idReparacaoAtual = idReparacaoAtual;
    }

    public int getIdReparacaoAtual() {
        return this.idReparacaoAtual;
    }

    public boolean isTecnicoDisponivel() {
        return this.idReparacaoAtual == 0;
    }

    public boolean logIn(String password){

        return this.passwordTecnico == hashPass(password);
    }

    public int hashPass(String password){
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash*19 + password.charAt(i);
        }
        return hash;
    }

}
