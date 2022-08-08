package Frontend.Observers;

import DAL.MessageHandler;

import java.util.List;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver(int num);
    void notifyObserver(String str);
    void notifyObserver(List<List<String>> levels);
    void notifyObserver(MessageHandler messageHandler);
}
