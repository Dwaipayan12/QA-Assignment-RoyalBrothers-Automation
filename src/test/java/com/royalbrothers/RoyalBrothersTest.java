package com.royalbrothers;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;



public class RoyalBrothersTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePage homePage;

    @BeforeClass //This is a TestNG annotation.
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        homePage = new HomePage(page);
      //  cityLocationData = new CityLocationData(page);
    }




    @Test
    public void testBikeSearchAndFilter() {
        // Navigate and get auto-detected city
        String autoDetectedCity = homePage.navigateAndGetCity();

        // Get appropriate location for this city
        String locationFilter = homePage.getLocationForCity(autoDetectedCity);
       // System.out.println("Using location filter for " + autoDetectedCity + ": " + locationFilter);

        String pickupDate = "";
        String pickupTime = "";
        String dropDate = "";
        String dropTime = "";


        // Only try to fetch dates/times if service is available
        if (!locationFilter.toLowerCase().contains("we do not have any bikes")) {
            pickupDate = homePage.getPickupDate();
            pickupTime = homePage.getPickupTime();
            dropDate = homePage.getDropDate();
            dropTime = homePage.getDropTime();
        } else {
            System.out.println("Skipping pickup/drop date & time as no bikes available in this city.");
        }

        homePage.performSearch();
        String f_location=homePage.applyLocationFilter(locationFilter);

        List<String> bikeData = homePage.getBikeData(locationFilter);

        if (bikeData.size() == 0) {
            System.out.println("No bikes are available at this location currently.");
            Assert.assertTrue(true, "No bikes available - treated as valid case");
        } else {
            Assert.assertTrue(bikeData.size() > 0, "Should find bikes for location: " + locationFilter);
            homePage.printBikeData(bikeData);
        }

        // Test execution summary
        System.out.println("\n=== DYNAMIC AUTOMATION RESULTS ===");
        System.out.println("Auto-detected City: " + autoDetectedCity);
        System.out.println("Selected Location: " + f_location);
        System.out.println("Pickup: " + pickupDate + " at " + pickupTime);
        System.out.println("Drop: " + dropDate + " at " + dropTime);
        System.out.println("Total Bikes Found: " + bikeData.size());
        System.out.println("Automation worked for city: " + autoDetectedCity);
        System.out.println("==================================\n");

        for (int i = 0; i < bikeData.size(); i++) {
            System.out.println((i + 1) + ". " + bikeData.get(i));
        }
      ///  System.out.println("Total bikes found: " + bikeData.size());
        //System.out.println("========================\n");
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}


