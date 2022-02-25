package controller.GestPedidosOrcamento;

import interfaces.IGestPedidosOrcamento;

import java.util.LinkedList;

public class GestPedidosOrcamento implements IGestPedidosOrcamento {
    LinkedList<Integer> pedidos;

    public GestPedidosOrcamento() {
        this.pedidos = new LinkedList<>();
    }

    public void addPedido(int idServico) {
        this.pedidos.add(idServico);
    }

    public int getPedido() {
        return this.pedidos.remove();
    }
}
