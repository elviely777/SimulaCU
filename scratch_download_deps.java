import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.X509Certificate;

public class scratch_download_deps {
    public static void main(String[] args) throws Exception {
        TrustManager[] trustAll = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAll, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        String testUrl = "https://maven.google.com/androidx/compose/compose-bom/2024.10.01/compose-bom-2024.10.01.pom";
        downloadFile(testUrl, new File("test.pom"));
    }

    private static void downloadFile(String urlStr, File outFile) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            int code = conn.getResponseCode();
            System.out.println(urlStr + " -> Code: " + code);
            if (code == 200) {
                outFile.getParentFile().mkdirs();
                try (InputStream in = conn.getInputStream();
                     FileOutputStream out = new FileOutputStream(outFile)) {
                    byte[] buffer = new byte[4096];
                    int n;
                    while ((n = in.read(buffer)) != -1) {
                        out.write(buffer, 0, n);
                    }
                }
                System.out.println("Downloaded successfully!");
            }
        } catch (Exception e) {
            System.out.println("Failed " + urlStr + ": " + e.getMessage());
        }
    }
}
