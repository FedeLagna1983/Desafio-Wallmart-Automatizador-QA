package walmart.utils;

import walmart.model.CheckoutData;

public class CheckoutCsvReader {

    private static final String CHECKOUT_DATA_FILE = "testdata/checkoutData.csv";

    public static CheckoutData getCheckoutData(String scenarioKey) {
        return CsvSupport.findFirstByKey(
                CHECKOUT_DATA_FILE,
                scenarioKey,
                0,
                "Checkout scenario",
                (values, rowNumber) -> new CheckoutData(
                        CsvSupport.requireColumn(values, 0, "scenario", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 1, "firstName", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 2, "lastName", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 3, "email", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 4, "telephone", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 5, "address1", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 6, "address2", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 7, "city", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 8, "postCode", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 9, "country", CHECKOUT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 10, "region", CHECKOUT_DATA_FILE, rowNumber)
                )
        );
    }
}
