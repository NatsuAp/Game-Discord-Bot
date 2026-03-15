package comandos;

import api.CurlRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class buscar {
    public static String comandoBuscar(String str){
        String botAns = "";

        try {
            String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?title=" + str);
            JsonArray jsonArray = JsonParser.parseString(request).getAsJsonArray();
            botAns += "Resultados:";
            botAns += "\n";
            for(JsonElement jsonElement : jsonArray){
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                botAns += jsonObject.get("external").getAsString();
                botAns += "\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return botAns;
    }


}
