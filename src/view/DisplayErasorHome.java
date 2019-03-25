/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import actions.EraserActions;
import connect.SqlConnect;
import entities.User;
import java.io.IOException;
import java.sql.SQLException;
import static utills.Utilities.sc;

/**
 *
 * @author stasi
 */
public class DisplayErasorHome extends DisplayEditorHome{

    public DisplayErasorHome() {
    }
    
     public void displayEraserMenu(String username) {
        displayEditorMenu(username);
        System.out.println("j - Delete users messages");
        System.out.println("k - Delete users posts");
    }

    public void displayEraserOptions(String username, EraserActions eraser, String option) throws SQLException, IOException {
        User user = SqlConnect.getUserByUsername(username);
        displayEditorOptions(username, eraser, option);
        switch (option) {
            case "j":
                eraser.deleteMsg(username);
                break;
            case "k":
                eraser.deletePost(username);
                break;
            default:
                if (user.getLevel() == 4) {
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    eraserMenu(username, eraser);
                    break;
                }
        }
    }

    public void eraserMenu(String username, EraserActions eraser) throws SQLException, IOException {
        displayEraserMenu(username);
        String option = sc.nextLine();
        displayEraserOptions(username, eraser, option);
    }
}
