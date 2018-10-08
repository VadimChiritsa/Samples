Narrative: I should be able to login and logout of my google mail account

Scenario: I should see my emails after loggining with my credentials

When I open google mail Home Page
When I enter login 'sann.poker@gmail.com' and password 'testpasswor'
Then Page title should contains 'sann.poker@gmail.com'

When I perform log out
Then Page title should not contains 'sann.poker@gmail.com'