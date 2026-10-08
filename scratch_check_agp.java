import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class scratch_check_agp {
    public static void main(String[] args) {
        try {
            System.setProperty("http.proxyHost", "proxy.vcl.sld.cu");
            System.setProperty("http.proxyPort", "3128");
            URL url = new URL("http://maven.aliyun.com/repository/google/com/android/tools/build/gradle/maven-metadata.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
