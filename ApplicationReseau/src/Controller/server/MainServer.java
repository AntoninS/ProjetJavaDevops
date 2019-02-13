package Controller.server;

import Model.Server.CourseManager;
import Model.Server.Server;

import java.io.IOException;
import java.util.Scanner;

public class MainServer {

    private static String START_COURSE_MSG = "start";

    /**
     * creates a new Controller.server
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        try {
            Server server = null;
            if (args.length != 1) {
                printUsage();

                Integer port = new Integer(1420);
                server = new Server(port);
            } else {
                Integer port = new Integer(args[0]);
                server = new Server(port);
            }


            if (null != server) {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    System.out.print("TAPEZ 'start' pour lancer une course >> ");
                    String m = sc.nextLine();
                    if (m.equals(START_COURSE_MSG)) {
                        CourseManager cm = new CourseManager();
                        cm.startCourse(server);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainServer(int port) {
        try {
            if (port <= 1024) {
                printUsage();
            } else {
                Server server = new Server(port);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("java Model.Server.Server <port>");
        System.out.println("\t<port>: Controller.server's port");
    }

}
