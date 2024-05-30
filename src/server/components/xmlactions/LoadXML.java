package server.components.xmlactions;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import public_classes.Participant;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadXML {

    public static List<Participant> loadParticipantsXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = schemaFactory.newSchema(new File("src/server/components/xmlactions/participantsSchemaValidation.xsd"));
        documentBuilderFactory.setSchema(schema);

        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        builder.setErrorHandler(new ParseErrorHandler());
        Document document = builder.parse(file);

        NodeList participantNames = document.getElementsByTagName("name");
        NodeList participantSurnames = document.getElementsByTagName("surname");
        NodeList participantOrganisations = document.getElementsByTagName("organisation");
        NodeList participantReports = document.getElementsByTagName("report");
        NodeList participantEmails = document.getElementsByTagName("email");

        List<Participant> participants = new ArrayList<>();
        for (int i = 0; i < participantNames.getLength(); i++) {
            participants.add(new Participant(
                    participantNames.item(i).getTextContent(),
                    participantSurnames.item(i).getTextContent(),
                    participantOrganisations.item(i).getTextContent(),
                    participantReports.item(i).getTextContent(),
                    participantEmails.item(i).getTextContent()
            ));
        }

        return participants;
    }

    private static class ParseErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }
}
