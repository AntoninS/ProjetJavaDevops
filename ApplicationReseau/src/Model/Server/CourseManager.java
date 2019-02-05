package Model.Server;

import Model.common.course.ThreadCourse;

public class CourseManager {

    private ThreadCourse course;
    private Server server;

    public CourseManager() {

    }


    public void startCourse(Server server) {
        this.server = server;
        course = new ThreadCourse("FCKebab", server);
        Thread courseThread = new Thread(course);
        courseThread.start();
    }

}
