import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorDados {

    // Gera uma lista de Registros com códigos de 9 dígitos
    public static List<Registro> gerarRegistros(int quantidade, long seed) {
        List<Registro> registros = new ArrayList<>();
        Random random = new Random(seed); // A SEED garante a repetibilidade dos dados

        for (int i = 0; i < quantidade; i++) {
            // Gera um número aleatório entre 100.000.000 e 999.999.999
            int codigo = 100_000_000 + random.nextInt(900_000_000);
            registros.add(new Registro(codigo));
        }
        return registros;
    }
}