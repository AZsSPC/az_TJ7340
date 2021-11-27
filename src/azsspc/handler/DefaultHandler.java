package azsspc.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultHandler implements HttpHandler {

    public static String byteArrToStr(byte... bytes) {
        StringBuilder ret = new StringBuilder();
        for (byte b : bytes) ret.append((char) b);
        return ret.toString();
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "default response";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

