package controller;

import controller.GestCientes.GestClientes;
import controller.GestPedidosOrcamento.GestPedidosOrcamento;
import controller.GestReparacoes.GestReparacoes;
import controller.GestReparacoes.Reparacao;
import controller.GestReparacoes.ReparacaoExpresso;
import controller.GestServicos.GestServicos;
import controller.GestServicos.Servico;
import controller.GestStaff.GestStaff;
import interfaces.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class SistemaLN {
    private IGestPedidosOrcamento pedidosOrcamento;
    private IGestServicos servicos;
    private IGestReparacoes reparacoes;
    private IGestStaff staff;
    private IGestClientes clientes;

    public SistemaLN() {
        this.pedidosOrcamento = new GestPedidosOrcamento();
        this.servicos = new GestServicos();
        this.reparacoes = new GestReparacoes();
        this.staff = new GestStaff();
        this.clientes = new GestClientes();
    }

    public boolean logIn(int userType, int idUser, String password) {
        return this.staff.logIn(userType, idUser, password);
    }

    public void loadStaff(String funcionarioPath, String tecnicoPath, String gestorPath) {
        this.staff.loadStaff(funcionarioPath, tecnicoPath, gestorPath);
    }

    public String getInfoServicosCliente(int nif) {
        StringBuilder sb = new StringBuilder();

        List<Integer> servicos = this.clientes.getServicos(nif);// estado 0 nao reparada, 1 reparada, -1 iterrompida
        for (int idServico : servicos) {
            Servico s = this.servicos.getServico(idServico);
            Reparacao r = this.reparacoes.getReparacao(idServico);
            if (!this.servicos.getServico(idServico).isEntregue() && r.isFinished()) {
                int idEquipamento = this.servicos.getServico(idServico).getIdEquipamento();
                sb.append("Servico: " + idServico + " | Equipamento: " + idEquipamento + " | Estado: Terminada" + '\n');
            }
        }
        return sb.toString();
    }

    public boolean verificaServico(int idServico) {
        return this.reparacoes.getReparacao(idServico).isFinished();
    }


    public int criarServicoNormal(int nif, String email, int idEquipamento, String descricao) {
        this.clientes.addClient(nif, email);
        int idServico = this.servicos.criarServicoNormal(nif, email, idEquipamento, descricao);
        this.clientes.addServico(nif, idEquipamento, idServico);
        this.pedidosOrcamento.addPedido(idServico);
        return idServico;
    }

    public int criarServicoExpresso(int numTelefonico, int preco, String descricao, int idEquipamento) {
        int idServico = -1;
        if (this.staff.isTecnicoDisponivel()) {
            idServico = this.servicos.criarServicoExpresso(numTelefonico, preco, descricao, idEquipamento);
            this.reparacoes.criarReparacaoExpresso(idServico);
        }
        return idServico;
    }

    public void criarOrcamento(int idReparacao){
        int custo = this.reparacoes.getCustoTotal(idReparacao);
        this.servicos.criarOrcamento(idReparacao,custo);
    }

    public void addRegisto(int idFuncionario, int idServico) {
        this.staff.addRegisto(idFuncionario, idServico);
    }

    public void confirmarOrcamento(int idServico, boolean resposta) {
        if (resposta) {
            this.servicos.aceitarOrcamento(idServico);
            this.reparacoes.aceitarOrcamento(idServico);
        } else {
            this.servicos.rejeitarOrcamento(idServico);
            this.reparacoes.rejeitarOrcamento(idServico);
        }
    }

    public void entregarEquipamento(int idServico, int idFuncionario) {
        this.servicos.entregarEquipamento(idServico);
        this.staff.addEntrega(idFuncionario, idServico);
    }

    public boolean temReparacaoInterrompida(int idTecnico) {
        return this.staff.temReparacaoInterrompida(idTecnico);
    }

    public int registarReparacao(int idTecnico) {
        int idProxRep = this.pedidosOrcamento.getPedido();
        this.reparacoes.criarReparacaoNormal(idProxRep, idTecnico);
        return idProxRep;
    }

    public void createPasso(int idServico, String descricao) {
        this.reparacoes.createPasso(idServico, descricao);
    }

    public void addSubPasso(int idServico, int custo, long duracaoString, String descricao) {
        Duration duracao = Duration.ofMinutes(duracaoString);
        this.reparacoes.addSubPasso(idServico, custo, duracao, descricao);
    }

    public boolean hasReparacoes() {
        return this.reparacoes.hasReparacoes();
    }

    public int getNextReparacao() {
        return this.reparacoes.getNextReparacao();
    }

    public void iniciarReparacao(int idServico, int idTecnico) {
        this.reparacoes.iniciarReparacao(idServico, idTecnico);
        this.staff.setIdReparacaoAtual(idTecnico, idServico);
    }

    public boolean isExpresso(int idReparacao){
        return (this.reparacoes.getReparacao(idReparacao) instanceof ReparacaoExpresso);
    }

    public List<String> getReparacaoToTecnico(int idServico) {
        int idEquipamento = this.servicos.getServico(idServico).getIdEquipamento();
        List<String> res = this.reparacoes.getReparacaoToTecnico(idServico);
        res.add(0, Integer.toString(idEquipamento));
        return res;
    }

    public int retomarReparacao(int idTecnico) {
        return this.staff.getIdReparacaoAtual(idTecnico);
    }

    public void interromperReparacao(int idReparacao) {
        this.reparacoes.interromperReparacao(idReparacao);
    }

    public void terminarReparacao(int idReparacao, int idTecnico) {
        this.reparacoes.terminarReparacao(idReparacao);
        this.staff.setIdReparacaoAtual(idTecnico, 0);
    }

    public String getListagemDeReparacao() {

        return this.reparacoes.getListagemDeReparacao();

    }

    public String getListagemFuncionarios() {

        return this.staff.getListagemFuncionarios();

    }

    public String getListagemDeReparacoesExaustiva() {

        Map<Integer, List<Reparacao>> lista = this.reparacoes.getListagemDeReparacaoExaustiva();

        StringBuilder sb = new StringBuilder();

        for (var e : lista.entrySet()) {

            Integer key = e.getKey();
            String nome = this.staff.getNomeTecnico(key);

            sb.append(nome).append(":\n\t");

            for (Reparacao r : e.getValue()) {
                sb.append(r.toString()).append("\n\t");
            }
        }

        return sb.toString();
    }
}
