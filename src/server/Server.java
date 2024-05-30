package server;

import public_classes.Registrable;
import server.components.RegistrableImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;

public class Server {

    private static final String NAME = "Registrable";
    RegistrableImpl regImpl;
    Registry registry;

    public Server(RegistrableImpl regImpl) {
        this.regImpl = regImpl;
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager() {
                public void checkConnect(String host, int port, Object context) { }
                public void checkConnect(String host,int port) { }
                public void checkPermission(Permission perm) { }
            });
        }

        System.setProperty("java.rmi.server.codebase", "file:///" + Registrable.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public void start(int port) throws RemoteException {
        registry = LocateRegistry.createRegistry(port);
        Registrable stub = (Registrable) UnicastRemoteObject.exportObject(regImpl, 0);
        registry.rebind(NAME, stub);
    }

    public void stop() throws RemoteException, NotBoundException {
        registry.unbind(NAME);
        UnicastRemoteObject.unexportObject(regImpl, true);
        UnicastRemoteObject.unexportObject(registry, true);
    }
}
