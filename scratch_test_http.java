import java.net.HttpURLConnection;
import java.net.URL;

public class scratch_test_http {
    public static void main(String[] args) {
        String[] urls = {
            "http://maven.aliyun.com/repository/google",
            "http://dl.google.com/dl/android/maven2/",
            "http://repo1.maven.org/maven2/"
        };
        for (String u : urls) {
            try {
                URL url = new URL(u);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                int code = conn.getResponseCode();
                System.out.println(u + " -> Response Code: " + code);
            } catch (Exception e) {
                System.out.println(u + " -> Error: " + e.getMessage());
            }
        }
    }
}
