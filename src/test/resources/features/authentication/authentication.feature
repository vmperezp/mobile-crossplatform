@Authentication
Feature: Authentication on the Lab Negocios application
  As a QA automation engineer
  I want to validate the login of the application on Android and iOS
  So that the same scenarios cover both platforms with a single code base

  Background:
    Given the user is on the login screen

  @CrossPlatform @Smoke
  Scenario: A registered user logs in successfully
    When the user logs in with the following credentials
      | document-type | username   | password  |
      | NIT           | 1032456789 | Clave1234 |
    Then the user should see the greeting "Hola, 1032456789"

  @CrossPlatform @Regression
  Scenario Outline: The application rejects an invalid authentication
    When the user logs in with the following credentials
      | document-type   | username   | password   |
      | <document-type> | <username> | <password> |
    Then the user should see the login error message "<message>"

    Examples:
      | document-type        | username   | password    | message                    |
      | NIT                  | 1032456789 | Clave0000   | Los datos no coinciden     |
      | NIT                  | 9999999999 | Clave1234   | Los datos no coinciden     |
      | Cedula de ciudadania | 1032456700 | Clave1234   | Tu usuario esta bloqueado  |

  # El boton fisico atras solo existe en Android: el IosRunner no toma este escenario.
  @AndroidOnly
  Scenario: The user hides the keyboard with the device back button
    When the user taps the document number field
    And the user presses the device back button
    Then the login form should still be visible
