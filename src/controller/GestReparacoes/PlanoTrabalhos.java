package controller.GestReparacoes;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class PlanoTrabalhos {
    private LinkedList<Passo> passos;

    public PlanoTrabalhos() {
        this.passos = new LinkedList<>();
    }

    public PlanoTrabalhos(LinkedList<Passo> passos) {
        this.passos = new LinkedList<>(passos);
    }

    /**
     * Adiciona um controller.GestReparacoes.Passo a Lista passos.
     *
     * @param descricao descricao do controller.GestReparacoes.Passo a ser adicionado
     */
    public void createPasso(String descricao) {
        Passo p = new Passo(descricao);
        this.passos.add(p);
    }

    /**
     * Adiciona um subPasso ao ultimo controller.GestReparacoes.Passo adicionado a Lista passos.
     *
     * @param custo     custo do subPasso a ser adicionado
     * @param duracao   duracao do subPasso a ser adicionado
     * @param descricao descricao do subPasso a ser adiconado
     */
    public void addSubPasso(int custo, Duration duracao, String descricao) {
        Passo p = this.passos.getLast();
        p.addSubPasso(custo, duracao, descricao);
    }

    /**
     * Retorna o elemento controller.GestReparacoes.Passo que se encontra na posicao index.
     *
     * @param index posicao do elemento desejado
     * @return elemento da posicao index
     */
    public Passo getPasso(int index) {
        return this.passos.get(index);
    }

    /**
     * Remove ultimo passo adicionado.
     */
    public void removePasso() {
        this.passos.remove();
    }

    public int calculaCustoTotal(){
        int custoTotal = 0;
        for(Passo p: this.passos){
            custoTotal += p.getCusto();
        }
        return custoTotal;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Plano de Trabalhos: \n");
        for (Passo p : this.passos) {
            sb.append(p.toString()).append('\n');
        }
        return sb.toString();
    }

    public List<String> toStringList() {
        List<String> lista = new ArrayList<>();
        for(Passo p:this.passos){
            lista.add(p.toString());
        }
        return lista;
    }

    @Override
    public PlanoTrabalhos clone() {
        return new PlanoTrabalhos(this.passos);
    }

}
