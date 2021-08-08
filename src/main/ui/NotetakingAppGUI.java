package ui;

import model.Course;
import model.Note;
import model.Subject;
import model.Topic;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NotetakingAppGUI extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/notes.json";
    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";
    private static String LOAD_COMMAND = "load";
    private static String SAVE_COMMAND = "save";
    private static int GUI_WIDTH = 300;
    private static int GUI_HEIGHT = 150;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Note note;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private NotetakingAppTree treePanel;

    // runs the notetaking app GUI
    public NotetakingAppGUI() {
        super(new BorderLayout());

        initFields();

        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);

        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand(LOAD_COMMAND);
        loadButton.addActionListener(this);

        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand(SAVE_COMMAND);
        saveButton.addActionListener(this);

        treePanel.setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        add(treePanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0,4));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(loadButton);
        panel.add(saveButton);
        add(panel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields for NotetakingAppGUI
    private void initFields() {
        treePanel = new NotetakingAppTree();
        this.note = treePanel.note;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates and shows the GUI for notetaking app
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Notetaking App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NotetakingAppGUI newContentPane = new NotetakingAppGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD_COMMAND.equals(command)) {
            String name = JOptionPane.showInputDialog("");
            try {
                treePanel.addObject(name);
            } catch (Exception exception) {
                toolkit.beep();
            }
        } else if (REMOVE_COMMAND.equals(command)) {
            treePanel.removeCurrentNode();
        } else if (LOAD_COMMAND.equals(command)) {
            loadFromFile();
        } else if (SAVE_COMMAND.equals(command)) {
            saveToFile();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a note from file and displays it in the GUI
    private void loadFromFile() {
        try {
            note = jsonReader.read();
            ArrayList<Subject> subjects = note.getSubjects();
            for (Subject s : subjects) {
                DefaultMutableTreeNode treeSubject = treePanel.addObject(s.getSubjectName());
                ArrayList<Course> courses = s.getCourses();
                for (Course c : courses) {
                    DefaultMutableTreeNode treeCourse = treePanel.addObject(treeSubject, c.getCourseName());
                    ArrayList<Topic> topics = c.getTopics();
                    for (Topic t : topics) {
                        treePanel.addObject(treeCourse, t.getTopicName());
                    }
                }
            }
            System.out.println("Loaded notes from: " + JSON_STORE);
        } catch (Exception exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the note to file
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(note);
            jsonWriter.close();
            System.out.println("Saved notes to: " + JSON_STORE);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
