package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    private static final String startPage = "http://czytam.pl/tania-ksiazka.html";

//    Elements elements = doc.select(".product");

    public static void main(String[] args) throws IOException {
        WebReader reader = new WebReader();
        Preparer preparer = new Preparer();
        List<String> promotionPages = preparer.getPromotionPages(reader, startPage);
        List<Document> promotionPageDocuments = new ArrayList<>();

//        promotionPages.forEach(promotionPageUrl -> promotionPageDocuments.add(reader.getDocumentFromWebPage(promotionPageUrl)));

        // new code to extract
        String promotionUrl = promotionPages.get(1);
        Document promotionPage = reader.getDocumentFromWebPage(promotionUrl);
        Elements elements = promotionPage.select(".product");
        Element book = elements.get(13);
        System.out.println(book.html());
        System.out.println("\n-------------------------------------------\n");

        Elements title = book.select(".product-title");
        System.out.println("Title: " + title.text());

        Elements author = book.select(".product-author");
        System.out.println("Author: " + author.text());

        List<String> prices = book.select(".product-price").select("strong").eachText();
        System.out.println("Old price: " + prices.get(0));
        System.out.println("New price: " + prices.get(1));

        Elements promoDetails = book.select(".image-container").select("a[href]");
        System.out.println("Promo details: " + promoDetails.text().replaceAll("\\s+", ""));
        System.out.println("Cover url: " + promoDetails.select("img").attr("src"));

        String bookUrl = "http://czytam.pl" + book.select(".image-container").select("a").attr("href");
        System.out.println("Book url: " + bookUrl);

        Document detailsPage = reader.getDocumentFromWebPage(bookUrl);

        Elements genre = detailsPage.select(".level-2").select(".active");
        String bookGenre = genre.html();
        System.out.println(bookGenre.equals("") ? "Unknown" : bookGenre);

        org.jsoup.nodes.Element details = detailsPage.getElementById("panel4-2");
        String subtitle = details.html().contains("Podtytuł") ? details.child(2).select("strong").text() : "-";
        System.out.println("Subtitle:" + subtitle);
    }
}
