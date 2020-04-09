package id.h_ion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ReadGitHubRepos {

    public static void main(String[] args) {
        getUserRepos("h-ion");
    }

    public static void getUserRepos(String user) {
        String url = "https://api.github.com/users/" + user + "/repos";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JsonElement jElement = new JsonParser().parse(json);
            JsonArray jArray = jElement.getAsJsonArray();

            for (int i = 0; i < jArray.size(); i++) {
                JsonObject jObject = (JsonObject) jArray.get(i);
                String fullName = jObject.get("full_name").toString();
                fullName = fullName.substring(1, fullName.length() - 1);
                System.out.println(fullName);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
