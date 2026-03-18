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

            if(jsonArray.size()>5){
                    botAns += "Mostrando los primeros 5 Resultados:";
                    botAns += "\n";
            }
            int i = 0;
            for(JsonElement jsonElement : jsonArray){
                if(i>4){
                    break;
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                botAns += jsonObject.get("external").getAsString();
                botAns += "\n";
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return botAns;
    }


}
