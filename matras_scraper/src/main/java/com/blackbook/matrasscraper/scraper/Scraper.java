package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import com.blackbook.matrasscraper.htmlprovider.JsoupHTMLDocumentProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
public class Scraper {
    private final static String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53?p=";
    private final static String BOOKSTORE = "Matras";
    private final static int PAGES_TO_COLLECT = 3;
    private HTMLDocumentProvider htmlDocumentProvider;

    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
    }

    public static void main(String[] args) {
        Scraper scraper = new Scraper(new JsoupHTMLDocumentProvider());
        scraper.extractBookElements();
    }

    public void extractBookElements() {
        for (int i = 0; i < PAGES_TO_COLLECT; i++) {
            Document pageDoc = htmlDocumentProvider.provide(MATRAS_URL + i);
            extractBookElementsFromSinglePage(pageDoc);
        }
    }

    private void extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("s-item eqh");
        bookElements.forEach(bookElement -> {
            String bookUrl = extractBookUrl(bookElement);
            BookDocument bookDoc = new BookDocument(htmlDocumentProvider.provide(bookUrl));

            String title = bookDoc.extractBookTitle();
            Set<String> authors = bookDoc.extractBookAuthors();
            String genre = bookDoc.extractBookGenre();
            String price = bookDoc.extractBookPrice();
            String promoDetails = bookDoc.extractBookPromoDetails();

            System.out.println(title + " - " + authors + " - " + genre + " - " + price + " - " + promoDetails);
        });
    }

    private String extractBookUrl(Element bookElement) {
        return bookElement.getElementsByClass("right-side")
                .select("a")
                .first()
                .attr("href");
    }

}
