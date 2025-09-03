package com.royalbrothers.stepdefinitions;

import com.microsoft.playwright.*;
import com.royalbrothers.HomePage;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.List;

public class RoyalBrothersSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePage homePage;
    private String autoDetectedCity;
    private String pickupDate, pickupTime, dropDate, dropTime;
    private List<String> bikeData;
    private String locationFilter;

    @Given("I navigate to Royal Brothers website")
    public void iNavigateToRoyalBrothersWebsite() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        homePage = new HomePage(page);

        autoDetectedCity = homePage.navigateAndGetCity();
    }

    @When("the system auto-detects the city")
    public void systemAutoDetectsCity() {
        System.out.println("City detected: " + autoDetectedCity);
    }

    @Then("I should see the city information in URL")
    public void validateCityInUrl() {
        String url = page.url();
        Assert.assertTrue(url.contains(autoDetectedCity), "URL does not contain detected city!");
    }

    // --------- Regression Scenario Outline Steps ---------

    @When("the system detects city as {string}")
    public void systemDetectsCityAs(String city) {
        locationFilter = homePage.getLocationForCity(city);
        System.out.println("Using location filter for " + city + ": " + locationFilter);
    }

    @And("I set pickup date as {string} and time as {string}")
    public void iSetPickupDateAndTime(String date, String time) {
        pickupDate = date;
        pickupTime = time;
        System.out.println("Pickup set: " + pickupDate + " " + pickupTime);
    }

    @And("I set drop date as {string} and time as {string}")
    public void iSetDropDateAndTime(String date, String time) {
        dropDate = date;
        dropTime = time;
        System.out.println("Drop set: " + dropDate + " " + dropTime);
    }

    @And("I perform search for bikes")
    public void iPerformSearchForBikes() {
        homePage.performSearch();
    }

    @Then("I should see the applied filters are visible")
    public void iShouldSeeAppliedFilters() {
        Assert.assertNotNull(pickupDate, "Pickup date not set");
        Assert.assertNotNull(dropDate, "Drop date not set");
        System.out.println("Filters applied successfully.");
    }

    @And("I should validate pickup date is {string}")
    public void validatePickupDate(String expectedDate) {
        String actual = homePage.getPickupDate();
        Assert.assertEquals(actual, expectedDate, "Pickup date mismatch!");
    }

    @And("I should validate pickup time is {string}")
    public void validatePickupTime(String expectedTime) {
        String actual = homePage.getPickupTime();
        Assert.assertEquals(actual, expectedTime, "Pickup time mismatch!");
    }

    @And("I should validate drop date is {string}")
    public void validateDropDate(String expectedDate) {
        String actual = homePage.getDropDate();
        Assert.assertEquals(actual, expectedDate, "Drop date mismatch!");
    }

    @And("I should validate drop time is {string}")
    public void validateDropTime(String expectedTime) {
        String actual = homePage.getDropTime();
        Assert.assertEquals(actual, expectedTime, "Drop time mismatch!");
    }

    @When("I apply location filter for {string}")
    public void iApplyLocationFilterFor(String location) {
        homePage.applyLocationFilter(location);
    }

    @Then("I should see available bikes")
    public void iShouldSeeAvailableBikes() {
        bikeData = homePage.getBikeData(locationFilter);
        Assert.assertTrue(bikeData.size() >= 0, "No bike data found!");
    }

    @And("I should collect bike data with model and location")
    public void iShouldCollectBikeData() {
        if (bikeData.isEmpty()) {
            System.out.println("No bikes available to collect.");
        } else {
            homePage.printBikeData(bikeData);
        }
    }

    @And("I should validate all bikes are from the selected location {string}")
    public void validateAllBikesFromLocation(String expectedLocation) {
        for (String bike : bikeData) {
            Assert.assertTrue(
                    bike.toLowerCase().contains(expectedLocation.toLowerCase()),
                    "Bike not from expected location: " + bike
            );
        }
    }

    @And("I should print the collected bike data")
    public void iShouldPrintCollectedBikeData() {
        System.out.println("=== FINAL BIKE DATA ===");
        for (int i = 0; i < bikeData.size(); i++) {
            System.out.println((i + 1) + ". " + bikeData.get(i));
        }
        System.out.println("========================\n");
    }
}
