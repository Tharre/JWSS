package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static final String USER_AGENT = "JWSS Client/1.0";

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

    public static <T> T sendGet(String ip, String res, String parameters, Class<T> typeClass) throws IOException {
        String url = ip + "/api/" + res + "/" + parameters;
        URL obj = new URL(url);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (con.getResponseCode() != 200)
            System.out.println("Error handling ... or something like that"); // TODO(Tharre): error handling

        return mapper.readValue(con.getInputStream(), typeClass);
    }

    public static void main(String []args) throws IOException {
        Player p1 = HttpUtil.sendPost("127.0.0.1:8000", "players/", "name=JKartoffel", Player.class);
        Player p2 = HttpUtil.sendPost("127.0.0.1:8000", "players/", "name=JHamster", Player.class);
        Player p3 = HttpUtil.sendPost("127.0.0.1:8000", "players/", "name=JEmily", Player.class);
        Player p4 = HttpUtil.sendPost("127.0.0.1:8000", "players/", "name=JGatto", Player.class);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
    }
}
