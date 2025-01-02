# Fetch Test

## Project Overview
  This project implements MVVM architecture to efficiently fetch data using Retrofit and display it with Jetpack Compose UI. It provides an intuitive and interactive list view while maintaining a clean and modular architecture.

## UI Components
  1. Splash View: Displays a loading screen until the data fetching process is complete.

  2. List View: Showcases the fetched data in a user-friendly list format.
Includes bottom navigation to switch between specific list groups.

  3. Error View:Displays an error message when the data fetching process fails.Features a retry button to reattempt data fetching.

## Models
  1. DataUIModel:A streamlined model containing only the necessary fields required for rendering UI components.

  2. DataModel:Acts as the intermediary layer between the ViewModel and Repository, processing and filtering data for business logic.

  3. DataResponse:Represents the raw data structure received directly from the API client.

### Note: Using three separate models ensures a clear separation of concerns.
  1. UI Model: Optimized for rendering.
  2. Domain Model: Handles filtered data for the ViewModel.
  3. API Model: Processes raw API responses at the Repository level.

## Network and Offline Management
  1. API Client:Utilizes Retrofit to fetch data efficiently from network sources.
  2. Cache Manager:Implements offline support using DataStore to store and retrieve cached data.Ensures the application provides list data even when the device is offline.

## Test case
  Created a test class for the main view model with test data using Junit and mockito

## DI
  Used **hilt** to provide dependency injection


### Api Source https://fetch-hiring.s3.amazonaws.com/hiring.json.

**Language**: Kotlin

**Libraries**: ViewModel, Flows, Retrofit, mockito

**Architecture**: MVVM


## Image
| List View | Error View | Splash View |
|-----------|------------|-------------|
|<img width="235" alt="Screenshot 2025-01-02 at 1 34 03 PM" src="https://github.com/user-attachments/assets/a61c3eec-11aa-416a-a85c-b05fe7b566ad" />|<img width="235" alt="Screenshot 2025-01-02 at 1 35 20 PM" src="https://github.com/user-attachments/assets/902cdf8e-8ff1-47d1-a0e4-f531d12d894a" />|<img width="240" alt="Screenshot 2025-01-02 at 1 34 33 PM" src="https://github.com/user-attachments/assets/360b746f-d096-44dc-b2cd-9a763d0d2313" />|

## Video Demo

Google drive link: https://drive.google.com/file/d/15eN1PlF8o-dAhF2h-EaB65kEqHenoa_s/view?usp=sharing

