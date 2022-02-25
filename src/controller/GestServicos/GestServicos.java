package controller.GestServicos;

import interfaces.IGestServicos;

import java.util.HashMap;
import java.util.Map;

public class GestServicos implements IGestServicos {
    private Map<Integer, Servico> servicos;
    private int idServico;

    public GestServicos() {
        this.servicos = new HashMap<>();
        int idServico = 0;
    }

    public int criarServicoExpresso(int numTelefonico, int preco, String descricao, int idEquipamento) {
        try {
            ServicoExpresso servico = new ServicoExpresso(idServico, idEquipamento, descricao, preco, numTelefonico);
            servicos.put(idServico, servico);
            return idServico;
        } finally {
            this.idServico++;
        }
    }

    public int criarServicoNormal(int nif, String email, int idEquipamento, String descricao) {
        try {
            ServicoNormal servico = new ServicoNormal(idServico, idEquipamento, descricao, email, nif);
            servicos.put(idServico, servico);
            return idServico;
        } finally {
            this.idServico++;
        }
    }

    public void removeServico(int idServico) {
        servicos.remove(idServico);
    }

    public void criarOrcamento(int idServico, int preco) {
        Servico servico = servicos.get(idServico);
        if (servico instanceof ServicoNormal) {
            ((ServicoNormal) servico).criarOrcamento(preco);
        }
    }

    public void aceitarOrcamento(int idServico) {
        Servico servico = servicos.get(idServico);
        if (servico instanceof ServicoNormal) {
            ((ServicoNormal) servico).aceitarOrcamento();
        }
    }

    public void rejeitarOrcamento(int idServico) {
        Servico servico = servicos.get(idServico);
        if (servico instanceof ServicoNormal) {
            ((ServicoNormal) servico).rejeitarOrcamento();
        }
    }

    public void entregarEquipamento(int idServico) {

        this.servicos.get(idServico).entregarEquipamento();

    }

    public Servico getServico(int idServico) {

        return this.servicos.get(idServico);

    }

    public boolean isEntregue(int idServico) {
        return this.servicos.get(idServico).isEntregue();
    }

}
