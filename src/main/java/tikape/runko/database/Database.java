package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE aihealue (id integer PRIMARY KEY, aihe varchar(255));");
        lista.add("INSERT INTO aihealue (aihe) VALUES ('autot');");
        lista.add("INSERT INTO aihealue (aihe) VALUES ('eläimet');");
        lista.add("INSERT INTO aihealue (aihe) VALUES ('harrastukset');");
        lista.add("INSERT INTO aihealue (aihe) VALUES ('koodaus');");

        lista.add("CREATE TABLE keskustelu (id integer PRIMARY KEY, aiheid integer FOREIGN KEY REFERENCES aihealue(id), nimimerkki varchar(255), luotu timestamp);");
        
        lista.add("CREATE TABLE viesti (id integer PRIMARY KEY, keskusteluid integer FOREIGN KEY REFERENCES keskustelu(id), nimimerkki varchar(255), luotu timestamp);");
         
        
        
        return lista;
    }
}
