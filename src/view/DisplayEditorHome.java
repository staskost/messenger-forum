/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import actions.EditorActions;
import connect.SqlConnect;
import entities.User;
import java.io.IOException;
import java.sql.SQLException;
import static utills.Utilities.sc;

/**
 *
 * @author stasi
 */
public class DisplayEditorHome extends DisplayViewerHome{

    public DisplayEditorHome() {
    }
    
     public void displayEditorMenu(String username) {
        displayViewerMenu(username);
        System.out.println("h - Edit users messages");
        System.out.println("i - Edit users posts");
    }

    public void displayEditorOptions(String username, EditorActions editor, String option) throws SQLException, IOException {
        User user = SqlConnect.getUserByUsername(username);
        displayViewewrOptions(username, editor, option);
        switch (option) {
            case "h":
                editor.editMsg(username);
                break;
            case "i":
                editor.editPost(username);
                break;
            default:
                if (user.getLevel() == 3) {
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    editorMenu(username, editor);
                    break;
                }
        }
    }

    public void editorMenu(String username, EditorActions editor) throws SQLException, IOException {
        displayEditorMenu(username);
        String option = sc.nextLine();
        displayEditorOptions(username, editor, option);

    }
}
