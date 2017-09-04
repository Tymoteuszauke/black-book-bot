package com.blackbook.taniaksiazkascraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class Tmp {

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\Workspace\\black-book-bot\\taniaksiazkascraper\\src\\test\\resources\\book.html");
        Document parse = Jsoup.parse(file, "UTF-8");
        System.out.println(parse.select(".product-image").select("a"));
    }
}
