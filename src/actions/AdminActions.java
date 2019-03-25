package actions;

import entities.ThreadTitle;
import entities.User;
import static utills.Utilities.sc;
import connect.SqlConnect;
import forum.Login;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminActions extends EraserActions {

    public AdminActions() {
    }

    public void deleteUser(String username) throws SQLException, IOException {
        System.out.println("a - Delete user");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                String user = printUsers();
                if (user.equals("admin")) {
                    System.out.println("Sorry you can not delete your own account.");
                    deleteUser(username);
                } else {
                    String sql = "delete from users where username=?";
                    SqlConnect.executeSqlUpdate(sql, user);
                    System.out.println(" ");
                    System.out.println("User deleted." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                deleteUser(username);
                break;
        }

    }

    public void viewUsers() throws SQLException {
        List<User> userList = SqlConnect.getAllUsers();
        for (User a : userList) {
            System.out.println("FIRST NAME:" + a.getFirstName() + "  LAST NAME:" + a.getLastName() + "  EMAIL:" + a.getEmail()
                    + "  USERNAME:" + a.getUsername() + "  LEVEL:" + a.getLevel() + "  BANNED STATUS:" + a.getBannedStatus() + "\n");
        }

    }

    public void changeUsersLevel(String username) throws SQLException, IOException {
        System.out.println("a - Change users level");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Which user's level do you want to change?");
                String user = printUsers();
                if (user.equals("admin")) {
                    System.out.println("You can not change your own level,please try another user" + "\n");
                    changeUsersLevel(username);
                } else {
                    System.out.println("What level do you want to make user " + user + "?");
                    System.out.println("Level-1-Regular user");
                    System.out.println("Level-2-Viewer moderator");
                    System.out.println("Level-3-Editor moderator");
                    System.out.println("Level-4-Eraser moderator");
                    System.out.println("Choose a number from above");
                    int level = sc.nextInt();
                    if ((level < 1) || (level > 4)) {
                        System.out.println("Your choice has to be between 0,1,2,3 and 4.Please try again." + "\n");
                        changeUsersLevel(username);
                    } else {
                        String sql = "update users set level =? where users.username=?";
                        SqlConnect.executeSqlUpdate(sql, level, user);
                        System.out.println(" ");
                        System.out.println("The level of user :" + user + " has now changed to level " + level + "\n");
                        revisitMenu(username);
                    }
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                changeUsersLevel(username);
                break;

        }
    }

    public void createThread(String username) throws SQLException, IOException {
        System.out.println("a - Create thread");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Enter thread title:");
                String s = sc.nextLine();
                while ((s.isEmpty()) || (s.matches("\\s+"))) {
                    System.out.println("Title can not be empty.");
                    System.out.println("Enter thread title:");
                    s = sc.nextLine();
                }
                int x = 0;
                List<ThreadTitle> titles = SqlConnect.getAllThreads();
                for (ThreadTitle title : titles) {
                    if (s.equals(title.getTitle())) {
                        x = 1;
                        break;
                    }
                }
                if (x == 1) {
                    System.out.println(" ");
                    System.out.println("Please select another title,this title already exists" + "\n");
                    createThread(username);
                } else {
                    String sql = "insert into forum_title values(?,default)";
                    SqlConnect.executeSqlUpdate(sql, s);
                    System.out.println("  ");
                    System.out.println("Forum thread " + s + " created." + "\n");
                    revisitMenu(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                createThread(username);
                break;

        }
    }

    public void editUser(String username) throws IOException, SQLException {
        System.out.println("a - Edit users account");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Choose the username of the user you want to update:");
                String un = sc.nextLine();
                User user = SqlConnect.getUserByUsername(un);
                if (user == null) {
                    System.out.println("Incorrect username,please try again" + "\n");
                    editUser(username);
                } else {
                    editAccount(user.getUsername());
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                editUser(username);
                break;

        }
    }

    public void deleteThread(String username) throws SQLException, IOException {
        System.out.println("a - delete thread");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Select forum thread to see or post your message");
                String thread = ThreadActions.printThreads();
                String sql = "delete from forum_title where title=?";
                SqlConnect.executeSqlUpdate(sql, thread);
                System.out.println(" ");
                System.out.println("Thread deleted." + "\n");
                revisitMenu(username);
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                deleteThread(username);
                break;
        }
    }

    public void bannUser(String username) throws SQLException, IOException {
        System.out.println("a - Bann user");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Enter the username of the user you want to bann from the Forum.");
                String user = sc.nextLine();
                User u = SqlConnect.getUserByUsername(user);
                if (u == null) {
                    System.out.println("Incorrect username,please try again" + "\n");
                    bannUser(username);
                } else {
                    if (u.getBannedStatus() == 0) {
                        String sql = "update users set banned_status =? where users.username=?";
                        SqlConnect.executeSqlUpdate(sql, 1, user);
                        System.out.println("   ");
                        System.out.println("User " + user + " is now banned from the Forum" + "\n");
                        revisitMenu(username);
                    } else if (u.getBannedStatus() == 1) {
                        System.out.println("User " + user + " is already banned" + "\n");
                        revisitMenu(username);
                    }
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                bannUser(username);
                break;
        }

    }

    public void unbannUser(String username) throws SQLException, IOException {
        System.out.println("a - Unbann user");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                List<User> bannedUsers = SqlConnect.getBannedUsers();
                if (bannedUsers.isEmpty()) {
                    System.out.println("There are no banned users" + "\n");
                    revisitMenu(username);
                } else {
                    System.out.println("Select the user you want to unbann");
                    for (int i = 1; i <= bannedUsers.size(); i++) {
                        System.out.println("option " + i + ": " + bannedUsers.get(i - 1).getUsername());
                    }
                    String answer = sc.nextLine();
                    try {
                        int index = Integer.parseInt(answer);
                        index = index - 1;
                        User user = bannedUsers.get(index);
                        String sql = "update users set banned_status =? where users.username=?";
                        SqlConnect.executeSqlUpdate(sql, 0, user.getUsername());
                        System.out.println("   ");
                        System.out.println("User " + user.getUsername() + " is now unbanned from the Forum" + "\n");
                        revisitMenu(username);
                    } catch (Exception e) {
                        System.out.println("That is an incorrect option entry, please try again." + "\n");
                        unbannUser(username);
                    }
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                unbannUser(username);
                break;
        }

    }

    public void lockThread(String username) throws SQLException, IOException {
        System.out.println("a - Lock Thread");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                System.out.println("Enter the title of the thread you want to lock.");
                String thread = sc.nextLine();
                int j = 0;
                List<ThreadTitle> threadList = SqlConnect.getAllThreads();
                for (ThreadTitle a : threadList) {
                    if ((a.getTitle().equals(thread)) && (a.getLockedStatus() == 0)) {
                        j = 1;
                        break;
                    }
                    if ((a.getLockedStatus() == 1) && (a.getTitle().equals(thread))) {
                        j = 2;
                        break;
                    }
                }
                if (j == 2) {
                    System.out.println("Thread " + thread + " is already locked." + "\n");
                    revisitMenu(username);
                } else if (j == 1) {
                    String sql = "update forum_title set locked_status =? where title=?";
                    SqlConnect.executeSqlUpdate(sql, 1, thread);
                    System.out.println("   ");
                    System.out.println("Thread " + thread + " is now locked" + "\n");
                    revisitMenu(username);
                } else {
                    System.out.println("Incorrect thread title,please try again" + "\n");
                    lockThread(username);
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                lockThread(username);
                break;
        }
    }

    public void unlockThread(String username) throws SQLException, IOException {
        System.out.println("a - Unlock thread");
        System.out.println("b - Go back to menu");
        switch (sc.nextLine()) {
            case "a":
                List<String> lockedThreads = new ArrayList();
                List<ThreadTitle> threadList = SqlConnect.getAllThreads();
                for (ThreadTitle a : threadList) {
                    if (a.getLockedStatus() == 1) {
                        lockedThreads.add(a.getTitle());
                    }
                }
                if (lockedThreads.isEmpty()) {
                    System.out.println("There are no locked threads" + "\n");
                    revisitMenu(username);
                } else {
                    System.out.println("Select the thread you want to unlock");
                    for (int i = 1; i <= lockedThreads.size(); i++) {
                        System.out.println("option " + i + ": " + lockedThreads.get(i - 1));
                    }
                    String answer = sc.nextLine();
                    try {
                        int index = Integer.parseInt(answer);
                        index = index - 1;
                        String thread = lockedThreads.get(index);
                        String sql = "update forum_title set locked_status =? where title=?";
                        SqlConnect.executeSqlUpdate(sql, 0, thread);
                        System.out.println("   ");
                        System.out.println("Thread " + thread + " is now unlocked" + "\n");
                        revisitMenu(username);
                    } catch (Exception e) {
                        System.out.println("That is an incorrect option entry, please try again." + "\n");
                        unlockThread(username);
                    }
                }
                break;
            case "b":
                Login.goToMenu(username);
                break;
            default:
                System.out.println("That is an incorrect option entry, please try again." + "\n");
                unlockThread(username);
                break;
        }
    }
}
