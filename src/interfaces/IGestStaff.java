package interfaces;

public interface IGestStaff {
    boolean logIn(int userType, int idUser, String password);

    void loadStaff(String funcionarioPath, String tecnicoPath, String gestorPath);

    void addTecnico(int idTecnico, String nome, String password);

    void addFuncionario(int idFuncionario, String nome, String password);

    void removeTecnico(int idTecnico);

    void removeFuncionario(int idFuncionario);

    void addRegisto(int idFuncionario, int idServico);

    void addEntrega(int idFuncionario, int idServico);

    boolean isTecnicoDisponivel();

    void setIdReparacaoAtual(int idTecnico, int idReparacaoAtual);

    int getIdReparacaoAtual(int idTecnico);

    boolean temReparacaoInterrompida(int idTecnico);

    String getListagemFuncionarios();

    String getNomeTecnico(Integer idTecnico);
}
