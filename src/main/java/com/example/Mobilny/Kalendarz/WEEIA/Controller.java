package com.example.Mobilny.Kalendarz.WEEIA;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {
    private final static String FILE_NAME = "Calendar_weeia";

    @GetMapping("/calendar")
    public ResponseEntity getCalendar(@RequestParam int year, @RequestParam int month) throws IOException, ParseException {
        if (year <= 1900 || year >= 2100)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Year is incorrect");
        if (month <= 0 || month > 12)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Month is incorrect");

        String monthString = String.valueOf(month);
        if (month < 10)
            monthString = "0" + monthString;

        ICalendar iCalendar = new ICalendar();
        iCalendar.setExperimentalProperty("X-WR-CALNAME", "Kalendarz");

        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=" + year + "&miesiac=" + monthString;
        List<EventDay> eventDays = getCalendarFromWeeia(url);
        for (EventDay eventDay : eventDays) {
            VEvent vEvent = new VEvent();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + monthString + "-" + eventDay.day);
            vEvent.setDateStart(date);
            vEvent.setDateEnd(date);
            vEvent.setSummary(eventDay.description);
            iCalendar.addEvent(vEvent);
        }
        File file = new File(FILE_NAME + "_" + year + "_" + monthString + ".ics");
        Biweekly.write(iCalendar).go(file);
        Path path = Paths.get(FILE_NAME + "_" + year + "_" + monthString + ".ics");
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
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
