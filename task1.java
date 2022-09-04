import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

            String simbolsRemoval = sb.toString().replaceAll("[^\\p{L}\\p{N}]+", "");
            String lowerCase = simbolsRemoval.toLowerCase();

            TreeMap<Character, Integer> string = new TreeMap<>();

            for (int i = 0; i < lowerCase.length(); i++) {
                Character key = lowerCase.charAt(i);
                string.put(key, string.getOrDefault(key, 0) + 1);
            }

            System.out.println("Сколько раз выводится каждый символ: " + string);

            String str = String.valueOf(string);

            String firstLineChange = str.replaceAll("=[0-9]", "");
            String secondLineChange = firstLineChange.replaceAll("[^\\p{L}\\p{N}]+", "");

            int numberOfUniqueCharacters = 0;
            for (int i = 0; i < secondLineChange.length(); i++) {
                numberOfUniqueCharacters++;
            }

            int sumOfSymbols = 0;
            for (int i = 0; i < lowerCase.length(); i++) {
                sumOfSymbols++;
            }

            double x = sumOfSymbols;
            double y = numberOfUniqueCharacters;
            double average = x / y;

            System.out.println("Среднее значение частоты: " + average);

            int rounded = (int)Math.round(average);

            Set<Map.Entry<Character,Integer>> entrySet = string.entrySet();
            Object desiredObject = rounded;
            for (Map.Entry<Character,Integer> pair : entrySet) {
                if (desiredObject.equals(pair.getValue())) {
                    System.out.println("Символ: " + pair.getKey() + "(" + Character.codePointAt(new char[] {pair.getKey()},0) + ")");
                }
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (conection != null) {
                conection.disconnect();
            }
        }
    }
}
