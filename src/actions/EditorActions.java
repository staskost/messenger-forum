package actions;

import static utills.Utilities.sc;
import connect.SqlConnect;
import forum.Login;
import java.io.IOException;
import java.sql.SQLException;

public class EditorActions extends ViewerActions {

    public EditorActions() {
    }

    public void editMsg(String username) throws SQLException, IOException {
        System.out.println("a - Edit messages");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Select 2 users to edit their personal chat." + "\n");
                System.out.println("Choose first user :");
                String user1 = printUsers();
                System.out.println("  ");
                System.out.println("Choose second user :");
                String user2 = printUsers();
                System.out.println("Which message do you want to edit?" + "\n");
                int msgId = TransactedDataActions.printUsersMessages(user1, user2);
                if (msgId == -1) {
                    System.out.println(user1 + " and " + user2 + " have no messages between them to edit.");
                    System.out.println("   ");
                    revisitMenu(username);
                } else {
                    System.out.println("Rewrite message here:");
                    String newMsg = sc.nextLine();
                    String sql = "update messages set message = ? where messages.m_id = ?";
                    SqlConnect.executeSqlUpdate(sql, newMsg, msgId);
                    System.out.println(" ");
                    System.out.println("Message edited." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                editMsg(username);
                break;
        }
    }

    public void editPost(String username) throws SQLException, IOException {
        System.out.println("a - Edit posts");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Select forum thread to edit posts");
                String thread = ThreadActions.printThreads();
                int msgId = TransactedDataActions.printUsersPosts(thread);
                if (msgId == -1) {
                    System.out.println("There are no posts to edit" + "\n");
                    revisitMenu(username);
                } else if (msgId == -2) {
                    System.out.println("The thread is locked" + "\n");
                    revisitMenu(username);
                } else {
                    System.out.println("Rewrite message here:");
                    String newMsg = sc.nextLine();
                    String sql = "update forum_messages set forum_messages = ? where m_id = ? ";
                    SqlConnect.executeSqlUpdate(sql, newMsg, msgId);
                    System.out.println(" ");
                    System.out.println("Post edited." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                editPost(username);
                break;
        }

    }

}
