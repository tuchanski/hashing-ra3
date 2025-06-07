import java.util.List;
import java.util.Random;
import java.util.function.ToIntBiFunction;

public class Main {
    public static void main(String[] args) {
        
        final long SEED = 12345L;
        int[] tamanhosTabela = {1000, 10000, 100000};
        int[] tamanhosDados = {1_000_000, 5_000_000, 20_000_000};
        
        // --- GERAÇÃO DOS DADOS ---
        System.out.println("Gerando conjuntos de dados...");
        List<Registro> dados1M = GeradorDados.gerarRegistros(tamanhosDados[0], SEED);
        List<Registro> dados5M = GeradorDados.gerarRegistros(tamanhosDados[1], SEED); 
        List<Registro> dados20M = GeradorDados.gerarRegistros(tamanhosDados[2], SEED);
        List<List<Registro>> todosOsConjuntos = List.of(dados1M, dados5M, dados20M);

        // --- LOOP PRINCIPAL DE TESTES ---
        System.out.println("TamanhoTabela;FuncaoHash;TamanhoDados;TempoInsercao(ms);Colisoes;TempoBusca(ns);ComparacoesMedias");
        
        for (int tamTabela : tamanhosTabela) {
            for (int i = 0; i < 3; i++) {
                for (List<Registro> conjuntoDados : todosOsConjuntos) {
                    
                    ToIntBiFunction<Integer, Integer> funcaoAtual;
                    String nomeFuncao;
                    
                    if (i == 0) {
                        funcaoAtual = FuncoesHash::hashDivisao;
                        nomeFuncao = "Divisao";
                    } else if (i == 1) {
                        funcaoAtual = FuncoesHash::hashMultiplicacao;
                        nomeFuncao = "Multiplicacao";
                    } else {
                        funcaoAtual = FuncoesHash::hashDobramento;
                        nomeFuncao = "Dobramento";
                    }
                    
                    // --- Teste de Inserção ---
                    TabelaHashEncadeada tabela = new TabelaHashEncadeada(tamTabela, funcaoAtual);
                    long startTime = System.currentTimeMillis();
                    for (Registro r : conjuntoDados) {
                        tabela.inserir(r);
                    }
                    long endTime = System.currentTimeMillis();
                    long tempoInsercao = endTime - startTime;
                    long colisoes = tabela.getColisoes();
                    
                    // --- Teste de Busca ---
                    long tempoBuscaTotal = 0;
                    long comparacoesTotais = 0;
                    int numBuscas = 5;
                    Random rand = new Random(SEED);
                    
                    for (int k = 0; k < numBuscas; k++) {
                        Registro registroParaBuscar = conjuntoDados.get(rand.nextInt(conjuntoDados.size()));
                        
                        long startBusca = System.nanoTime();
                        tabela.buscar(registroParaBuscar.getCodigo());
                        long endBusca = System.nanoTime();
                        
                        tempoBuscaTotal += (endBusca - startBusca);
                        comparacoesTotais += tabela.getComparacoesBusca();
                    }
                    long tempoBuscaMedio = tempoBuscaTotal / numBuscas;
                    double comparacoesMedias = (double) comparacoesTotais / numBuscas;

                    System.out.printf("%d;%s;%d;%d;%d;%d;%.2f\n",
                        tamTabela, nomeFuncao, conjuntoDados.size(), tempoInsercao, colisoes, tempoBuscaMedio, comparacoesMedias);
                }
            }
        }
    }
}