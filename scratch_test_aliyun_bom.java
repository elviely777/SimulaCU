import java.net.HttpURLConnection;
import java.net.URL;

public class scratch_test_aliyun_bom {
    public static void main(String[] args) {
        String u = "http://maven.aliyun.com/repository/public/androidx/compose/compose-bom/2024.10.00/compose-bom-2024.10.00.pom";
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
