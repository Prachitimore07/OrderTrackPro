public class TestDriver {

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("MYSQL DRIVER FOUND ✅");

        } catch (Exception e) {

            System.out.println("MYSQL DRIVER NOT FOUND ❌");
            e.printStackTrace();
        }
    }
}