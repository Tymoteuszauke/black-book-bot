package com.blackbook.db.parser;

import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.db.parser.core.DataParser;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleParser implements DataParser<JSONObject> {

    @Override
    public BookCreationData parseBook(JSONObject objectToParse) {
        return null;
    }

    @Override
    public List<BookCreationData> parseBooks(JSONObject objectToParse) {
        return null;
    }
}
