package ui;

import model.Course;
import model.Note;
import model.Subject;
import model.Topic;
import model.exceptions.EmptyListException;
import model.exceptions.InvalidAdditionException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Some code from NotetakingAppGUI and NotetakingAppTree was based on / implemented from:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-DynamicTreeDemoProject.zip
// The methods for the collapsing and expanding of the tree in NotetakingAppTree
// are implemented from (although slightly edited):
// https://www.logicbig.com/tutorials/java-swing/jtree-expand-collapse-all-nodes.html

// represents a notetaking application with a Graphic User Interface
public class NotetakingAppGUI extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/notes.json";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String CLEAR_COMMAND = "clear";
    private static final String EXPAND_COMMAND = "expand";
    private static final String COLLAPSE_COMMAND = "collapse";
    private static final String LOAD_COMMAND = "load";
    private static final String SAVE_COMMAND = "save";
    private static final int GUI_WIDTH = 300;
    private static final int GUI_HEIGHT = 150;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Note note;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private NotetakingAppTree treePanel;

    // runs the notetaking app GUI
    public NotetakingAppGUI() {
        super(new BorderLayout());

        initFields();

        JButton addButton = createAddButton();

        JButton removeButton = createRemoveButton();

        JButton clearButton = createClearButton();

        JButton expandButton = createExpandButton();

        JButton collapseButton = createCollapseButton();

        JButton loadButton = createLoadButton();

        JButton saveButton = createSaveButton();

        treePanel.setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        add(treePanel, BorderLayout.CENTER);

        initButtonPanel(addButton, removeButton, clearButton, collapseButton, expandButton, loadButton, saveButton);
    }

    // EFFECTS: creates a new JButton for the collapse command and returns it
    private JButton createCollapseButton() {
        JButton collapseButton = new JButton("Collapse All");
        collapseButton.setActionCommand(COLLAPSE_COMMAND);
        collapseButton.addActionListener(this);
        return collapseButton;
    }

    // EFFECTS: creates a new JButton for the expand command and returns it
    private JButton createExpandButton() {
        JButton expandButton = new JButton("Expand All");
        expandButton.setActionCommand(EXPAND_COMMAND);
        expandButton.addActionListener(this);
        return expandButton;
    }

    // EFFECTS: creates a new JButton for the save command and returns it
    private JButton createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand(SAVE_COMMAND);
        saveButton.addActionListener(this);
        return saveButton;
    }

    // EFFECTS: creates a new JButton for the load command and returns it
    private JButton createLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand(LOAD_COMMAND);
        loadButton.addActionListener(this);
        return loadButton;
    }

    // EFFECTS: creates a new JButton for the clear command and returns it
    private JButton createClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_COMMAND);
        clearButton.addActionListener(this);
        return clearButton;
    }

    // EFFECTS: creates a new JButton for the remove command and returns it
    private JButton createRemoveButton() {
        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);
        return removeButton;
    }

    // EFFECTS: creates a new JButton for the add command and returns it
    private JButton createAddButton() {
        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        return addButton;
    }

    // EFFECTS: initializes the button panel for NotetakingAppGUI
    private void initButtonPanel(JButton addButton, JButton removeButton, JButton clearButton, JButton collapseButton,
                                 JButton expandButton, JButton loadButton, JButton saveButton) {
        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(collapseButton);
        panel.add(expandButton);
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields for NotetakingAppGUI
    private void initFields() {
        treePanel = new NotetakingAppTree();
        this.note = treePanel.getNote();
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
            tryAddObject(name);
        } else if (REMOVE_COMMAND.equals(command)) {
            tryRemove();
        } else if (CLEAR_COMMAND.equals(command)) {
            treePanel.clear();
        } else if (COLLAPSE_COMMAND.equals(command)) {
            treePanel.expandTree(false);
        } else if (EXPAND_COMMAND.equals(command)) {
            treePanel.expandTree(true);
        } else if (LOAD_COMMAND.equals(command)) {
            tryLoadFromFile();
        } else if (SAVE_COMMAND.equals(command)) {
            trySaveToFile();
        }
    }

    private void tryRemove() {
        if (!treePanel.removeCurrentNode()) {
            toolkit.beep();
            showRemovalError();
        }
    }

    private void trySaveToFile() {
        try {
            saveToFile();
        } catch (FileNotFoundException exception) {
            showSaveError();
        }
    }

    private void tryLoadFromFile() {
        try {
            loadFromFile();
        } catch (Exception exception) {
            showLoadError();
        }
    }

    private void tryAddObject(String name) {
        try {
            treePanel.addObject(name);
        } catch (Exception exception) {
            toolkit.beep();
            showInvalidAdditionError();
        }
    }

    private void showInvalidAdditionError() {
        JOptionPane.showMessageDialog(this, "Invalid addition", "Add Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showLoadError() {
        JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE,
                "Load Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSaveError() {
        JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE,
                "Save Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showRemovalError() {
        JOptionPane.showMessageDialog(this, "Invalid selection", "Remove Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: loads a note from file and displays it in the GUI
    private void loadFromFile() throws InvalidAdditionException, IOException, EmptyListException {
        note = jsonReader.read();
        ArrayList<Subject> subjects = note.getSubjects();
        treePanel.clear();
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
    }

    // MODIFIES: this
    // EFFECTS: saves the note to file
    private void saveToFile() throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(note);
        jsonWriter.close();
        System.out.println("Saved notes to: " + JSON_STORE);
    }
}
