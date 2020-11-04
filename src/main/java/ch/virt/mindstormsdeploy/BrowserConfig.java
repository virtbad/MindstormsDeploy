package ch.virt.mindstormsdeploy;

/**
 * @author VirtCode
 * @version 1.0
 */
public class BrowserConfig {
    private Browser browser = Browser.FIREFOX;
    private String browserExecutable = null;
    private String webEngineDriver = "Add engine Driver here.";
    private boolean headless = true;

    public Browser getBrowser() {
        return browser;
    }

    public String getBrowserExecutable() {
        return browserExecutable;
    }

    public String getWebEngineDriver() {
        return webEngineDriver;
    }

    public boolean isHeadless() {
        return headless;
    }
}
