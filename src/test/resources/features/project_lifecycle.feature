Feature: Todo.ly project lifecycle

  @api @project_lifecycle
  Scenario: Create, update, and delete a project successfully
    Given a new Todo.ly user is registered
    And the user obtains an authentication token
    When the user creates a project named "Initial project"
    And the user updates the project name to "Updated project"
    And the user deletes the project
    Then the project reflects the expected changes during its lifecycle
    And the API confirms the project was successfully deleted
