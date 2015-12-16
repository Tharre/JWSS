package org.htl_hl.bibiProject.Server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;
import org.htl_hl.bibiProject.Common.*;

public class Server {

	private static Map<Integer, Item> items;
	private static ObjectMapper mapper = new ObjectMapper();
	private static List<Player> players = new ArrayList<>();

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

		public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
			Map<String, String> query_pairs = new LinkedHashMap<>();
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
						URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			}
			return query_pairs;
		}

		public Map<String, String> getPostParameters(HttpExchange t) throws IOException {
			if (!t.getRequestHeaders().getFirst("Content-Type").equals("application/x-www-form-urlencoded")) {
				sendString(t, 400, "<h1>400 Bad Request</h1>Bad Content-Type"); // TODO(Tharre): HTTP code?
				return null;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "UTF-8"));
			String query = br.readLine();

			return splitQuery(query);
		}

		public void handleItems(String[] c, HttpExchange t) throws IOException {
			switch (t.getRequestMethod().toUpperCase()) {
			case "GET":
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
			default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public void handleOrders(String[] c, HttpExchange t) throws IOException {
			switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
				// TODO(Tharre): implement
                break;
            case "POST":
				Map<String, String> m = getPostParameters(t);

				if (m != null) {
					// TODO(Tharre): implement
				}
                break;
            default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public void handlePlayers(String[] c, HttpExchange t) throws IOException {
			switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
				if (c.length < 2 || c[1].equals("")) {
					sendJSON(t, players);
					break;
				}
				int playerID = Integer.parseInt(c[1]);
				if (playerID < players.size())
					sendJSON(t, players.get(playerID));
				else
					sendString(t, 404, "<h1>404 Not Found</h1>");
                break;
            case "POST":
				Map<String, String> m = getPostParameters(t);

				if (m != null) {
					int index = players.size();
					players.add(new Player(index, m.get("name"))); // TODO(Tharre): check if "name" exists

					sendString(t, 200, "{ \"id\": " + index + " }\n");
				}
                break;
            default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			// /api/items/23
			URI sub = URI.create("/api").relativize(t.getRequestURI());

			System.out.println(sub);
			String[] c = sub.getPath().split("/", 3);
			switch (c[0]) {
			case "items":
				handleItems(c, t);
				break;
			case "orders":
				handleOrders(c, t);
				break;
			case "players":
				handlePlayers(c, t);
				break;
			default:
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
			}
		}
	}
}
