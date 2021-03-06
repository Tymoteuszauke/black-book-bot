package com.blackbook.utils.core;

import com.blackbook.utils.model.creationmodel.BookDiscountData;

import java.util.List;
import java.util.function.Consumer;


/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface Collector {
    void start(Consumer<List<BookDiscountData>> supplier);
    int getId();
}
