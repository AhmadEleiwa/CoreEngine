package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scenes.Node;
import signals.Event;
import signals.Signal;
import utils.Transform;



public abstract class Script {
    protected Transform transform;
    protected Node node;
    private List<Signal> signals;
    public void init(Transform transform, Node node) {
        this.transform = transform;
        this.node = node;

    }
    public void registerSignal(Signal signal){
        if(this.signals==null){
            signals=new ArrayList<>();
        }
        signals.add(signal);
    }
    public abstract void start();

    public abstract void update(double deltaTime);
    public void _processSignal(HashMap<String, Event>  event){
        if(signals==null){
            return;
        }
        for(Signal signal:this.signals){
            signal.run(event,this.node);
        }
    }
    public void onTrigger(){
        
    }

}