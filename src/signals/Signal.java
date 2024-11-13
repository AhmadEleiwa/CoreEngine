package signals;

import java.util.HashMap;

import scenes.Node;

public abstract class Signal{
    protected Node ref;
    public abstract void run(HashMap<String, Event> event, Node ref );
}
