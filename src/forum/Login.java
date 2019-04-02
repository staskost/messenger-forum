package forum;

import actions.AdminActions;
import actions.EditorActions;
import actions.EraserActions;
import actions.CommonActions;
import actions.ViewerActions;
import entities.User;
import static utills.Utilities.sc;
import connect.SqlConnect;
import encryption.CryptoConverter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import view.DisplayAdminHome;
import view.DisplayEditorHome;
import view.DisplayHome;
import view.DisplayViewerHome;
import view.DisplayErasorHome;

public class Login {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement prs;

    public static void login() throws IOException, SQLException {

        System.out.println("Enter username:");
        String un = sc.nextLine();
        System.out.println("Enter password:");
        String p = sc.nextLine();
        String crpwd = CryptoConverter.encrypt(p);

        User user = SqlConnect.getUser(un, crpwd);
        if (user != null) {
            goToMenu(un);
        } else {
            System.out.println("Invalid username or password." + "\n");
            login();
        }

    }

    public static void goToMenu(String username) throws SQLException, IOException {
        CommonActions common = new CommonActions();
        ViewerActions viewer = new ViewerActions();
        EditorActions editor = new EditorActions();
        EraserActions eraser = new EraserActions();
        AdminActions admin = new AdminActions();
        DisplayHome dh = new DisplayHome();
        DisplayViewerHome dvh = new DisplayViewerHome();
        DisplayEditorHome dedh = new DisplayEditorHome();
        DisplayErasorHome derh = new DisplayErasorHome();
        DisplayAdminHome dah = new DisplayAdminHome();

        ResultSet rs;

        User user = SqlConnect.getUserByUsername(username);
        int bannedStatus = user.getBannedStatus();
        if (bannedStatus == 1) {
            System.out.println("You are banned from this forum!");
            System.out.println("        -ACCESS DENIED-        ");
        } else {
            String sql1 = "select level from users where username=?";
            rs = SqlConnect.executeSqlQuery(sql1, username);
            int level = 0;
            while (rs.next()) {
                level = rs.getInt("level");
            }
            switch (level) {
                case 1:
                    dh.regularMenu(username, common);
                    break;
                case 2:
                    dvh.viewerMenu(username, viewer);
                    break;
                case 3:
                    dedh.editorMenu(username, editor);
                    break;
                case 4:
                    derh.eraserMenu(username, eraser);
                    break;
                case 5:
                    dah.adminMenu(username, admin);
                    break;
                default:
                    break;
            }
        }
    }

}
