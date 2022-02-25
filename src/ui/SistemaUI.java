package ui;

import controller.SistemaLN;

import java.util.List;
import java.util.Scanner;

public class SistemaUI {
    private SistemaLN model;
    private int userType;
    private int idUser;
    private Scanner scin;

    /**
     * Construtor.
     * <p>
     * Cria os menus e a camada de negócio.
     */
    public SistemaUI() {
        this.model = new SistemaLN();
        scin = new Scanner(System.in);
        this.model.loadStaff("D:\\IdeaProjects\\SRDE\\src\\csv\\funcionarios.csv",
                "D:\\IdeaProjects\\SRDE\\src\\csv\\tecnicos.csv",
                "D:\\IdeaProjects\\SRDE\\src\\csv\\gestor.csv");
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão de Reapracao de dispositivos eletronicos!");

        // load from csv os morcoes do staff
        boolean logged = false;
        int userType = 0;
        int idUser = 0;
        while (!logged) {
            System.out.println("Insira o tipo de login: ");
            System.out.println("1 - Funcionario ");
            System.out.println("2 - Tecnico");
            System.out.println("3 - Gestor ");
            userType = Integer.parseInt(scin.nextLine());

            System.out.println("LOGIN");
            System.out.println("Insira id: ");
            idUser = Integer.parseInt(scin.nextLine());
            System.out.println("Insira password: ");
            String password = scin.nextLine();

            logged = this.model.logIn(userType, idUser, password);
            if (!logged)
                System.out.println("Informacao fornecida incorreta.");
        }

        this.userType = userType;
        this.idUser = idUser;

        switch (this.userType) {
            case 1 -> {
                this.menuFuncionario();
            }
            case 2 -> {
                this.menuTecnico();
            }
            case 3 -> {
                this.menuGestor();
            }
        }
        System.out.println("Até breve...");
    }

    // Métodos auxiliares - Estados da UI
    private void menuFuncionario() {
        Menu menu = new Menu(new String[]{
                "Registar Servicos",
                "Registar Entrega e Pagamento",
                "Confirmar Orcamento"
        });

        menu.setHandler(1, () -> gestaoDeServicos());
        menu.setHandler(2, () -> registarEntregaPagamento());
        menu.setHandler(3, () -> confirmarOrcamento());

        menu.run();

    }

    private void menuTecnico() {
        Menu menu = new Menu(new String[]{
                "Registar Reparacao",
                "Iniciar Reparacao",
                "Retomar Reparacao" // consultar se tecnico tem reparacao interrompida
        });

        menu.setPreCondition(1, () -> !this.model.temReparacaoInterrompida(this.idUser));
        menu.setPreCondition(2, () -> !this.model.temReparacaoInterrompida(this.idUser) && this.model.hasReparacoes());
        menu.setPreCondition(3, () -> this.model.temReparacaoInterrompida(this.idUser));

        menu.setHandler(1, () -> registarReparacao());
        menu.setHandler(2, () -> iniciarReparacao());
        menu.setHandler(3, () -> retomarReparacao());
        menu.run();
    }

    private void menuGestor() {
        Menu menu = new Menu(new String[]{
                "Consultar listagem de reparacaoes",
                "Consultar listagem de entregas",
                "Consultar listagem de todas as intervencoes"
        });

        menu.setHandler(1, () -> consultarListagemDeReparacoes());
        menu.setHandler(2, () -> consultarListagemFuncionarios());
        menu.setHandler(3, () -> consultarListagemDeReparacoesExaustiva());

        menu.run();
    }

    /**
     * Estado - Registar Servicos
     * <p>
     * Transições para:
     * Registar Servico Normal
     * Registar Servico Expresso
     */
    private void gestaoDeServicos() {
        Menu menu = new Menu("Registar Servicos", new String[]{
                "Registar Servico Normal",
                "Registar Servico Expresso"
        });

        menu.setHandler(1, () -> registarServicoNormal());
        menu.setHandler(2, () -> registarServicoExpresso());

        menu.run();
    }

    /**
     * Estado - Registar Servico Normal
     */
    private void registarServicoNormal() {
        System.out.println("NIF do cliente: ");
        int nif = Integer.parseInt(scin.nextLine());

        System.out.println("Email do cliente: ");
        String email = scin.nextLine();

        System.out.println("Id atribuido ao equipamento: ");
        int idEquipamento = Integer.parseInt(scin.nextLine());

        System.out.println("Descricao breve do servico: ");
        String descricao = scin.nextLine();

        int idServico = this.model.criarServicoNormal(nif, email, idEquipamento, descricao);
        this.model.addRegisto(idUser, idServico);
        System.out.println("Servico adicionado");
    }

