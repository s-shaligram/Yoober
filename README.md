# Yooeber Ride Database Application

![Java Version](https://img.shields.io/badge/Java-8%2B-blue)
![SQLite](https://img.shields.io/badge/Database-SQLite-green)

## Description

Yooeber Ride Database Application is a Java command-line application that interacts with a SQLite database to manage the Yooeber ride service. It allows users to view account details, calculate average driver ratings, calculate total money spent by passengers, create new accounts, submit ride requests, and complete rides.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

- Java Development Kit (JDK) 8 or above
- SQLite database management system

## Installation

1. Ensure you have JDK 8 or above installed on your system.
2. Set up a SQLite database with the necessary tables and data. The application assumes that the database is named `yoober_project.db` and is located in the `src` folder.
3. Compile the Java source code using the following command:


4. Run the application using the following command:


## Usage

Upon running the application, a menu will be displayed with various options. Use the numeric keys to select the desired option.

1. View all account details: This option displays the details of all Yooeber ride service accounts, including both drivers and passengers.

2. Calculate the average rating for a specific driver: Enter the email address of the driver to calculate their average rating based on the ratings provided by passengers.

3. Calculate the total money spent by a specific passenger: Enter the email address of the passenger to calculate the total amount of money spent by that passenger on Yooeber rides.

4. Create a new account: Use this option to create a new account for a passenger or a driver. You can choose to create an account for both roles if required.

5. Submit a ride request: Submit a ride request by providing the necessary details like pickup location, drop-off location, pickup date, and time.

6. Complete a ride: Use this option to complete a ride by providing the necessary details like ride ID, driver's email address, actual start date, actual start time, actual end date, actual end time, driver's rating, passenger's rating, distance traveled, and ride charge.

## Note

- The application assumes that the database schema is properly set up with all required tables and columns.
- Ensure that the SQLite database file `yoober_project.db` is located in the `src` folder and contains the necessary data for the application to function correctly.
- Make sure to input valid data and follow the instructions provided by the application to avoid errors.

## Contributing

This application was developed by Sameer Shaligram(https://github.com/s-shaligram) and Ghanashyam Shingate(https://github.com/GhanashyamShingate). Contributions are welcome! Feel free to open issues and submit pull requests.

