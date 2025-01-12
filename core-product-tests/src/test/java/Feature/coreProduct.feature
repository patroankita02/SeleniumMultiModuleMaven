Feature: Test Core Product Feature

#  @regression @coreproduct
#  Scenario: Store Men's Jacket Price in a txt file
#    Given User lands on CP home page
#    And User mousehover on shop icon
#    And User clicks on Mens Option
#    Then Verify that user navigates to a new tab
#    When User selects jacket from all department section
#    And Store each Jacket Price,Title and Top Seller message message into a text file

  @regression
  Scenario: Validates videos which were uploaded 3 days prior
    Given User lands on CP home page
    And User mouse hovers menu item
    And User clicks on News and Features
    Then User verifies that the videos are displayed
    And Count the total no of video feeds
    And Count video present from 3 days
