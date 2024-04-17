import Data.DataHandler;
import Windows.StartWindow;

public class Main {
    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler();
        dataHandler.init();
        new StartWindow();
    }
}