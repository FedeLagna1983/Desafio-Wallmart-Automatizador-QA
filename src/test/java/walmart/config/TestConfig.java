package walmart.config;

public final class TestConfig {

    private static final String DEFAULT_BASE_URL = "https://opencart.abstracta.us";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final String DEFAULT_HEADLESS = "false";

    private TestConfig() {
    }

    public static String getBaseUrl() {
        String baseUrl = System.getProperty("baseUrl", DEFAULT_BASE_URL).trim();

        if (baseUrl.endsWith("/")) {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }

        return baseUrl;
    }

    public static String getBrowser() {
        return System.getProperty("browser", DEFAULT_BROWSER).trim().toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", DEFAULT_HEADLESS));
    }
}
