@alerts-windows
Feature: Alerts, Frames y Windows — Manejo avanzado de Selenium

  @alerts @smoke
  Scenario: Aceptar un alert simple
    Given el usuario navega a la sección Alerts
    When dispara un alert simple
    Then el alert debería mostrar un mensaje
    And acepta el alert

  @alerts @smoke
  Scenario: Confirmar un confirm alert y verificar resultado
    Given el usuario navega a la sección Alerts
    When dispara un confirm alert
    And acepta el confirm alert
    Then el resultado debería mostrar "Ok"

  @alerts
  Scenario: Rechazar un confirm alert y verificar resultado
    Given el usuario navega a la sección Alerts
    When dispara un confirm alert
    And rechaza el confirm alert
    Then el resultado debería mostrar "Cancel"

  @alerts
  Scenario: Ingresar texto en un prompt alert
    Given el usuario navega a la sección Alerts
    When dispara un prompt alert y escribe "Sebastian QA"
    Then el resultado del prompt debería contener "Sebastian QA"

  @windows @smoke
  Scenario: Abrir nueva ventana y verificar contenido
    Given el usuario navega a Browser Windows
    When abre una nueva ventana
    Then la nueva ventana debería mostrar el heading correcto
    And cierra la nueva ventana y vuelve a la original

  @frames @smoke
  Scenario: Leer contenido dentro de un iframe
    Given el usuario navega a la sección Frames
    When accede al contenido del frame 1
    Then debería ver el texto del frame 1
    And accede al contenido del frame 2
    Then debería ver el texto del frame 2

  @modals @smoke
  Scenario: Abrir y cerrar el modal pequeño
    Given el usuario navega a Modal Dialogs
    When abre el modal pequeño
    Then el modal debería estar visible con el título correcto
    And cierra el modal pequeño

  @modals
  Scenario: Abrir el modal grande y verificar contenido
    Given el usuario navega a Modal Dialogs
    When abre el modal grande
    Then el modal grande debería estar visible
    And cierra el modal grande