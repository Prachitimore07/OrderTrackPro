import java.sql.*;
import java.util.Scanner;

public class OrderTrackPro {

    static final String URL =
            "jdbc:mysql://localhost:3306/ordertrackpro";


    static final String USER = "root";
    static final String PASSWORD = "root123";

    static Scanner sc = new Scanner(System.in);

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    // ADD PRODUCT
    public static void addProduct() {

        try {

            Connection conn = getConnection();

            System.out.print("Product Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            String sql =
                    "INSERT INTO products(product_name,price,quantity) VALUES(?,?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);

            ps.executeUpdate();

            System.out.println("Product Added Successfully");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW PRODUCTS
    public static void viewProducts() {

        try {

            Connection conn = getConnection();

            String sql = "SELECT * FROM products";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nPRODUCT LIST");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("product_id")
                        + " | "
                        + rs.getString("product_name")
                        + " | "
                        + rs.getDouble("price")
                        + " | "
                        + rs.getInt("quantity")
                );
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ADD CUSTOMER
    public static void addCustomer() {

        try {

            Connection conn = getConnection();

            System.out.print("Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            String sql =
                    "INSERT INTO customers(customer_name,email) VALUES(?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);

            ps.executeUpdate();

            System.out.println("Customer Added");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW CUSTOMERS
    public static void viewCustomers() {

        try {

            Connection conn = getConnection();

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery("SELECT * FROM customers");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("customer_id")
                        + " | "
                        + rs.getString("customer_name")
                        + " | "
                        + rs.getString("email")
                );
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CREATE ORDER
    public static void createOrder() {

        try {

            Connection conn = getConnection();

            System.out.print("Customer ID: ");
            int customerId = sc.nextInt();

            System.out.print("Product ID: ");
            int productId = sc.nextInt();

            System.out.print("Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            String createOrder =
                    "INSERT INTO orders(customer_id) VALUES(?)";

            PreparedStatement orderPs =
                    conn.prepareStatement(
                            createOrder,
                            Statement.RETURN_GENERATED_KEYS
                    );

            orderPs.setInt(1, customerId);
            orderPs.executeUpdate();

            ResultSet generated =
                    orderPs.getGeneratedKeys();

            generated.next();

            int orderId = generated.getInt(1);

            String productQuery =
                    "SELECT price,quantity FROM products WHERE product_id=?";

            PreparedStatement productPs =
                    conn.prepareStatement(productQuery);

            productPs.setInt(1, productId);

            ResultSet productRs =
                    productPs.executeQuery();

            if (productRs.next()) {

                double price =
                        productRs.getDouble("price");

                int stock =
                        productRs.getInt("quantity");

                if (stock < qty) {

                    System.out.println(
                            "Not enough stock"
                    );

                    return;
                }

                double total =
                        price * qty;

                String detailSql =
                        "INSERT INTO order_details(order_id,product_id,quantity,total_price) VALUES(?,?,?,?)";

                PreparedStatement detailPs =
                        conn.prepareStatement(detailSql);

                detailPs.setInt(1, orderId);
                detailPs.setInt(2, productId);
                detailPs.setInt(3, qty);
                detailPs.setDouble(4, total);

                detailPs.executeUpdate();

                String updateStock =
                        "UPDATE products SET quantity=quantity-? WHERE product_id=?";

                PreparedStatement updatePs =
                        conn.prepareStatement(updateStock);

                updatePs.setInt(1, qty);
                updatePs.setInt(2, productId);

                updatePs.executeUpdate();

                System.out.println(
                        "Order Created Successfully"
                );
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // SALES REPORT
    public static void salesReport() {

        try {

            Connection conn =
                    getConnection();

            String sql =
                    "SELECT SUM(total_price) AS sales FROM order_details";

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            if (rs.next()) {

                System.out.println(
                        "Total Sales: ₹"
                        + rs.getDouble("sales")
                );
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== ORDER TRACK PRO =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Add Customer");
            System.out.println("4. View Customers");
            System.out.println("5. Create Order");
            System.out.println("6. Sales Report");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addProduct();
                    break;

                case 2:
                    viewProducts();
                    break;

                case 3:
                    addCustomer();
                    break;

                case 4:
                    viewCustomers();
                    break;

                case 5:
                    createOrder();
                    break;

                case 6:
                    salesReport();
                    break;

                case 7:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}