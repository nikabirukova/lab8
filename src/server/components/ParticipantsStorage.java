package server.components;

import public_classes.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsStorage {

    private List<Participant> participants;

    public ParticipantsStorage() {
        participants = new ArrayList<>();
    }

    public ParticipantsStorage(List<Participant> participants) {
        this.participants = participants;
    }

    public void add(Participant participant) {
        participants.add(participant);
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public int size() {
        return participants.size();
    }

    public Participant get(int index) {
        return participants.get(index);
    }
}
