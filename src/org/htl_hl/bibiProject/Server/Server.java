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

/**
 * <p>Title: Server</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Server.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Server {
  /** items - Private Eigenschaft der Klasse Server vom Typ Map<Interger, Item>.<br>
   * Alle Items mit dazugeh&ouml;riger ID verkn&uuml;pft.
   */
  private static Map<Integer, Item> items;
  /** mapper - Private Eigenschaft der Klasse Server vom Typ ObjectMapper.<br>
   * ObjectMapper ist ein externes Package - n&auml;here Informationen dar&uuml;ber finden Sie <a href="https://fasterxml.github.io/jackson-databind/javadoc/2.5/com/fasterxml/jackson/databind/ObjectMapper.html">hier</a>.
   */
  private static ObjectMapper mapper = new ObjectMapper();
  /** games - Private Eigenschaft der Klasse Server vom Typ List<Game>.<br>
   * Auf einem Server k&ouml;nnen n-Games laufen - in dieser Liste sind alle laufenden Games eingetragen.
   */
  private static List<Game> games = new LinkedList<>();

  public static void main(String[] args) throws Exception {
    items = Item.loadItems(new File("res/Items.json"));

    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/api", new APIHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  /**
   * <p>Title: APIHandler</p>
   * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse APIHandler.</p>
   * <p>Copyright: Copyright (c) 2016</p>
   * <p>Company: HTL Hollabrunn</p>
   * <br><br>
   * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
   * <br>
   * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
   * @version 0.1
   */
  static class APIHandler implements HttpHandler {
    /** Methode zum Beantworten einer Anfrage an den Server.
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
     * @param httpCode int - HTTP Statuscode
     * @param s String - Antwort des Servers
         * @throws IOException
         */
    static void sendString(HttpExchange t, int httpCode, String s) throws IOException {
      t.sendResponseHeaders(httpCode, s.length());
      OutputStream os = t.getResponseBody();
      os.write(s.getBytes());
      os.close();
    }

    /** Methode zum Beantworten einer Anfrage mittels JSON.
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
     * @param o Object - JSON-Objekt welches versendet werden soll
         * @throws IOException
         */
    static void sendJSON(HttpExchange t, Object o) throws IOException {
      t.sendResponseHeaders(200, 0);
      OutputStream os = t.getResponseBody();
      mapper.writerWithDefaultPrettyPrinter().writeValue(os, o);
      os.close();
    }

    /** Hilfsmethode zum Aufteilen einer HTTP-Query.
     * @param query String - Query
     * @return queryPairs Die aufgeteilte Query
     * @throws UnsupportedEncodingException
         */
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

    /** Methode zum Auslesen der POST-Parameter.
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
     * @return query POST-Parameter
         * @throws IOException
         */
    public Map<String, String> getPostParameters(HttpExchange t) throws IOException {
      if (!t.getRequestHeaders().getFirst("Content-Type").equals("application/x-www-form-urlencoded")) {
        sendString(t, 400, "<h1>400 Bad Request</h1>Bad Content-Type"); // TODO(Tharre): HTTP code?
        return null;
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "UTF-8"));
      String query = br.readLine();

      return splitQuery(query);
    }

    /** Methode zum Abarbeiten aller Anfragen bez&uuml; /items.
     * @param comps List<SimpleEntry<String, String>> - Aufgeteilter HTTP-Path
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
         * @throws IOException -
         */
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

    /** Methode zum Abarbeiten aller Anfragen bez&uuml; /orders.
     * @param comps List<SimpleEntry<String, String>> - Aufgeteilter HTTP-Path
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
         * @throws IOException
         */
    public void handleOrders(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
            if (comps.size() < 2 || !comps.get(0).getKey().equals("games")) {
        sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
        return;
      }

      int gameId = Integer.parseInt(comps.get(0).getValue());
      if (gameId < 0 || gameId > games.size()) {
        sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
        return;
      }

      Game game = games.get(gameId);
      game.updateRounds();
            List<Order> orders;

      List<Player> players = game.getPlayers();

      switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
                if (comps.size() != 3 || !comps.get(1).getKey().equals("rounds")) {
                    sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
                    return;
                }

                int roundId = Integer.parseInt(comps.get(1).getValue());
                if (roundId < 0 || roundId > game.getRounds().size()) {
                    sendString(t, 400, "<h1>404 Not Found</h1>Round not found");
                    return;
                }

                Round round = game.getRounds().get(roundId);
                orders = round.getOrders();

        if (comps.get(2).getValue() == null) {
          sendJSON(t, orders);
          return;
        }

        int orderId = Integer.parseInt(comps.get(2).getValue());
        if (orderId < 0 || orderId > orders.size()) {
          sendString(t, 404, "<h1>404 Not Found</h1>");
          return;
        }

        sendJSON(t, orders.get(orderId));
        return;
            case "POST":
        if (comps.get(1).getValue() != null) {
          sendString(t, 400, "<h1>400 Bad Request</h1>Superfluous argument");
          return;
        }

                orders = game.getActiveRound().getOrders();
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

    /** Methode zum Abarbeiten aller Anfragen bez&uuml; /players.
     * @param comps List<SimpleEntry<String, String>> - Aufgeteilter HTTP-Path
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
         * @throws IOException
         */
    public void handlePlayers(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
      if (comps.size() != 2 || !comps.get(0).getKey().equals("games")) {
        sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
        return;
      }

      int gameId = Integer.parseInt(comps.get(0).getValue());
      if (gameId < 0 || gameId > games.size()) {
        sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
        return;
      }

      Game game = games.get(gameId);

      game.updateRounds();
      List<Player> players = game.getPlayers();

      switch (t.getRequestMethod().toUpperCase()) {
            case "GET":
        if (comps.get(1).getValue() == null) {
          sendJSON(t, players);
          return;
        }

        int playerId = Integer.parseInt(comps.get(1).getValue());
        if (playerId < 0 || playerId > players.size()) {
          sendString(t, 404, "<h1>404 Not Found</h1>");
          return;
        }

        sendJSON(t, players.get(playerId));
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
          // TODO(Tharre): eliminate duplicates
          playerStocks.add(new Stock(items.get((int) (Math.random()*items.size()) + 1), 500));
          playerStocks.add(new Stock(items.get((int) (Math.random()*items.size()) + 1), 500));
          playerStocks.add(new Stock(items.get((int) (Math.random()*items.size()) + 1), 500));
          playerStocks.add(new Stock(items.get((int) (Math.random()*items.size()) + 1), 500));
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

    /** Methode zum Abarbeiten aller Anfragen bez&uuml; /games.
     * @param comps List<SimpleEntry<String, String>> - Aufgeteilter HTTP-Path
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
         * @throws IOException
         */
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

    /** Methode zum Abarbeiten aller Anfragen bez&uuml; /rounds.
     * @param comps List<SimpleEntry<String, String>> - Aufgeteilter HTTP-Path
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
         * @throws IOException
         */
    private void handleRounds(List<SimpleEntry<String, String>> comps, HttpExchange t) throws IOException {
      if (comps.size() != 2 || !comps.get(0).getKey().equals("games")) {
        sendString(t, 400, "<h1>400 Bad Request</h1>API entry non-existent");
        return;
      }

      int gameId = Integer.parseInt(comps.get(0).getValue());
      if (gameId < 0 || gameId > games.size()) {
        sendString(t, 400, "<h1>404 Not Found</h1>Game not found");
        return;
      }

      Game game = games.get(gameId);
      game.updateRounds();
      List<Round> rounds = game.getRounds();

      switch (t.getRequestMethod().toUpperCase()) {
        case "GET":
          if (comps.get(1).getValue() == null) {
            sendJSON(t, game.getActiveRound());
            return;
          }

          int roundId = Integer.parseInt(comps.get(1).getValue());
          if (roundId < 0 || roundId > rounds.size()) {
            sendString(t, 404, "<h1>404 Not Found</h1>");
            return;
          }

          sendJSON(t, rounds.get(roundId));
          return;
        default:
          sendString(t, 405, "<h1>405 Method Not Allowed</h1>");
      }
    }

    /** Methode zum Aufteilen eines HTTP-Paths.
     * @param s String - URL
         * @return list Aufgeteilter HTTP-Path
         */
    public static List<SimpleEntry<String, String>> parseURL(String s) {
      String[] comps = s.split("/");
      List<SimpleEntry<String, String>> list = new LinkedList<>();

      for (int i = 0; i < comps.length; i+=2) {
        if (i == comps.length-1)
          list.add(new SimpleEntry<>(comps[i], (String) null));
        else
          list.add(new SimpleEntry<>(comps[i], comps[i+1]));
      }

      return list;
    }

    /** Methode zum Abarbeiten von Anfragen.
     * @param t HttpExchange - Enth&auml;lt die Verbindung zum Anfragenden -
     * <a href="https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html">N&auml;hre Infos zu HttpExchange</a>
     * @throws IOException
         */
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
