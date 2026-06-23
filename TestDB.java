import java.sql.Connection;
public class TestDB {

    public static void main(String[] args) {

        try {

            Connection con = DBConnection.getConnection();

            if(con != null) {
                System.out.println("DATABASE CONNECTED SUCCESSFULLY ✅");
                con.close();
            } else {
                System.out.println("Connection Failed ❌");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
