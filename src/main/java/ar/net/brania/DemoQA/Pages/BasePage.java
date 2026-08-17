package ar.net.brania.DemoQA.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * BasePage — clase padre de todos los Page Objects.
 * Incluye métodos avanzados para DemoQA:
 * manejo de iframes, alerts, drag & drop, ventanas múltiples.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    // ─── Clicks ───────────────────────────────────────────────────
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void clickWithJS(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    protected void safeClick(By locator) {
        removeAds(); // limpia ads justo antes del click
        scrollToElement(locator);
        try {
            click(locator);
        } catch (Exception e) {
            clickWithJS(locator); // fallback con JS si sigue tapado
        }
    }

    // ─── Escritura ────────────────────────────────────────────────
    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    protected void typeWithTab(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
        el.sendKeys(Keys.TAB);
    }

    // ─── Lectura ──────────────────────────────────────────────────
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected String getValue(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute("value");
    }

    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    // ─── Visibilidad ──────────────────────────────────────────────
    protected boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected boolean isChecked(By locator) {
        return driver.findElement(locator).isSelected();
    }

    // ─── Selects ──────────────────────────────────────────────────
    protected void selectByVisibleText(By locator, String text) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)))
                .selectByVisibleText(text);
    }

    // ─── Alerts ───────────────────────────────────────────────────
    protected String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    protected void typeInAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
        driver.switchTo().alert().accept();
    }

    // ─── Iframes ──────────────────────────────────────────────────
    protected void switchToIframe(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ─── Ventanas múltiples ───────────────────────────────────────
    protected void switchToNewWindow() {
        String original = driver.getWindowHandle();
        // Espera hasta que haya más de 1 ventana abierta
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                return;
            }
        }
    }

    protected void closeCurrentWindowAndSwitch() {
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
    }

    // ─── Drag & Drop ──────────────────────────────────────────────
    protected void dragAndDrop(By source, By target) {
        WebElement from = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
        WebElement to = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
        actions.dragAndDrop(from, to).perform();
    }

    protected void dragAndDropByOffset(By source, int xOffset, int yOffset) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
        actions.dragAndDropBy(el, xOffset, yOffset).perform();
    }

    protected void dragAndDropWithJS(By source, By target) {
        WebElement from = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
        WebElement to = wait.until(ExpectedConditions.visibilityOfElementLocated(target));

        // Para Chrome 150+ con librerías de pointer events
        actions
                .moveToElement(from)
                .pause(Duration.ofMillis(300))
                .clickAndHold(from)
                .pause(Duration.ofMillis(500))
                .moveToElement(to, 1, 1)
                .pause(Duration.ofMillis(500))
                .release()
                .perform();
    }

    // ─── Hover ────────────────────────────────────────────────────
    protected void hoverOver(By locator) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.moveToElement(el).perform();
    }

    // ─── Scroll ───────────────────────────────────────────────────
    protected void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ─── Navegación ───────────────────────────────────────────────
    protected void navigateTo(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        removeAds();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ─── Upload ───────────────────────────────────────────────────
    protected void uploadFile(By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }

    // ─── Esperas ──────────────────────────────────────────────────
    protected void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForText(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ─── Helper ──────────────────────────────────────────────────
    protected void centerElement(By locator) {

        WebElement element =
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    // ─── Eliminar publicidad DemoQA ─────────────────────────────────
    protected void removeAds() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('iframe').forEach(e => e.remove());" +
                            "['adplus-anchor','adplus-interstitial']" +
                            ".forEach(id => {" +
                            " const el=document.getElementById(id);" +
                            " if(el) el.remove();" +
                            "});"
            );
        } catch (Exception ignored) {
        }
    }


}

