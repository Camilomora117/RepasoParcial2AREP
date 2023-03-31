package org.example;

import static spark.Spark.get;
import static spark.Spark.port;

public class MathService1 {

    public static void main(String... args){
        port(getPort());

        get("/hello", (req,res) -> "Hello Math Service 1!");

        get("/mathService", (req, res) -> {
            String s = req.queryString().split("=")[1];
            return "{ \"operation\" : \"palindromo \", \n"+
                    " \"input\" : \"" + s + "\", \n" +
                    " \"output\" : \" " + isPalindromo(s) + " \"}";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }

    private static String isPalindromo(String input) {
        String invertida = new StringBuilder(input).reverse().toString();
        if (invertida.equals(input)) {
            return "Si es Palindromo";
        } else {
            return "No es Palindromo";
        }
    }
}
