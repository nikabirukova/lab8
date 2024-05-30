package public_classes;

import java.io.Serializable;

public class Participant implements Serializable {
    private String name;
    private String surname;
    private String organisation;
    private String report;
    private String email;

    private static final String NEXT_LINE = System.lineSeparator();

    public Participant(String name, String surname, String organisation, String report, String email) {
        this.name = name;
        this.surname = surname;
        this.organisation = organisation;
        this.report = report;
        this.email = email;
    }

    public Participant() { }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getReport() {
        return report;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return  "NAME: " + name + NEXT_LINE +
                "SURNAME: " + surname + NEXT_LINE +
                "ORGANISATION: " + organisation + NEXT_LINE +
                "REPORT: " + report + NEXT_LINE +
                "EMAIL: " + email + NEXT_LINE;
    }
}
