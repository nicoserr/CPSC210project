package ui;

import model.Course;
import model.Note;
import model.Subject;
import model.exceptions.EmptyListException;
import model.exceptions.EmptyNameException;
import model.exceptions.InvalidAdditionException;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

// represents the tree which the notetaking application with a GUI uses
public class NotetakingAppTree extends JPanel {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    protected Note note;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    // EFFECTS: constructs an empty and editable NotetakingAppTree
    public NotetakingAppTree() {
        super(new GridLayout(1, 0));

        rootNode = new DefaultMutableTreeNode("Notes");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        note = new Note();
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: tries to remove the current node, if no node is selected then emit a beep
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        toolkit.beep();
    }

    // MODIFIES: this
    // EFFECTS: adds Object child to the tree under appropriate parent
    public DefaultMutableTreeNode addObject(Object child) throws EmptyNameException, EmptyListException,
            InvalidAdditionException {
        DefaultMutableTreeNode parentNode;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    // MODIFIES: this
    // EFFECTS: adds Object child to the tree under parent
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) throws EmptyNameException,
            EmptyListException, InvalidAdditionException {
        return addObject(parent, child, false);
    }

    // MODIFIES: this
    // EFFECTS: adds Object child to the tree underneath parent
    //          if the object is added to the rootNode, adds it the note as a Subject
    //          if it is added underneath a Subject, adds it to the subject as a Course
    //          if it is added underneath a Course, adds it to the course as a Topic
    //          if it is added underneath a Topic, throw InvalidAdditionException
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible)
            throws EmptyNameException, EmptyListException, InvalidAdditionException {
        String childToString = (String) child;
        DefaultMutableTreeNode childNode;

        if (parent == null) {
            parent = rootNode;
        }

        if (parent == rootNode) {
            note.addSubject(childToString);
            childNode = new DefaultMutableTreeNode("Subject: " + child);
        } else if (parent.getParent() == rootNode) {
            addCourseToSubject(parent, childToString);
            childNode = new DefaultMutableTreeNode("Course: " + child);
        } else if (parent.getParent().getParent() == rootNode) {
            addTopicToCourse(parent, childToString);
            childNode = new DefaultMutableTreeNode("Topic: " + child);
        } else {
            throw new InvalidAdditionException();
        }
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    // EFFECTS: adds the course to its corresponding subject
    private void addCourseToSubject(DefaultMutableTreeNode parent, String childToString) throws EmptyListException,
            EmptyNameException {
        String subjectName = (String) parent.getUserObject();
        Subject parentSubject = note.retrieveTreeSubject(subjectName);
        parentSubject.addCourse(childToString);
    }

    // EFFECTS: find the corresponding course to add the topic to and adds it
    private void addTopicToCourse(DefaultMutableTreeNode parent, String childToString) throws EmptyListException,
            EmptyNameException {
        ArrayList<Subject> subjects = note.getSubjects();
        String courseName = (String) parent.getUserObject();
        for (Subject s : subjects) {
            if (s.retrieveTreeCourse(courseName) != null) {
                Course parentCourse = s.retrieveTreeCourse(courseName);
                parentCourse.addTopic(childToString);
                break;
            }
        }
    }

}
