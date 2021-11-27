package azsspc.handler;

import azsspc.DBC;
import azsspc.Main;
import azsspc.PageReader;
import azsspc.Player;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamsHandler extends DefaultHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Player[][] pset = getPlayerSets();
        String response = "where players?";
        if (pset != null) {
            response = "{";
            response += "\n\t\"firstTeam\": [";
            for (int j = 0; j < 3; j++)
                for (int i = 0; i < 3; i++)
                    response += "\n\t\t{\"nickname\":\"" + pset[j][i].name + "\", \"vehcleType\":" + pset[j][i].vt + "},";
            response += "\n\t],";
            response += "\n\t\"secondTeam\": [";
            for (int j = 0; j < 3; j++)
                for (int i = 0; i < 3; i++)
                    response += "\n\t\t{\"nickname\":\"" + pset[j][i + 3].name + "\", \"vehcleType\":" + pset[j][i + 3].vt + "},";
            response += "\n\t]\n}";
        }
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        String request = byteArrToStr(t.getRequestBody().readAllBytes());
        System.out.println(request);
        os.close();
    }

    public Player[][] getPlayerSets() {
        try {
            ResultSet rs;
            Player[][] players = new Player[3][6];
            Statement st = new DBC().connect();
            String[] id_id_queue = new String[18];

            for (int c = 1; c < 3; c++) {
                rs = st.executeQuery("select name,vt,iq from queue where vt=" + (c + 1) + " limit 6;");
                for (int i = 0; i < 6; i++) {
                    rs.next();
                    players[c][i] = new Player(rs.getString(1), Integer.parseInt(rs.getString(2)));
                    id_id_queue[6 * c + i] = rs.getString(3);
                }
            }

            StringBuilder delete = new StringBuilder();
            for (String iq : id_id_queue) delete.append(" OR iq=").append(iq);
            try {
                st.executeQuery("delete from queue where" + delete.substring(3) + ";");
            } catch (Exception ignored) {
            }
            return players;
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        //in players count (6 of 3vt) < 18
        return null;
    }
}
