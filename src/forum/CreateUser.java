package forum;

import static utills.Utilities.sc;
import connect.SqlConnect;
import encryption.CryptoConverter;
import entities.User;
import java.io.IOException;
import java.sql.SQLException;

public class CreateUser {

    public static void createAccount() throws SQLException, IOException {

        System.out.println("Enter first name:");
        String f = sc.nextLine();
        while ((f.isEmpty()) || (f.matches("\\s+"))) {
            System.out.println("All fields are required");
            System.out.println("Enter first name:");
            f = sc.nextLine();
        }
        System.out.println("Enter last name:");
        String l = sc.nextLine();
        while ((l.isEmpty()) || (l.matches("\\s+"))) {
            System.out.println("All fields are required");
            System.out.println("Enter last name:");
            l = sc.nextLine();
        }
        System.out.println("Enter email:");
        String e = sc.nextLine();
        while ((e.isEmpty()) || (e.matches("\\s+"))) {
            System.out.println("All fields are required");
            System.out.println("Enter email:");
            e = sc.nextLine();
        }
        System.out.println("Enter username:");
        String u = sc.nextLine();
        while ((u.isEmpty()) || (u.matches("\\s+"))) {
            System.out.println("All fields are required");
            System.out.println("Enter username:");
            u = sc.nextLine();
        }
        System.out.println("Enter password:");
        String ps1 = sc.nextLine();
        while ((ps1.isEmpty()) || (ps1.matches("\\s+"))) {
            System.out.println("All fields are required");
            System.out.println("Enter password:");
            ps1 = sc.nextLine();
        }
        System.out.println("Rewrite password:");
        String ps2 = sc.nextLine();
        if (ps1.equals(ps2)) {
            User user = SqlConnect.getUserByUsername(u);
            if (user != null) {
                System.out.println("This username already exists,please try another." + "\n");
                createAccount();
            } else {
                String sql = ("insert into users(first_name,last_name,email,username,password)values(?,?,?,?,?);");
                SqlConnect.executeSqlUpdate(sql, f, l, e, u,CryptoConverter.encrypt(ps1));
                System.out.println("      -Registration successfull-     ");
                System.out.println("Welcome to Developers Community Forum");
            }
        } else {
            System.out.println("Passwords have to match,please try again." + "\n");
            createAccount();
        }

    }
}
