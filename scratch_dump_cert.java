import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.io.*;

public class scratch_dump_cert {
    public static void main(String[] args) throws Exception {
        String host = "maven.aliyun.com";
        int port = 443;
        
        TrustManager[] trustAll = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    System.out.println("Captured " + certs.length + " certificates from " + host);
                    for (int i = 0; i < certs.length; i++) {
                        System.out.println("Cert [" + i + "]: " + certs[i].getSubjectDN());
                        System.out.println("Issuer [" + i + "]: " + certs[i].getIssuerDN());
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
