/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import actions.AdminActions;
import actions.CommonActions;
import actions.EditorActions;
import actions.EraserActions;
import actions.ViewerActions;
import connect.SqlConnect;
import entities.Message;
import entities.User;
import forum.CreateUser;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static utills.Utilities.sc;

/**
 *
 * @author stasi
 */
public class DisplayHome {

    public DisplayHome() {
    }

    public void displaySimpleMenu(String username) {
        List<Message> unreadMessages = SqlConnect.getUsersUnreadMessages(username);
        System.out.println("Choose an option from the following:");
        System.out.println("a - New messages" + "(" + unreadMessages.size() + ")");
        System.out.println("b - Read/Send messages with users");
        System.out.println("c - Delete personal sent messages");
        System.out.println("d - Read users posts and post your own");
        System.out.println("e - Edit your account");
        System.out.println("f - Exit developers community forum");
    }

    public void displaySimpleOptions(String username, CommonActions common, String option) throws SQLException, IOException {
        User user = SqlConnect.getUserByUsername(username);
        switch (option) {
            case "a":
                common.newMsg(username);
                break;
            case "b":
                common.chatMenu(username);
                break;
            case "c":
                common.deletePersonalMsg(username);
                break;
            case "d":
                common.postMenu(username);
                break;
            case "e":
                common.editAccount(username);
                common.revisitMenu(username);
                break;
            case "f":
                common.exit();
                break;
            default:
                if (user.getLevel() == 1) {
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    regularMenu(username, common);
                    break;
                }
        }
    }

    public void regularMenu(String username, CommonActions common) throws SQLException, IOException {
        displaySimpleMenu(username);
        String option = sc.nextLine();
        displaySimpleOptions(username, common, option);
    }

//    public void displayViewerMenu(String username) {
//        displaySimpleMenu(username);
//        System.out.println("g - View users messages");
//    }
//
//    public void displayViewewrOptions(String username, ViewerActions viewer, String option) throws SQLException, IOException {
//        User user = SqlConnect.getUserByUsername(username);
//        displaySimpleOptions(username, viewer, option);
//        switch (option) {
//            case "g":
//                viewer.viewMsg();
//                viewer.revisitMenu(username);
//                break;
//            default:
//                if (user.getLevel() == 2) {
//                    System.out.println("That is an incorrect option entry, please try again." + "\n");
//                    viewerMenu(username, viewer);
//                    break;
//                }
//        }
//    }
//
//    public void viewerMenu(String username, ViewerActions viewer) throws SQLException, IOException {
//        displayViewerMenu(username);
//        String option = sc.nextLine();
//        displayViewewrOptions(username, viewer, option);
//    }
//
//    public void displayEditorMenu(String username) {
//        displayViewerMenu(username);
//        System.out.println("h - Edit users messages");
//        System.out.println("i - Edit users posts");
//    }
//
//    public void displayEditorOptions(String username, EditorActions editor, String option) throws SQLException, IOException {
//        User user = SqlConnect.getUserByUsername(username);
//        displayViewewrOptions(username, editor, option);
//        switch (option) {
//            case "h":
//                editor.editMsg(username);
//                break;
//            case "i":
//                editor.editPost(username);
//                break;
//            default:
//                if (user.getLevel() == 3) {
//                    System.out.println("That is an incorrect option entry, please try again." + "\n");
//                    editorMenu(username, editor);
//                    break;
//                }
//        }
//    }
//
//    public void editorMenu(String username, EditorActions editor) throws SQLException, IOException {
//        displayEditorMenu(username);
//        String option = sc.nextLine();
//        displayEditorOptions(username, editor, option);
//
//    }
//
//    public void displayEraserMenu(String username) {
//        displayEditorMenu(username);
//        System.out.println("j - Delete users messages");
//        System.out.println("k - Delete users posts");
//    }
//
//    public void displayEraserOptions(String username, EraserActions eraser, String option) throws SQLException, IOException {
//        User user = SqlConnect.getUserByUsername(username);
//        displayEditorOptions(username, eraser, option);
//        switch (option) {
//            case "j":
//                eraser.deleteMsg(username);
//                break;
//            case "k":
//                eraser.deletePost(username);
//                break;
//            default:
//                if (user.getLevel() == 4) {
//                    System.out.println("That is an incorrect option entry, please try again." + "\n");
//                    eraserMenu(username, eraser);
//                    break;
//                }
//        }
//    }
//
//    public void eraserMenu(String username, EraserActions eraser) throws SQLException, IOException {
//        displayEraserMenu(username);
//        String option = sc.nextLine();
//        displayEraserOptions(username, eraser, option);
//    }
//
//    public void displayAdminMenu(String username) {
//        displayEraserMenu(username);
//        System.out.println("l - View users");
//        System.out.println("m - Change users level");
//        System.out.println("n - Create users");
//        System.out.println("o - Edit users accounts");
//        System.out.println("p - Delete users");
//        System.out.println("q - Bann users");
//        System.out.println("r - Unbann users");
//        System.out.println("s - Create new forum thread");
//        System.out.println("t - Lock thread");
//        System.out.println("u - Unlock thread");
//        System.out.println("v - Delete forum threads");
//    }
//
//    public void displayAdminOptions(String username, AdminActions admin, String option) throws SQLException, IOException {
//        User user = SqlConnect.getUserByUsername(username);
//        displayEditorOptions(username, admin, option);
//        switch (option) {
//            case "l":
//                admin.viewUsers();
//                admin.revisitMenu(username);
//                break;
//            case "m":
//                admin.changeUsersLevel(username);
//                break;
//            case "n":
//                CreateUser.createAccount();
//                admin.revisitMenu(username);
//                break;
//            case "o":
//                admin.editAccount(username);
//                admin.revisitMenu(username);
//                break;
//            case "p":
//                admin.deleteUser(username);
//                break;
//            case "q":
//                admin.bannUser(username);
//                break;
//            case "r":
//                admin.unbannUser(username);
//                break;
//            case "s":
//                admin.createThread(username);
//                break;
//            case "t":
//                admin.lockThread(username);
//                break;
//            case "u":
//                admin.unlockThread(username);
//                break;
//            case "v":
//                admin.deleteThread(username);
//                break;
//            default:
//                if (user.getLevel() == 5) {
//                    System.out.println("That is an incorrect option entry, please try again." + "\n");
//                    adminMenu(username, admin);
//                    break;
//                }
//        }
//    }
//
//    public void adminMenu(String username, AdminActions admin) throws SQLException, IOException {
//        displayAdminMenu(username);
//        String option = sc.nextLine();
//        displayAdminOptions(username, admin, option);
//    }
}
