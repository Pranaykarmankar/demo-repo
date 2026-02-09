import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static List<String> quotes;

    public static void main(String[] args) throws IOException {
        // Load quotes from file
        try (BufferedReader reader = new BufferedReader(new FileReader("quotes.txt"))) {
            quotes = reader.lines().collect(Collectors.toList());
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", (HttpExchange exchange) -> {
            String quote = quotes.get(new Random().nextInt(quotes.size()));
            byte[] response = quote.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        });

        System.out.println("Server started on port 8080...");
        server.start();
    }
}
