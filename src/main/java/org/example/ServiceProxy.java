package org.example;

import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ServiceProxy {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://";

    public static void main(String... args) {
        port(getPort());

        staticFiles.location("/public");

        get("/prueba", (req, res) -> "Prueba exitosa");

        get("/hello", (req,res) -> {
            String s = req.queryString().split("=")[1];
            return "Hello Service Proxy! " + s;
        });

        get("/serviceProxy", (req, res) -> {
            String s = req.queryString().split("=")[1];
            return connectionMathervice(s);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 42000;
    }

    private static String connectionMathervice(String input) throws IOException {
        URL obj = new URL(getRoundRobin(input));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return response.toString();
    }

    private static String getRoundRobin(String input) {
        String[] ips = {"3.93.3.223:35001", "3.82.174.91:35002"};
        String ramdom = ips[new Random().nextInt(2)];
        return GET_URL + ramdom + "/mathService?value=" + input;
    }

}
