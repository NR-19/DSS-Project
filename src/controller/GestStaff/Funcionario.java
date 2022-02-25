package controller.GestStaff;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private int idFuncionario;
    private String nomeFuncionario;
    private List<Integer> listaRegistos;
    private List<Integer> listaEntregas;
    private int passwordFuncionario;

    public Funcionario(int idFuncionario, String nomeFuncionario,String password) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.listaRegistos = new ArrayList<Integer>();
        this.listaEntregas = new ArrayList<Integer>();
        this.passwordFuncionario = hashPass(password);
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public List<Integer> getListaRegistos() {
        return listaRegistos;
    }

    public void setListaRegistos(List<Integer> listaRegistos) {
        this.listaRegistos = new ArrayList<Integer>(listaRegistos);
    }

    public List<Integer> getListaEntregas() {
        return listaEntregas;
    }

    public void setListaEntregas(List<Integer> listaEntregas) {
        this.listaEntregas = new ArrayList<Integer>(listaEntregas);
    }

    public void addRegisto(int idServico) {
        this.listaRegistos.add(idServico);
    }

    public void addEntrega(int idServico) {
        this.listaEntregas.add(idServico);
    }

    public boolean logIn(String password){
        return this.passwordFuncionario == hashPass(password);
    }

    public int hashPass(String password){
        int hash = 5;
        for (int i = 0; i < password.length(); i++) {
            hash = hash*13 + password.charAt(i);
        }
        return hash;
    }
}
