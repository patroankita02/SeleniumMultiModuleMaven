package Utils;

import org.testng.ITestContext;
import org.testng.ITestListener;

public class Listener implements ITestListener {

    private static final ThreadLocal<String> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<String> tlYamlPath = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String browser = context.getCurrentXmlTest().getParameter("browser");
        String yamlPath = context.getCurrentXmlTest().getParameter("yamlPath");

        if (browser != null) {
            tlBrowser.set(browser);
            System.out.println("Browser is: " + Thread.currentThread().getName() + ": " + browser);
        } else {
            System.out.println("Browser parameter is undefined in testng.xml");
        }

        if (yamlPath != null) {
            tlYamlPath.set(yamlPath);
            System.out.println("YAML Path : " + Thread.currentThread().getName() + ": " + yamlPath);
        } else {
            System.out.println("YAML path parameter is undefined in testng.xml");
        }
    }

    public static String getBrowser() {
        String browser = tlBrowser.get();
        if (browser == null || browser.isEmpty()) {
            throw new IllegalStateException("Browser is empty for current thread"+ Thread.currentThread().getName());
        }
        return browser;
    }

    public static String getYamlPath() {
        String yamlPath = tlYamlPath.get();
        if (yamlPath == null || yamlPath.isEmpty()) {
            throw new IllegalStateException("YAML Path is empty for current thread"+ Thread.currentThread().getName());
        }
        return yamlPath;
    }

    @Override
    public void onFinish(ITestContext context) {
        tlBrowser.remove();
        tlYamlPath.remove();
    }
}

