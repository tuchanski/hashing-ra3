import java.util.LinkedList;
import java.util.List;
import java.util.function.ToIntBiFunction; // Para passar a função hash como parâmetro

public class TabelaHashEncadeada {
    private List<Registro>[] tabela;
    private int tamanho;
    private ToIntBiFunction<Integer, Integer> funcaoHash; // Recebe (código, tamanho) -> índice

    // Métricas
    private long colisoes = 0;
    private long comparacoesBusca = 0;

    @SuppressWarnings("unchecked")
    public TabelaHashEncadeada(int tamanho, ToIntBiFunction<Integer, Integer> funcaoHash) {
        this.tamanho = tamanho;
        this.funcaoHash = funcaoHash;
        this.tabela = new LinkedList[tamanho];
        // Inicializa todas as listas na tabela
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    public void inserir(Registro registro) {
        int codigo = registro.getCodigo();
        int indice = funcaoHash.applyAsInt(codigo, tamanho);

        // A colisão ocorre se a lista naquele índice JÁ tiver algum elemento.
        if (!tabela[indice].isEmpty()) {
            colisoes++;
        }
        
        tabela[indice].add(registro);
    }

    public Registro buscar(int codigo) {
        comparacoesBusca = 0; // Reseta para cada busca
        int indice = funcaoHash.applyAsInt(codigo, tamanho);
        
        List<Registro> bucket = tabela[indice];
        if (bucket.isEmpty()) {
            comparacoesBusca++; // 1 comparação para verificar se a lista é vazia
            return null;
        }

        for (Registro registro : bucket) {
            comparacoesBusca++;
            if (registro.getCodigo() == codigo) {
                return registro;
            }
        }
        return null;
    }

    // Getters para as métricas
    public long getColisoes() { return colisoes; }
    public long getComparacoesBusca() { return comparacoesBusca; }
}