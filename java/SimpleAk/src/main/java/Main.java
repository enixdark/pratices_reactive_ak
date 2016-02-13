import actor.AkkaDb;
import actor.PongActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import client.JClient;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ActorSystem actorSys = ActorSystem.create();
//        ActorRef ref = actorSys.actorOf(Props.create(PongActor.class));
//        ref.tell("Pong", ActorRef.noSender());
//        actorSys.actorOf(Props.create(AkkaDb.class), "akkadb");
        JClient client = new JClient("127.0.0.1:2552");
        client.set("hello", "world");
        String result = (String)((CompletableFuture) client.get("123")).get();
        System.out.println(result);
    }
}
