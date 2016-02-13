package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import mol.*;
import scala.PartialFunction;

import javax.sound.midi.Receiver;
import java.util.concurrent.TimeoutException;

/**
 * Created by cqshinn on 2/13/16.
 */
public class TellArticleParser extends AbstractActor {
    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection artcileParseActor;
    private final Timeout timeout;

    public TellArticleParser(String cacheActorPath, String httpClientActorPath,
                             String artcileParseActorPath, Timeout timeout) {
        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.artcileParseActor = context().actorSelection(artcileParseActorPath);
        this.timeout = timeout;
    }

    public PartialFunction receive(){
        return ReceiveBuilder.match(ParseArticle.class, mes -> {
            ActorRef ref = buildExtraActor(sender(), mes.url);
            cacheActor.tell(new GetRequest(mes.url),ref);
            httpClientActor.tell(mes.url,ref);
            context().system().scheduler().scheduleOnce(timeout.duration(), ref, "timeout",
                    context().system().dispatcher(), ActorRef.noSender());

        }).build();
    }

    private ActorRef buildExtraActor(ActorRef actor, String url){
        class MyActor extends AbstractActor {
            public MyActor(){
                receive(ReceiveBuilder
                                .matchEquals(String.class, mes -> mes.equals("timeout"), mes -> {
                                    actor.tell(new akka.actor.Status.Failure(new TimeoutException("timeout!")), self());
                                    context().stop(self());
                                }).match(HttpResponse.class, mes -> {
                                    artcileParseActor.tell(new ParseHtmlArticle(url, mes.body), self());
                                }).match(String.class, mes -> {
                                    actor.tell(mes, self());
                                    context().stop(self());
                                }).match(ArticleBody.class, mes -> {
                                    cacheActor.tell(new SetRequest(mes.url, mes.body), self());
                                    sender().tell(mes.body, self());
                                    context().stop(self());
                                }).matchAny(mes -> {
                                    System.out.println("Any");
                                })
                                .build()
                );
            }
        }
        return context().actorOf(Props.create(MyActor.class, () -> new MyActor()));

    }
}

