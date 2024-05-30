package server;

import org.xml.sax.SAXException;
import public_classes.Participant;
import server.components.ParticipantsStorage;
import server.components.RegistrableImpl;
import server.components.xmlactions.LoadXML;
import server.components.xmlactions.SaveXML;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ServerApp extends JFrame {
    private JPanel contentPane;

    private JLabel hostLabel;
    private JTextField hostTextField;
    private JLabel portLabel;
    private JTextField portTextField;
    private JLabel participantsLabel;
    private JTextField participantsTextField;

    private JButton startButton;
    private JButton stopButton;
    private JButton saveButton;
    private final JFileChooser fileChooser = new JFileChooser();
    private final FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML files (.xml)", "xml");

    private JButton loadButton;
    private JButton exitButton;

    private RegistrableImpl participantsPanel;
    private final Server server;

    public ServerApp(String title) {
        setTitle(title);

        server = new Server(participantsPanel);
        addElementsAction();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
    }

    private void createUIComponents() {
        hostLabel = new JLabel("Host");

        try {
            hostTextField = new JTextField(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ignore) {
            hostTextField = new JTextField("null");
        }

        portLabel = new JLabel("Port");
        portTextField = new JTextField("6666");
        participantsLabel = new JLabel("Participants");
        participantsTextField = new JTextField("0");

        participantsPanel = new RegistrableImpl(participantsTextField);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");
    }

    private void addElementsAction() {
        startButton.addActionListener(event -> {
            try {
                int port = Integer.parseInt(portTextField.getText());

                server.start(port);

                portTextField.setEditable(false);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                loadButton.setEnabled(false);
                exitButton.setEnabled(false);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Enter correct port.", "Port error", JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException exception) {
                JOptionPane.showMessageDialog(null, "Message: " + exception.getMessage(), "Starting server error", JOptionPane.ERROR_MESSAGE);
            }
        });

        stopButton.addActionListener(event -> {
            try {
                server.stop();

                portTextField.setEditable(true);
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                loadButton.setEnabled(true);
                exitButton.setEnabled(true);
            } catch (RemoteException | NotBoundException exception) {
                exception.printStackTrace();
            }
        });

        saveButton.addActionListener(event -> {
            try {
                fileChooser.removeChoosableFileFilter(xmlFilter);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                    String directoryPath = fileChooser.getSelectedFile().getPath();
                    String fileName = "participants.xml";
                    SaveXML.saveParticipantsXML(participantsPanel.getStorage().getParticipants(), directoryPath + "/" + fileName);
                }
            } catch (TransformerException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        loadButton.addActionListener(event -> {
            fileChooser.setFileFilter(xmlFilter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                String filePath = fileChooser.getSelectedFile().getPath();
                try {
                    List<Participant> newParticipants = LoadXML.loadParticipantsXML(new File(filePath));
                    participantsPanel.setStorage(new ParticipantsStorage(newParticipants));
                } catch (ParserConfigurationException | IOException exception) {
                    exception.printStackTrace();
                } catch (SAXException exception) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid file." + System.lineSeparator() + "Message: " + exception.getMessage(),
                            "Load error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        exitButton.addActionListener(event -> dispose());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ServerApp frame = new ServerApp("(Server) Andrew's Klochko Application");
                frame.setSize(new Dimension(750, 600));
                frame.setResizable(false);

                // Окно появляется по центру экрана
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
