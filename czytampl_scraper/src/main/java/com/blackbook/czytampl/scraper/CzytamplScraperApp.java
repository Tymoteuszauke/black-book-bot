package com.blackbook.czytampl.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CzytamplScraperApp {

    private final String startPage = "http://czytam.pl/tania-ksiazka.html";

    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("http://czytam.pl/tania-ksiazka.html").get();
//        Elements elements = doc.select(".product");
//
//        Integer maxPage = Integer.valueOf(doc.select(".pagination").select(".show-for-medium-up").next().first().text());
//        System.out.println(maxPage);
//
//        String pageAddress = "http://czytam.pl/tania-ksiazka,%d.html";
//        List<String> promotionPages = new ArrayList<>();
//        IntStream.range(1,43).forEach(pageId -> promotionPages.add(String.format(pageAddress, pageId)));
//
//        promotionPages.forEach(System.out::println);

    }
}
