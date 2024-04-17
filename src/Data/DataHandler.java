package Data;

import Person.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static String _ExternalDir = "D:\\QuizPack\\External";
    private static String _InternalDir = "D:\\QuizPack\\Internal";
    private static List<Person> personList = new ArrayList<>();
    private static int lastIndex = 0;

    public void init(){
        readPersonsFromDirectory(_ExternalDir);
        readPersonsFromDirectory(_InternalDir);
        if (!personList.isEmpty())
            lastIndex++;
        else
            lastIndex = 0;
    }
    public static List<Person> GetAllPersons(){
        return personList;
    }

    private static Person getPersonById(String id){
        return personList.stream()
                .filter(p -> p.getPersonId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static boolean remove(String id) {
        Person person = getPersonById(id);
        if (person == null) {
            System.out.println("RemovePerson error!");
            return false;
        }
        String filePath = (person.isInternal() ? _InternalDir : _ExternalDir) + "/" + person.getPersonId() + "_" + (person.getFirstName() + person.getLastName()) + ".xml";
        File file = new File(filePath);
        if (!file.exists())
            return false;
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
            return false;
        }
        personList.remove(person);
        return true;
    }

    public static void create(Person person)
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element personElement = doc.createElement("Person");
            doc.appendChild(personElement);
            personElement.setAttribute("personId", Integer.toString(lastIndex));
            personElement.setAttribute("firstName", person.getFirstName());
            personElement.setAttribute("lastName", person.getLastName());
            personElement.setAttribute("mobile", person.getMobile());
            personElement.setAttribute("email", person.getEmail());
            personElement.setAttribute("pesel", person.getPesel());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            String filePath = (person.isInternal() ? _InternalDir : _ExternalDir) + "/" + lastIndex + "_" + (person.getFirstName() + person.getLastName()) + ".xml";
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            personList.add(person);
            lastIndex++;
            System.out.println("XML file saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modify(String id, String name, String lastName, String phone, String mail, String pesel, boolean isInternal) {
        Person person = getPersonById(id);
        if (person == null) {
            System.out.println("EditPerson error!");
            return;
        }
        boolean internal = person.isInternal() != isInternal;
        String oldName = person.getFirstName();
        String oldLastName = person.getLastName();
        person.setFirstName(name);
        person.setLastName(lastName);
        person.setMobile(phone);
        person.setEmail(mail);
        person.setPesel(pesel);
        person.setInternal(isInternal);

        updatePersonFile(person, oldName, oldLastName,
                !person.getFirstName().equals(oldName) ||
                        !person.getLastName().equals(oldLastName),
                internal);
    }

    private static void updatePersonFile(Person person, String name, String lastName, boolean rename, boolean directory) {
        try {
            String filePath = (directory ? person.isInternal() ? _ExternalDir  : _InternalDir :
                    person.isInternal() ? _InternalDir : _ExternalDir) + "/" + person.getPersonId() +
                    "_" + (name + lastName) + ".xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            if (!new File(filePath).exists())
                return;
            Document doc = docBuilder.parse(new File(filePath));

            Element personElement = (Element) doc.getElementsByTagName("Person").item(0);
            personElement.setAttribute("firstName", person.getFirstName());
            personElement.setAttribute("lastName", person.getLastName());
            personElement.setAttribute("mobile", person.getMobile());
            personElement.setAttribute("email", person.getEmail());
            personElement.setAttribute("pesel", person.getPesel());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            if (rename || directory)
            {
                File oldFile = new File(filePath);
                oldFile.renameTo(new File((person.isInternal() ? _InternalDir : _ExternalDir)
                        + "/" + person.getPersonId() + "_" + (person.getFirstName() + person.getLastName()) + ".xml"));
                System.out.println("Person data rename!");
            }

            System.out.println("Person data updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Person find(String id, String name, String lastName, String phone, String mail, String pesel, boolean isInternal)
    {
        if (id.isEmpty() && name.isEmpty() && lastName.isEmpty() && phone.isEmpty() && mail.isEmpty() && pesel.isEmpty())
            return null;

        id = id != null ? id.toLowerCase() : null;
        name = name != null ? name.toLowerCase() : null;
        lastName = lastName != null ? lastName.toLowerCase() : null;
        phone = phone != null ? phone.toLowerCase() : null;
        mail = mail != null ? mail.toLowerCase() : null;
        pesel = pesel != null ? pesel.toLowerCase() : null;

        for (Person person : personList) {
            if (id != null && !person.getPersonId().toLowerCase().contains(id))
                continue;
            if (name != null && !person.getFirstName().toLowerCase().contains(name))
                continue;
            if (lastName != null && !person.getLastName().toLowerCase().contains(lastName))
                continue;
            if (phone != null && !person.getMobile().toLowerCase().contains(phone))
                continue;
            if (mail != null && !person.getEmail().toLowerCase().contains(mail))
                continue;
            if (pesel != null && !person.getPesel().toLowerCase().contains(pesel))
                continue;
            if (person.isInternal() != isInternal)
                continue;
            return person;
        }
        return null;
    }

    private void readPersonsFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory())
            return;

        File[] files = directory.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                Person person = readPersonFromFile(file, directoryPath.equals(_InternalDir));
                if (person == null)
                    continue;
                personList.add(person);
            }
        }
    }

    private Person readPersonFromFile(File file, boolean internal) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element personElement = document.getDocumentElement();
            String personId = personElement.getAttribute("personId");
            String firstName = personElement.getAttribute("firstName");
            String lastName = personElement.getAttribute("lastName");
            String mobile = personElement.getAttribute("mobile");
            String email = personElement.getAttribute("email");
            String pesel = personElement.getAttribute("pesel");
            Person person = new Person(personId, firstName, lastName, mobile, email, pesel, internal);
            lastIndex = Math.max(lastIndex, Integer.parseInt(personId));
            return person;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
