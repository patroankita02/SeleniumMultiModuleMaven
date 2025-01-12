package factory;

import org.openqa.selenium.WebDriver;

public class PageFactory
{

    private static final ThreadLocal<Object> pageObjects = new ThreadLocal<>();

    public static <T> T get_page(WebDriver driver, Class<T> pageClass) {
        T pageInstance = org.openqa.selenium.support.PageFactory.initElements(driver, pageClass);
        pageObjects.set(pageInstance);
        return pageInstance;
    }

    public static <T> T getCurrentPage() {
        return (T) pageObjects.get();
    }

}
