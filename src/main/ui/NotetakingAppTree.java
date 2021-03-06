package ui;

import model.Course;
import model.Note;
import model.Subject;
import model.exceptions.EmptyListException;
import model.exceptions.InvalidAdditionException;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
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

    // EFFECTS: constructs an empty and editable NotetakingAppTree
    public NotetakingAppTree() {
        super(new GridLayout(1, 0));

        rootNode = new DefaultMutableTreeNode("Notes");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        note = new Note();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: tries to remove the current node, if no node is selected then emit a beep
    public boolean removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds Object child to the tree under appropriate parent
    public DefaultMutableTreeNode addObject(Object child) throws InvalidAdditionException, EmptyListException {
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
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) throws
            InvalidAdditionException, EmptyListException {
        return addObject(parent, child, false);
    }

    // MODIFIES: this
    // EFFECTS: adds Object child to the tree underneath parent
    //          if the object is added to the rootNode, adds it the note as a Subject
    //          if it is added underneath a Subject, adds it to the subject as a Course
    //          if it is added underneath a Course, adds it to the course as a Topic
    //          if it is added underneath a Topic, throw InvalidAdditionException
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible)
            throws InvalidAdditionException, EmptyListException {
        String childToString = (String) child;
        DefaultMutableTreeNode childNode;

        if (parent == null) {
            parent = rootNode;
        }

        if (parent == rootNode) {
            note.addSubject(childToString);
            childNode = new DefaultMutableTreeNode("Subject: " + child);
        } else if (parent.getLevel() == 1) {
            addCourseToSubject(parent, childToString);
            childNode = new DefaultMutableTreeNode("Course: " + child);
        } else if (parent.getLevel() == 2) {
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

    // MODIFIES: this
    // EFFECTS: removes all children except root node
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    // EFFECTS: adds the course to its corresponding subject
    private void addCourseToSubject(DefaultMutableTreeNode parent, String childToString) throws EmptyListException,
            InvalidAdditionException {
        String subjectName = (String) parent.getUserObject();
        Subject parentSubject = note.retrieveTreeSubject(subjectName);
        parentSubject.addCourse(childToString);
    }

    // EFFECTS: find the corresponding course to add the topic to and adds it
    private void addTopicToCourse(DefaultMutableTreeNode parent, String childToString) throws EmptyListException,
            InvalidAdditionException {
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

    public Note getNote() {
        return note;
    }

    // MODIFIES: this
    // EFFECTS: if expand is true, expand all paths in this tree
    //          if expand is false, collapse all paths in this tree
    public void expandTree(boolean expand) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        expandNode(node, expand);
    }

    // MODIFIES: this
    // EFFECTS: if expand is true, then expand the path for this node
    //          if expand is false, collapse the path for this node
    private void expandNode(DefaultMutableTreeNode node, boolean expand) {
        ArrayList<DefaultMutableTreeNode> listOfNodes = Collections.list(node.children());
        for (DefaultMutableTreeNode treeNode : listOfNodes) {
            expandNode(treeNode, expand);
        }
        if (!expand && node.isRoot()) {
            return;
        }
        TreePath path = new TreePath(node.getPath());
        if (expand) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

}
