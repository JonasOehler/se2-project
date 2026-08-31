package LMSPackage.HelperPackage;

import LMSPackage.BookPackage.BookManager;
import LMSPackage.UserPackage.UserManager;

public class ReadThread implements Runnable{
    @Override
    public void run() {

            BookManager manager = BookManager.getInstance();
            manager.loadJsonFile();

            UserManager manager1 = UserManager.getInstance();
            manager1.loadJsonFile();

    }

}
