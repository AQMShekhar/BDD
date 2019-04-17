@Important
Feature: HMS Registration Feature
Scenario Outline:HMS Registration Test Scenario
Given user already on Login Page
When title login page is HMS
Then user enter "<username>" and "<password>"
Then user clicks registration link
#Then close the browser

Examples:
| username |password|
| user1   | user1 |

@Regression
Scenario Outline:HMS Permanent Registration
Given user already on Login Page
When title login page is HMS
Then user enter "<username>" and "<password>"
Then user clicks registration link
Then fill the registration form "<firstname>" and "<lastname>" and "<dob>" and "<age>" and "<panNo>" and "<address1>" and "<mobileno>" and "<zipcode>"
#Then close the browser

Examples:
| username|password| firstname|lastname |dob |age |panNo |address1|mobileno|zipcode|
| user1   | user1 | Rahul    | singh |04-02-2009 |25|APGPT4906H|raheza plaza ghatkopar|8801320960|400806|
