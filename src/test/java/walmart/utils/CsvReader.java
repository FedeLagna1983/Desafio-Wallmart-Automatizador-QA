package walmart.utils;

import walmart.model.ProductData;

public class CsvReader {

    private static final String PRODUCT_DATA_FILE = "testdata/products.csv";

    public static ProductData getProductData(String productKey) {
        return CsvSupport.findFirstByKey(
                PRODUCT_DATA_FILE,
                productKey,
                0,
                "Product",
                (values, rowNumber) -> new ProductData(
                        CsvSupport.requireColumn(values, 0, "searchTerm", PRODUCT_DATA_FILE, rowNumber),
                        CsvSupport.requireColumn(values, 1, "productName", PRODUCT_DATA_FILE, rowNumber)
                )
        );
    }
}
