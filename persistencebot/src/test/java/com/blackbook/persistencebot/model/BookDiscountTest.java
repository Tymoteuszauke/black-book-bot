package com.blackbook.persistencebot.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookDiscountTest {

    @DataProvider
    public Object[][] equalsAndHashCodeTestDataProvider() {
        return new Object[][]{
                {"-25%", 25.99},
                {null, 25.99}
        };
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testEquals(String details, double price) throws Exception {
        // Given
        BookDiscount discount1 = new BookDiscount();
        discount1.setBookDiscountDetails(details);
        discount1.setPrice(price);

        BookDiscount discount2 = new BookDiscount();
        discount2.setBookDiscountDetails(details);
        discount2.setPrice(price);

        // Then
        assertTrue(discount1.equals(discount2));
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testHashCode(String details, Double price) throws Exception {
        // Given
        BookDiscount discount1 = new BookDiscount();
        discount1.setBookDiscountDetails(details);
        discount1.setPrice(price);

        BookDiscount discount2 = new BookDiscount();
        discount2.setBookDiscountDetails(details);
        discount2.setPrice(price);

        // Then
        assertEquals(discount1.hashCode(), discount2.hashCode());
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testToString(String details, Double price) throws Exception {
        // Given
        BookDiscount discount1 = new BookDiscount();
        discount1.setBookDiscountDetails(details);
        discount1.setPrice(price);

        BookDiscount discount2 = new BookDiscount();
        discount2.setBookDiscountDetails(details);
        discount2.setPrice(price);

        // Then
        assertEquals(discount1.toString(), discount2.toString());
    }
}