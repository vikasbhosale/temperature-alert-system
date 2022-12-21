# **Temperature Alert System**

# Contents

[System diagram: 1](#_Toc122438826)

[Components and external parties: 1](#_Toc122438827)

[Pre-requisite: 2](#_Toc122438828)

[Steps to run the project 2](#_Toc122438829)

[Testing steps: 2](#_Toc122438830)

[Wait for notification from system 4](#_Toc122438831)

[Unit test case running tips: 5](#_Toc122438832)

# System diagram:

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/Temperature_Alert.jpg)

# Components and external parties:

- User Management / Temperature settings

- User
  - End user who can set the temperature criteria and based on criteria system generate alerts when temperature meet the criteria.

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/User.jpg)

- Analyser

As named Analyser, it analyses the users and its temperature settings and build a cache based on different parameters. For now, we just consider city as single parameter to build cache.

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/Analyser.jpg)

- Scheduler

We have configured scheduler which run's every 1 min [we can change the configuration] and pull the current temperature details from External Temperature API's.

Additionally, it triggers the event processors. In our case we have City wise event processor which processor the data as per city.

- Event Processors

Event processors invoke from scheduler when latest feed came from External temperature system. Event processors validate the user settings against the latest feed and generate alert's by calling notification manager.

- Notification Manager

Responsible to send notification in real world. For POC, We just print the alert in console. Like below

**===========================================================================**

**Hi Vikas,**

**ALERT - Temperature touched 10.1 Degree Celsius in Mumbai.**

**Current Temperature noted - -5.21**

**You requested to alert on temperature less than 10.1**

**Thanks,**

**Temperature Alert System**

**===========================================================================**

- User city cache

Internal cache to hold the city wise user temperature settings.

- Static DB [Later replace by actual DB]

We are not using real database. Instead, we are storing data in Map for POC.

All user and temperature setting store in Map.

- External Temperature API's system

System which returns the latest temperature reading across the region.

We have mocked this system using WireMock for testing.

- End user

User who set the temperature criteria and receives alerts when criteria meets.

# Pre-requisite:

- Java 11
- Maven 3.0.3
- IDE
- WireMock jar for mocking external temperature systems.
- Tomcat 9.0

# Steps to run the project:

1. Pull the project from my public repo.

[https://github.com/vikasbhosale/temperature-alert-system](https://github.com/vikasbhosale/temperature-alert-system)

Or download the zip and extract to eclipse or preferred IDE.

1. Setup a tomcat and run the project.
2. To test with External temperature system, We used mock server as WireMock.

Download from attachment and start with following command

java -jar wiremock-jre8-standalone-2.35.0.jar --port=8081 --global-response-templating
![java -jar wiremock-jre8-standalone-2.35.0.jar --port=8081 --global-response-templating](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/wiremock.jpg)

# Testing steps:

1. Create User with temperature criteria set by user. _[Note: We would have segregated the User and temperature configuration. However, due to time limitation we club them for now.]_

**User - Vikas**

curl --location --request POST 'localhost:8080/TemperatureAlertSystem/rest/user/create' \

--header 'Authorization: Basic YW51cmFnX2FkbWluOkNyb21hQDEyMzQ1' \

--header 'Content-Type: application/json' \

--data-raw '{

    "firstName": "Vikas",

    "lastName": "Bhosale",

    "email": "vikas@data.com",

    "temperatureSettings": [

        {

            "id": 111,

            "city": "Delhi",

            "condition": "gt",

            "reading": 30.1

        },

        {

            "id": 112,

            "city": "Mumbai",

             "condition": "ls",

            "reading": 10.1

        }

    ]

}'

**User - Anurag**

curl --location --request POST 'localhost:8080/TemperatureAlertSystem/rest/user/create' \

--header 'Authorization: Basic YW51cmFnX2FkbWluOkNyb21hQDEyMzQ1' \

--header 'Content-Type: application/json' \

--data-raw '{

    "firstName": "Anurag",

    "lastName": "A",

    "email": "a@data.com",

    "temperatureSettings": [

        {

            "id": 111,

            "city": "Delhi",

            "condition": "gt",

            "reading": 10.1

        },

        {

            "id": 112,

            "city": "Mumbai",

             "condition": "ls",

            "reading": 30.1

        }

    ]

}'

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/postman.jpg)

You can use swagger UI for testing.

# Wait for notification from system

**===========================================================================**

**Hi Anurag,**

**ALERT - Temperature touched 30.1 Degree Celsius in Mumbai.**

**Current Temperature noted - -5.21**

**You requested to alert on temperature less than 30.1**

**Thanks,**

**Temperature Alert System**

**===========================================================================**

**2022-12-20 13:36:00 INFO NotificationService:19 -**

**===========================================================================**

**Hi Anurag,**

**ALERT - Temperature touched 10.1 Degree Celsius in Delhi.**

**Current Temperature noted - 47.61**

**You requested to alert on temperature greater than 10.1**

**Thanks,**

**Temperature Alert System**

**===========================================================================**

**2022-12-20 13:36:00 INFO NotificationService:19 -**

**===========================================================================**

**Hi Vikas,**

**ALERT - Temperature touched 10.1 Degree Celsius in Mumbai.**

**Current Temperature noted - -5.21**

**You requested to alert on temperature less than 10.1**

**Thanks,**

**Temperature Alert System**

**===========================================================================**

# Unit test case running tips:

While running jUnit Test

Add VM arguments.

**--add-opens=java.base/java.lang=ALL-UNNAMED**

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/eclipse-test.jpg)

# Swagger docs:

[http://localhost:8080/TemperatureAlertSystem/swagger-ui.html](http://localhost:8080/TemperatureAlertSystem/swagger-ui.html)

![](https://github.com/vikasbhosale/temperature-alert-system/blob/main/images/swagger-ui.jpg)

You can use swagger UI to add users and configuration.
