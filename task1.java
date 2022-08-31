import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

public class task1 {
    public static void main(String[] args) {
        String query = "http://numbersapi.com/random/trivia";

        HttpURLConnection conection = null;
        try {
            conection = (HttpURLConnection) new URL(query).openConnection();

            conection.setRequestMethod("GET");
            conection.setUseCaches(false);
            conection.setConnectTimeout(250);
            conection.setReadTimeout(250);

            conection.connect();

            StringBuilder sb = new StringBuilder();

            if (HttpURLConnection.HTTP_OK == conection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                System.out.println(sb);
            } else {
                System.out.println("fail: " + conection.getResponseCode() + ", " + conection.getInputStream());
            }

            String simbolsRemoval = sb.toString().replaceAll("\\p{Punct}", "");
            String removingSpaces = simbolsRemoval.replaceAll("\\s", "");
            String lowerCase = removingSpaces.toLowerCase();

            TreeMap<Character, Integer> string = new TreeMap<>();

            for (int i = 0; i < lowerCase.length(); i++) {
                Character key = lowerCase.charAt(i);
                string.put(key, string.getOrDefault(key, 0) + 1);

            }
            System.out.println("Сколько раз выводится каждый символ: " + string);

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (conection != null) {
                conection.disconnect();
            }
        }
    }
}
