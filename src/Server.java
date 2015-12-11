import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	private static Map<Integer, Item> items;
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		items = Item.loadItems(new File("res/Items.json"));

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/api", new APIHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class APIHandler implements HttpHandler {

		static void sendString(HttpExchange t, int httpCode, String s) throws IOException {
			t.sendResponseHeaders(httpCode, s.length());
			OutputStream os = t.getResponseBody();
			os.write(s.getBytes());
			os.close();
		}

		static void sendJSON(HttpExchange t, Object o) throws IOException {
			t.sendResponseHeaders(200, 0);
			OutputStream os = t.getResponseBody();
			mapper.writerWithDefaultPrettyPrinter().writeValue(os, o);
			os.close();
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			// /api/items/23
			URI sub = URI.create("/api").relativize(t.getRequestURI());

			System.out.println(sub);
			String[] c = sub.getPath().split("/", 3);
			switch (c[0]) {
			case "items":
				if (c.length < 2 || c[1].equals("")) {
					sendJSON(t, items);
					break;
				}
                int itemID = Integer.parseInt(c[1]);
                if (items.containsKey(itemID))
                    sendJSON(t, items.get(itemID));
                else
                    sendString(t, 404, "<h1>404 Not Found</h1>");

				break;
			case "orders":
				int orderID = Integer.parseInt(c[1]);
				sendString(t, 200, "Order: " + orderID);
				break;
			default:
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
			}
		}
	}
}
