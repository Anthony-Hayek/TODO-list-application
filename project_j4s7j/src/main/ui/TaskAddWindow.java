package ui;

import model.Task;
import model.TaskNotValidException;
import model.TodoList;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TaskAddWindow implements ActionListener {
    private JFrame frame;
    private JLabel prompt;
    private JPanel panel;
    private JTextField taskToAdd;
    private JButton button;
    private String taskString;
    private DefaultListModel<Task> listModel;
    private TodoList todoList;
    private JButton removeButton;
    private Clip clip;



    //EFFECTS: constructs the taskAddWindow
    public TaskAddWindow(DefaultListModel<Task> listModel, TodoList todoList, JButton removeButton, Clip clip) {


        this.clip = clip;
        this.listModel = listModel;
        this.todoList = todoList;
        this.removeButton = removeButton;

        button = new JButton();
        button.setBounds(100, 80, 150, 25);
        button.addActionListener(this);
        button.setText("Add this Task");
        button.setFocusable(false);

        taskToAdd = new JTextField(20);
        taskToAdd.setBounds(100, 20, 165, 25);
        taskToAdd.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(null);
        panel.add(taskToAdd);
        panel.add(button);

        prompt = new JLabel("Add a Task:");
        prompt.setBounds(10, 20, 80, 25);

        frame = new JFrame();
        frame.setSize(350, 200);
        frame.add(prompt);
        frame.add(panel);

    }

    //MODIFIES: this
    //EFFECTS: sets the frame to visible or not visible based on given boolean
    public void setVisible(Boolean b) {
        frame.setVisible(b);
    }

    //MODIFIES: this
    //EFFECTS: adds the task to listModel and todolist

    @Override
    public void actionPerformed(ActionEvent e) {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
        taskString = taskToAdd.getText();
        System.out.println(taskString);
        Task task = null;
        task = new Task(taskString);

        try {
            todoList.addTask(task);
            listModel.addElement(task);
            removeButton.setEnabled(true);
        } catch (TaskNotValidException taskNotValidException) {
            System.out.println("Exception caught");
            JOptionPane.showMessageDialog(
                    null,
                    "Task must be at least one character long",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );


        }

        setVisible(false);

    }
}
