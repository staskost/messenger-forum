package forum;


import static utills.Utilities.sc;
import java.io.IOException;
import java.sql.SQLException;

public class Start {

    public static void startMenu() throws IOException, SQLException {

        System.out.println("WELCOME TO DEVELOPERS COMMUNITY FORUM."+"\n");
        System.out.println("Choose an option from the following:");
        System.out.println("a - Login");
        System.out.println("b - Create account");
        System.out.println("c - Exit forum");
        System.out.print("Your option? ");
        String a = sc.nextLine();
        switch (a) {
            case "a":
                Login.login();
                break;
            case "b":
                CreateUser.createAccount();
                break;
            case "c":
                System.out.println("See you next time.");
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again."+"\n");
                startMenu();
                break;
        }
    }

}
