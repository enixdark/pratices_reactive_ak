package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.actor.UntypedActor;

/**
 * Created by cqshinn on 2/12/16.
 */
public class PongActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof String){
            if(message.equals("Ping")){
                sender().tell("Pong", ActorRef.noSender());
            }
        }
        else {
            sender().tell(new Status.Failure(new Exception("unknown message")), self());
        }
    }

    public static Props props(String args){
        return Props.create(PongActor.class,args);
    }
}
