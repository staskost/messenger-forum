package actions;

import static utills.Utilities.sc;
import connect.SqlConnect;
import forum.Login;
import java.io.IOException;
import java.sql.SQLException;

public class EraserActions extends EditorActions {

    public EraserActions() {
    }

    public void deleteMsg(String username) throws SQLException, IOException {
        System.out.println("a - Delete messages");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Select 2 users to delete their personal chat." + "\n");
                System.out.println("Choose first user :");
                String user1 = printUsers();
                System.out.println("  ");
                System.out.println("Choose second user :");
                String user2 = printUsers();
                System.out.println("Which message do you want to delete?" + "\n");
                int msgId = TransactedDataActions.printUsersMessages(user1, user2);
                if (msgId == -1) {
                    System.out.println(user1 + " and " + user2 + " have no messages between them to delete.");
                    System.out.println("   ");
                    revisitMenu(username);
                } else {
                    String sql = "delete from messages where messages.m_id = ?";
                    SqlConnect.executeSqlUpdate(sql, msgId);
                    System.out.println(" ");
                    System.out.println("Message deleted." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                deleteMsg(username);
                break;
        }
    }

    public void deletePost(String username) throws SQLException, IOException {
        System.out.println("a - Delete posts");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Select forum thread to delete posts");
                String thread = ThreadActions.printThreads();
                int msgId = TransactedDataActions.printUsersPosts(thread);
                if (msgId == -1) {
                    System.out.println("There are no posts to delete" + "\n");
                    revisitMenu(username);
                } else if (msgId == -2) {
                    System.out.println("The thread is locked" + "\n");
                    revisitMenu(username);
                } else {
                    String sql = "delete from forum_messages where m_id=?";
                    SqlConnect.executeSqlUpdate(sql, msgId);
                    System.out.println(" ");
                    System.out.println("Post deleted." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                deletePost(username);
                break;
        }

    }

}
