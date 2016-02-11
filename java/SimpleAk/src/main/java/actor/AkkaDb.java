package actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.collect.Maps;
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
                            map.put(mes.getKey(),mes.getValue());
                        }).matchAny(obj -> {
                            log.info("receive unknown obj {}\n",obj);
                        })
                        .build()
        );
    }
}
