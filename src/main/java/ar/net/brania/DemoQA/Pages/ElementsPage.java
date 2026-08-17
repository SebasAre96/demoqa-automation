package ar.net.brania.DemoQA.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * ElementsPage — módulo Elements de DemoQA.
 * Cubre: Text Box, Checkboxes, Radio Buttons, Web Tables, Buttons, Links.
 * URL base: https://demoqa.com/elements
 */
public class ElementsPage extends BasePage {

    // ─── Text Box ─────────────────────────────────────────────────
    private final By fullNameInput = By.id("userName");
    private final By emailInput = By.id("userEmail");
    private final By currentAddress = By.id("currentAddress");
    private final By permanentAddress = By.id("permanentAddress");
    private final By submitButton = By.id("submit");
    private final By outputName = By.id("name");
    private final By outputEmail = By.id("email");

    // ─── Check Box ────────────────────────────────────────────────
    private final By homeCheckbox = By.cssSelector(".rc-tree-treenode:first-child .rc-tree-checkbox");
    private final By expandAllBtn = By.cssSelector(".rct-option-expand-all");
    private final By checkboxResult = By.id("result");

    // ─── Radio Button ─────────────────────────────────────────────
    private final By yesRadio = By.cssSelector("[for='yesRadio']");
    private final By impressiveRadio = By.cssSelector("[for='impressiveRadio']");
    private final By noRadio = By.cssSelector("[for='noRadio']");
    private final By radioResult = By.cssSelector(".mt-3");

    // ─── Web Tables ───────────────────────────────────────────────
    private final By addRecordBtn = By.id("addNewRecordButton");
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By ageField = By.id("age");
    private final By salaryField = By.id("salary");
    private final By departmentField = By.id("department");
    private final By submitForm = By.id("submit");
    private final By searchBox = By.id("searchBox");
    private final By tableRows = By.cssSelector("table tbody tr");
    private final By tableBody = By.cssSelector("table tbody");
    private final By deleteRowBtn = By.cssSelector("[title='Delete']");

    // ─── Buttons ──────────────────────────────────────────────────
    private final By doubleClickBtn = By.id("doubleClickBtn");
    private final By rightClickBtn = By.id("rightClickBtn");
    private final By dynamicClickBtn = By.cssSelector("#buttonsWrapper button:last-child");
    private final By doubleClickMsg = By.id("doubleClickMessage");
    private final By rightClickMsg = By.id("rightClickMessage");
    private final By dynamicClickMsg = By.id("dynamicClickMessage");

    public ElementsPage(WebDriver driver) {
        super(driver);
    }

    // ─── Text Box acciones ────────────────────────────────────────
    public void navigateToTextBox() {
        navigateTo("https://demoqa.com/text-box");
    }

    public void fillTextBoxForm(String name, String email, String current, String permanent) {
        type(fullNameInput, name);
        type(emailInput, email);
        type(currentAddress, current);
        type(permanentAddress, permanent);
        scrollToElement(submitButton);
        clickWithJS(submitButton);
    }

    public String getOutputName() {
        return getText(outputName);
    }

    public String getOutputEmail() {
        return getText(outputEmail);
    }

    // ─── Check Box acciones ───────────────────────────────────────
    public void navigateToCheckBox() {
        navigateTo("https://demoqa.com/checkbox");
    }


    public void clickHomeCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(homeCheckbox));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }

    public boolean isCheckboxResultDisplayed() {
        return isVisible(checkboxResult);
    }

    public String getCheckboxResult() {
        return getText(checkboxResult);
    }

    // ─── Radio Button acciones ────────────────────────────────────
    public void navigateToRadioButton() {
        navigateTo("https://demoqa.com/radio-button");
    }

    public void selectYesRadio() {
        click(yesRadio);
    }

    public void selectImpressiveRadio() {
        click(impressiveRadio);
    }

    public boolean isNoRadioDisabled() {
        return driver.findElement(By.id("noRadio")).getAttribute("disabled") != null;
    }

    public String getRadioResult() {
        return getText(radioResult);
    }

    public boolean isRecordPresent(String text) {
        try {
            // Scroll al body de la tabla para que todas las filas sean visibles
            WebElement tbody = wait.until(
                    ExpectedConditions.presenceOfElementLocated(tableBody)
            );
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", tbody
            );

            new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(20))
                    .until(driver ->
                            driver.findElements(tableRows)
                                    .stream()
                                    .anyMatch(row -> row.getText().trim().contains(text))
                    );
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ─── Web Tables acciones ──────────────────────────────────────
    public void navigateToWebTables() {
        navigateTo("https://demoqa.com/webtables");
    }

    public void addNewRecord(String firstName, String lastName, String email,
                             String age, String salary, String department) {
        click(addRecordBtn);
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(ageField, age);
        type(salaryField, salary);
        type(departmentField, department);
        click(submitForm);
        // Espera a que el modal cierre antes de verificar la tabla
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.id("submit")  // el botón submit del modal desaparece al cerrarse
        ));
    }

    public void searchRecord(String term) {
        type(searchBox, term);

    }

    public int getRowCount() {
        return (int) driver.findElements(tableRows).stream()
                .filter(r -> !r.getText().trim().isEmpty())
                .count();
    }

    public void deleteFirstRecord() {
        click(deleteRowBtn);
    }

    // ─── Buttons acciones ─────────────────────────────────────────
    public void navigateToButtons() {
        navigateTo("https://demoqa.com/buttons");
    }

    public void doubleClickButton() {
        removeAds();
        scrollToElement(doubleClickBtn);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(doubleClickBtn));
        // Doble click con JS — más confiable que Actions en Chrome moderno
        ((JavascriptExecutor) driver).executeScript(
                "var evt = new MouseEvent('dblclick', {bubbles: true, cancelable: true});" +
                        "arguments[0].dispatchEvent(evt);", btn
        );
    }

    public String getDoubleClickMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(doubleClickMsg)).getText();
    }

    public void rightClickButton() {
        removeAds();
        scrollToElement(rightClickBtn);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(rightClickBtn));

        // Dispara el evento contextmenu directamente — evita el menú nativo del browser
        ((JavascriptExecutor) driver).executeScript(
                "var evt = new MouseEvent('contextmenu', {bubbles: true, cancelable: true, button: 2});" +
                        "arguments[0].dispatchEvent(evt);", btn
        );
    }

    public void dynamicClick() {
        click(dynamicClickBtn);
    }

    public String getRightClickMessage() {
        return getText(rightClickMsg);
    }

    public String getDynamicClickMessage() {
        return getText(dynamicClickMsg);
    }
}
