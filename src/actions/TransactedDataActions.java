package actions;

import entities.Message;
import entities.Post;
import static utills.Utilities.sc;
import connect.SqlConnect;
import java.sql.SQLException;
import java.util.List;

public class TransactedDataActions {

    public TransactedDataActions() {
    }

    public static int printUsersPosts(String thread) throws SQLException {

        List<Post> postList = SqlConnect.getThreadPosts(thread);
        if (thread.equals("0")) {
            return -2;
        } else if (postList.isEmpty()) {
            return -1;
        } else {
            int j = 1;
            for (Post posts : postList) {
                System.out.println("option " + j + ": from: " + posts.getPoster() + "  ,post: " + posts.getPost() + "  ,date" + posts.getDate() + "\n");
                j++;
            }
            System.out.println("Choose a number from above:");
            try {
                String answer2 = sc.nextLine();
                int index2 = Integer.parseInt(answer2);
                index2 = index2 - 1;
                int msgId = postList.get(index2).getId();
                return msgId;
            } catch (Exception e) {
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                return printUsersPosts(thread);
            }
        }
    }

    public static int printUsersMessages(String user1, String user2) throws SQLException {

        List<Message> msgList = SqlConnect.getUsersMessages(user1, user2);
        if (msgList.isEmpty()) {
            return -1;
        } else {
            int j = 1;
            for (Message m : msgList) {
                System.out.println("option " + j + ": " + m);
                j++;
            }
            System.out.println("Choose a number from above:");
            try {
                String answer2 = sc.nextLine();
                int index2 = Integer.parseInt(answer2);
                index2 = index2 - 1;
                int msgId = msgList.get(index2).getId();
                return msgId;
            } catch (Exception e) {
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                return TransactedDataActions.printUsersMessages(user1, user2);
            }
        }
    }

    public static int printUsersMessages2(String user, String receiver) throws SQLException {

        List<Message> msgList = SqlConnect.getUsersSentMessages(user, receiver);
        if (msgList.isEmpty()) {
            return -1;
        } else {
            int j = 1;
            for (Message m : msgList) {
                System.out.println("option " + j + ": " + m);
                j++;
            }
            System.out.println("Choose a number from above:");
            try {
                String answer2 = sc.nextLine();
                int index2 = Integer.parseInt(answer2);
                index2 = index2 - 1;
                int msgId = msgList.get(index2).getId();
                return msgId;
            } catch (Exception e) {
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                return printUsersMessages2(user, receiver);
            }
        }
    }
}
