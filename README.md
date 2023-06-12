# twasdiag

`twasdiag` is a Java Enterprise Edition 6 web application for simulating diagnostic situations.

Running this application in production should be done with care because it may be used to execute various powerful functions.

## Download

Download `twasdiag.ear` (or `twasdiag.war`) from <https://github.com/IBM/twasdiag/releases/latest>

## Development

1. Java >= 8 is required on your `PATH`; for example, [IBM Semeru Runtimes](https://developer.ibm.com/languages/java/semeru-runtimes/downloads/)
1. Build:
    * macOS and Linux:
      ```
      ./mvnw clean install
      ```
    * Windows:
      ```
      mvnw clean install
      ```
1. Run:
    * macOS and Linux:
      ```
      ./mvnw -pl '!:twasdiag-war,!:twasdiag' liberty:run
      ```
    * Windows:
      ```
      mvnw -pl '!:twasdiag-war,!:twasdiag' liberty:run
      ```
1. Wait for the message, "server is ready to run a smarter planet". For example:
   ```
   CWWKF0011I: The twasdiagServer server is ready to run a smarter planet.
   ```
1. Open your browser to the HTTP or HTTPS page:
    * <http://localhost:9080/>
    * <https://localhost:9443/>
1. Use `Ctrl^C` to stop the server.
