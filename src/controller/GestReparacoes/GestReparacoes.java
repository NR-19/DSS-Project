package controller.GestReparacoes;

import interfaces.IGestReparacoes;

import java.time.Duration;
import java.util.*;

public class GestReparacoes implements IGestReparacoes {
    private Map<Integer, Reparacao> reparacoes;
    private Map<Integer, Reparacao> reparacoesPendentes;
    private LinkedList<Integer> queueReparacoes;

    public GestReparacoes() {
        this.reparacoes = new HashMap<>();
        this.reparacoesPendentes = new HashMap<>();
        this.queueReparacoes = new LinkedList<>();
    }

    @Override
    public Reparacao getReparacao(int idReparacao) {
        return this.reparacoes.get(idReparacao);
    }

    @Override
    public void criarReparacaoNormal(int idServico, int idTecnicoPT) {
        Reparacao rep = new ReparacaoNormal(idServico, idTecnicoPT);
        this.reparacoesPendentes.put(idServico, rep);
    }

    @Override
    public void criarReparacaoExpresso(int idServico) {
        Reparacao rep = new ReparacaoExpresso(idServico);
        this.reparacoes.put(idServico, rep);
        this.queueReparacoes.addFirst(idServico);
    }

    public int getCustoTotal(int idReparacao){
        ReparacaoNormal r = (ReparacaoNormal) this.reparacoesPendentes.get(idReparacao);
        return r.getCustoTotal();
    }

    @Override
    public void aceitarOrcamento(int idServico) {
        Reparacao aceite = this.reparacoesPendentes.remove(idServico);
        this.reparacoes.put(idServico, aceite);
        this.queueReparacoes.add(idServico);
    }

    @Override
    public void rejeitarOrcamento(int idServico) {
        this.reparacoesPendentes.remove(idServico);
    }

    @Override
    public void removeReparacao(int idServico) {
        this.reparacoes.remove(idServico);
    }

    @Override
    public void createPasso(int idServico, String descricao) {
        ReparacaoNormal r = (ReparacaoNormal) this.reparacoesPendentes.get(idServico);
        r.createPasso(descricao);
    }

    @Override
    public void addSubPasso(int idServico, int custo, Duration duracao, String descricao) {
        ReparacaoNormal r = (ReparacaoNormal) this.reparacoesPendentes.get(idServico);
        r.addSubPasso(custo, duracao, descricao);
    }

    @Override
    public int getNextReparacao() {
        return this.queueReparacoes.remove();
    }

    @Override
    public boolean hasReparacoes() {
        return !this.queueReparacoes.isEmpty();
    }

    @Override
    public void iniciarReparacao(int idServico, int idTecnicoRep) {
        Reparacao r = this.reparacoes.get(idServico);
        r.iniciarReparacao(idTecnicoRep);
    }

    // criar reparacao interrompida no tecnico!
    @Override
    public void interromperReparacao(int idServico) {
        Reparacao r = this.reparacoes.get(idServico);
        r.interromperReparacao();
    }

    @Override
    public void retomarReparacao(int idServico) {
        Reparacao r = this.reparacoes.get(idServico);
        r.retomarReparacao();
    }

    @Override
    public void terminarReparacao(int idServico) {
        Reparacao r = this.reparacoes.get(idServico);
        r.terminarReparacao();
    }

    @Override
    public String getListagemDeReparacao() {
        StringBuilder sb = new StringBuilder();

        Map<Integer, Integer> quantidade = new HashMap<>();
        for (Reparacao r : this.reparacoes.values()) {
            Integer key = r.getIdTecnicoRep();
            if (quantidade.containsKey(key)) {
                Integer times = quantidade.get(r.getIdTecnicoRep());
                System.out.println(times);
                quantidade.put(key,times+1);
            } else {
                quantidade.put(key, 1);
            }
        }

        sb.append("Listagem de Reparacoes: " + '\n');

        for (var e : quantidade.entrySet()) {
            sb.append("Técnico ").append(e.getKey());
            sb.append("-> Numero de reparacoes realizadas: ").append(e.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public List<String> getReparacaoToTecnico(int idServico) {
        ReparacaoNormal r = (ReparacaoNormal) this.reparacoes.get(idServico);
        return r.getReparacaoToTecnico();
    }

    @Override
    public Map<Integer, List<Reparacao>> getListagemDeReparacaoExaustiva() {

        Map<Integer, List<Reparacao>> reparacoesExaustiva = new HashMap<>();

        for (Reparacao r : this.reparacoes.values()) {
            Integer key = r.getIdTecnicoRep();
            reparacoesExaustiva.putIfAbsent(key,new ArrayList<>());
            reparacoesExaustiva.get(key).add(r);
        }

        return reparacoesExaustiva;

    }

}
