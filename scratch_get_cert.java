import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.SecureRandom;
import java.net.URL;
import java.io.FileOutputStream;

public class scratch_get_cert {
    public static void main(String[] args) throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        String[] hosts = {"https://dl.google.com", "https://repo.maven.apache.org", "https://www.google.com"};
        for (String host : hosts) {
            System.out.println("Checking " + host);
            try {
                URL url = new URL(host);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.connect();
                
                java.security.cert.Certificate[] certs = conn.getServerCertificates();
                int i = 0;
                for (java.security.cert.Certificate cert : certs) {
                    X509Certificate x509 = (X509Certificate) cert;
                    System.out.println("  Cert " + i + ": " + x509.getSubjectDN());
                    System.out.println("  Issuer: " + x509.getIssuerDN());
                    byte[] bytes = cert.getEncoded();
                    String filename = host.replaceAll("[^a-zA-Z0-9]", "_") + "_" + (i++) + ".cer";
                    FileOutputStream os = new FileOutputStream(filename);
                    os.write(bytes);
                    os.close();
                }
            } catch (Exception e) {
                System.out.println("  Failed: " + e.getMessage());
            }
        }
    }
}
