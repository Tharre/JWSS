package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static <T> T sendPost(String res, String parameters, Class<T> typeClass) throws IOException {
        String url = "http://127.0.0.1:8000/api/" + res;
        URL obj = new URL(url);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if (responseCode != 200)
            System.out.println("Error handling ... or something like that"); // TODO(Tharre): error handling

        return mapper.readValue(con.getInputStream(), typeClass);
    }

    public static void main(String []args) throws IOException {
        Player p1 = HttpUtil.sendPost("players/", "name=JKartoffel", Player.class);
        Player p2 = HttpUtil.sendPost("players/", "name=JHamster", Player.class);
        Player p3 = HttpUtil.sendPost("players/", "name=JEmily", Player.class);
        Player p4 = HttpUtil.sendPost("players/", "name=JGatto", Player.class);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
    }
}