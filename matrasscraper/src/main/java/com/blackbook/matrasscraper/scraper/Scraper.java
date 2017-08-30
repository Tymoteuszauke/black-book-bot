package com.blackbook.matrasscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Patka on 2017-08-30.
 */
public class Scraper {
    private static String matrasUrl = "http://www.matras.pl/ksiazki/promocje,k,53?p=2";

    public static void main(String[] args) throws IOException {

        Document document = Jsoup.connect(matrasUrl).get();
        Elements bookElements = document.getElementsByClass("s-item eqh");
        bookElements.forEach(bookElement -> {
            String title = bookElement.getElementsByClass("item-title").text();
            String author = bookElement.getElementsByClass("item-author").text();
            String price = bookElement.getElementsByClass("item-price").text()
                    .replaceAll("[\\s+a-żA-Ż]","")
                    .replaceAll(",", ".");
            String promoDetails = bookElement.getElementsByClass("line discount-line").text()
                    .replaceAll("[\\s+a-żA-Ż]","");

            Elements links = bookElement.getElementsByClass("right-side");
            Element link = links.select("a").first();
            String url = link.attr("href");
                    System.out.println(url);
            try {
                Document doc = Jsoup.connect(url).get();
                System.out.println(doc.getElementsByClass("m-list").text());
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println(title + " - " + author + " - " + price + " - " + promoDetails);
        });
    }
}
