package org.htl_hl.bibiProject.Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.htl_hl.bibiProject.Common.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class Server {

	private static Map<Integer, Item> items;
	private static ObjectMapper mapper = new ObjectMapper();
	private static List<Game> games = new LinkedList<>();

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
			Map<String, String> queryPairs = new LinkedHashMap<>();
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
						URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			}
			return queryPairs;
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

		public void handleItems(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
			if (comps.size() != 1) {
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
				return;
			}

			switch (t.getRequestMethod().toUpperCase()) {
			case "GET":
				if (comps.get(0).getValue() == null) {
					sendJSON(t, items);
					return;
				}

				int itemId = Integer.parseInt(comps.get(0).getValue());
				if (items.containsKey(itemId))
					sendJSON(t, items.get(itemId));
				else
					sendString(t, 404, "<h1>404 Not Found</h1>");
				return;
			default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public void handleOrders(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
			if (comps.size() != 3 || !comps.get(0).getKey().equals("games") || !comps.get(1).getKey().equals("rounds")){
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
				return;
			}

			int gameId = Integer.parseInt(comps.get(0).getValue());
			Game game;
			try {
				game = games.get(gameId);
			} catch (IndexOutOfBoundsException e) {
				sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
				return;
			}

			int roundId = Integer.parseInt(comps.get(1).getValue());
			Round round;
			try {
				game.updateRounds();
				round = game.getRounds().get(roundId);
			} catch (IndexOutOfBoundsException e) {
				sendString(t, 400, "<h1>404 Not Found</h1>Round not found");
				return;
			}

			List<Player> players = game.getPlayers();
			List<Order> orders = round.getOrders();

			switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
				if (comps.get(2).getValue() == null) {
					sendJSON(t, orders);
					return;
				}

				int orderId = Integer.parseInt(comps.get(2).getValue());
				try {
					sendJSON(t, orders.get(orderId));
				} catch (IndexOutOfBoundsException e) {
					sendString(t, 404, "<h1>404 Not Found</h1>");
				}
				return;
            case "POST":
				if (comps.get(2).getValue() != null) {
					sendString(t, 400, "<h1>400 Bad Request</h1>Superfluous argument");
					return;
				}

				Map<String, String> m = getPostParameters(t);

				if (m != null && m.containsKey("itemId") && m.containsKey("playerId") && m.containsKey("isBuy")
						&& m.containsKey("limit") && m.containsKey("quantity")) {
					int index = orders.size();

					Order o = new Order(index, items.get(Integer.parseInt(m.get("itemId"))),
							players.get(Integer.parseInt(m.get("playerId"))),
							Boolean.parseBoolean(m.get("isBuy")),
							Double.parseDouble(m.get("limit")),
							Integer.parseInt(m.get("quantity")));

					orders.add(o);

					sendJSON(t, o);
				} else {
					sendString(t, 400, "<h1>400 Bad Request</h1>One or more parameter(s) missing");
				}
				return;
            default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public void handlePlayers(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
			if (comps.size() != 2 || !comps.get(0).getKey().equals("games")) {
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
				return;
			}

			int gameId = Integer.parseInt(comps.get(0).getValue());
			Game game;
			try {
				game = games.get(gameId);
				game.updateRounds();
			} catch (IndexOutOfBoundsException e) {
				sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
				return;
			}

			List<Player> players = game.getPlayers();

			switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
				if (comps.get(1).getValue() == null) {
					sendJSON(t, players);
					return;
				}

				int playerId = Integer.parseInt(comps.get(1).getValue());
				try {
					sendJSON(t, players.get(playerId));
				} catch (IndexOutOfBoundsException e) {
					sendString(t, 404, "<h1>404 Not Found</h1>");
				}
				return;
            case "POST":
				if (comps.get(1).getValue() != null) {
					sendString(t, 400, "<h1>400 Bad Request</h1>Superfluous argument");
					return;
				}

				Map<String, String> m = getPostParameters(t);

				if (m != null && m.containsKey("name")) {
					int index = players.size();
					List<Stock> playerStocks = new LinkedList<>();
					playerStocks.add(new Stock(items.get((int) (Math.random()*items.size())), 500));
					playerStocks.add(new Stock(items.get((int) (Math.random()*items.size())), 500));
					playerStocks.add(new Stock(items.get((int) (Math.random()*items.size())), 500));
					playerStocks.add(new Stock(items.get((int) (Math.random()*items.size())), 500));
					Player p = new Player(index, m.get("name"), playerStocks, 1000000.0);
					players.add(p); // TODO(Tharre): check if "name" exists

					sendJSON(t, p);
				} else {
					sendString(t, 400, "<h1>400 Bad Request</h1>name parameter missing");
				}
				return;
            default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public void handleGames(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
			if (comps.size() != 1) {
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
				return;
			}

			switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
				if (comps.get(0).getValue() == null) {
					sendJSON(t, games);
					return;
				}

				int gameId = Integer.parseInt(comps.get(0).getValue());
				for (Game game : games)
					if (game.getId() == gameId) {
						sendJSON(t, game);
						return;
					}

				sendString(t, 404, "<h1>404 Not Found</h1>");
				return;
			case "POST":
				if (comps.get(0).getValue() != null) {
					sendString(t, 400, "<h1>400 Bad Request</h1>Superfluous argument");
					return;
				}

				Map<String, String> m = getPostParameters(t);

				if (m != null && m.containsKey("name")) {
					int index = games.size();
					List<Player> players = new LinkedList<>();
					Game game = new Game(index, m.get("name"), players);
					game.start();
					games.add(game); // TODO(Tharre): check if "name" exists

					sendJSON(t, game);
				} else {
					sendString(t, 400, "<h1>400 Bad Request</h1>name parameter missing");
				}
				return;
			default:
				sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		private void handleRounds(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
			if (comps.size() != 2 || !comps.get(0).getKey().equals("games")) {
				sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
				return;
			}

			int gameId = Integer.parseInt(comps.get(0).getValue());
			Game game;
			try {
				game = games.get(gameId);
			} catch (IndexOutOfBoundsException e) {
				sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
				return;
			}

			game.updateRounds();
			List<Round> rounds = game.getRounds();

			switch (t.getRequestMethod().toUpperCase()) {
				case "GET":
					if (comps.get(1).getValue() == null) {
						sendJSON(t, game.getActiveRound());
						return;
					}

					int roundId = Integer.parseInt(comps.get(1).getValue());
					try {
						sendJSON(t, rounds.get(roundId));
	 				} catch (IndexOutOfBoundsException e) {
						sendString(t, 404, "<h1>404 Not Found</h1>");
					}
					return;
				default:
					sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
			}
		}

		public static List<SimpleEntry<String, String>> parseURL(String s) {
			String[] comps = s.split("/");
			List<SimpleEntry<String, String>> list = new LinkedList<>();

			for (int i = 0; i < comps.length; i+=2) {
				if (i == comps.length-1)
					list.add(new SimpleEntry<>(comps[i], null));
				else
					list.add(new SimpleEntry<>(comps[i], comps[i+1]));
			}

			return list;
		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			// /api/items/23
			// /api/games/23/players
			URI sub = URI.create("/api").relativize(t.getRequestURI());

			System.out.println(sub);

			List<SimpleEntry<String, String>> comps = parseURL(sub.getPath());
			SimpleEntry<String, String> last = comps.get(comps.size()-1);
			switch (last.getKey()) {
            case "games":
                handleGames(comps, t);
                break;
            case "items":
				handleItems(comps, t);
                break;
            case "orders":
				handleOrders(comps, t);
                break;
            case "players":
				handlePlayers(comps, t);
                break;
            case "rounds":
				handleRounds(comps, t);
				break;
            default:
                sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
			}
		}
	}
}
