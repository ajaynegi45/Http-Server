package com.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents a worker thread for handling HTTP connections.
 * This class extends Thread to manage the communication
 * between the server and a client over a given socket.
 */
public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private final Socket socket;

    /**
     * Constructs an HttpConnectionWorkerThread with the specified socket.
     *
     * @param socket the socket connected to the client
     */
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Runs the worker thread, handling incoming requests and sending responses.
     * <p>
     * This method reads data from the input stream of the socket,
     * processes the HTTP request, and writes a simple HTML response
     * back to the client. It logs the completion of the connection
     * and handles any IOExceptions that may occur during communication.
     * </p>
     *
     * @throws IOException if an I/O error occurs while reading from or writing to the socket
     */
    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
        ) {

            // int _byte;
            // while ( (_byte = inputStream.read() ) >= 0) {
            //     System.out.print( (char) _byte );
            //     //    outputStream.write(_byte);
            // }

            String html = "<html><head><title>Simple Java HTTP Server</title></head><body>This page was served using Java</body></html>";

            // CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n"; // 10 , 13 ASIC CODE

            String response =
                    "HTTP/1.1 200 OK" + CRLF // HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE ("HTTP/1.1 200 OK\r\n")
                            + "Content-Length: " + html.getBytes().length + CRLF // HEADER
                            + CRLF + html + CRLF + CRLF;

            outputStream.write(response.getBytes());
            // TODO we would read

            // TODO we would writing

            LOGGER.info("Connection completed..");
        } catch (IOException e) {
            LOGGER.error("Problem with Communication", e);
            e.printStackTrace();
        }
        // finally {
        //     inputStream.close();
        //     outputStream.close();
        //     socket.close();
        //
        //     // serverSocket.close(); // TODO Handle close
        // }
    }
}
