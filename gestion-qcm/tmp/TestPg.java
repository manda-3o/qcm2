import java.sql.Connection;
import java.sql.DriverManager;

public class TestPg {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestion_qcm", "postgres", "postgress1847")) {
                System.out.println("CONNECTED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
