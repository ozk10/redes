package modelo;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class HashUtil {

    // Método sha256() de Google Guava
    public static String sha256(String input) {
        if (input == null) {
            throw new IllegalArgumentException("La entrada no puede ser null");
        }

        return Hashing.sha256()
                     .hashString(input, StandardCharsets.UTF_8)
                     .toString();
    }
    
}
