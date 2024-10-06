package com.httpserver.core.https;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents a worker thread for handling HTTPS connections.
 * This class extends Thread to manage communication between
 * the server and a client over a given socket.
 */
public class HttpsConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpsConnectionWorkerThread.class);

    private final Socket socket;

    /**
     * Constructs an HttpsConnectionWorkerThread with the specified socket.
     *
     * @param socket the socket connected to the client
     */
    public HttpsConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Runs the worker thread, handling incoming HTTPS requests and sending responses.
     * <p>
     * This method reads data from the input stream of the socket,
     * processes the HTTPS request, and writes a simple HTML response
     * back to the client. It logs the completion of the connection
     * and handles any IOExceptions that may occur during communication.
     * </p>
     *
     * @throws IOException if an I/O error occurs while reading from or writing to the socket
     */
    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            // Read and process the incoming request (currently commented out)
            // int _byte;
            // while ((_byte = inputStream.read()) >= 0) {
            //     System.out.print((char) _byte);
            //     // outputStream.write(_byte);
            // }

            // Create a simple HTML response
            String html = "<html><head><title>Simple Java HTTPS Server</title></head><body>This page was served using Java</body></html>";

            // CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n"; // 10 , 13 ASCII CODE

            // Build the HTTP response
            String response =
                    "HTTP/1.1 200 OK" + CRLF // HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            + "Content-Length: " + html.getBytes().length + CRLF // HEADER
                            + CRLF + html + CRLF + CRLF;

            // Write the response to the output stream
            outputStream.write(response.getBytes());

            LOGGER.info("Connection completed..");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Close the socket after handling the request
            } catch (IOException e) {
                LOGGER.error("Error closing socket", e);
            }
        }
    }
}
