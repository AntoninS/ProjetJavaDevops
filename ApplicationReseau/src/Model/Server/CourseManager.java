package Model.Server;

import Model.common.Message;

public class CourseManager {

    public CourseManager() {

    }

    public void startCourse(Server server) {
        sendPositions(server);
    }

    public void sendPositions(Server server) {
        Message mess = new Message("Controller/client", "WOUHOUUUUU");
        server.broadcastMessage(mess, -1);
    }
}
