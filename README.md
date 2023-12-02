# JPOS Test Server for Call-home Messages

This repository contains a test JPOS server designed to accept incoming Call-home messages with the process code 9D0000.

## Usage

### Server Setup

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/lovisgod/IsoServerTest.git

2. **Build the Project:**

Use your preferred build tool (e.g., Maven, Gradle) to build the project.

3. **Run the JPOS Server:**
   ```sh
   java -jar target/jpos-callhome-server.jar
##### NOTE: you can also build directly using your IntelliJ IDE

# Testing with Sample ISOMsg

### 1. Ensure the JPOS Server is Running:

Make sure the JPOS server is up and running, listening for incoming messages on the specified port.

### 2. Send Sample ISOMsg:

Use a tool or script to send a sample ISOMsg to the server. The sample ISOMsg should have the following characteristics:

MTI (Message Type Indicator): 0800
Process Code: 9D0000
Other relevant fields as needed for testing.

### 3. Monitor Server Logs:

Monitor the server logs to observe the processing of the incoming message.

## Modifying the Generic Packager

### 1. Edit the Packager Configuration:

The server uses a generic packager. which the configuration file defined in XML format (e.g., fields.xml). Modify the  packager configuration file to suit your specific requirements.

### 2. Adjust Field Definitions:

Customize field definitions, message templates, and other configurations as needed.

### 3. Restart the Server:

Restart the server to apply the changes to the packager.

# Important Note

This is a test server and should not be used in a production environment.
Ensure that you have the necessary permissions to run a server on the specified port.
Review and customize the server configurations as needed for your testing.
Feel free to explore and adapt the code and configurations based on your project's requirements.