    /**
     * Estado - Registar Servico Expresso
     */
    private void registarServicoExpresso() {
        System.out.println("Numero telefonico do cliente: ");
        int numTelefonico = Integer.parseInt(scin.nextLine());

        System.out.println("Preco do servico: ");
        int preco = Integer.parseInt(scin.nextLine());

        System.out.println("Descricao do servico: ");
        String descricao = scin.nextLine();

        System.out.println("Id atribuido ao equipamento: ");
        int idEquipamento = Integer.parseInt(scin.nextLine());

        int idServico = this.model.criarServicoExpresso(numTelefonico, preco, descricao, idEquipamento);

        if (idServico != -1) {
            this.model.addRegisto(idUser, idServico);
            System.out.println("Servico adicionado");
        } else System.out.println("Operecao recusada: Nenhum tecnico disponivel de momento.");
    }

    /**
     * Estado - Registar Entrega Pagamento
     */
    private void registarEntregaPagamento() {
        System.out.println("NIF: ");
        int nif = Integer.parseInt(scin.nextLine());

        String lista = this.model.getInfoServicosCliente(nif);
        System.out.println(lista);

        int idServico;

        do {
            System.out.println("Introduza o id do servico a ser entregue: ");
            idServico = Integer.parseInt(scin.nextLine());
        } while (!this.model.verificaServico(idServico));

        this.model.entregarEquipamento(idServico, idUser);
        System.out.println("Equipamento entregue");
    }

    /**
     * Estado - Confirmar Orcamento
     */
    private void confirmarOrcamento() {
        System.out.println("Id Servico: ");
        int idServico = Integer.parseInt(scin.nextLine());
        int resposta = 0;
        while (resposta != 1 && resposta != 2) {
            System.out.println("Resposta do cliente: ");
            System.out.println("  1 - Aceite");
            System.out.println("  2 - Reijeitado");
            resposta = Integer.parseInt(scin.nextLine());
        }

        this.model.confirmarOrcamento(idServico, resposta == 1);
        System.out.println("Orcamento confirmado");
    }


    /**
     * Estado - Registar Reparacao
     */
    private void registarReparacao() {
        int idServico = this.model.registarReparacao(this.idUser);
        System.out.println("Registar reparacao (plano de trabalhos)");
        while (true) {
            System.out.println("Insira a descricao da seccao (Escreva \"sair\" para acabar)");
            String descricao = scin.nextLine();
            if (descricao.equals("sair")) break;
            this.model.createPasso(idServico, descricao);
            System.out.println("Seccao - " + descricao.toUpperCase());
            while (true) {
                System.out.println("\tInsira a descricao do passo (Escreva \"fim\" para acabar)");
                String descricaoSubPasso = scin.nextLine();
                if (descricaoSubPasso.equals("fim")) break;
                System.out.println("\t\tInsira o custo do passo: ");
                int custo = Integer.parseInt(scin.nextLine());
                System.out.println("\t\tInsira a duracao em minutos do passo: ");
                long duracao = Long.parseLong(scin.nextLine());
                this.model.addSubPasso(idServico, custo, duracao, descricaoSubPasso);
            }
        }
        this.model.criarOrcamento(idServico);
    }

    /**
     * Estado - Iniciar Reparacao
     */
    private void iniciarReparacao() {
        // proximo passo, passo anterior, interromper e terminar.
        int idReparacao = this.model.getNextReparacao();
        boolean isExpresso = this.model.isExpresso(idReparacao);
        this.model.iniciarReparacao(idReparacao, this.idUser);
        boolean interrompida = false;
        if(!isExpresso){
            interrompida = this.viewReparacao(idReparacao);
        }
        if (!interrompida)
            this.model.terminarReparacao(idReparacao, this.idUser);
        else
            this.model.interromperReparacao(idReparacao);
    }

    private boolean viewReparacao(int idReparacao) {
        List<String> reparacaoToShow = this.model.getReparacaoToTecnico(idReparacao);
        String idEquipamento = reparacaoToShow.remove(0);
        for (int i = 0; i < reparacaoToShow.size(); ) {
            System.out.println("Id Reparacao: " + idReparacao + "Id Equipamento: " + idEquipamento);
            System.out.println(reparacaoToShow.get(i));
            if (i != reparacaoToShow.size() - 1) {
                System.out.println("1 - Avancar");
            }
            if (i != 0) {
                System.out.println("2 - Recuar");
            }
            System.out.println("2 - Interromper");
            System.out.println("4 - Terminar");
            int resposta = Integer.parseInt(scin.nextLine());
            switch (resposta) {
                case 1 -> i++;
                case 2 -> i--;
                case 3 -> {
                    return true;
                }
                case 4 -> {
                    return false;
                }
                default -> {
                    System.out.println("Opcao invalida");
                }
            }
        }
        return false;
    }

    private void retomarReparacao() {
        int idReparacao = this.model.retomarReparacao(this.idUser);
        boolean interrompida = this.viewReparacao(idReparacao);
        if (!interrompida)
            this.model.terminarReparacao(idReparacao, this.idUser);
        else
            this.model.interromperReparacao(idReparacao);
    }


    private void consultarListagemDeReparacoes() {
        System.out.println(this.model.getListagemDeReparacao());
    }

    private void consultarListagemFuncionarios() {

        System.out.println(this.model.getListagemFuncionarios());

    }

    private void consultarListagemDeReparacoesExaustiva() {

        System.out.println(this.model.getListagemDeReparacoesExaustiva());

    }


}

