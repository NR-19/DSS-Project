package interfaces;

import controller.GestReparacoes.Reparacao;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface IGestReparacoes {

    Reparacao getReparacao(int idReparacao);

    void criarReparacaoNormal(int idServico, int idTecnicoPT);

    void criarReparacaoExpresso(int idServico);

    void aceitarOrcamento(int idServico);

    void rejeitarOrcamento(int idServico);

    void removeReparacao(int idServico);

    void createPasso(int idServico, String descricao);

    void addSubPasso(int idServico, int custo, Duration duracao, String descricao);

    int getNextReparacao();

    boolean hasReparacoes();

    void iniciarReparacao(int idServico, int idTecnicoRep);


    void interromperReparacao(int idServico);

    void retomarReparacao(int idServico);

    void terminarReparacao(int idServico);

    Map<Integer, List<Reparacao>> getListagemDeReparacaoExaustiva();

    String getListagemDeReparacao();

    List<String> getReparacaoToTecnico(int idServico);

    int getCustoTotal(int idReparacao);
}
