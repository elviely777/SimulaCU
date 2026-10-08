// This runs early in the build lifecycle, before dependency resolution
// Force wagon-http provider (JDK HttpURLConnection) instead of wagon-httpclient (Apache HttpClient)
System.setProperty("maven.wagon.provider.https", "http")
System.setProperty("maven.wagon.provider.http", "http")

// Apache HttpClient properties (if wagon-httpclient is still used)
System.setProperty("org.apache.http.conn.ssl.allow-all-hostnames", "true")
System.setProperty("org.apache.http.conn.ssl.trust-all", "true")
System.setProperty("org.apache.http.conn.ssl.verifyhost", "false")

// Aether (Maven resolver) properties
System.setProperty("aether.connector.https.acceptAllCertificates", "true")
System.setProperty("aether.connector.https.verifyHostname", "false")
System.setProperty("aether.connector.http.acceptAllCertificates", "true")
System.setProperty("aether.connector.http.verifyHostname", "false")

// JDK HttpClient properties
System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true")

// Wagon properties
System.setProperty("maven.wagon.http.ssl.insecure", "true")
System.setProperty("maven.wagon.http.ssl.allowall", "true")
System.setProperty("maven.wagon.https.ssl.insecure", "true")
System.setProperty("maven.wagon.https.ssl.allowall", "true")

// Force JDK to use our trust store
System.setProperty("javax.net.ssl.trustStore", "D:/SimulaCU/SimulaCU/cacerts")
System.setProperty("javax.net.ssl.trustStorePassword", "changeit")

// Debug
println("=== SSL/Proxy Config Applied ===")
println("maven.wagon.provider.https = ${System.getProperty("maven.wagon.provider.https")}")
println("maven.wagon.provider.http = ${System.getProperty("maven.wagon.provider.http")}")
println("org.apache.http.conn.ssl.allow-all-hostnames = ${System.getProperty("org.apache.http.conn.ssl.allow-all-hostnames")}")
println("org.apache.http.conn.ssl.trust-all = ${System.getProperty("org.apache.http.conn.ssl.trust-all")}")
println("javax.net.ssl.trustStore = ${System.getProperty("javax.net.ssl.trustStore")}")

try {
    val trustAllCerts = arrayOf<javax.net.ssl.TrustManager>(object : javax.net.ssl.X509TrustManager {
        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? = null
        override fun checkClientTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
    })
    val sc = javax.net.ssl.SSLContext.getInstance("TLS")
    sc.init(null, trustAllCerts, java.security.SecureRandom())
    javax.net.ssl.SSLContext.setDefault(sc)
    javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
    println("[init.gradle.kts] Global trust-all SSLContext installed")
} catch (e: Exception) {
    e.printStackTrace()
}

// Also configure Apache HttpClient if available
try {
    val sslContext = javax.net.ssl.SSLContext.getInstance("TLS")
    val trustManager = object : javax.net.ssl.X509TrustManager {
        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? = null
        override fun checkClientTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<java.security.cert.X509Certificate>, authType: String) {}
    }
    sslContext.init(null, arrayOf(trustManager), java.security.SecureRandom())
    
    // Try to set Apache HttpClient SSL context
    val httpClientBuilderClass = Class.forName("org.apache.http.impl.client.HttpClientBuilder")
    val createMethod = httpClientBuilderClass.getMethod("create")
    val builder = createMethod.invoke(null)
    val setSSLContextMethod = builder.javaClass.getMethod("setSSLContext", javax.net.ssl.SSLContext::class.java)
    setSSLContextMethod.invoke(builder, sslContext)
    val setSSLHostnameVerifierMethod = builder.javaClass.getMethod("setSSLHostnameVerifier", Class.forName("org.apache.http.conn.ssl.HostnameVerifier"))
    val allowAllVerifier = Class.forName("org.apache.http.conn.ssl.NoopHostnameVerifier").getDeclaredConstructor().newInstance()
    setSSLHostnameVerifierMethod.invoke(builder, allowAllVerifier)
    println("[init.gradle.kts] Apache HttpClient configured with trust-all")
} catch (e: Exception) {
    println("[init.gradle.kts] Apache HttpClient config skipped: ${e.message}")
}
