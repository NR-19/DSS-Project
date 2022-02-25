package controller.GestStaff;

import interfaces.IGestStaff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class GestStaff implements IGestStaff {
    Map<Integer, Tecnico> listTecnicos;
    Map<Integer, Funcionario> listFuncionarios;
    Map<Integer, Gestor> listGestores;

    public GestStaff() {
        this.listTecnicos = new HashMap<>();
        this.listFuncionarios = new HashMap<>();
        this.listGestores = new HashMap<>();
    }

    public boolean logIn(int userType, int idUser, String password) {
        switch (userType) {
            case 1 -> {
                if (this.listFuncionarios.containsKey(idUser)) {
                    return this.listFuncionarios.get(idUser).logIn(password);
                }
            }
            case 2 -> {
                if (this.listTecnicos.containsKey(idUser)) {
                    return this.listTecnicos.get(idUser).logIn(password);
                }
            }
            case 3 -> {
                if (this.listGestores.containsKey(idUser)) {
                    return this.listGestores.get(idUser).logIn(password);
                }
            }
            default -> {

            }
        }
        return false;
    }

    public void loadStaff(String funcionarioPath, String tecnicoPath, String gestorPath) {
        List<List<String>> funcionarios = csvParser(funcionarioPath);
        List<List<String>> tecnicos = csvParser(tecnicoPath);
        List<List<String>> gestores = csvParser(gestorPath);

        for (List<String> linha : funcionarios) {
            this.addFuncionario(Integer.parseInt(linha.get(0)), linha.get(1), linha.get(2));
        }
        for (List<String> linha : tecnicos) {
            this.addTecnico(Integer.parseInt(linha.get(0)), linha.get(1), linha.get(2));
        }
        for (List<String> linha : gestores) {
            this.addGestor(Integer.parseInt(linha.get(0)), linha.get(1), linha.get(2));
        }
    }

    private List<List<String>> csvParser(String path) {
        List<List<String>> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (Exception ignore) {
        }
        records.remove(0);
        return records;
    }

    /**
     * Adiciona um tecnico na lista de tecnicos
     *
     * @param idTecnico id do tecnico a adicionar
     * @param nome      nome do tecnico a adicionar
     */
    @Override
    public void addTecnico(int idTecnico, String nome, String password) {
        Tecnico novoTecnico = new Tecnico(idTecnico, nome, password);
        listTecnicos.put(idTecnico, novoTecnico);
    }

    /**
     * Adiciona um funcionario na lista de funcionarios
     *
     * @param idFuncionario id do funcionario a adicionar
     * @param nome          nome do funcionario a adicionar
     */
    public void addFuncionario(int idFuncionario, String nome, String password) {
        Funcionario novoFuncionario = new Funcionario(idFuncionario, nome, password);
        listFuncionarios.put(idFuncionario, novoFuncionario);
    }

    /**
     * Adiciona um gestor na lista de funcionarios
     *
     * @param idGestor id do gestor a adicionar
     * @param nome     nome do gestor a adicionar
     */
    public void addGestor(int idGestor, String nome, String password) {
        Gestor novoGestor = new Gestor(idGestor, nome, password);
        listGestores.put(idGestor, novoGestor);
    }

    /**
     * Remove um tecnico da lista da lista de tecnicos
     *
     * @param idTecnico id do tecnico a remover
     */
    public void removeTecnico(int idTecnico) {
        listTecnicos.remove(idTecnico);
    }

    /**
     * Remove um funcionario da lista de funcionarios
     *
     * @param idFuncionario id do funcionario a remover
     */
    public void removeFuncionario(int idFuncionario) {
        listFuncionarios.remove(idFuncionario);
    }

    /**
     * Remove um gestor da lista de funcionarios
     *
     * @param idGestor id do gestor a remover
     */
    public void removeGestor(int idGestor) {
        listFuncionarios.remove(idGestor);
    }

    /**
     * @param idRegisto
     * @param idServico
     */
    public void addRegisto(int idRegisto, int idServico) {
        Funcionario funcionario = listFuncionarios.get(idRegisto);
        funcionario.addRegisto(idServico);
        listFuncionarios.put(idRegisto, funcionario);
    }

    public void addEntrega(int idFuncionario, int idServico) {
        Funcionario funcionario = listFuncionarios.get(idFuncionario);
        funcionario.addEntrega(idServico);
        listFuncionarios.put(idFuncionario, funcionario);
    }

    public boolean isTecnicoDisponivel() {
        for (Tecnico tecnico : listTecnicos.values()) {
            if (tecnico.isTecnicoDisponivel())
                return true;
        }
        return false;
    }

    public void setIdReparacaoAtual(int idTecnico, int idReparacaoAtual) {
        this.listTecnicos.get(idTecnico).setIdReparacaoAtual(idReparacaoAtual);
    }

    public int getIdReparacaoAtual(int idTecnico) {
        return this.listTecnicos.get(idTecnico).getIdReparacaoAtual();
    }

    public boolean temReparacaoInterrompida(int idTecnico) {
        return this.listTecnicos.get(idTecnico).getIdReparacaoAtual() != 0;
    }

    public String getListagemFuncionarios() {

        StringBuilder sb = new StringBuilder();
        sb.append("Listagem de Funcionarios: ").append('\n');
        for (Funcionario f : this.listFuncionarios.values()) {

            sb.append(f.getNomeFuncionario()).append("\n\t");
            sb.append("Entregas Realizadas: ").append(f.getListaEntregas()).append("\n\t");
            sb.append("Registos Realizados: ").append(f.getListaRegistos()).append("\n");

        }

        return sb.toString();
    }

    public String getNomeTecnico(Integer idTecnico) {

        return this.listTecnicos.get(idTecnico).getNomeTecnico();

    }


}
