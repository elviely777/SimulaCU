import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.io.*;

public class scratch_save_cert {
    public static void main(String[] args) throws Exception {
        String host = "plugins.gradle.org";
        int port = 443;
        
        TrustManager[] trustAll = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
                    X509Certificate cert = certs[0];
                    try {
                        byte[] encoded = cert.getEncoded();
                        FileOutputStream fos = new FileOutputStream("proxy_cert.cer");
                        fos.write(encoded);
                        fos.close();
                        System.out.println("Certificate saved to proxy_cert.cer");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAll, new java.security.SecureRandom());
        SSLSocketFactory ssf = sc.getSocketFactory();

        try (SSLSocket socket = (SSLSocket) ssf.createSocket(host, port)) {
            socket.startHandshake();
            System.out.println("Handshake successful!");
        } catch (Exception e) {
            System.out.println("Handshake error: " + e.getMessage());
        }
    }
}
