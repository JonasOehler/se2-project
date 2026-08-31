package LMSPackage.HelperPackage;

import LMSPackage.BookPackage.BookManager;
import LMSPackage.UserPackage.UserManager;

public class WriteThread implements Runnable {
    private final EnumWrite Write;

    public WriteThread(EnumWrite write) {
        this.Write = write;
    }

    @Override
    public void run() {
        if (Write == EnumWrite.WRITEBOOK){
            BookManager manager = BookManager.getInstance();
            manager.saveJsonFile();
        }
        if (Write == EnumWrite.WRITEUSER){
            UserManager manager1 = UserManager.getInstance();
            manager1.saveJsonFile();
        }

    }
}
