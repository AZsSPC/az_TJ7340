package azsspc;

import azsspc.handler.LobbyHandler;
import azsspc.handler.TeamsHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class Main {
    public static void main(String... a) throws IOException, SQLException {
        HttpServer server = HttpServer.create(new InetSocketAddress(2180), 0);
        server.createContext("/lobby", new LobbyHandler());
        server.createContext("/teams/generate", new TeamsHandler());
        server.setExecutor(null);
        server.start();
        //Statement st = new DBC().connect();
        //st.executeQuery("create table queue (iq serial primary key,name text,vt int);");
        //st.executeQuery("drop table queue;");
    }


    public static String readFile(String str_path) {
        try {
            return Files.readString(Path.of(str_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}