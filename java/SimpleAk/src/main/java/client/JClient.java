package client;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import mol.GetRequest;
import mol.SetRequest;

import java.util.concurrent.CompletionStage;
import static scala.compat.java8.FutureConverters.*;
import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;
/**
 * Created by cqshinn on 2/13/16.
 */
public class JClient {
    private final ActorSystem sActor = ActorSystem.create("LocalSystem");
    private final ActorSelection remote;

    public JClient(String address){
        this.remote = sActor.actorSelection(String.format("akka.tcp://akkademy@%s/user/akkadb",address));
    }

    public CompletionStage set(String key, Object value){
        return toJava(ask(remote, new SetRequest(key,value),2000));
    }

    public CompletionStage<Object> get(String key){
        return toJava(ask(remote, new GetRequest(key), 2000));
    }
}
