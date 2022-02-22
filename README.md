# spring-boot-playwright-pdf
demo spring-boot-playwright-pdf restful API


Clone the test repo on local machine. My test is on MAC OS, but should be the same on window or other OSs.

1) create a working folder and cd to it.
2) git clone https://github.com/tony-playwright/spring-boot-playwright-pdf.git
3) run "mvn clean install"
4) this will build spring-boot-playwright-pdf-0.0.1-SNAPSHOT.war under /target dir



A) failed on Embedded Tomcat . Reproduce steps
1) run "mvn clean install"
2) java -jar ./target/spring-boot-playwright-pdf-0.0.1-SNAPSHOT.war
   1) open POSTMAN with following setting
      Header: 
               key: Accept   value: application/pdf
               key: Content-Type   value: text/html
      POST:
           url :http://localhost:1234/app/html2pdf
      Payload: any string
   
   2) exception
      java.lang.RuntimeException: Failed to create driver
      at com.microsoft.playwright.impl.Driver.ensureDriverInstalled(Driver.java:54)
      at com.microsoft.playwright.impl.PlaywrightImpl.create(PlaywrightImpl.java:40)
      at com.microsoft.playwright.Playwright.create(Playwright.java:96)
      at com.microsoft.playwright.Playwright.create(Playwright.java:100)
      at spring.boot.playwright.pdf.controller.ApplicationController.convertHtmlToPdf(ApplicationController.java:48)
      at spring.boot.playwright.pdf.controller.ApplicationController.generatePdfByPlaywright(ApplicationController.java:40)
      at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
      ..................
     at java.lang.Thread.run(Thread.java:748)
         Caused by: java.lang.NullPointerException
         at com.microsoft.playwright.impl.DriverJar.extractDriverToTempDir(DriverJar.java:85)
         at com.microsoft.playwright.impl.DriverJar.initialize(DriverJar.java:44)
         at com.microsoft.playwright.impl.Driver.ensureDriverInstalled(Driver.java:52)
         ... 55 more
      2022-02-22 15:25:20.511 ERROR 2677 --- [nio-1234-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is javax.xml.ws.WebServiceException: 500 INTERNAL_SERVER_ERROR] with root cause

       java.lang.NullPointerException: null
       at com.microsoft.playwright.impl.DriverJar.extractDriverToTempDir(DriverJar.java:85) ~[driver-bundle-1.18.0.jar!/:1.18.0]
       at com.microsoft.playwright.impl.DriverJar.initialize(DriverJar.java:44) ~[driver-bundle-1.18.0.jar!/:1.18.0]
       at com.microsoft.playwright.impl.Driver.ensureDriverInstalled(Driver.java:52) ~[driver-1.18.0.jar!/:1.18.0]
       at com.microsoft.playwright.impl.PlaywrightImpl.create(PlaywrightImpl.java:40) ~[playwright-1.18.0.jar!/:1.18.0]
       at com.microsoft.playwright.Playwright.create(Playwright.java:96) ~[playwright-1.18.0.jar!/:1.18.0]
       at com.microsoft.playwright.Playwright.create(Playwright.java:100) ~[playwright-1.18.0.jar!/:1.18.0]
       at spring.boot.playwright.pdf.controller.ApplicationController.convertHtmlToPdf(ApplicationController.java:48) ~[classes!/:0.0.1-SNAPSHOT]
       at spring.boot.playwright.pdf.controller.ApplicationController.generatePdfByPlaywright(ApplicationController.java:40) ~[classes!/:0.0.1-SNAPSHOT]


    ) these 3 jars are under
       spring-pdf-0.0.1-SNAPSHOT\WEB-INF\lib\driver-bundle-1.18.0.jar
                                         \driver-1.18.jar
                                         \playwright-1.18.0.jar

B)  failed on standard Tomcat . Reproduce steps on standard Apache Tomcat 8.5
1) download Apache Tomcat 8.5
2) drop the \apache-tomcat-8.5.75\webapps\spring-boot-playwright-pdf-0.0.1-SNAPSHOT.war
3) cd "\apache-tomcat-8.5.75\bin"
4) run " ./startup.sh "
5) open POSTMAN with following setting
   Header:
   key: Accept   value: application/pdf
   key: Content-Type   value: text/html
   POST:
   url :http://localhost:8080/spring-boot-playwright-pdf-0.0.1-SNAPSHOT/app/html2pdf
   Payload: any string

6) similar exception as before

C) TEST OK . Reproduce steps on standard Apache Tomcat 8.5.
   same steps as test B except I have to modify ""\apache-tomcat-8.5.75\bin"\setenv.sh" with following
    CLASSPATH="$CLASSPATH":/download/driver-bundle-1.18.0.jar;"$CLASSPATH":/download/driver-1.18.0.jar;


Question. How to make playwright working  via Restful API on spring boot application? 
