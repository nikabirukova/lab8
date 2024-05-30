package client;

import public_classes.Participant;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientApp extends JFrame {
    private JPanel contentPane;
    private JTextField hostTextField;
    private JLabel hostLabel;
    private JTextField portTextField;
    private JLabel portLabel;
    private JButton registerButton;
    private JButton clearButton;
    private JButton getInfoButton;
    private JButton exitButton;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JTextField organisationTextField;
    private JTextField reportTextField;
    private JTextField emailTextField;
    private JLabel organizationLabel;
    private JLabel reportLabel;
    private JLabel emailLabel;

    private final Client client;
    private static final String NEXT_LINE = System.lineSeparator();

    public ClientApp(String title) {
        super(title);

        client = new Client();
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

        nameLabel = new JLabel("Name");
        surnameLabel = new JLabel("Surname");
        organizationLabel = new JLabel("Organisation");
        reportLabel = new JLabel("Report");
        emailLabel = new JLabel("Email");

        registerButton = new JButton("Register");
        clearButton = new JButton("Clear");
        getInfoButton = new JButton("Get info");
        exitButton = new JButton("Exit");
    }

    private void addElementsAction() {
        registerButton.addActionListener(event -> {
            try {
                client.register(new Participant(
                        nameTextField.getText(),
                        surnameTextField.getText(),
                        organisationTextField.getText(),
                        reportTextField.getText(),
                        emailTextField.getText()
                ), hostTextField.getText(), Integer.parseInt(portTextField.getText()));
                JOptionPane.showMessageDialog(null, "Participant successfully registered.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | NotBoundException exception) {
                JOptionPane.showMessageDialog(null, "Server not found.", "Connection error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(event -> {
            nameTextField.setText("");
            surnameTextField.setText("");
            organisationTextField.setText("");
            reportTextField.setText("");
            emailTextField.setText("");
        });

        getInfoButton.addActionListener(event -> {
            try {
                JDialog dialogWindow = new JDialog();
                dialogWindow.setTitle("Participants info");
                dialogWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                StringBuilder participantText = new StringBuilder();
                client.getAllParticipants(hostTextField.getText(), Integer.parseInt(portTextField.getText())).forEach(participant -> {
                    participantText.append("NAME: ").append(participant.getName()).append(NEXT_LINE);
                    participantText.append("SURNAME: ").append(participant.getSurname()).append(NEXT_LINE);
                    participantText.append("ORGANISATION: ").append(participant.getOrganisation()).append(NEXT_LINE);
                    participantText.append("REPORT: ").append(participant.getReport()).append(NEXT_LINE);
                    participantText.append("EMAIL: ").append(participant.getEmail()).append(NEXT_LINE);
                    participantText.append(NEXT_LINE);
                });

                JTextArea textArea = new JTextArea(participantText.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, 18));
                textArea.setCaretPosition(textArea.getText().length());
                JScrollPane scrollPane = new JScrollPane(textArea);
                dialogWindow.add(scrollPane);
                dialogWindow.setSize(new Dimension(500, 300));
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                dialogWindow.setLocation(dim.width/2 - dialogWindow.getSize().width/2, dim.height/2-dialogWindow.getSize().height/2);
                dialogWindow.setVisible(true);

            } catch (RemoteException | NotBoundException exception) {
                exception.printStackTrace();
            }
        });

        exitButton.addActionListener(event -> dispose());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ClientApp frame = new ClientApp("(Client) Andrew's Klochko Application");
                frame.pack();
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
