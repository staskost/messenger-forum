package actions;

import entities.Message;
import entities.Post;
import entities.User;
import static utills.Utilities.sc;
import connect.SqlConnect;
import forum.Login;
import static forum.Login.goToMenu;
import java.io.IOException;
import java.sql.SQLException;
import static java.time.LocalDateTime.now;
import java.util.List;
import static utills.Utilities.writeToFile;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonActions {

    public CommonActions() {
    }

    protected String printUsers() throws SQLException {
        List<User> userList = SqlConnect.getAllUsers();
        int i = 1;
        for (User a : userList) {
            System.out.println("option " + i + ": " + a.getUsername());
            i++;
        }
        System.out.println("Choose a number from above:");
        String answer = sc.nextLine();
        try {
            int index = Integer.parseInt(answer);
            index = index - 1;
            String user = userList.get(index).getUsername();
            return user;
        } catch (Exception e) {
            System.out.println("That is an incorrect option entry, please try again." + "\n");
            return printUsers();
        }
    }

    public void chatMenu(String username) throws SQLException, IOException {
        System.out.println("Select user :");
        String receiver = printUsers();
        read(username, receiver);
        String sq = "update messages set is_read='" + 0 + "' where sender=? and receiver=?";
        SqlConnect.executeSqlUpdate(sq, receiver, username);
        int z = 0;
        while (z < 1) {
            System.out.println("Choose your option");
            System.out.println("a - Send message");
            System.out.println("b - Go back to menu");
            switch (sc.nextLine()) {
                case "a":
                    send(username, receiver);
                    break;
                case "b":
                    Login.goToMenu(username);
                    z = 2;
                    break;
                default:
                    System.out.println("That is an incorrect option entry, please try again." + "\n");
                    break;
            }
        }
    }

    public void send(String username, String receiver) throws SQLException, IOException {
        System.out.println("Type your message bellow and press enter:");
        String message = sc.nextLine();
        String sql = "insert into messages(sender,receiver,message,date,is_read)values(?,?,?,'" + now() + "','" + 1 + "');";
        SqlConnect.executeSqlUpdate(sql, username, receiver, message);
        read(username, receiver);
        writeToFile(username, receiver, message);
    }

    public void read(String username, String receiver) throws SQLException {
        List<Message> msgList2 = SqlConnect.getUsersMessages(username, receiver);
        if (msgList2.isEmpty()) {
            System.out.println("There are no messages between you and " + receiver + "\n");
        } else {
            for (Message m : msgList2) {
                System.out.println(m.getSenderUsername() + ":" + "\n" + m.getMessage() + "\n" + m.getDate() + "\n");
            }
        }

    }

    public void deletePersonalMsg(String username) throws SQLException, IOException {
        System.out.println("Choose your option");
        System.out.println("a - delete message");
        System.out.println("b - go back to menu");
        String s = sc.nextLine();
        switch (s) {
            case "a":
                System.out.println("Delete your sent messages from your chat with:");
                String receiver = printUsers();
                int msgId = TransactedDataActions.printUsersMessages2(username, receiver);
                if (msgId == -1) {
                    System.out.println("There are no messages to delete" + "\n");
                    revisitMenu(username);
                } else {
                    String sql2 = "Delete from messages where m_id=?";
                    SqlConnect.executeSqlUpdate(sql2, msgId);
                    System.out.println("   ");
                    System.out.println("Message deleted" + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                deletePersonalMsg(username);
                break;

        }
    }

    public void editAccount(String username) throws SQLException, IOException {
        System.out.println("Which field do you want to edit?");
        System.out.println("a - first name");
        System.out.println("b - last name");
        System.out.println("c - email");
        System.out.println("d - username");
        System.out.println("e - password");
        System.out.println("f - go back to menu");
        String w = sc.nextLine();
        switch (w) {
            case "a":
                System.out.println("Enter new first name:");
                String fn = sc.nextLine();
                while ((fn.isEmpty()) || (fn.matches("\\s+"))) {
                    System.out.println("All fields are required");
                    System.out.println("Enter new first name:");
                    fn = sc.nextLine();
                }
                String sql = "update users set first_name = ? where users.username = ?;";
                SqlConnect.executeSqlUpdate(sql, fn, username);
                System.out.println("First name updated to " + fn + "\n");
                break;
            case "b":
                System.out.println("Enter new last name:");
                String ln = sc.nextLine();
                while ((ln.isEmpty()) || (ln.matches("\\s+"))) {
                    System.out.println("All fields are required");
                    System.out.println("Enter new last name:");
                    ln = sc.nextLine();
                }
                String sql2 = "update users set last_name = ? where users.username = ?;";
                SqlConnect.executeSqlUpdate(sql2, ln, username);
                System.out.println("Last name updated to " + ln + "\n");
                break;
            case "c":
                System.out.println("Enter new email:");
                String em = sc.nextLine();
                while ((em.isEmpty()) || (em.matches("\\s+"))) {
                    System.out.println("All fields are required");
                    System.out.println("Enter new email:");
                    em = sc.nextLine();
                }
                String sql3 = "update users set email = ? where users.username = ?;";
                SqlConnect.executeSqlUpdate(sql3, em, username);
                System.out.println("Email updated to " + em + "\n");
                break;
            case "d":
                System.out.println("Enter new username:");
                String un = sc.nextLine();
                while ((un.isEmpty()) || (un.matches("\\s+"))) {
                    System.out.println("All fields are required");
                    System.out.println("Enter new username:");
                    un = sc.nextLine();
                }
                String sql4 = "update users set username = ? where users.username = ?;";
                SqlConnect.executeSqlUpdate(sql4, un, username);
                System.out.println("Username updated to " + un + "\n");
                break;
            case "e":
                System.out.println("Enter new password:");
                String ps = sc.nextLine();
                while ((ps.isEmpty()) || (ps.matches("\\s+"))) {
                    System.out.println("All fields are required");
                    System.out.println("Enter new password:");
                    ps = sc.nextLine();
                }
                String sql5 = "update users set password = ? where users.username = ?;";
                SqlConnect.executeSqlUpdate(sql5, ps, username);
                System.out.println("Password updated to " + ps + "\n");
                break;
            case "f":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                editAccount(username);
                break;
        }
    }

    public void seeUsers() throws SQLException {
        List<User> userList = SqlConnect.getAllUsers();
        for (User a : userList) {
            System.out.println(a.getFirstName() + " " + a.getLastName() + ", A.K.A: " + a.getUsername() + "\n");
        }
    }

    public void postMenu(String username) throws SQLException, IOException {
        String thread = ThreadActions.printThreads();
        if (thread.equals("0")) {
            System.out.println("This thread is locked" + "\n");
            revisitMenu(username);
        } else {
            readPosts(thread);
            int z = 0;
            while (z < 1) {
                System.out.println("Choose your option");
                System.out.println("a - post message");
                System.out.println("b - go back to menu");
                String yn = sc.nextLine();
                switch (yn) {
                    case "a":
                        System.out.println("Type your message bellow and press enter:");
                        String message = sc.nextLine();
                        String sql = "insert into forum_messages values(default,?,?,'" + now() + "',?);";
                        SqlConnect.executeSqlUpdate(sql, message, thread, username);
                        readPosts(thread);
                        break;
                    case "b":
                        Login.goToMenu(username);
                        z = 2;
                        break;
                    default:
                        System.out.println("That is an incorrect option entry, please try again." + "\n");
                        break;
                }
            }
        }
    }

    public void readPosts(String thread) throws SQLException {
        List<Post> postList2 = SqlConnect.getThreadPosts(thread);
        String postMsg = "";
        for (Post posts : postList2) {
            if (posts.getThread().equals(thread)) {
                postMsg += posts.getPoster() + ":" + "\n" + posts.getPost() + "\n" + posts.getDate() + "\n" + "\n";
            }
        }
        System.out.println(postMsg);
    }

    public void revisitMenu(String username) throws SQLException, IOException {
        java.util.Scanner vm = new java.util.Scanner(System.in);
        System.out.println("Choose your next step.");
        System.out.println("a - Go back to menu.");
        System.out.println("b - Exit forum.");
        String w = vm.next();
        switch (w) {
            case "a":
                goToMenu(username);
                break;
            case "b":
                exit();
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                revisitMenu(username);
                break;
        }
    }

    public void newMsg(String username) throws SQLException, IOException {
        List<Message> unreadMessages = SqlConnect.getUsersUnreadMessages(username);
        if (unreadMessages.isEmpty()) {
            System.out.println("You have no new messages" + "\n");
            revisitMenu(username);
        } else {
            if (unreadMessages.size() == 1) {
                System.out.println("You have 1 new msg" + "\n");
            } else {
                System.out.println("You have " + unreadMessages.size() + " new msgs" + "\n");
            }
            Map<String, Long> couterMap = unreadMessages.stream().collect(Collectors.groupingBy(e -> e.toString2(), Collectors.counting()));
            for (String j : couterMap.keySet()) {
                System.out.println(j + ":" + couterMap.get(j) + "\n");
            }
            int x = 0;
            while (x < 1) {
                System.out.println("a - read new messages");
                System.out.println("b - go back to menu");
                switch (sc.nextLine()) {
                    case "a":
                        x = 2;
                        String user;
                        if (couterMap.keySet().size() == 1) {
//                            for (Message unreadMessage : unreadMessages) {
//                                user = unreadMessage.getSenderUsername();
//                            }
                            user = unreadMessages.get(0).getSenderUsername();
                        } else {
                            System.out.println("Type the name of the user you want to read new messages:");
                            user = sc.nextLine();
                        }
                        int y = 0;
                        for (Message unreadMessage : unreadMessages) {
                            if (user.equals(unreadMessage.getSenderUsername())) {
                                y = 1;
                            }
                        }
                        if (y != 1) {
                            System.out.println("That is an incorrect option entry, please try again." + "\n");
                            newMsg(username);
                        } else {
                            read(username, user);
                            String sq = "update messages set is_read='" + 0 + "' where sender=? and receiver=?";
                            SqlConnect.executeSqlUpdate(sq, user, username);
                            int z = 0;
                            while (z < 1) {
                                System.out.println("choose your option");
                                System.out.println("a - Send message");
                                System.out.println("b - Go back to menu");
                                switch (sc.nextLine()) {
                                    case "a":
                                        send(username, user);
                                        break;
                                    case "b":
                                        goToMenu(username);
                                        z = 2;
                                    default:
                                        System.out.println("That is an incorrect option entry, please try again." + "\n");
                                        break;
                                }
                            }
                        }
                        break;
                    case "b":
                        goToMenu(username);
                        x = 2;
                        break;
                    default:
                        System.out.println("That is an incorrect option entry, please try again." + "\n");
                        break;
                }
            }
        }
    }

    public void exit() {
        System.out.println("See you next time.");
        System.exit(0);
    }

}
