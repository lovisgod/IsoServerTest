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

### Example request will be something like this

```kotlin
  fun echoMessageSample(
            processCode: String,
            terminalId: String): Any {
        return try {
            val now = Date()
            val stan = getNextStan()
            // Load package from resources directory.
            val isoMsg = ISOMsg()
            isoMsg.packager = packager
            isoMsg.mti = "0800"
            isoMsg[3] = processCode
            isoMsg[7] = timeAndDateFormatter.format(now)
            isoMsg[11] = stan
            isoMsg[12] = timeFormatter.format(now)
            isoMsg[13] = monthFormatter.format(now)
            isoMsg[41] = terminalId
            printISOMessage(isoMsg)
            val dataToSend = isoMsg.pack()

            val c  = ASCIIChannel("localhost", 8083, packager)

            c.connect()

            c.send(isoMsg)

            val response = c.receive()

            printISOMessage(response)
            println(response.getValue(39))
            println(response.getString(53))

            if (response.getValue(39) != "00") {
                return "call home not successful"
            } else {
                return "got back"
            }

        } catch (e: ISOException) {
            throw Exception(e)
        }
    }
```

### 3. Monitor Server Logs:

Monitor the server logs to observe the processing of the incoming message.

## Modifying the Generic Packager

### 1. Edit the Packager Configuration:

The server uses a generic packager. which the configuration file defined in XML format (e.g., fields.xml). Modify the  packager configuration file to suit your specific requirements.

### 2. Adjust Field Definitions:

Customize field definitions, message templates, and other configurations as needed.

### 3. Restart the Server:

Restart the server to apply the changes to the packager.

# USING Q2 AND QServer

`Q2` is a lightweight framework built on top of jPOS, providing additional features and capabilities.

`QServer` is part of jPOS's Q2 (Quick-Start 2) framework.

### Functionality:

QServer extends the functionality of ISOServer by introducing asynchronous processing through the use of queues (Q stands for Queue).
Messages are processed asynchronously in a multithreaded environment, which can be beneficial for handling a high volume of concurrent connections.

### Usage:

You typically use QServer when you need advanced features like asynchronous processing, thread pooling, and the ability to configure various components through Q2's configuration files.

To test this server application using `Q2` and `QServer`, navigate to the `[use-q-server](https://github.com/lovisgod/IsoServerTest/tree/use-q-server)` branch and Rebuild/Restart the application.

# Important Note

This is a test server and should not be used in a production environment.
Ensure that you have the necessary permissions to run a server on the specified port.
Review and customize the server configurations as needed for your testing.
Feel free to explore and adapt the code and configurations based on your project's requirements.