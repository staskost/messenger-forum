/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import actions.ViewerActions;
import connect.SqlConnect;
import entities.User;
import java.io.IOException;
import java.sql.SQLException;
import static utills.Utilities.sc;

/**
 *
 * @author stasi
 */
public class DisplayViewerHome extends DisplayHome{

    public DisplayViewerHome() {
    }
    
    public void displayViewerMenu(String username) {
        displaySimpleMenu(username);
        System.out.println("g - View users messages");
    }

    public void displayViewewrOptions(String username, ViewerActions viewer, String option) throws SQLException, IOException {
        User user = SqlConnect.getUserByUsername(username);
        displaySimpleOptions(username, viewer, option);
        switch (option) {
            case "g":
                viewer.viewMsg();
                viewer.revisitMenu(username);
                break;
            default:
                if (user.getLevel() == 2) {
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    viewerMenu(username, viewer);
                    break;
                }
        }
    }

    public void viewerMenu(String username, ViewerActions viewer) throws SQLException, IOException {
        displayViewerMenu(username);
        String option = sc.nextLine();
        displayViewewrOptions(username, viewer, option);
    }

}
