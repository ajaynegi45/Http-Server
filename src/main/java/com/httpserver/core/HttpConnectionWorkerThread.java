package com.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private final Socket socket;
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ) {

//            int _byte;
//            while ( (_byte = inputStream.read() ) >= 0) {
//                System.out.print( (char) _byte );
//            //    outputStream.write(_byte);
//            }

            String html = "<html><head><title>Simple Java HTTP Server</title></head><body>This is page was served using java</body></html>";

//          CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n"; // 10 , 13 ASIC CODE

            String response =
                    "HTTP/1.1 200 OK" + CRLF // HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE ("HTTP/1.1 200 OK\r\n")
                            + "Content-Length: " + html.getBytes().length + CRLF // HEADER
                            + CRLF + html + CRLF + CRLF;

            outputStream.write(response.getBytes());
            // TODO we would read


            // TODO we would writing



            LOGGER.info("Conection completed..");
        } catch (IOException e) {
            LOGGER.error("Problem with Communication", e);
            e.printStackTrace();
        }
//        finally {
//            inputStream.close();
//            outputStream.close();
//            socket.close();
//
//
////            serverSocket.close(); // TODO Handle close

//        }

    }
}
