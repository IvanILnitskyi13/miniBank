package external.client;

import com.squareup.okhttp.OkHttpClient;
import org.junit.Assert;
import org.testng.annotations.Test;
import java.time.LocalDate;

public class NbpApiClientTest {

    @Test
    public void getTableCForDay(){
        NbpApiClient nbpApiClient = new NbpApiClient(new OkHttpClient());
        Assert.assertNotNull(nbpApiClient.getNbpTableForDay(LocalDate.parse("2021-03-19")));
    }

}