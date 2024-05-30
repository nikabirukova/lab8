package server.components;

import public_classes.Participant;
import public_classes.Registrable;

import javax.swing.*;
import java.util.List;

public class RegistrableImpl extends JComponent implements Registrable {

    private JPanel contentPane;
    private final JTextField participantTextField;
    private JTextArea participantsTextArea;

    private ParticipantsStorage storage;

    public RegistrableImpl(JTextField participantTextField) {
        this.participantTextField = participantTextField;
        storage = new ParticipantsStorage();
    }

    @Override
    public void register(Participant participant) {
        storage.add(participant);
        addParticipantToTextArea(participant);
    }

    @Override
    public List<Participant> getAllParticipants() {
        return storage.getParticipants();
    }

    public ParticipantsStorage getStorage() {
        return storage;
    }

    public void setStorage(ParticipantsStorage storage) {
        this.storage = storage;
        for (Participant participant : this.storage.getParticipants()) {
            addParticipantToTextArea(participant);
        }
    }

    private void addParticipantToTextArea(Participant participant) {
        participantsTextArea.append(participant.toString() + System.lineSeparator());
        participantTextField.setText(String.valueOf(storage.size()));
        // for scrolling down when add new info
        participantsTextArea.setCaretPosition(participantsTextArea.getText().length());
    }
}
