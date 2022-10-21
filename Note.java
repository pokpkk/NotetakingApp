/**
 * Note Class for initializing Note object.
 * @author pokpkk
 *
 */
public final class Note {
    private static int latestNoteID;
    private final int noteID;
    private final String topic;
    private final String noteContents;

    public Note(String newTopic, String newNoteContents) {
        latestNoteID++;
        noteID = latestNoteID;
        topic = newTopic;
        noteContents = newNoteContents;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getNoteTopic() {
        return topic;
    }

    public String getNoteContents() {
        return noteContents;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Note ID: ").append(noteID).append("\n").append("Topic: ")
                .append(topic).append("\n").append("Content: ").append(noteContents).append("\n");
        return output.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Note otherNote = (Note) obj;
        return noteID == otherNote.getNoteID();
    }
}
