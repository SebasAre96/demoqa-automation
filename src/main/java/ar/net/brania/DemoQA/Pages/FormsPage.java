package ar.net.brania.DemoQA.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * FormsPage — módulo Forms de DemoQA.
 * Cubre el Practice Form completo: inputs, radio buttons,
 * checkboxes, date picker, select, upload, y validación del modal de resultado.
 * URL: https://demoqa.com/automation-practice-form
 */
public class FormsPage extends BasePage {

    // ─── Campos del formulario ────────────────────────────────────
    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By mobileInput = By.id("userNumber");
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By subjectsInput = By.id("subjectsInput");
    private final By uploadFile = By.id("uploadPicture");
    private final By addressInput = By.id("currentAddress");
    private final By submitBtn = By.id("submit");

    // ─── Gender radios ────────────────────────────────────────────
    private final By maleRadio = By.cssSelector("[for='gender-radio-1']");
    private final By femaleRadio = By.cssSelector("[for='gender-radio-2']");
    private final By otherRadio = By.cssSelector("[for='gender-radio-3']");

    // ─── Hobbies checkboxes ───────────────────────────────────────
    private final By sportsCheckbox = By.cssSelector("[for='hobbies-checkbox-1']");
    private final By readingCheckbox = By.cssSelector("[for='hobbies-checkbox-2']");
    private final By musicCheckbox = By.cssSelector("[for='hobbies-checkbox-3']");

    // ─── State & City selects ─────────────────────────────────────
    private final By stateDropdown = By.id("state");
    private final By cityDropdown = By.id("city");
    private final By stateInput = By.cssSelector("#state input");
    private final By cityInput = By.cssSelector("#city input");

    // ─── Modal de confirmación ────────────────────────────────────
    private final By successModal = By.id("example-modal-sizes-title-lg");
    private final By modalTable = By.cssSelector(".table-responsive");
    private final By closeModalBtn = By.id("closeLargeModal");

    public FormsPage(WebDriver driver) {
        super(driver);
    }

    // ─── Navegación ───────────────────────────────────────────────
    public void navigateToPracticeForm() {
        navigateTo("https://demoqa.com/automation-practice-form");
    }

    // ─── Acciones ─────────────────────────────────────────────────
    public void enterFirstName(String name) {
        type(firstNameInput, name);
    }

    public void enterLastName(String name) {
        type(lastNameInput, name);
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterMobile(String mobile) {
        type(mobileInput, mobile);
    }

    public void enterAddress(String address) {
        type(addressInput, address);
    }

    public void selectGenderMale() {
        removeAds();
        clickWithJS(maleRadio); // siempre JS para los radios de gender
    }

    public void selectGenderFemale() {
        removeAds();
        clickWithJS(femaleRadio);
    }

    public void selectGenderOther() {
        click(otherRadio);
    }

    public void selectSportsHobby() {
        click(sportsCheckbox);
    }

    public void selectReadingHobby() {
        click(readingCheckbox);
    }

    public void selectMusicHobby() {
        click(musicCheckbox);
    }

    public void enterDateOfBirth(String date) {
        // Limpiar el campo y escribir la fecha directamente
        driver.findElement(dateOfBirthInput).clear();
        type(dateOfBirthInput, date);
        driver.findElement(dateOfBirthInput)
                .sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void enterSubject(String subject) {
        type(subjectsInput, subject);
        driver.findElement(subjectsInput)
                .sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void uploadPicture(String filePath) {
        uploadFile(uploadFile, filePath);
    }

    public void selectState(String state) {
        click(stateDropdown);
        type(stateInput, state);
        driver.findElement(stateInput)
                .sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void selectCity(String city) {
        click(cityDropdown);
        type(cityInput, city);
        driver.findElement(cityInput)
                .sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void submitForm() {
        scrollToElement(submitBtn);
        clickWithJS(submitBtn);
    }

    // ─── Flujo completo ───────────────────────────────────────────
    public void fillCompleteForm(String firstName, String lastName,
                                 String email, String mobile) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectGenderMale();
        enterMobile(mobile);
        selectSportsHobby();
        enterAddress("123 Test Street, QA City");
        submitForm();
    }

    // ─── Verificaciones ───────────────────────────────────────────
    public boolean isSuccessModalDisplayed() {
        return isVisible(successModal);
    }

    public String getModalTitle() {
        return getText(successModal);
    }

    public String getModalContent() {
        return getText(modalTable);
    }

    public boolean modalContains(String text) {
        return getModalContent().contains(text);
    }

    public void closeModal() {
        click(closeModalBtn);
    }
}
