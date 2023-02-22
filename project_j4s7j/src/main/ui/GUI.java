package ui;

import model.Task;
import model.TaskNotValidException;
import model.TodoList;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//CITATION: referred to this video to learn java swing: https://www.youtube.com/watch?v=Kmgo00avvEw
//          also referred to components-ListDemoProject for how to work with the scrollPanel and selection
//          for the audio component I watched this: https://www.youtube.com/watch?v=SyZQVJiARTQ
//          For JOptionPane I watched this: https://www.youtube.com/watch?v=BuW7y21FcYI

public class GUI extends JFrame implements ActionListener, ListSelectionListener {
    private static final String JSON_STORE = "./data/todolist.json";
    private File file;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;

    private JButton addButton;
    private JButton removeButton;
    private JButton markButton;
    private JButton loadButton;
    private JButton saveButton;

    private JScrollPane listScrollPane;
    private JPanel listPanel;
    private JPanel buttonsPanel;

    private JLabel savedLabel;

    private TaskAddWindow myWindow;
    private TodoList todoList;

    private JList<Task> list;
    private DefaultListModel<Task> listModel;

    private Clip clip;


    //EFFECTS: Constructs the entire GUI
    public GUI() throws FileNotFoundException {

        try {
            setUpSound();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        todoList = new TodoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        createAddButton();
        createRemoveButton();
        createMarkButton();
        createLoadButton();
        createSaveButton();
        listModel = new DefaultListModel<Task>();
        myWindow = new TaskAddWindow(listModel, todoList, removeButton, clip);
        setUpList();
        savedLabel = new JLabel("");
        savedLabel.setBounds(100, 100, 10, 10);
        setUpListScrollPane();
        setUpListPanel();
        setUpButtonPanel();
        setUpFrame();

    }

    //MODIFIES: this
    //EFFECTS: sets up frame and add ths listPanel and buttonsPanel to it
    private void setUpFrame() {
        frame = new JFrame("Todo list:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        frame.setResizable(false); //prevents frame from being resized
        frame.setLayout(null);
        frame.setSize(500, 500); //sets x and y dimension
        frame.add(listPanel);
        frame.add(buttonsPanel);
        frame.setLocation(500, 500);
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets up the buttons panel by adding all the buttons
    private void setUpButtonPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(60, 135, 186));
        buttonsPanel.setBounds(0, 250, 500, 250);
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(markButton);
        buttonsPanel.add(loadButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(savedLabel);
    }

    //MODIFIES: this
    //EFFECTS: sets up list panel and add the listScrollPane to it
    private void setUpListPanel() {
        listPanel = new JPanel();
        listPanel.setBackground(new Color(129, 196, 215));
        listPanel.setBounds(0, 0, 500, 250);
        listPanel.add(listScrollPane);
    }

    //MODIFIES: this
    //EFFECTS: sets up the list scroll pane
    private void setUpListScrollPane() {
        listScrollPane = new JScrollPane(list);
        listScrollPane.setBounds(100, 200, 200, 200);
        listScrollPane.setMinimumSize(new Dimension(200, 200));
    }

    //MODIFIES: this
    //EFFECTS: intializes and sets customizations to list
    private void setUpList() {
        list = new JList<Task>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        list.setMinimumSize(new Dimension(200, 50));
    }

    //MODIFIES:
    //EFFECTS: sets up the sound that will be played when a button is pushed
    public void setUpSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File("./data/Wood Plank Flicks (1).wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

    }

    //MODIFIES: this
    //EFFECTS: performs an action based on which button is clicked

    @Override
    public void actionPerformed(ActionEvent e) {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
        if (e.getSource() == addButton) {
            myWindow.setVisible(true);

        } else if (e.getSource() == removeButton) {
            removeSelectedTask();

        } else if (e.getSource() == markButton) {
            int index = list.getSelectedIndex();
            Task t = listModel.get(index);
            t.markAsComplete();
            frame.setVisible(false);
            frame.setVisible(true);

        } else if (e.getSource() == loadButton) {
            loadTodoList();

        } else if (e.getSource() == saveButton) {
            savedLabel.setText("Todo list saved!");
            saveTodoList();
        }
    }

    //MODIFIES: this
    //EFFECTS: removes the selected element from the listModel as well as the todolist
    private void removeSelectedTask() {
        int index = list.getSelectedIndex();
        Task t = listModel.remove(index);
        todoList.removeTask(t.getName());

        int size = listModel.getSize();

        if (size == 0) {
            removeButton.setEnabled(false);

        } else {
            if (index == listModel.getSize()) {

                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the todolist to file
    private void saveTodoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            System.out.println(todoList.getTaskList());
            jsonWriter.close();
            System.out.println("Saved TO-DO list to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads todolist from file
    private void loadTodoList() {
        try {
            TodoList todoList = jsonReader.read();

            this.todoList.getTaskList().clear();
            listModel.clear();
            for (Task task : todoList.getTaskList()) {
                listModel.addElement(task);
                this.todoList.addTask(task);

            }
            if (listModel.getSize() > 0) {
                removeButton.setEnabled(true);
            }
            System.out.println("Loaded TO-DO list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (TaskNotValidException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates an add button
    public void createAddButton() {
        addButton = new JButton();
        addButton.setBounds(100, 50, 100, 50);
        addButton.addActionListener(this);
        addButton.setText("Add Task");
        addButton.setFocusable(false);

    }

    //MODIFIES: this
    //EFFECTS: creates a remove button
    public void createRemoveButton() {
        removeButton = new JButton();
        removeButton.setBounds(300, 50, 100, 50);
        removeButton.addActionListener(this);
        removeButton.setText("Remove Task");
        removeButton.setFocusable(false);
        removeButton.setEnabled(false);

    }

    //MODIFIES: this
    //EFFECTS: creates a mark button
    public void createMarkButton() {
        markButton = new JButton();
        markButton.setBounds(100, 100, 100, 50);
        markButton.addActionListener(this);
        markButton.setText("Mark Task ");
        markButton.setFocusable(false);

    }

    //MODIFIES: this
    //EFFECTS: creates a Load button
    public void createLoadButton() {
        loadButton = new JButton();
        loadButton.setBounds(300, 100, 100, 50);
        loadButton.addActionListener(this);
        loadButton.setText("Load Todo");
        loadButton.setFocusable(false);

    }

    //MODIFIES: this
    //EFFECTS: creates a save button
    public void createSaveButton() {
        saveButton = new JButton();
        saveButton.setBounds(200, 150, 100, 50);
        saveButton.addActionListener(this);
        saveButton.setText("Save Todo");
        saveButton.setFocusable(false);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

}
