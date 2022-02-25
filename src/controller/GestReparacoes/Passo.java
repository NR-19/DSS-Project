package controller.GestReparacoes;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Passo {
    private int custo;
    private Duration duracao;
    private String descricao;
    private LinkedList<Passo> subPassos;

    /**
     * Cria um controller.GestReparacoes.Passo, com a descricao geral
     *
     * @param descricao descricao a ser colocada no controller.GestReparacoes.Passo
     */

    public Passo(String descricao) {
        this.custo = 0;
        this.duracao = Duration.ofMinutes(0);
        this.descricao = descricao;
        this.subPassos = new LinkedList<>();
    }

    public Passo(int custo, Duration duracao, String descricao) {
        this.custo = custo;
        this.duracao = duracao;
        this.descricao = descricao;
        this.subPassos = new LinkedList<>();
    }

    public int getCusto() {
        return this.custo;
    }

    public Duration getDuracao() {
        return this.duracao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public List<Passo> getSubPassos() {
        return this.subPassos.stream().map(Passo::clone).collect(Collectors.toList());
    }

    /**
     * Adiciona um controller.GestReparacoes.Passo a lista de sub passos.
     *
     * @param custo   custo do passo
     * @param duracao duracao do passo
     */
    public void addSubPasso(int custo, Duration duracao, String descricao) {
        Passo p = new Passo(custo, duracao, descricao);
        this.custo += custo;
        this.duracao = this.duracao.plus(duracao);
        this.subPassos.add(p);
        this.updateCustos();
    }

    public int updateCustos() {
        if (!this.subPassos.isEmpty()) {
            this.custo = this.subPassos.stream().mapToInt(Passo::updateCustos).sum();
        }
        return this.custo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.custo + "€ | ");
        sb.append(this.duracao.toHours() + " horas | ");
        sb.append(this.descricao);
        if (!(this.subPassos.isEmpty())) {
            sb.append(":\n");
            for (Passo subPasso : this.subPassos) {
                sb.append("\t");
                sb.append(subPasso.toString() + "\n");
            }
        } else {
            sb.append("\n");
        }
        return sb.toString();
    }


    @Override
    public Passo clone() {
        return new Passo(this.custo, this.duracao, this.descricao);
    }
}
