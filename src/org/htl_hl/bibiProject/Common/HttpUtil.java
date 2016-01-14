package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/** Objekt vom Typ HttpUtil.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften von HttpUtil.
 */
public class HttpUtil {

    private static final String USER_AGENT = "JWSS Client/1.0";

    /** Methode zum Eintragen von am Server Daten per HTTP-POST.
     * @param ip String IP von Server
     * @param res String Angeforderte Daten
     * @param parameters String POST-Parameter
     * @param typeClass Class<T> Hilfsvariable
     * @return <T> Gibt angefordertes Objekt zurück
     * @throws IOException
     */
    public static <T> T sendPost(String ip, String res, String parameters, Class<T> typeClass) throws IOException {
        String url = ip + "/api/" + res;
        URL obj = new URL(url);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        if (con.getResponseCode() != 200)
            System.out.println("Error handling ... or something like that"); // TODO(Tharre): error handling

        return mapper.readValue(con.getInputStream(), typeClass);
    }

    /** Methode zum Abrufen von Daten per HTTP-GET.
     * @param ip String IP von Server
     * @param res String Angeforderte Daten
     * @param typeClass Class<T> Hilfsvariable
     * @return <T> Gibt angefordertes Objekt zurück
     * @throws IOException
     */
    public static <T> T sendGet(String ip, String res, Class<T> typeClass) throws IOException {
        String url = ip + "/api/" + res + "/";
        URL obj = new URL(url);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (con.getResponseCode() != 200)
            System.out.println("Error handling ... or something like that"); // TODO(Tharre): error handling

        return mapper.readValue(con.getInputStream(), typeClass);
    }
}
