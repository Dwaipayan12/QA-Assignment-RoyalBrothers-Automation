# ***RoyalBrothers Automation Project***
Overview

This project is an automation framework for the RoyalBrothers bike rental website (https://www.royalbrothers.com
). It dynamically selects cities, dates, and locations to fetch available bike information. The automation validates the data displayed on the website, ensuring the filters and selections work correctly.

Automation project for Royal Brothers bike rental website using **Java, Maven, Playwright, and TestNG**.

**Features**

      -Automatically detects the city and selects a location.

      -Searches for bikes based on dynamic pickup and drop dates and times.

      -Collects and validates all available bike data.

      -Handles multiple cities dynamically without code changes.

      -Generates console logs with detailed results of bikes found.

**Prerequisites**

     -Before running the project, make sure you have:

     -Java 17 or higher installed

     -Maven installed

     -Node.js installed (required by Playwright)

     -IntelliJ IDEA or any Java IDE

     -Internet connection (to access the RoyalBrothers website)



---

## Setup & Running Tests

1. Clone the repository:  
   ```bash
   git clone https://github.com/Dwaipayan12/QA-Assignment-RoyalBrothers-Automation.git
   
2.  Navigate to the project directory:
      ```bash
     cd rb-automation
3.  Run tests using Maven:
    ```bash
    mvn clean test
4.  Check console output for results:

    ```bash
    Auto-detected City: Bangalore
    Selected Location: Whitefield
    Pickup: 01 Sep, 2025 at 12:00 PM
    Drop: 02 Sep, 2025 at 10:00 AM
    Total Bikes Found: 103

    1. Honda Activa 6G - Available at Indiranagar - Metro Station (Near metro station)
    2. Honda Activa 125 (BS6) - Available at HSR Layout (4th Sector)
    3. KTM Adventure-X (2025) - Available at Indiranagar - Metro Station (Near metro station)
    4. Aprilia Tuono 457 - Available at Indiranagar - Metro Station (Near metro station)
    5. Honda NX 200 - Available at Indiranagar - Metro Station (Near metro station)
    6. TVS Jupiter 110 (BS6) - Available at Koramangala (Beside Sony Signal)
    7. Triumph Speed 400 T4 - Available at Koramangala (Beside Sony Signal)
    8. Royal Enfield Bear 650 - Available at Marathalli Bridge (CN Reddy Plaza) (Beside J Spiders)
    9. Aprilia SR 160 N - Available at Hebbal (Esteem Mall)
    10. BMW G310 R (2024) - Available at BTM 2nd Stage (VAKA LIVING PG Basement)

    **Total bikes found: 103**  
    **Note**: Only the first 10 results are shown here. Full results include all 103 bikes.

## Author
  ```bash
  **Dwaipayan Bhowmik**  
  - B.Tech in Computer Science and Engineering
  - 2025 graduate
    
