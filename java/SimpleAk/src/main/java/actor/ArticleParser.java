package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import akka.japi.pf.UnitPFBuilder;
import akka.util.Timeout;
import mol.*;
import scala.PartialFunction;
import scala.compat.java8.functionConverterImpls.AsJavaFunction;
import scala.runtime.AbstractFunction1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import static scala.compat.java8.FutureConverters.*;
import static akka.pattern.Patterns.ask;
/**
 * Created by cqshinn on 2/13/16.
 */
public class ArticleParser extends AbstractActor {
    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection articleParseActor;
    private final Timeout timeout;

    public ArticleParser(String cacheActorPath, String
            httpClientActorPath, String artcileParseActorPath, Timeout timeout){
        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.articleParseActor = context().actorSelection(artcileParseActorPath);
        this.timeout = timeout;
    }


    public PartialFunction receice(){
        return ReceiveBuilder.
                match(ParseArticle.class, mes -> {
                    final CompletionStage cacheResult = toJava(ask(cacheActor, new GetRequest(mes.url), timeout));
                    final CompletionStage result = cacheResult.handle((x, t) -> {
                        return x != null ? CompletableFuture.completedFuture(x) : toJava(ask(httpClientActor, mes.url, timeout));
                    }).thenCompose(new AsJavaFunction(new AbstractFunction1<HttpResponse,CompletionStage>() {
                        @Override
                        public CompletionStage apply(HttpResponse raw) {
                            return toJava(ask(articleParseActor,
                                    new ParseHtmlArticle(mes.url, raw.body),
                                    timeout));
                        }
                    })).thenCompose(new AsJavaFunction(new AbstractFunction1() {
                        @Override
                        public Object apply(Object v1) {
                            return v1;
                        }
                    }));
                    result.handle((x,t) -> {
                        if(x!=null){
                            if(x instanceof ArticleBody){
                                String body = ((ArticleBody) x).body;
                                cacheActor.tell(body, self());
                                sender().tell(body, self());
                            }else if(x instanceof String){
                                sender().tell(x, self());
                            }
                        }
                        else {
                            sender().tell(new Status.Failure((Throwable) t), self());
                        }
                        return null;
                    });
                }).build();
    }
}
