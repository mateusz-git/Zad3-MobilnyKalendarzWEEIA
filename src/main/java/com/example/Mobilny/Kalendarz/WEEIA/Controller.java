package com.example.Mobilny.Kalendarz.WEEIA;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @GetMapping("/calendar")
    public void getCalendar(@RequestParam int year, @RequestParam int month) throws IOException {
        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + month;
        System.out.println(getCalendarFromWeeia(url));

    }

    private List<EventDay> getCalendarFromWeeia(String urlWeeia) throws IOException {
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
        Elements activeElement = document.select("td.active");
        Elements events = activeElement.select("div.InnerBox");
        Elements days = activeElement.select("a.active");

        List<EventDay> eventDayList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            eventDayList.add(new EventDay(Integer.parseInt(days.get(i).text()), events.get(i).text()));
        }
        return eventDayList;
    }
}
