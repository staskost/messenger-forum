package utills;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.time.LocalDateTime.now;

public class Utilities {

    public static java.util.Scanner sc = new java.util.Scanner(System.in);

    public static void createFile() throws IOException {

        String path = "C:" + File.separator + "Project_messages_Stasinos" + File.separator + "messages.txt";
        File f = new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
    }

    public static void writeToFile(String username, String receiver, String message) throws IOException {

        java.io.FileWriter fw = new java.io.FileWriter("C://Project_messages_Stasinos//messages.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        pw.print("from " + username + " to " + receiver + " message: " + message + " date: " + now() + "\n" + "\n");
        pw.close();
    }

}
