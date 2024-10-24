package com.httpserver.core.https;

import com.httpserver.http.HttpResponse;
import com.httpserver.http.HttpStatusCode;
import com.httpserver.http.HttpVersion;
import com.httpserver.middleware.Middleware;
import com.httpserver.middleware.SecurityHeadersMiddleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents a worker thread for handling HTTPS connections. This class extends
 * Thread to manage communication between the server and a client over a given
 * socket.
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
        LOGGER.debug("HttpsConnectionWorkerThread created for socket: {}", socket);
    }

    /**
     * Runs the worker thread, handling incoming HTTPS requests and sending
     * responses.
     * <p>
     * This method performs the following steps:
     * 1. Reads data from the input stream of the socket.
     * 2. Creates an {@link HttpResponse} object representing the HTTP response.
     * 3. Sets the HTTP version, status code, headers, and body content for the response.
     * 4. Applies security headers using a middleware implementation (e.g., {@link SecurityHeadersMiddleware}).
     * 5. Builds the full HTTP response (including status line, headers, and body).
     * 6. Writes the constructed HTTP response to the output stream, sending it back to the client.
     * 7. Logs the completion of the connection, and closes the socket.
     * </p>
     * <p>
     * This method also handles and logs any {@link IOException} that may occur during
     * communication, such as reading from or writing to the socket, or closing the socket.
     * </p>
     *
     * @throws IOException if an I/O error occurs while reading from or writing to the socket
     */
    @Override
    public void run() {
        LOGGER.debug("Handling connection from client: {}", socket.getInetAddress());

        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            // Log that the input and output streams have been successfully obtained
            LOGGER.debug("Input and output streams obtained for socket: {}", socket);

            // Reading the request (not fully implemented)

//			StringBuilder requestBuilder = new StringBuilder();
//			int byteRead;
//			while ((byteRead = inputStream.read()) != -1) {
//				requestBuilder.append((char) byteRead);
//			}
//
//			String request = requestBuilder.toString();
//			LOGGER.debug("Received request: {}", request);

            // Create a HTML response

            HttpResponse httpResponse = new HttpResponse();

            httpResponse.setHttpVersion(HttpVersion.HTTP_1_1);
            httpResponse.setStatusCode(HttpStatusCode.SUCCESS_200_OK);
            httpResponse.setBody("<html><head><title>Simple Java HTTPS Server</title></head><body>This page was served using Java</body></html>");

            httpResponse.addHeader("Content-Type", "text/html");
            httpResponse.addHeader("Content-Length", String.valueOf(httpResponse.getBody().getBytes().length));

            Middleware middleware = new SecurityHeadersMiddleware();
            middleware.apply(httpResponse);

            // Build the HTTP response
            String response = httpResponse.buildResponse();

            // Write the response to the output stream
            outputStream.write(response.getBytes());

            LOGGER.debug("Sent response to client: {}", socket.getInetAddress());

            LOGGER.info("Connection completed with client: {}", socket.getInetAddress());
        } catch (IOException e) {
            LOGGER.error("IOException occurred while handling connection with client: {}", socket.getInetAddress(), e);
        } finally {
            try {
                socket.close();
                LOGGER.debug("Socket closed for client: {}", socket.getInetAddress());
            } catch (IOException e) {
                LOGGER.error("Failed to close socket for client: {}", socket.getInetAddress(), e);
            }
        }
    }
}
