import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class scratch_check_repo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://repo.umcc.cu/repository/maven-public/org/jetbrains/kotlin/kotlin-stdlib/maven-metadata.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
