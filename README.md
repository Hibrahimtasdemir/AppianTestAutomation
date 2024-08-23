# SNAP Appian Maven TestNG Automation Framework!
Appian automation framework with Appian-Selenium-API, Selenium, Java, Maven, TestNG, Extent Reporting and Page Factory implementation.
![SnapLogo](lib/logo.png)

## Introduction

> Introducing SNAP - Simplified Next-gen Automation Platform!
> 
> Streamline your testing process with our cutting-edge framework. SNAP offers cross-browser support, intelligent automation, and seamless integrations. 
> Experience faster, reliable testing and unlock unparalleled efficiency.
> Join the future of automation with SNAP!

## Required System Configuration
1. Intellij Community Edition
2. JDK - 11 (Update compiler to Java 11 on Intellij and Also update the same on global level on your pc)
3. Git Bash

## Steps to run Sample Test present on the framework
1. Open Pre-requisites Folder and double-click on "Install_Appian_Selenium_API.bat"
2. Open folder in terminal
3. Run command - mvn clean
4. Run command - mvn test -DskipTests
5. Run command - mvn test -Dgroups=login

## Steps to configure according to the Project's Needs.

1. Open this project in an IDE, such as [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/#section=windows)
2. Update configurations:
   1. Open file `src/test/java/nexus/resources/properties/config.properties`
   2. Update Project Related configs:
      a. test_browser = *Enter Browser Name on which you want to run your tests - CHROME or EDGE*
      b. url = *Enter Website URL you want to test*
      c. Example:
         * test_browser = chrome
         * url = *Present inside the code*

   3. Update Appian Related Configs
      a. appian_version=24.2
      b. appian_locale=en_US
      c. appian_timeout=60

   4. Update User Related Details
      a. Open file `src/test/java/nexus/resources/properties/testData.properties`
      b. Update USERNAME and PASSWORD

P.S. - Further updates related to this framework, I'll post in the upcoming weeks. So, for now playaround with it and let me know if you face any issues. 

Contact me via mail - yadavarjit@gmail.com

Happy Testing!