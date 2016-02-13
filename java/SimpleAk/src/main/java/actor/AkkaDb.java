package actor;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.collect.Maps;
import mol.GetRequest;
import mol.KeyNotFoundException;
import mol.SetRequest;

import java.util.Map;

/**
 * Created by cqshinn on 2/11/16.
 */
public class AkkaDb extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    public final Map<String, Object> map = Maps.newHashMap();

    private AkkaDb() {
        receive(ReceiveBuilder
                        .match(SetRequest.class, mes -> {
                            log.info("Received set request – key: {}\n value: {}\n", mes.getKey(), mes.getValue());
                            map.put(mes.getKey(), mes.getValue());
                        })
                        .match(GetRequest.class, mes -> {
                            log.info("Received get request – key: {}\n", mes);
                            String value = (String)map.get(mes.key);
                            Object response = value != null ? value : new Status.Failure(new KeyNotFoundException(mes.key));
                            sender().tell(response, self());
                        })
                        .matchAny(obj -> {
                            log.info("receive unknown obj {}\n", obj);
                            sender().tell(new Status.Failure(new ClassNotFoundException("not found obj")), self());
                        })
                        .build()
        );
    }
}
