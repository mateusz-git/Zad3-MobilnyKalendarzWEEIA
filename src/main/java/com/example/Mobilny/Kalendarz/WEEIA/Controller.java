package com.example.Mobilny.Kalendarz.WEEIA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RestController
public class Controller {
    @GetMapping("/calendar")
    public void getCalendar(@RequestParam int year, @RequestParam int month) throws IOException {
        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        getCalendarFromWeeia(url);

    }

    private void getCalendarFromWeeia(String urlWeeia) throws IOException {
        URL url = new URL(urlWeeia);
        URLConnection connection = url.openConnection();
        StringBuilder fromWebsite = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            fromWebsite.append(line);
        }
        String htmlContent = fromWebsite.toString();
        Document document = Jsoup.parse(htmlContent);
        System.out.println(document);
    }
}
