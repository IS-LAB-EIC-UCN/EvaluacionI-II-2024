package cl.ucn.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(this);
        }
    }
}
