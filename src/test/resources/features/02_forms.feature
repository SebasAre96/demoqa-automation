@forms
Feature: Forms — Practice Form completo

  @smoke @e2e
  Scenario: Completar el Practice Form y verificar modal de confirmación
    Given el usuario navega al Practice Form
    When completa el formulario con los datos básicos
      | firstName | lastName | email       | mobile     |
      | Sebastian | Arevalo  | seba@qa.com | 1234567890 |
    Then debería ver el modal de confirmación "Thanks for submitting the form"
    And el modal debería contener el nombre "Sebastian Arevalo"

  @negative
  Scenario: Enviar formulario sin datos requeridos muestra errores
    Given el usuario navega al Practice Form
    When intenta enviar el formulario sin completarlo
    Then el formulario no debería enviarse

  Scenario: Seleccionar género femenino y hobby lectura
    Given el usuario navega al Practice Form
    When selecciona género femenino y hobby lectura
    And completa los datos mínimos y envía
    Then el modal debería contener "Female"
    And el modal debería contener "Reading"