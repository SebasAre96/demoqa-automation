@interactions
Feature: Interactions — Drag & Drop, Sortable, Selectable

  @smoke
  Scenario: Drag and Drop — arrastrar elemento al drop zone
    Given el usuario navega a la sección Droppable
    When arrastra el elemento al drop zone
    Then el drop zone debería mostrar "Dropped!"

  Scenario: Sortable — arrastrar primer elemento al final
    Given el usuario navega a la sección Sortable
    When registra el texto del primer elemento
    And arrastra el primer elemento al último lugar
    Then el primer elemento debería haber cambiado de posición

  Scenario: Selectable — seleccionar un elemento de la lista
    Given el usuario navega a la sección Selectable
    When hace clic en el primer elemento de la lista
    Then debería haber 1 elemento seleccionado

  Scenario: Selectable — seleccionar múltiples elementos con Ctrl
    Given el usuario navega a la sección Selectable
    When selecciona múltiples elementos con Ctrl
    Then debería haber más de 1 elemento seleccionado