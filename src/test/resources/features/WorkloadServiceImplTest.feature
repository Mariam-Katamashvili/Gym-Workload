Feature: Workload management

  Scenario: Add workload for a trainer
    Given the database contains a trainer with username "john_doe" and the following yearly summaries:
      | year | month    | workAmount |
      | 2023 | JANUARY  | 120        |
    When the workload is added with the following details:
      | username | date       | duration | actionType |
      | john_doe | 2023-01-15 | 60       | ADD        |
    Then the trainer's monthly summary for JANUARY 2023 should be updated to 180

  Scenario: Delete workload for a trainer
    Given the database contains a trainer with username "john_doe" and the following yearly summaries:
      | year | month    | workAmount |
      | 2023 | JANUARY  | 120        |
    When the workload is deleted with the following details:
      | username | date       | duration | actionType |
      | john_doe | 2023-01-15 | 60       | DELETE     |
    Then the trainer's monthly summary for JANUARY 2023 should be updated to 60

  Scenario: Add workload for a non-existent trainer
    Given the database does not contain a trainer with username "new_trainer"
    When the workload is added with the following details:
      | username   | date       | duration | actionType |
      | new_trainer | 2023-01-15 | 60       | ADD        |
    Then a new trainer should be created with username "new_trainer" and the monthly summary for JANUARY 2023 should be 60