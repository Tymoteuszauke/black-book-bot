package com.blackbook.taniaksiazkascraper.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import static com.blackbook.taniaksiazkascraper.scraper.Scraper.BOOKSTORE_ID;

@Slf4j
@Component
public class PromoDetailsReader {

    private static final String BOOKSTORE_URL = "http://www.taniaksiazka.pl";
    private String DETAILS_URL;

    BookDiscountData readDiscountDataProperties(Connector connector, Element book) {
        DETAILS_URL = BOOKSTORE_URL + readBookDetailsPagePath(book);
        Document detailsPage = connector.getDocumentFromWebPage(DETAILS_URL);

        return BookDiscountData.builder()
                .bookDiscountDetails(readPromoDetails(detailsPage))
                .price(readPrice(detailsPage))
                .bookstoreId(BOOKSTORE_ID)
                .bookData(BookData.builder()
                        .title(readTitle(detailsPage))
                        .subtitle(readSubtitle(detailsPage))
                        .publisher(readBookPublisher(detailsPage))
                        .genre(readReadBookGenre(detailsPage))
                        .authors(readAuthors(detailsPage))
                        .bookPageUrl(DETAILS_URL)
                        .coverUrl(readCoverUrl(detailsPage))
                        .build())
                .build();
    }

    private String readCoverUrl(Document detailsPage) {
        return BOOKSTORE_URL + detailsPage.select(".gallery-new").select("img").attr("src");
    }

    private Double readPrice(Document detailsPage) {
        String price = detailsPage
                .select(".book-price-bg")
                .select("#p-ourprice")
                .text()
                .replaceAll("[a-żA-Ż]", "")
                .replaceAll(",", ".")
                .trim();
        return Double.valueOf(price);
    }

    private String readPromoDetails(Document detailsPage) {
        String promoDetails = detailsPage.select(".book-price-bg").select("#p-discount").text();
        return "-" + promoDetails.substring(promoDetails.indexOf("(") + 1, promoDetails.indexOf(")")).trim();
    }

    private String readAuthors(Document detailsPage) {
        return detailsPage.select(".author").select("a").text();
    }

    private String readReadBookGenre(Document detailsPage) {
        return detailsPage.getElementById("path-top").select(".active").text();
    }

    private String readBookDetailsPagePath(Element book) {
        return book.select("a").next().attr("href");
    }

    private String readTitle(Document detailsPage) {
        return detailsPage
                .select("a")
                .attr("data-name")
                .split("\\.", 2)
                [0];
    }

    private String readSubtitle(Document detailsPage) {
        String[] titltes = detailsPage
                .select("a")
                .attr("data-name")
                .split("\\.", 2);
        return getSubtitle(titltes);
    }

    private String getSubtitle(String[] titltes) {
        return titltes.length == 2 ? titltes[1].trim() : null;
    }

    private String readBookPublisher(Document detailsPage) {
        return detailsPage.select(".with-extra-name")
                .select("a")
                .text()
                .replace("Wydawnictwo", "");
    }
}
