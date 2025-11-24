package com.royalbrothers;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.AriaRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

public class HomePage {
    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    // Navigate and let website auto-detect city
    public String navigateAndGetCity() {
        System.out.println("Navigating to Royal Brothers website...");

 // page.navigate("https://www.royalbrothers.com/");

        page.navigate("https://www.royalbrothers.com/", new Page.NavigateOptions().setTimeout(60000));
      page.waitForTimeout(12000);
       page.waitForLoadState(LoadState.NETWORKIDLE);



        String finalUrl = page.url();
        String detectedCity = extractCityFromUrl(finalUrl);

        System.out.println("Final URL: " + finalUrl);
        System.out.println("Auto-detected city: " + detectedCity);

        return detectedCity;
    }

    private String extractCityFromUrl(String url) {
        if (url.contains("/bike-rentals")) {
            String[] parts = url.split("/");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("bike-rentals") && i > 0) {
                    return parts[i-1];
                }
            }
        }
        // check query string for city or city_name
        if (url.contains("city=")) {
            String[] params = url.split("&");
            for (String param : params) {
                if (param.startsWith("city=")) {
                    return param.split("=")[1].toLowerCase();
                } else if (param.startsWith("city_name=")) {
                    return param.split("=")[1].toLowerCase();
                }
            }
        }
        return "unknown";
    }



    public String getPickupDate() {
        Locator pickupDate = page.locator("#pickup-date-desk");
        pickupDate.waitFor();
        return pickupDate.inputValue();
    }

    public String getPickupTime() {
        Locator pickupTime = page.locator("#pickup-time-desk");
        pickupTime.waitFor();
        return pickupTime.inputValue();
    }

    public String getDropDate() {
        Locator dropDate = page.locator("#dropoff-date-desk");
        dropDate.waitFor();
        return dropDate.inputValue();
    }

    public String getDropTime() {
        Locator dropTime = page.locator("#dropoff-time-desk");
        dropTime.waitFor();
        return dropTime.inputValue();
    }



    // Get appropriate location for any city
    public String getLocationForCity(String city) {
        switch (city.toLowerCase()) {
            case "bangalore": return "Yeshwanthpur (BMTC Bus Station)";
            case "belagavi" : return "buddha vihara";
            case "ahmedabad": return "Bopal";
            case "bhubaneswar": return "Bidyabharati Complex";
            case "calicut": return "YMCA Cross Road";
            case "kolkata": return "beliaghata";
            case "pune": return "Hinjewadi";
            case "hyderabad": return "Punjagutta ( Near Khairtabad Metro Station)";
            case "chennai": return "Anna Nagar";
            case "mumbai": return "Bandra";
            case "agra": return "Sultanpura Police chowki";
            case "vijayawada":return "Mogalarajapuram";
            case "wayanad":return "Kalpetta";
            case "vizag":return "Asilmetta";
            case "udaipur":return "Bhrampole Road";
            case "tirupati":return "Tata Nagar";
            case "jamshedpur":return "Gaddi Mohhalha (Dhramsala Road)";
            case "cochin":return "Ernakulam South";
            case "coorg":return "Ernakulam South";
            case "guntur":return "Guntur (Opposite kothapeta police station)";
            case "gurugram":return "Dlf City Court, Mehrauli-Gurgaon Rd (Sikanderpur Metro)";
            case "guwahati":return "Birubari (GMC Hospital Road)";
            case "hampi":return "Hosapete Road (Near KU Campus)";
            case "hubli-dharwad":return "Balkrishna Square (Harsha Complex)";
            case "jaipur":return "Ganpati Plaza (Shopping complex)";
            case "leh ladakh":return "Venture Third pole";
            case "mangalore":return "Near Mangalore central railway station ";
            case "mysore":return "Vani Vilas road";
            case "pondicherry":return "Chetty Street";
            case "puri":return "Baliapanda Housing Board Colony";
            case "ranchi":return "Kokar Industrial Area ";
            case "surat":return "Begampura";
            case "trivandrum":return "Kochuveli Railway Station";
            case "udupi-manipal":return "Manipal Motors - Udupi Road";
            case "jaisalmer":return "Jaisalmer";

            case "bangalore+airport": return "We do not have any bikes in bangalore-airport currently.";
            case "delhi": return "We do not have any bikes in delhi currently.";
            case "chandigarh":return "We do not have any bikes in chandigarh currently.";
            case "chikmagalur":return "We do not have any bikes in chikmagalur currently.";
            case "gandhinagar":return "We do not have any bikes in Gandhinagar currently.";
            case "lucknow":return "We do not have any bikes in Lucknow currently.";
            case "manali":return "We do not have any bikes in Manali currently.";
            default: return "City Center";
        }

    }

    public String applyLocationFilter(String location) {

        // 1️⃣ Handle special case
        if (location.toLowerCase().contains("we do not have any bikes")) {
            System.out.println("Skipping location filter: No bikes available in this city.");
            return "NO_LOCATION";
        }

        // 2️⃣ Wait for and focus on the search box
        page.waitForSelector("input[placeholder='Search Location']");
        Locator searchBox = page.locator("input[placeholder='Search Location']");
        searchBox.click();
        searchBox.fill(location);

        // 3️⃣ Wait for the location list to appear
        page.waitForSelector("ul.location_listing li.each_list label");

        // 4️⃣ Find the checkbox label that matches the location text
        Locator option = page.locator("ul.location_listing li.each_list label")
                .filter(new Locator.FilterOptions().setHasText(location));

        if (option.count() == 0) {
            System.out.println("⚠ No matching UI location found: " + location);
            return "NOT_FOUND_UI";
        }

        // 5️⃣ Click the first matching location checkbox
        option.first().click();

        // 6️⃣ Wait for the selected chip to appear and get its exact text
        page.waitForSelector(".selected_location_filter .chip span");
        Locator selectedChip = page.locator(".selected_location_filter .chip span");
        String selectedLocation = selectedChip.first().textContent().trim();

        System.out.println("Selected Location Applied → " + selectedLocation);

        // 7️⃣ Wait for page content to refresh (optional)
        page.waitForSelector(".search_page_row.each_card_form");
        page.waitForTimeout(2000);

        return selectedLocation;
    }

    // Simple search - avoid all problematic interactions
    public void performSearch() {
        System.out.println("Skipping search - working with current page content");
        page.waitForTimeout(3000);
    }


    public List<String> getBikeData(String expectedLocation) {
        System.out.println("Collecting bike data from results page...");

        page.waitForTimeout(5000); // Wait for results to load
        List<String> result = new ArrayList<>();
        List<Integer>res=new ArrayList<>();
        // Target each bike card on results page
        Locator bikeCards = page.locator(".search_page_row.each_card_form");
        int bikeCount = bikeCards.count();

        System.out.println("Found " + bikeCount + " bike cards on results page");
        if (bikeCount == 0) {
            System.out.println("No bikes are available at this location currently.");
            return result; // returns empty list
        }

        if (bikeCount > 0) {
            for (int i = 0; i < bikeCount; i++) {
                Locator bikeCard = bikeCards.nth(i);

                // Extract bike model name from h6.bike_name
                String bikeModel = "Unknown Model";
                String price="N/A";
                Locator bikeNameElement = bikeCard.locator("h6.bike_name");
                // Locator@.search_page_row.each_card_form >> nth=1 >> id.rental_amount
                if (bikeNameElement.count() > 0) {
                    bikeModel = bikeNameElement.textContent().trim();
                }
                if (bikeModel.equals("Unknown Model") || bikeModel.isEmpty()) {
                    continue;   // skip this bike card completely
                }

                Locator priceElement = bikeCard.locator("#rental_amount");
                if (priceElement.count() > 0) {
                    price = priceElement.textContent().trim();
                }
                // Extract location from the default location input field
                String location = expectedLocation; // Fallback
                Locator locationInput = bikeCard.locator("input.loc_input");
                if (locationInput.count() > 0) {
                    String locationValue = locationInput.inputValue();
                    if (!locationValue.isEmpty() && !locationValue.equals("Location")) {
                        location = locationValue;
                    }
                }

                // If location input is empty, try to get from first available location in dropdown
                if (location.equals(expectedLocation)) {
                    Locator firstLocation = bikeCard.locator("li.location.fully_available").first();
                    if (firstLocation.count() > 0) {
                        location = firstLocation.textContent().trim();
                    }
                }


                String bikeInfo = bikeModel + " - Available at " + location + " - price is " + price;
                res.add(Integer.parseInt(price));
                result.add(bikeInfo);
                System.out.println("Bike " + (i + 1) + ": " + bikeInfo);
            }
            Collections.sort(res);
            for(int i=0;i<res.size();i++){
              //System.out.print(res.get(i)+"    ");
            }
        } else {
           // System.out.println("No bike cards found on results page");
        }
        if (result.size() > 0) {
          //  System.out.println("Successfully extracted " + result.size() + " bikes");
            return result; // Return immediately to avoid page context issues
        }
        return result;
    }


    // Print bike data
    public void printBikeData(List<String> bikeData) {
       // System.out.println("\n=== BIKE DATA RESULTS ===");
        for (int i = 0; i < bikeData.size(); i++) {
           // System.out.println((i + 1) + ". " + bikeData.get(i));
        }
      //  System.out.println("Total bikes found: " + bikeData.size());
        //System.out.println("========================\n");
    }
}
