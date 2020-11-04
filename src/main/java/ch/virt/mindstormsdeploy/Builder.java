package ch.virt.mindstormsdeploy;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.indexeddb.model.Key;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * @author VirtCode
 * @version 1.0
 */
public class Builder {
    private final Config config;

    WebDriver driver;
    WebDriverWait wait;

    public Builder(Config config){
        this.config = config;
    }

    public boolean start(){
        driver = createDriver();

        if (driver == null) {
            System.out.println("    Failed to Create Driver");
            return false;
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            System.out.println("    Loading Website");
            driver.get(config.getBuildWebsite());

            System.out.println("    Creating new Project");
            List<WebElement> projects = wait.until(presenceOfAllElementsLocatedBy(By.className("carouselitem")));
            for (WebElement project : projects) {
                if ("New Project".equals(project.findElement(By.className("header")).getText())){
                    project.click();
                    break;
                }
            }

            System.out.println("    Opening JS View");
            WebElement js = wait.until(presenceOfElementLocated(By.className("javascript-menuitem")));
            Thread.sleep(5000);
            js.click();

            Thread.sleep(3000);

            System.out.println("    Naming Project");
            WebElement name = driver.findElement(By.id("fileNameInput2"));
            name.click();
            new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).sendKeys(config.getProjectName()).sendKeys(Keys.ENTER).perform();

        } catch (Exception e) {
            driver.quit();
            System.err.println("    Failed to Initialize Project");
            return false;
        }

        return true;
    }

    public boolean build(String content){
        try {

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);

            WebElement line = driver.findElement(By.className("view-line"));
            line.click();

            System.out.println("    Pasting Code");
            new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

            System.out.println("    Downloading Built File");
            List<WebElement> download = driver.findElements(By.className("button"));
            for (WebElement webElement : download) {
                if (webElement.getText().contains("Download")) webElement.click();
            }

            Thread.sleep(2000);
        }catch (Exception e){
            System.err.println("    Failed to build code");
            return false;
        }

        return true;
    }

    public WebDriver createDriver(){
        if (config.getBrowser().getBrowser() == Browser.FIREFOX){
            System.out.println("    Using Firefox Driver");

            System.setProperty("webdriver.gecko.driver", config.getBrowser().getWebEngineDriver());

            new File(config.getBuildFolder()).mkdir();

            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(config.getBrowser().isHeadless());
            options.setLogLevel(FirefoxDriverLogLevel.FATAL);
            options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");
            options.addPreference("browser.download.dir", new File(config.getBuildFolder()).getAbsolutePath());
            options.addPreference("browser.download.folderList", 2);
            if (config.getBrowser().getBrowserExecutable() != null) options.setBinary(config.getBrowser().getBrowserExecutable());

            return new FirefoxDriver(options);

        }else if (config.getBrowser().getBrowser() == Browser.CHROME){
            System.err.println("Chrome is not supported at the moment!");
            System.exit(0);
        }else {
            System.err.println("Please select a supported Browser");
            System.exit(0);
        }

        return null;
    }

    public void quit(){
        if (driver == null) return;
        driver.quit();
    }
}
