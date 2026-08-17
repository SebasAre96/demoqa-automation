package ar.net.brania.DemoQA.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * AlertsWindowsPage — módulo Alerts, Frame & Windows de DemoQA.
 * Cubre: Browser Windows, Alerts, Frames, Nested Frames, Modal Dialogs.
 * URL base: https://demoqa.com/alertsWindows
 * <p>
 * Este módulo demuestra manejo avanzado de Selenium:
 * switchTo().alert(), switchTo().frame(), switchTo().window()
 */
public class AlertsWindowsPage extends BasePage {

    // ─── Browser Windows ──────────────────────────────────────────
    private final By newTabBtn = By.id("tabButton");
    private final By newWindowBtn = By.id("windowButton");
    private final By newWindowMsgBtn = By.id("messageWindowButton");
    private final By sampleHeading = By.id("sampleHeading");

    // ─── Alerts ───────────────────────────────────────────────────
    private final By alertBtn = By.id("alertButton");
    private final By timerAlertBtn = By.id("timerAlertButton");
    private final By confirmBtn = By.id("confirmButton");
    private final By promptBtn = By.id("promtButton");
    private final By confirmResult = By.id("confirmResult");
    private final By promptResult = By.id("promptResult");

    // ─── Frames ───────────────────────────────────────────────────
    private final By frame1 = By.id("frame1");
    private final By frame2 = By.id("frame2");
    private final By frameHeading = By.id("sampleHeading");

    // ─── Modal Dialogs ────────────────────────────────────────────
    private final By smallModalBtn = By.id("showSmallModal");
    private final By largeModalBtn = By.id("showLargeModal");
    private final By smallModalTitle = By.id("example-modal-sizes-title-sm");
    private final By largeModalTitle = By.id("example-modal-sizes-title-lg");
    private final By closeSmallModal = By.id("closeSmallModal");
    private final By closeLargeModal = By.id("closeLargeModal");
    private final By modalBody = By.cssSelector(".modal-body");

    public AlertsWindowsPage(WebDriver driver) {
        super(driver);
    }

    // ─── Navegación ───────────────────────────────────────────────
    public void navigateToBrowserWindows() {
        navigateTo("https://demoqa.com/browser-windows");
    }

    public void navigateToAlerts() {
        navigateTo("https://demoqa.com/alerts");
    }

    public void navigateToFrames() {
        // No llamar removeAds() en esta página — elimina los frames legítimos
        driver.get("https://demoqa.com/frames");
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        // Solo eliminar los ads específicos, no todos los iframes
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "['adplus-anchor','adplus-interstitial','google_ads_iframe_']" +
                        ".forEach(id => { const el = document.getElementById(id); if(el) el.remove(); });"
        );
    }

    public void navigateToModalDialogs() {
        navigateTo("https://demoqa.com/modal-dialogs");
    }

    // ─── Browser Windows acciones ─────────────────────────────────
    public void openNewTab() {
        click(newTabBtn);
        switchToNewWindow();
    }

    public void openNewWindow() {
        click(newWindowBtn);
        switchToNewWindow();
    }

    public String getNewWindowHeading() {
        return getText(sampleHeading);
    }

    public void closeNewWindowAndReturn() {
        closeCurrentWindowAndSwitch();
    }

    // ─── Alerts acciones ──────────────────────────────────────────
    public void triggerSimpleAlert() {
        scrollToElement(alertBtn);
        clickWithJS(alertBtn);
    }

    public void triggerTimerAlert() {
        click(timerAlertBtn);
    }

    public void triggerConfirmAlert() {
        scrollToElement(confirmBtn);
        clickWithJS(confirmBtn);
    }

    public void triggerPromptAlert() {
        scrollToElement(promptBtn);
        clickWithJS(promptBtn);
    }

    public String getAlertTextContent() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
        return driver
                .switchTo()
                .alert()
                .getText();
    }

    public void acceptCurrentAlert() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
        driver.switchTo()
                .alert()
                .accept();
    }

    public void dismissCurrentAlert() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
        driver.switchTo()
                .alert()
                .dismiss();
    }

    public void typeInPromptAlert(String text) {
        typeInAlert(text);
    }

    public String getConfirmResult() {
        return getText(confirmResult);
    }

    public String getPromptResult() {
        return getText(promptResult);
    }

    // ─── Frames acciones ──────────────────────────────────────────
    public String getFrame1Text() {
        switchToIframe(frame1);
        String text =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(frameHeading)
                ).getText();
        switchToDefaultContent();
        return text;
    }

    public String getFrame2Text() {
        switchToIframe(frame2);
        String text =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(frameHeading)
                ).getText();
        switchToDefaultContent();
        return text;
    }

    // ─── Modal Dialogs acciones ───────────────────────────────────
    public void openSmallModal() {
        click(smallModalBtn);
    }

    public void openLargeModal() {
        click(largeModalBtn);
    }

    public boolean isSmallModalDisplayed() {
        return isVisible(smallModalTitle);
    }

    public boolean isLargeModalDisplayed() {
        return isVisible(largeModalTitle);
    }

    public String getSmallModalTitle() {
        return getText(smallModalTitle);
    }

    public String getModalBodyText() {
        return getText(modalBody);
    }

    public void closeSmallModal() {
        click(closeSmallModal);
    }

    public void closeLargeModal() {
        click(closeLargeModal);
    }
}
