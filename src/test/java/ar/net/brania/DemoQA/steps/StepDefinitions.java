package ar.net.brania.DemoQA.steps;


import ar.net.brania.DemoQA.Pages.AlertsWindowsPage;
import ar.net.brania.DemoQA.Pages.ElementsPage;
import ar.net.brania.DemoQA.Pages.FormsPage;
import ar.net.brania.DemoQA.Pages.InteractionsPage;
import ar.net.brania.DemoQA.driver.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class StepDefinitions {


    private ElementsPage elementsPage;
    private FormsPage formsPage;
    private AlertsWindowsPage alertsPage;
    private InteractionsPage interactionsPage;

    private String firstSortableText;

    private ElementsPage elements() {
        if (elementsPage == null)
            elementsPage = new ElementsPage(DriverManager.getDriver());
        return elementsPage;
    }

    private FormsPage forms() {
        if (formsPage == null)
            formsPage = new FormsPage(DriverManager.getDriver());
        return formsPage;
    }

    private AlertsWindowsPage alerts() {
        if (alertsPage == null)
            alertsPage = new AlertsWindowsPage(DriverManager.getDriver());
        return alertsPage;
    }

    private InteractionsPage interactions() {
        if (interactionsPage == null)
            interactionsPage = new InteractionsPage(DriverManager.getDriver());
        return interactionsPage;
    }

    // ─── ELEMENTS STEPS ───────────────────────────────────────────

    @Given("el usuario navega al Text Box de DemoQA")
    public void navigateToTextBox() {
        elements().navigateToTextBox();
    }

    @When("completa el formulario con nombre {string} email {string} dirección {string}")
    public void fillTextBox(String name, String email, String address) {
        elements().fillTextBoxForm(name, email, address, address);
    }

    @Then("el output debería mostrar el nombre {string}")
    public void verifyOutputName(String name) {
        Assert.assertTrue(elements().getOutputName().contains(name),
                "El output no contiene el nombre: " + name);
    }

    @Then("el output debería mostrar el email {string}")
    public void verifyOutputEmail(String email) {
        Assert.assertTrue(elements().getOutputEmail().contains(email),
                "El output no contiene el email: " + email);
    }

    @Given("el usuario navega a la sección CheckBox")
    public void navigateToCheckBox() {
        elements().navigateToCheckBox();
    }

    @When("hace clic en el checkbox Home")
    public void clickHomeCheckbox() {
        elements().clickHomeCheckbox();
    }

    @When("accede al contenido del frame {int}")
    public void accessFrame(int frameNum) {
        // Este step solo representa la acción.
    }

    @Then("debería ver el resultado de selección visible")
    public void verifyCheckboxResult() {
        Assert.assertTrue(elements().isCheckboxResultDisplayed(),
                "No se muestra el resultado del checkbox");
    }

    @Given("el usuario navega a la sección Radio Button")
    public void navigateToRadioButton() {
        elements().navigateToRadioButton();
    }

    @When("selecciona el radio button {string}")
    public void selectRadioButton(String option) {
        switch (option.toLowerCase()) {
            case "yes":
                elements().selectYesRadio();
                break;
            case "impressive":
                elements().selectImpressiveRadio();
                break;
            default:
                throw new IllegalArgumentException("Radio button inválido: " + option);
        }
    }

    @Then("debería ver el mensaje {string}")
    public void verifyRadioMessage(String message) {
        Assert.assertTrue(elements().getRadioResult().contains(message),
                "El resultado del radio no contiene: " + message);
    }

    @Then("el radio button {string} debería estar deshabilitado")
    public void verifyRadioDisabled(String option) {
        if (option.equals("No")) {
            Assert.assertTrue(elements().isNoRadioDisabled(),
                    "El radio button No no está deshabilitado");
        }
    }

    @Given("el usuario navega a Web Tables")
    public void navigateToWebTables() {
        elements().navigateToWebTables();
    }

    @When("agrega un registro con nombre {string} apellido {string} email {string} edad {string} salario {string} departamento {string}")
    public void addRecord(String fn, String ln, String email, String age, String salary, String dept) {
        elements().addNewRecord(fn, ln, email, age, salary, dept);
    }

    @Then("el registro debería aparecer en la tabla")
    public void verifyRecordAdded() {
        Assert.assertTrue(elements().isRecordPresent("Juan"),
                "No se encontró el registro agregado");
    }

    @When("busca el término {string}")
    public void searchRecord(String term) {
        elements().searchRecord(term);
    }

    @Then("la tabla debería mostrar el resultado filtrado")
    public void verifyFilteredTable() {
        Assert.assertTrue(elements().isRecordPresent("Cierra"),
                "No se encontró el resultado filtrado");
    }

    @Given("el usuario navega a la sección Buttons")
    public void navigateToButtons() {
        elements().navigateToButtons();
    }

    @When("hace doble clic en el botón")
    public void doubleClick() {
        elements().doubleClickButton();
    }

    @Then("debería ver el mensaje de doble clic")
    public void verifyDoubleClick() {
        Assert.assertTrue(elements().getDoubleClickMessage().contains("double click"),
                "No se muestra el mensaje de doble clic");
    }

    @When("hace clic derecho en el botón")
    public void rightClick() {
        elements().rightClickButton();
    }

    @Then("debería ver el mensaje de clic derecho")
    public void verifyRightClick() {
        Assert.assertTrue(elements().getRightClickMessage().contains("right click"),
                "No se muestra el mensaje de clic derecho");
    }

    // ─── FORMS STEPS ──────────────────────────────────────────────

    @Given("el usuario navega al Practice Form")
    public void navigateToPracticeForm() {
        forms().navigateToPracticeForm();
    }

    @When("completa el formulario con los datos básicos")
    public void fillFormWithDataTable(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> data = rows.get(0);
        forms().fillCompleteForm(
                data.get("firstName"),
                data.get("lastName"),
                data.get("email"),
                data.get("mobile")
        );
    }

    @Then("debería ver el modal de confirmación {string}")
    public void verifySuccessModal(String title) {
        Assert.assertTrue(forms().isSuccessModalDisplayed(),
                "No se muestra el modal de confirmación");
        Assert.assertTrue(forms().getModalTitle().contains(title),
                "El título del modal no contiene: " + title);
    }

    @Then("el modal debería contener el nombre {string}")
    public void verifyModalContainsName(String name) {
        Assert.assertTrue(forms().modalContains(name),
                "El modal no contiene: " + name);
    }

    @Then("el modal debería contener {string}")
    public void verifyModalContains(String text) {
        Assert.assertTrue(forms().modalContains(text),
                "El modal no contiene: " + text);
    }

    @When("intenta enviar el formulario sin completarlo")
    public void submitEmptyForm() {
        forms().submitForm();
    }

    @Then("el formulario no debería enviarse")
    public void verifyFormNotSubmitted() {
        Assert.assertFalse(forms().isSuccessModalDisplayed(),
                "El formulario se envió sin datos requeridos");
    }

    @When("selecciona género femenino y hobby lectura")
    public void selectFemaleAndReading() {
        forms().selectGenderFemale();
        forms().selectReadingHobby();
    }

    @When("completa los datos mínimos y envía")
    public void fillMinimumAndSubmit() {
        forms().enterFirstName("Ana");
        forms().enterLastName("Test");
        forms().enterMobile("9876543210");
        forms().submitForm();
    }

    // ─── ALERTS & WINDOWS STEPS ───────────────────────────────────

    @Given("el usuario navega a la sección Alerts")
    public void navigateToAlerts() {
        alerts().navigateToAlerts();
    }

    @When("dispara un alert simple")
    public void triggerSimpleAlert() {
        alerts().triggerSimpleAlert();
    }

    @Then("el alert debería mostrar un mensaje")
    public void verifyAlertHasMessage() {
        String text = alerts().getAlertTextContent();
        Assert.assertNotNull(text, "El alert no tiene texto");
        Assert.assertFalse(text.isEmpty(), "El texto del alert está vacío");
    }

    @Then("acepta el alert")
    public void acceptTheAlert() {
        alerts().acceptCurrentAlert();
    }

    @When("dispara un confirm alert")
    public void triggerConfirmAlert() {
        alerts().triggerConfirmAlert();
    }

    @When("acepta el confirm alert")
    public void acceptConfirmAlert() {
        alerts().acceptCurrentAlert();
    }

    @When("rechaza el confirm alert")
    public void dismissConfirmAlert() {
        alerts().dismissCurrentAlert();
    }

    @Then("el resultado debería mostrar {string}")
    public void verifyConfirmResult(String expected) {
        Assert.assertTrue(alerts().getConfirmResult().contains(expected),
                "El resultado no contiene: " + expected);
    }

    @When("dispara un prompt alert y escribe {string}")
    public void triggerPromptAndType(String text) {
        alerts().triggerPromptAlert();
        alerts().typeInPromptAlert(text);
    }

    @Then("el resultado del prompt debería contener {string}")
    public void verifyPromptResult(String text) {
        Assert.assertTrue(alerts().getPromptResult().contains(text),
                "El resultado del prompt no contiene: " + text);
    }

    @Given("el usuario navega a Browser Windows")
    public void navigateToBrowserWindows() {
        alerts().navigateToBrowserWindows();
    }

    @When("abre una nueva ventana")
    public void openNewWindow() {
        alerts().openNewWindow();
    }

    @Then("la nueva ventana debería mostrar el heading correcto")
    public void verifyNewWindowHeading() {
        Assert.assertFalse(alerts().getNewWindowHeading().isEmpty(),
                "La nueva ventana no tiene heading");
    }

    @Then("cierra la nueva ventana y vuelve a la original")
    public void closeNewWindow() {
        alerts().closeNewWindowAndReturn();
    }

    @Given("el usuario navega a la sección Frames")
    public void navigateToFrames() {
        alerts().navigateToFrames();
    }

    @Then("debería ver el texto del frame {int}")
    public void verifyFrameText(int frameNum) {
        String text = frameNum == 1 ? alerts().getFrame1Text() : alerts().getFrame2Text();
        Assert.assertFalse(text.isEmpty(), "El frame " + frameNum + " no tiene texto");
    }

    @Given("el usuario navega a Modal Dialogs")
    public void navigateToModals() {
        alerts().navigateToModalDialogs();
    }

    @When("abre el modal pequeño")
    public void openSmallModal() {
        alerts().openSmallModal();
    }

    @Then("el modal debería estar visible con el título correcto")
    public void verifySmallModal() {
        Assert.assertTrue(alerts().isSmallModalDisplayed(),
                "El modal pequeño no está visible");
    }

    @Then("cierra el modal pequeño")
    public void closeSmallModal() {
        alerts().closeSmallModal();
    }

    @When("abre el modal grande")
    public void openLargeModal() {
        alerts().openLargeModal();
    }

    @Then("el modal grande debería estar visible")
    public void verifyLargeModal() {
        Assert.assertTrue(alerts().isLargeModalDisplayed(),
                "El modal grande no está visible");
    }

    @Then("cierra el modal grande")
    public void closeLargeModal() {
        alerts().closeLargeModal();
    }

    // ─── INTERACTIONS STEPS ───────────────────────────────────────

    @Given("el usuario navega a la sección Droppable")
    public void navigateToDroppable() {
        interactions().navigateToDroppable();
    }

    @When("arrastra el elemento al drop zone")
    public void dragToDropZone() {
        interactions().dragElementToDropZone();
    }

    @Then("el drop zone debería mostrar {string}")
    public void verifyDropped(String expected) {
        Assert.assertEquals(interactions().getDroppableText(), expected,
                "El texto del drop zone no es el esperado");
    }

    @Given("el usuario navega a la sección Sortable")
    public void navigateToSortable() {
        interactions().navigateToSortable();
    }

    @When("registra el texto del primer elemento")
    public void recordFirstElement() {
        firstSortableText = interactions().getFirstSortableText();
    }

    @When("arrastra el primer elemento al último lugar")
    public void dragFirstToLast() {
        interactions().dragFirstItemToLast();
    }

    @Then("el primer elemento debería haber cambiado de posición")
    public void verifyOrderChanged() {
        String newFirst = interactions().getFirstSortableText();
        Assert.assertNotEquals(newFirst, firstSortableText,
                "El orden no cambió después del drag");
    }

    @Given("el usuario navega a la sección Selectable")
    public void navigateToSelectable() {
        interactions().navigateToSelectable();
    }

    @When("hace clic en el primer elemento de la lista")
    public void clickFirstSelectable() {
        interactions().clickFirstSelectableItem();
    }

    @Then("debería haber {int} elemento seleccionado")
    public void verifySelectedCount(int count) {
        Assert.assertEquals(interactions().getSelectedItemCount(), count,
                "La cantidad de elementos seleccionados no es la esperada");
    }

    @When("selecciona múltiples elementos con Ctrl")
    public void selectMultipleWithCtrl() {
        interactions().selectMultipleItems();
    }

    @Then("debería haber más de {int} elemento seleccionado")
    public void verifyMoreThanOneSelected(int count) {
        Assert.assertTrue(interactions().getSelectedItemCount() > count,
                "No hay más de " + count + " elementos seleccionados");
    }
}
