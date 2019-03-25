package clienttcp1;

public class Message {
    final String user;
    final String message;
    final byte[] file;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
        this.file = null;
    }

    public Message(String user, String filename, byte[] content) {
        this.user = user;
        this.message = filename;
        this.file = content;
    }

    public boolean isMessage() {
        return this.file == null;
    }
}

