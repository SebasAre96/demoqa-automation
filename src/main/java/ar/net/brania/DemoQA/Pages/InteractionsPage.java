package ar.net.brania.DemoQA.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * InteractionsPage — módulo Interactions de DemoQA.
 * Cubre: Sortable, Selectable, Resizable, Droppable, Drag & Drop.
 * URL base: https://demoqa.com/interaction
 * <p>
 * Este módulo demuestra el uso de Actions API de Selenium:
 * dragAndDrop, dragAndDropBy, moveToElement, clickAndHold.
 */
public class InteractionsPage extends BasePage {

    // ─── Sortable ─────────────────────────────────────────────────
    private final By sortableItems = By.cssSelector("#demo-tabpane-list .list-group-item");
    private final By firstSortable = By.cssSelector("#demo-tabpane-list .list-group-item:first-child");
    private final By lastSortable = By.cssSelector("#demo-tabpane-list .list-group-item:last-child");

    // ─── Selectable ───────────────────────────────────────────────
    private final By selectableItems = By.cssSelector("#verticalListContainer li");
    private final By selectedItem = By.cssSelector("#verticalListContainer li.active");

    // ─── Droppable ────────────────────────────────────────────────
    private final By draggable = By.id("draggable");
    private final By droppable = By.id("droppable");
    private final By droppableText = By.cssSelector("#droppable p");

    // ─── Drag & Drop (simple) ─────────────────────────────────────
    private final By dragBox = By.id("dragBox");

    // ─── Resizable ────────────────────────────────────────────────
    private final By resizableBox = By.id("resizableBoxWithRestriction");
    private final By resizeHandle = By.cssSelector("#resizableBoxWithRestriction .react-resizable-handle");

    public InteractionsPage(WebDriver driver) {
        super(driver);
    }

    // ─── Navegación ───────────────────────────────────────────────
    public void navigateToSortable() {
        navigateTo("https://demoqa.com/sortable");
    }

    public void navigateToSelectable() {
        navigateTo("https://demoqa.com/selectable");
    }

    public void navigateToDroppable() {
        navigateTo("https://demoqa.com/droppable");
    }

    public void navigateToDragabilly() {
        navigateTo("https://demoqa.com/dragabilly");
    }

    public void navigateToResizable() {
        navigateTo("https://demoqa.com/resizable");
    }

    // ─── Sortable acciones ────────────────────────────────────────
    public String getFirstSortableText() {
        return getText(firstSortable);
    }

    public String getLastSortableText() {
        return getText(lastSortable);
    }

    public void dragFirstItemToLast() {
        dragAndDropWithJS(firstSortable, lastSortable);
    }

    public int getSortableItemCount() {
        return getElements(sortableItems).size();
    }

    // ─── Selectable acciones ──────────────────────────────────────
    public void clickFirstSelectableItem() {
        waitForVisible(selectableItems);
        WebElement first =
                getElements(selectableItems).get(0);
        first.click();
    }

    public void selectMultipleItems() {
        waitForVisible(selectableItems);
        java.util.List<WebElement> items =
                getElements(selectableItems);
        if (items.size() < 3) {
            throw new RuntimeException("No hay suficientes elementos para seleccionar");
        }
        actions.keyDown(org.openqa.selenium.Keys.CONTROL)
                .click(items.get(0))
                .click(items.get(1))
                .click(items.get(2))
                .keyUp(org.openqa.selenium.Keys.CONTROL)
                .perform();
    }

    public int getSelectedItemCount() {
        return getElements(selectedItem).size();
    }

    // ─── Droppable acciones ───────────────────────────────────────
    public void dragElementToDropZone() {
        dragAndDropWithJS(draggable, droppable);
    }

    public String getDroppableText() {
        return getText(droppableText);
    }

    public boolean isDropSuccessful() {
        return getDroppableText().equalsIgnoreCase("Dropped!");
    }

    // ─── Drag Box ─────────────────────────────────────────────────
    public void dragBoxByOffset(int x, int y) {
        dragAndDropByOffset(dragBox, x, y);
    }

    // ─── Resizable acciones ───────────────────────────────────────
    public void resizeBox(int xOffset, int yOffset) {
        WebElement handle = driver.findElement(resizeHandle);
        actions.clickAndHold(handle)
                .moveByOffset(xOffset, yOffset)
                .release()
                .perform();
    }

    public String getResizableBoxSize() {
        WebElement box = driver.findElement(resizableBox);
        return "width: " + box.getCssValue("width") +
                " height: " + box.getCssValue("height");
    }
}
