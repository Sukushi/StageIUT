package ALL;

import java.sql.*;

public abstract class BDD {

    private String path;

    public BDD(String path) {
        this.path = path;
    }

    /**
     * Lance la connexion SQLite
     * @return la connexion ouverte
     */
    protected Connection connect() {
        String url = "jdbc:sqlite:" + this.path;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * renvoie le dernier indice ajouter à DISPOSITIF
     * @return le dernier indice
     */
    public int getLastRowIDOf(String table) {
        String query = "SELECT MAX(id) AS LAST FROM ?";
        int rowid=-1;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,table);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rowid = Integer.valueOf(rs.getString("LAST"));

            }
            /*pstmt.execute();
            pstmt.executeUpdate();*/
        } catch (SQLException e) {
            System.out.println(e);
        }
        return rowid;
    }
}
