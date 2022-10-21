import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Java Swing GUI Notetaking Application
 * @author pokpkk
 *
 */
public class NoteApp {
    private JFrame frame;
    private JPanel pane;

    private JPanel inputPane;
    private JPanel inputTextPane;
    private JLabel inputTopicLabel;
    private JTextField inputTopicText;
    private JLabel inputNoteLabel;
    private JTextArea inputNoteText;
    private JScrollPane inputNoteTextScroller;
    private JButton addNewNoteButton;

    private JPanel deletePane;
    private JLabel deleteLabel;
    private JTextField deleteText;
    private JButton deleteButton;

    private JPanel searchPane;
    private JLabel searchLabel;
    private JTextField searchText;
    private JButton searchByTopicButton;
    private JButton searchByKeywordButton;

    private JTextArea displayArea;
    private JScrollPane displayScroller;

    private NoteDirectory dir;

    public NoteApp() {
        dir = new NoteDirectory();

        frame = new JFrame("Note Tracking App by Sasis Sawartsut");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JPanel();

        inputPane = new JPanel();

        inputTopicLabel = new JLabel("Topic (or date or whatever)");
        inputTopicText = new JTextField(8);

        inputNoteLabel = new JLabel("Your Note");
        inputNoteText = new JTextArea(8, 60);
        inputNoteText.setLineWrap(true);

        addNewNoteButton = new JButton("Add this note");
        addNewNoteButton.addActionListener(e -> {
            String date = inputTopicText.getText().trim();
            String noteContents = inputNoteText.getText().trim();
            if (date.equals("")) {
                displayArea.setText("Must Input Topic\n");
                return;
            }
            if (noteContents.equals("")) {
                displayArea.setText("Please Note Something\n");
                return;
            }
            Note newNote = new Note(date, noteContents);
            dir.addNote(newNote);
            displayArea.append("New Note Added!\n");
            inputTopicText.setText("");
            inputNoteText.setText("");
        });

        inputPane.add(inputTopicLabel);
        inputPane.add(inputTopicText);
        inputPane.add(addNewNoteButton);

        pane.add(inputPane);

        inputTextPane = new JPanel();

        inputNoteTextScroller = new JScrollPane(inputNoteText);

        inputTextPane.add(inputNoteLabel);
        inputTextPane.add(inputNoteTextScroller);

        pane.add(inputTextPane);

        deletePane = new JPanel();
        deleteLabel = new JLabel("Enter Note ID to delete");
        deleteText = new JTextField(5);
        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(deleteText.getText().trim());
                dir.deleteNote(id);
                displayArea.append("Delete Note ID " + String.valueOf(id) + "\n");
                deleteText.setText("");
            } catch (Exception er) {
                displayArea.setText("Invalid Note ID\n");
                deleteText.setText("");
            }
        });

        deletePane.add(deleteLabel);
        deletePane.add(deleteText);
        deletePane.add(deleteButton);

        pane.add(deletePane);

        searchPane = new JPanel();
        searchLabel = new JLabel("Enter Key to Search:");
        searchText = new JTextField(15);
        searchByTopicButton = new JButton("By Exact Topic Name, enter none to show all notes");
        searchByKeywordButton = new JButton("By Keyword");

        searchByTopicButton.addActionListener(e -> {
            String topic = searchText.getText().trim();
            try {
                List<Note> output = dir.searchByTopic(topic);
                displayArea.append("This is your note search results\n");
                for (Note i : output) {
                    displayArea.append(i.toString() + "=====================================\n");
                }
                searchText.setText("");
            } catch (Exception er) {
                displayArea.setText("No Match Found\n");
            }
        });

        searchByKeywordButton.addActionListener(e -> {
            String keyword = searchText.getText().trim();
            if (keyword.equals("")) {
                displayArea.setText("Please enter keyword to search from\n");
                return;
            }
            displayArea.append("This is your note search results\n");
            List<Note> output = dir.searchByKeyword(keyword);
            for (Note i : output) {
                displayArea.append(i.toString() + "=====================================\n");
            }
            searchText.setText("");

        });

        searchPane.add(searchLabel);
        searchPane.add(searchText);
        searchPane.add(searchByTopicButton);
        searchPane.add(searchByKeywordButton);

        pane.add(searchPane);

        displayArea = new JTextArea(10, 70);
        displayArea.setEditable(false);

        displayScroller = new JScrollPane(displayArea);
        pane.add(displayScroller);

        frame.setContentPane(pane);
        frame.setVisible(true);
    }

}
