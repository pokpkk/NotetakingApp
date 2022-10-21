import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Directory to manage Note objects for Notetaking Application.
 * @author pokpkk
 *
 */
public class NoteDirectory {
    private List<Note> noteList;
    private Map<Integer, Note> idDirectory;
    private Map<String, List<Note>> topicDirectory;

    public NoteDirectory() {
        noteList = new ArrayList<Note>();
        idDirectory = new HashMap<Integer, Note>();
        topicDirectory = new HashMap<String, List<Note>>();
    }

    public void addNote(Note newNote) {
        if (idDirectory.containsKey(newNote.getNoteID())) {
            throw new IllegalArgumentException("This Note ID is already exist");
        }
        noteList.add(newNote);
        idDirectory.put(newNote.getNoteID(), newNote);

        List<Note> existedList = topicDirectory.get(newNote.getNoteTopic());
        if (existedList == null) {
            existedList = new ArrayList<Note>();
        }
        existedList.add(newNote);

        topicDirectory.put(newNote.getNoteTopic(), existedList);
    }

    public void deleteNote(int noteID) {
        if (!idDirectory.containsKey(noteID)) {
            throw new IllegalArgumentException("No such ID in the directory");
        }

        Note thisNote = idDirectory.get(noteID);
        List<Note> thisTopicNotes = topicDirectory.get(thisNote.getNoteTopic());
        idDirectory.remove(noteID);
        List<Note> copyOfThisTopicNotes = List.copyOf(thisTopicNotes);
        copyOfThisTopicNotes.forEach(n -> {
            if (n.getNoteID() == noteID) {
                thisTopicNotes.remove(n);
            }
        });
        topicDirectory.put(thisNote.getNoteTopic(), thisTopicNotes);

        Note noteToDelete = null;
        for (Note n : noteList) {
            if (n.getNoteID() == noteID) {
                noteToDelete = n;
            }
        }
        noteList.remove(noteToDelete);
    }

    public List<Note> searchByTopic(String topic) {
        if (topic.equals("")) {
            return Collections.unmodifiableList(noteList);
        }
        if (!topicDirectory.containsKey(topic)) {
            throw new IllegalArgumentException("No such topic in the directory");
        }
        return Collections.unmodifiableList(topicDirectory.get(topic));

    }

    public List<Note> searchByKeyword(String keyword) {
        if (keyword == null || keyword.equals("")) {
            throw new IllegalArgumentException("Input Some Keyword");
        }
        List<Note> searchOutput = new ArrayList<Note>();
        for (Note n : noteList) {
            String noteContents = n.getNoteContents();
            if (noteContents.contains(keyword)) {
                searchOutput.add(n);
            }
        }
        return Collections.unmodifiableList(searchOutput);
    }

}
