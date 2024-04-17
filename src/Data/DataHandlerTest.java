package Data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Person.Person;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {
    private static String EXTERNAL_DIR = "D:\\QuizPack\\External";
    private static String INTERNAL_DIR = "D:\\QuizPack\\Internal";

    @BeforeAll
    static void setUp() {
        cleanDirectory(EXTERNAL_DIR);
        cleanDirectory(INTERNAL_DIR);
    }

    private static void cleanDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @Test
    void createAndFind() {
        Person person = new Person("0", "John", "Bam", "123456789", "john.doe@example.com", "12345678901", true);
        DataHandler.create(person);
        List<Person> personList = DataHandler.GetAllPersons();
        assertNotNull(personList);
        assertEquals("Bam", Objects.requireNonNull(DataHandler.find("0", "John", "Bam", "", "", "", true)).getLastName());
    }

    @Test
    void createAndModify() {
        Person person = new Person("1", "John", "John", "123456789", "john.doe@example.com", "12345678901", true);
        DataHandler.create(person);
        DataHandler.modify("1", "Jane", "Smith", "987654321", "jane.smith@example.com", "98765432109", false);
        Person modifiedPerson = DataHandler.find("1", null, null, null, null, null, false);
        assertNotNull(modifiedPerson);
        assertEquals("Jane", modifiedPerson.getFirstName());
        assertEquals("Smith", modifiedPerson.getLastName());
    }

    @Test
    void createFindAndRemove() {
        Person person = new Person("2", "Finish", "Him", "123456789", "john.doe@example.com", "12345678901", true);
        DataHandler.create(person);
        assertNotNull(DataHandler.GetAllPersons());
        DataHandler.remove("2");
        Person removedPerson = DataHandler.find("2","Finish", "Him", "123456789", "john.doe@example.com", "12345678901", true);
        assertNull(removedPerson);
    }
}