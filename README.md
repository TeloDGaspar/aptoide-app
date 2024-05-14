# aptoide-app

This is a simple Android application that interacts with the Aptoide API to fetch and display a list of applications. 
It was built using the Model-View-ViewModel (MVVM) architecture, that interacts with the Aptoide API to fetch and display a list of applications. 
The MVVM architecture was chosen for this project due to its advantages in handling lifecycle events and its ability to separate concerns, making the code more modular and easier to maintain compared to the Model-View-Presenter (MVP) architecture.

## Features

- Uses Retrofit2 to fetch and display apps from the Aptoide API.
- Allows users to view additional details about each application by tapping on it in the list.
- Includes a "Download" button on the app details screen
- Displays a loading spinner while fetching data from the API.
- The application utilizes the WorkManager and NotificationManager to periodically (every 30 minutes) send notifications to the user, alerting them of new applications available.
- The application supports offline usage. If the internet connection is lost, the app will continue to work using cached data. This feature was implemented using Room database and listening to the Connectivity Service.


## API Used

The application uses the following Aptoide API endpoint to fetch the list of applications:

http://ws2.aptoide.com/api/6/bulkRequest/api_list/listApps


## Future Improvements

If given more time, the following improvements could be made:

- Implement a more robust error handling system for network requests.
- Improve the user interface for a more engaging user experience.
- Database Optimization: Review the database queries and optimize them for better performance.
- Implement more tests to ensure the application is working as expected.
