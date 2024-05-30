package server.components.xmlactions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import public_classes.Participant;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class SaveXML {

    public static Document getParticipantsDOM(List<Participant> participants) throws ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Participants");
        document.appendChild(root);

        for (Participant p : participants) {
            Element participant = document.createElement("Participant");
            root.appendChild(participant);

            Element nameElement = document.createElement("name");
            nameElement.setTextContent(p.getName());
            participant.appendChild(nameElement);

            Element surnameElement = document.createElement("surname");
            surnameElement.setTextContent(p.getSurname());
            participant.appendChild(surnameElement);

            Element organisationElement = document.createElement("organisation");
            organisationElement.setTextContent(p.getOrganisation());
            participant.appendChild(organisationElement);

            Element reportElement = document.createElement("report");
            reportElement.setTextContent(p.getReport());
            participant.appendChild(reportElement);

            Element emailElement = document.createElement("email");
            emailElement.setTextContent(p.getEmail());
            participant.appendChild(emailElement);
        }

        return document;
    }

    public static void saveParticipantsXML(List<Participant> participants, String filePath) throws ParserConfigurationException, TransformerException {
        Document document = getParticipantsDOM(participants);

        document.setXmlStandalone(true);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
