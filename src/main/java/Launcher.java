import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Launcher {

    public static final String requestTemplate = "https://api.telegram.org/bot";
    public static final String myId = "220259888";

    public static void main(String[] args) throws IOException {
        String token = args[0];

        CloseableHttpClient client = HttpClients.createDefault();
        try {
            getMeRequest(token, client);
            getUpdates(token, client);
        } finally {
            client.close();
        }
    }

    private static void getMeRequest(String token, CloseableHttpClient client) throws IOException {
        HttpGet httpget = new HttpGet(requestTemplate + token + "/getMe");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response = client.execute(httpget);
        System.out.println(readResponse(response));
    }

    private static String readResponse(CloseableHttpResponse response) throws IOException {
        try {
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();

            // If the response does not enclose an entity, there is no need
            // to bother about connection release
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    return IOUtils.toString(instream, Charset.defaultCharset());
                    // do something useful with the response
                } catch (IOException ex) {
                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    throw ex;
                } finally {
                    // Closing the input stream will trigger connection release
                    instream.close();
                }
            }
        } finally {
            response.close();
        }
        return null;
    }

    private static void getUpdates(String token, CloseableHttpClient client) throws IOException {
        HttpGet httpget = new HttpGet(requestTemplate + token + "/getUpdates");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response = client.execute(httpget);
        System.out.println(readResponse(response));
    }
}
