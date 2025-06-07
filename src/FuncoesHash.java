public class FuncoesHash {

    // 1. Função Hash: Resto da Divisão
    public static int hashDivisao(int codigo, int tamanhoTabela) {
        return codigo % tamanhoTabela;
    }

    // 2. Função Hash: Multiplicação
    public static int hashMultiplicacao(int codigo, int tamanhoTabela) {
        double A = 0.6180339887; // Constante (pode ser qualquer valor entre 0 e 1)
        double val = codigo * A;
        double frac = val - (int) val;
        return (int) (tamanhoTabela * frac);
    }

    // 3. Função Hash: Dobramento
    public static int hashDobramento(int codigo, int tamanhoTabela) {
        // Converte o código para string para facilitar o "dobramento"
        String s = String.format("%09d", codigo); // Garante 9 dígitos
        
        // Divide em 3 partes de 3 dígitos
        int p1 = Integer.parseInt(s.substring(0, 3));
        int p2 = Integer.parseInt(s.substring(3, 6));
        int p3 = Integer.parseInt(s.substring(6, 9));
        
        int soma = p1 + p2 + p3;
        
        return soma % tamanhoTabela;
    }
}