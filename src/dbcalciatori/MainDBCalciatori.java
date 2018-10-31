package dbcalciatori;

//Applimport java.sql.Connection;icazione per accedere ad un databse già presente.
import com.mysql.cj.protocol.Resultset;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainDBCalciatori {

    public static void main(String[] args) {

        System.out.println("Connecting database...");
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/test?"
                + "zeroDateTimeBehavior = convertToNull"
                + "&serverTimezone = UTC";
        try {
            con = DriverManager.getConnection(url, "root", "reddragon");
            System.out.println("Database connected!");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Database non connesso!");
        }
        try {
            inserisciCalciatore(con, "Antonio", "Inter");
            if(cancellaCalciatore(con, "Antonio"))
            visualizzaTabella(con, "calciatori");
            
            //dove chiamare i comandi SQL
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    static void inserisciCalciatore(Connection con, String nomeCalciatore, String nomeSquadra) throws SQLException {
        Statement stmt = null;
        String SQL = "INSERT INTO Calciatori(name, squadra) VALUES ('" + nomeCalciatore + "','" + nomeSquadra + "');";
        System.out.println(SQL);
        try {
            stmt = con.createStatement();
            int n = stmt.executeUpdate(SQL);
            System.out.println("Inserite/a" + n + "righe/a");
        } catch (SQLException e) {
            System.out.println(e);

        }

    }

    static void visualizzaTabella(Connection con, String nomeTabella) {
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * FROM " + nomeTabella + ";";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {

                String nome;
                nome = rs.getString("name");
                String squadra = rs.getString("squadra");
                System.out.println("- " + nome + " " + squadra + " ");
            }
        } catch (SQLException ex) {
            System.out.println("Errore nel nome della tabella");
            System.out.println(ex);
        }
    }

    static boolean cancellaCalciatore(Connection con, String nome) {

        Statement stmt = null;
        int n;
        String SQL = "DELETE FROM calciatori WHERE NAME='" + nome + "';";
        try {
            stmt = con.createStatement();
             n=stmt.executeUpdate(SQL);
        } catch (SQLException ex) {
            System.out.println("Cancellazione non riuscita");
            return false;
        }
        System.out.println("Cncellata/e"+ n+"riga/e");
        return true;

    }

}
