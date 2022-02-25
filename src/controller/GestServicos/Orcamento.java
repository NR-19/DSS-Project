package controller.GestServicos;

import java.time.LocalDateTime;

public class Orcamento {
    int preco;
    LocalDateTime data;
    int estado; // -1 = rejeitado , 0 = undefined , 1 = aceite;

    public Orcamento(int preco) {
        this.preco = preco;
        this.data = LocalDateTime.now();
        this.estado = 0;
    }

    public void aceitarOrcamento(){
        this.estado = 1;
    }

    public void rejeitarOrcamento(){
        this.estado = -1;
    }

}
