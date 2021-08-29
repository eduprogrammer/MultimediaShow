/*
 * Copyright 2021. Eduardo Programador
 * www.eduardoprogramador.com
 *
 * */

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Proposal {



    public Proposal() {

    }


    public boolean send(String name, String surname, String email, String phone, String msg) {
        try {

            System.out.println("date: " + new Date().toString());

            URL url = new URL("http://eduardoprogramador.com/php/chat.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8")
             + "&" + URLEncoder.encode("surname","UTF-8") + "=" + URLEncoder.encode(surname,"UTF-8")
             + "&" + URLEncoder.encode("message","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "__"
            + URLEncoder.encode(phone,"UTF-8") + "___" + URLEncoder.encode(msg,"UTF-8") + "___"
             + "&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(new Date().toString(),"UTF-8"));

            byte[] out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

            urlConnection.setFixedLengthStreamingMode(out.length);
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");

            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(out);
            outputStream.flush();

            return true;

        } catch (Exception ex) {

            return false;
        }
    }
}
