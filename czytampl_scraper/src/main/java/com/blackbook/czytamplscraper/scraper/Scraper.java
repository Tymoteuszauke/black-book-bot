package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Scraper {

    private static final String startPage = "http://czytam.pl/tania-ksiazka.html";
    private final static int BOOKSTORE_ID = 2;

//    Elements elements = doc.select(".product");

    public static void main(String[] args) throws IOException {
        WebReader reader = new WebReader();
        Preparer preparer = new Preparer();
        List<String> promotionPages = preparer.getPromotionPages(reader, startPage);

        // new code to extract
        String promotionUrl = promotionPages.get(5);
        Document promotionPage = reader.getDocumentFromWebPage(promotionUrl);
        Elements elements = promotionPage.select(".product");
        Element book = elements.get(22);
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

//        Document detailsPage = reader.getDocumentFromWebPage(bookUrl);
        //System.out.println(detailsPage.html());

//        Elements subtitle = detailsPage.getElementById("panel4-2").child(2).select("strong");
//        System.out.println("Subtitle: " + subtitle.html());

    }
}
