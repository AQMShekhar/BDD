@Important
Feature: HMS Login Feature
@Smoke
Scenario: HMS Login Test Scenario
Given user already on Login Page
When title login page HMS
Then user enters username and password

| username |password|
| user1   | user1 |
Then user clicks login button

@Smoke
Scenario: HMS Registration Tests Scenario
Given user already on Login Page
When title login page HMS
Then user enters username and password
| username |password|
| user1   | user1 |
Then user clicks login button
Then user clicks registration links

