package ui;

public class Main {
    public static void main(String[] args) {
//        new NotetakingApp();
//        Subject s = new Subject("yessir");
//        Course c = new Course("hello",s);
//        c.addTopic("yellow");
//        ArrayList<Topic> ohTopics = c.getTopics();
//        Topic t = ohTopics.get(0);
//        Course parentCourseCourse = t.getParentCourse();
//        System.out.println(parentCourseCourse.getCourseName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotetakingAppGUI notetakingAppGUI = new NotetakingAppGUI();
                notetakingAppGUI.createAndShowGUI();
            }
        });
    }
}
