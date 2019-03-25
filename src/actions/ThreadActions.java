package actions;

import entities.ThreadTitle;
import static utills.Utilities.sc;
import connect.SqlConnect;
import java.sql.SQLException;
import java.util.List;

public class ThreadActions {

    public ThreadActions() {
    }

    public static String printThreads() throws SQLException {
        List<ThreadTitle> titles = SqlConnect.getAllThreads();
        int i = 1;
        for (ThreadTitle title : titles) {
            System.out.println("option " + i + ": " + title.getTitle());
            i++;
        }
        System.out.println("Choose a number from above:");
        String answer = sc.nextLine();
        try {
            int index = Integer.parseInt(answer);
            index = index - 1;
            String thread = titles.get(index).getTitle();
            if (titles.get(index).getLockedStatus() == 1) {
                return "0";
            } else {
                return thread;
            }
        } catch (Exception e) {
            System.out.println("That is an incorrect option entry, please try again.");
            System.out.println("         ");
            return printThreads();
        }
    }
}
