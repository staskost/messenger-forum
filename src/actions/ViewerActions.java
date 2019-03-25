package actions;

import entities.Message;
import connect.SqlConnect;
import java.sql.SQLException;
import java.util.List;

public class ViewerActions extends CommonActions {

    public ViewerActions() {
    }

    public void viewMsg() throws SQLException {
        System.out.println("Choose 2 users to view their personal chat." + "\n");
        System.out.println("Choose first user:");
        String user1 = printUsers();
        System.out.println("  ");
        System.out.println("Choose second user:");
        String user2 = printUsers();
        List<Message> msgList = SqlConnect.getUsersMessages(user1, user2);
        if (msgList.isEmpty()) {
            System.out.println("Users have no messages");
        } else {
            for (Message message : msgList) {
                System.out.println(message + "\n");
            }
        }

    }
}
