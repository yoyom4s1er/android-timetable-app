package com.example.timetableapp.service.parser;

import com.example.timetableapp.service.HtmlService;
import com.example.timetableapp.util.WeekState;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class TimeTableParser {

    private final String timetableUrl;

    private final HtmlService htmlService;

    private final static String prefix = "https://www.miit.ru";


    public TimeTableParser(String TimetableUrl,
                           HtmlService htmlService) {
        this.timetableUrl = TimetableUrl;
        this.htmlService = htmlService;
    }

    private Document parsePage(String url) {
        try {
            return getOrUploadHtml(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String[] location = url.split("/");
        String fileName = location[location.length - 1];
        Optional<HtmlFile> htmlFile = htmlService.getHtmlFile(fileName);

        if (htmlFile.isPresent()) {
            if (htmlFile.get().getContent() != null) {
                return Jsoup.parse(htmlFile.get().getContent());
            }
        }*/

        return null;
    }

    private String getUrlPageTimetableByGroup(String group) {
        Document document = parsePage(timetableUrl);
        Element ref = document.select("a:contains(" + group + ")").first();
        return "https://web.archive.org/web/20221129113728/" + prefix + ref.attr("href");
    }

    public Element getTimeTableByGroup(String group, WeekState weekState) {
        Document page = parsePage(getUrlPageTimetableByGroup(group));
        Element table = null;
        if (weekState.equals(WeekState.WEEK_ONE)) {
            table = page.getElementById("week-1");
        }
        else {
            table = page.getElementById("week-2");
        }

        return table;
    }

    public boolean findGroup(String group) {
        Document document = parsePage(timetableUrl);
        return document.text().contains(group);
    }

    private Document getOrUploadHtml(String url) throws IOException {
        String[] location = url.split("/");
        String fileName = location[location.length - 1];

        /*Optional<HtmlFile> htmlFile = htmlService.getHtmlFile(fileName);
        if (htmlFile.isPresent()) {
            if (htmlFile.get().getContent() != null) {
                if (Math.abs(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).getDayOfMonth() - htmlFile.get().getLastUpdateDate()) < 1) {
                    return Jsoup.parse(htmlFile.get().getContent());
                }
            }
        }*/
        Document document = Jsoup.connect(url).timeout(30 * 1000).get();

        //log.info("Html parsed from url: {}", document.location());

        location = document.location().split("/");
        fileName = location[location.length - 1];

        //htmlService.saveHtmlFile(fileName, document);

        return document;
    }
}
