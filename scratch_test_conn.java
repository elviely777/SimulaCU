import java.net.URL;
import java.net.HttpURLConnection;

public class scratch_test_conn {
    public static void main(String[] args) {
        String[] versions = {"8.5.0", "8.3.2", "8.2.0", "8.1.1", "8.1.0"};
        for (String v : versions) {
            try {
                URL url = new URL("http://maven.aliyun.com/repository/google/com/android/tools/build/gradle/" + v + "/gradle-" + v + ".pom");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.connect();
                System.out.println("Version " + v + " response code: " + conn.getResponseCode());
                conn.disconnect();
            } catch (Exception e) {
                System.out.println("Version " + v + " failed: " + e.getMessage());
            }
        }
    }
}
