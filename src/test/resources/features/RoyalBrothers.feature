Feature: Royal Brothers Bike Booking Automation
  As a user, I want to search and filter bikes on Royal Brothers website
  So that I can find available bikes in my preferred location and time

  @smoke
  Scenario: Verify auto-detected city in Royal Brothers website
    Given I navigate to Royal Brothers website
    When the system auto-detects the city
    Then I should see the city information in URL

  @regression
  Scenario Outline: Search and validate bike availability with location filter
    Given I navigate to Royal Brothers website
    When the system detects city as "<city>"
    And I set pickup date as "<pickupDate>" and time as "<pickupTime>"
    And I set drop date as "<dropDate>" and time as "<dropTime>"
    And I perform search for bikes
    Then I should see the applied filters are visible
    And I should validate pickup date is "<pickupDate>"
    And I should validate pickup time is "<pickupTime>"
    And I should validate drop date is "<dropDate>"
    And I should validate drop time is "<dropTime>"
    When I apply location filter for "<location>"
    Then I should see available bikes
    And I should collect bike data with model and location
    And I should validate all bikes are from the selected location "<location>"
    And I should print the collected bike data

    Examples:
      | city      | pickupDate   | pickupTime | dropDate    | dropTime | location                                     |
      | bangalore | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Whitefield                                   |
      | pune      | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Hinjewadi                                    |
      | hyderabad | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Punjagutta ( Near Khairtabad Metro Station ) |
      | chennai   | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Anna Nagar                                   |
      | mumbai    | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Bandra                                       |
      | kolkata   | 26 Feb, 2025 | 8:30 AM    | 27 Feb, 2025 | 9:30 AM | Beliaghata                                   |
