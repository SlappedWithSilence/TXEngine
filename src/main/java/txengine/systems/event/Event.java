package txengine.systems.event;

public abstract class Event {

    String[] properties;

    public Event() {

    }

    public void perform() {
        execute(); // Perform the event's effects
        if (print() != null && !print().equals("")) System.out.println(print()); // Print out optional user prompting
    }

    public abstract void execute();
    public abstract String print();


    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }
}
