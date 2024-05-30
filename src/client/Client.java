package client;

import public_classes.Participant;
import public_classes.Registrable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Permission;
import java.util.List;

public class Client {

    private static final String NAME = "Registrable";

    public Client() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager() {
                public void checkConnect(String host, int port, Object context) { }
                public void checkConnect(String host,int port) { }
                public void checkPermission(Permission perm) { }
            });
        }
    }

    public void register(Participant participant, String host, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        Registrable reg = (Registrable) registry.lookup(NAME);
        reg.register(participant);
    }

    public List<Participant> getAllParticipants(String host, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        Registrable reg = (Registrable) registry.lookup(NAME);
        return reg.getAllParticipants();
    }
}
