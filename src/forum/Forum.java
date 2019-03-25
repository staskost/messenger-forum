package forum;


import utills.Utilities;
import java.io.IOException;
import java.sql.SQLException;

public class Forum {
    
   
    public static void main(String[] args) throws SQLException, IOException {
        Utilities.createFile();
        Start.startMenu();

    }
}
