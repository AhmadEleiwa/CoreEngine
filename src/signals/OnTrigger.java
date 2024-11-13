package signals;

import java.util.HashMap;

import scenes.CollisionShape2D;
import scenes.Node;

public abstract class OnTrigger extends Signal {

    @Override
    public void run(HashMap<String, Event> event, Node ref) {
        // TODO Auto-generated method stub
        if(event.get("OnTrigger")!= null){
            CollisionShape2D other= ((TriggerEvent)event.get("OnTrigger")).getData(ref.getName());
            if( other !=null){
                onTrigger(other);
            }
        }
    }
    public abstract void onTrigger(CollisionShape2D other);
    
}
