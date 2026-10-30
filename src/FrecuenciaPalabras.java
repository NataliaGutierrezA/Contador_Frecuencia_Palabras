import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrecuenciaPalabras {

    public static void main(String[] args) throws IOException {
        // Ruta del archivo: por argumento, o "texto.txt" por defecto
        Path ruta = Path.of(args.length > 0 ? args[0] : "C:\\Users\\Test_User\\Documents\\java_projects\\ContadorFrecuenciaPalabras\\resources\\texto.txt");

        Map<String, Integer> frecuencias = new HashMap<>();
        int totalCaracteres = 0;
        int totalPalabras = 0;

        // readAllLines separa por saltos de linea y NO los incluye en cada String,
        // asi que linea.length() ya omite los saltos pero conserva los espacios.
        for (String linea : Files.readAllLines(ruta)) {
            totalCaracteres += linea.length();

            // Separar por uno o mas espacios/tabs
            for (String token : linea.trim().split("\\s+")) {
                // Quitar signos de puntuacion y pasar a minuscula
                String palabra = token.toLowerCase().replaceAll("[^\\p{L}\\p{N}]", "");
                if (palabra.isEmpty()) {
                    continue;
                }
                frecuencias.merge(palabra, 1, Integer::sum);
                totalPalabras++;
            }
        }

        System.out.println("Total de caracteres (con espacios, sin saltos de linea): " + totalCaracteres);
        System.out.println("Total de palabras: " + totalPalabras);
        System.out.println("Palabras distintas: " + frecuencias.size());
        System.out.println();

        imprimirHistograma(frecuencias);
    }

    private static void imprimirHistograma(Map<String, Integer> frecuencias) {
        // Pasar el mapa a una lista para poder ordenarla de mayor a menor frecuencia
        List<Map.Entry<String, Integer>> entradas = new ArrayList<>(frecuencias.entrySet());
        entradas.sort((a, b) -> {
            int porFrecuencia = b.getValue().compareTo(a.getValue());
            return porFrecuencia != 0 ? porFrecuencia : a.getKey().compareTo(b.getKey());
        });

        // Ancho de la columna de palabras = la palabra mas larga
        int ancho = 0;
        for (Map.Entry<String, Integer> e : entradas) {
            ancho = Math.max(ancho, e.getKey().length());
        }

        for (Map.Entry<String, Integer> e : entradas) {
            String barra = "#".repeat(e.getValue());
            System.out.printf("%-" + ancho + "s | %s (%d)%n", e.getKey(), barra, e.getValue());
        }
    }
}
