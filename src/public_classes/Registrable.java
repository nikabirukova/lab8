package public_classes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Registrable extends Remote {
    void register(Participant p) throws RemoteException;
    List<Participant> getAllParticipants() throws RemoteException;
}
