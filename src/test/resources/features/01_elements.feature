@elements
Feature: Elements — Formularios e interacciones básicas

  @smoke
  Scenario: Completar el formulario Text Box y verificar output
    Given el usuario navega al Text Box de DemoQA
    When completa el formulario con nombre "Sebastian Arevalo" email "seba@qa.com" dirección "Corrientes AR"
    Then el output debería mostrar el nombre "Sebastian Arevalo"
    And el output debería mostrar el email "seba@qa.com"

  @smoke
  Scenario: Seleccionar checkbox Home y verificar resultado
    Given el usuario navega a la sección CheckBox
    When hace clic en el checkbox Home
    Then debería ver el resultado de selección visible

  Scenario: Seleccionar radio button Yes y verificar mensaje
    Given el usuario navega a la sección Radio Button
    When selecciona el radio button "Yes"
    Then debería ver el mensaje "Yes"

  Scenario: El radio button No debería estar deshabilitado
    Given el usuario navega a la sección Radio Button
    Then el radio button "No" debería estar deshabilitado

  @smoke
  Scenario: Agregar un nuevo registro en Web Tables
    Given el usuario navega a Web Tables
    When agrega un registro con nombre "Juan" apellido "Perez" email "juan@test.com" edad "30" salario "50000" departamento "QA"
    Then el registro debería aparecer en la tabla

  Scenario: Buscar un registro en Web Tables
    Given el usuario navega a Web Tables
    When busca el término "Cierra"
    Then la tabla debería mostrar el resultado filtrado

  @smoke
  Scenario: Double click muestra mensaje correcto
    Given el usuario navega a la sección Buttons
    When hace doble clic en el botón
    Then debería ver el mensaje de doble clic

  Scenario: Right click muestra mensaje correcto
    Given el usuario navega a la sección Buttons
    When hace clic derecho en el botón
    Then debería ver el mensaje de clic derecho