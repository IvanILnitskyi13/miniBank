package external.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.time.LocalDate;

public class NbpApiClient {
    private final OkHttpClient okHttpClient;

    public NbpApiClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public NbpTable getNbpTableForDay(LocalDate date) {
        Request rq = new Request.Builder()
                .url("http://api.nbp.pl/api/exchangerates/tables/C/"
                        + date.toString() + "?format=json")
                .build();
        try {
            Response response = okHttpClient.newCall(rq).execute();
            if (response.isSuccessful()) {
                String tableJson = response.body().string();
                String json = tableJson.substring(1, tableJson.length()-1);

                return getGson().fromJson(json, NbpTable.class);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
}
