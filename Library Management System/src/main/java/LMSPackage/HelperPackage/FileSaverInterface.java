package LMSPackage.HelperPackage;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.ArrayList;


public interface FileSaverInterface extends Serializable {

    public Object loadJsonFile();

    public String saveJsonFile();

}
