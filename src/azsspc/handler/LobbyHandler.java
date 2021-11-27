package azsspc.handler;

import azsspc.DBC;
import azsspc.PageReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LobbyHandler extends DefaultHandler {
    static final Pattern un = Pattern.compile("(?<=username=)\\w+?(?=$|&)"), vt = Pattern.compile("(?<=vtype=)\\w+(?=$|&)");

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = new PageReader().getPage("lobby");
        OutputStream os = t.getResponseBody();
        String request = byteArrToStr(t.getRequestBody().readAllBytes());
        System.out.println(request);
        try {
            Matcher m = un.matcher(request);
            m.find();
            String uname = m.group();
            m = vt.matcher(request);
            m.find();
            String vtype = m.group();
            System.out.println("insert into queue (name,vt) values ('" + uname + "'," + vtype + ");");
            Statement st = new DBC().connect();
            try {
                st.executeQuery("insert into queue (name,vt) values ('" + uname + "'," + vtype + ");");
            } catch (Exception ignored) {
            }
            response = "200";
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.sendResponseHeaders(200, response.length());
        os.write(response.getBytes());
        os.close();
    }
}
