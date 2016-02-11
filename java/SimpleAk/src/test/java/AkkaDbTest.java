import actor.AkkaDb;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestActorRef;
import mol.SetRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by cqshinn on 2/11/16.
 */
public class AkkaDbTest {
    ActorSystem actor = ActorSystem.create();

    @Test
    public void ActorTest(){
        TestActorRef<AkkaDb> actorRef = TestActorRef.create(actor, Props.create(AkkaDb.class));
        actorRef.tell(new SetRequest("hello", "world"), ActorRef.noSender());
        AkkaDb db = actorRef.underlyingActor();
        assertEquals(db.map.get("hello"),"world");
    }

}
