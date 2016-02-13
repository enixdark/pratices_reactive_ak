/**
 * Created by cqshinn on 2/12/16.
 */
import actor.PongActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static scala.compat.java8.FutureConverters.*;
import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

public class PongTest {
    ActorSystem sActor = ActorSystem.create();
    ActorRef ref = sActor.actorOf(Props.create(PongActor.class));
    public CompletionStage<String> askPong(String message){
        Future sFuture = ask(ref, "Ping", 1000);
        CompletionStage<String> cs = toJava(sFuture);
        return cs;
    }
    @Test
    public void PingTest() throws Exception{

//        Future sFuture = ask(ref, "Ping", 1000);
        final CompletableFuture<String> jFuture = (CompletableFuture<String>) askPong("Ping");
        assert(jFuture.get(1000, TimeUnit.MICROSECONDS).equals("Pong"));

    }

    @Test(expected = ExecutionException.class)
    public void ErrorTest() throws Exception{
        final CompletableFuture<String> jFuture = (CompletableFuture<String>) askPong("unknown");
        jFuture.get(1000, TimeUnit.MILLISECONDS);
        askPong("cause error")
                .handle((res, ex) -> {
                    return ex == null ? CompletableFuture.completedFuture(res) : askPong("Ping");
                }).thenCompose( x -> x );
    }


}
