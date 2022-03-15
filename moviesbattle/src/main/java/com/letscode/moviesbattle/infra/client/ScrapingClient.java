package com.letscode.moviesbattle.infra.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class ScrapingClient {
    private static final String URL = "https://www.imdb.com/chart/top/";

    public Set<Pair<String, String>> list() {
        var moviesTitleById = new HashSet<Pair<String, String>>();
        try {
            Jsoup.connect(URL)
                .sslSocketFactory(socketFactory())
                .timeout(10000)
                .ignoreHttpErrors(true)
                .get()
                .select("td.titleColumn")
                .parallelStream()
                .forEach(film -> moviesTitleById.add(Pair.of(getMovieTitle(film), getMovieId(film))));
        } catch (IOException e) {
            log.error("error while loading database", e);
        }
        return moviesTitleById;
    }

    private String getMovieTitle(Element element) {
        return element.getElementsByTag("a").text();
    }

    private String getMovieId(Element element) {
        return element.getElementsByTag("a").attr("href").split("/")[2];
    }

    private SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory result = sslContext.getSocketFactory();

            return result;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }
}
