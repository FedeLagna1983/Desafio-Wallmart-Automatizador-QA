package walmart.context;

public class TestContext {

    private final PageManager pages = new PageManager();
    private String searchTerm;
    private String expectedProductName;
    private Integer expectedCartItemCount;

    public PageManager pages() {
        return pages;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getExpectedProductName() {
        return expectedProductName;
    }

    public void setExpectedProductName(String expectedProductName) {
        this.expectedProductName = expectedProductName;
    }

    public Integer getExpectedCartItemCount() {
        return expectedCartItemCount;
    }

    public void setExpectedCartItemCount(Integer expectedCartItemCount) {
        this.expectedCartItemCount = expectedCartItemCount;
    }
}
