/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import actions.AdminActions;
import connect.SqlConnect;
import entities.User;
import forum.CreateUser;
import java.io.IOException;
import java.sql.SQLException;
import static utills.Utilities.sc;

/**
 *
 * @author stasi
 */
public class DisplayAdminHome extends DisplayErasorHome{
    public void displayAdminMenu(String username) {
        displayEraserMenu(username);
        System.out.println("l - View users");
        System.out.println("m - Change users level");
        System.out.println("n - Create users");
        System.out.println("o - Edit users accounts");
        System.out.println("p - Delete users");
        System.out.println("q - Bann users");
        System.out.println("r - Unbann users");
        System.out.println("s - Create new forum thread");
        System.out.println("t - Lock thread");
        System.out.println("u - Unlock thread");
        System.out.println("v - Delete forum threads");
    }

    public void displayAdminOptions(String username, AdminActions admin, String option) throws SQLException, IOException {
        User user = SqlConnect.getUserByUsername(username);
        displayEraserOptions(username, admin, option);
        switch (option) {
            case "l":
                admin.viewUsers();
                admin.revisitMenu(username);
                break;
            case "m":
                admin.changeUsersLevel(username);
                break;
            case "n":
                CreateUser.createAccount();
                admin.revisitMenu(username);
                break;
            case "o":
                admin.editAccount(username);
                admin.revisitMenu(username);
                break;
            case "p":
                admin.deleteUser(username);
                break;
            case "q":
                admin.bannUser(username);
                break;
            case "r":
                admin.unbannUser(username);
                break;
            case "s":
                admin.createThread(username);
                break;
            case "t":
                admin.lockThread(username);
                break;
            case "u":
                admin.unlockThread(username);
                break;
            case "v":
                admin.deleteThread(username);
                break;
            default:
                if (user.getLevel() == 5) {
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    adminMenu(username, admin);
                    break;
                }
        }
    }

    public void adminMenu(String username, AdminActions admin) throws SQLException, IOException {
        displayAdminMenu(username);
        String option = sc.nextLine();
        displayAdminOptions(username, admin, option);
    }
}



