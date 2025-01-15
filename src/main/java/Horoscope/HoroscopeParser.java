package Horoscope;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class HoroscopeParser {

    public Map<String, String> getHoroscopes(String jsonResponse) {
        JsonObject response = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = response.getAsJsonObject("response").getAsJsonArray("items");

        // Поиск актуального поста(последнего по дате)
        JsonObject latestPost = null;
        long latestDate = 0;

        for(JsonElement item : items) {
            JsonObject post = item.getAsJsonObject();
            long postDate = post.get("date").getAsLong();

            if(postDate > latestDate) {
                latestDate = postDate;
                latestPost = post;
            }
        }

        // если пост найден,извлекаем его гороскопы:
        if(latestPost != null) {
            String text = latestPost.get("text").getAsString();
            return extractHoroscopes(text);
        }
        // если пост не найден:
        return new HashMap<>();
    }

    private static Map<String, String> extractHoroscopes(String text) {
        Map<String, String> horoscopes = new HashMap<>();

        String[] lines = text.split("\n");

        // Обработка каждой строки:
        for (String line : lines) {
            if (line.trim().startsWith("♈️") || line.trim().startsWith("♉️") ||
                    line.trim().startsWith("♊️") || line.trim().startsWith("♋️") ||
                    line.trim().startsWith("♌️") || line.trim().startsWith("♍️") ||
                    line.trim().startsWith("♎️") || line.trim().startsWith("♏️") ||
                    line.trim().startsWith("♐️") || line.trim().startsWith("♑️") ||
                    line.trim().startsWith("♒️") || line.trim().startsWith("♓️")){
                // Извлекаемо знак зодиака и текст гороскопа
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String sign = parts[0].trim().substring(2);
                    String horoscope = parts[1].trim();
                    horoscopes.put(sign, horoscope);
                }

            }
        }

        return horoscopes;
    }



















}
