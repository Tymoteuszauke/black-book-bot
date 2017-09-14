package com.blackbook.googlecrawler.processor;

import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.paginator.impl.DefaultPaginator;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import lombok.Data;

import java.util.List;

/**
 * @author Siarhei Shauchenka at 07.09.17
 */
@Data
public class ResultModel {
    private final List<BookDiscountData> bookData;
    private final Paginator paginator;

    public ResultModel(List<BookDiscountData> bookData, Paginator paginator) {
        this.bookData = bookData;
        this.paginator = paginator;
    }

    public ResultModel(List<BookDiscountData> bookData) {
        this.bookData = bookData;
        this.paginator = new DefaultPaginator();
    }
}
