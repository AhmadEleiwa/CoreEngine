package signals;

import java.util.HashMap;

import scenes.CollisionShape2D;


public class TriggerEvent extends Event {
    private HashMap<String, CollisionShape2D> triggerRef =null;
    public TriggerEvent(HashMap<String, CollisionShape2D> triggerRef){
        this.triggerRef =triggerRef;
    }
    CollisionShape2D getData(String key){
        return triggerRef.get(key);
    }
}
