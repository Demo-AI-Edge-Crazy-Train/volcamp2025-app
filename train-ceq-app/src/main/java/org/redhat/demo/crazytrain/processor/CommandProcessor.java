package org.redhat.demo.crazytrain.processor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import jakarta.inject.Named;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import org.redhat.demo.crazytrain.model.Detection;
import org.redhat.demo.crazytrain.model.Result;

// import org.kie.dmn.api.core.DMNResult;
// import org.kie.kogito.decision.DecisionModel;
// import org.kie.kogito.decision.DecisionModels;


@Named("CommandProcessor") 
@ApplicationScoped
public class CommandProcessor implements Processor{
    private static final Logger LOGGER = Logger.getLogger(CommandProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        String classId = null;
        LOGGER.debugf("Received : '%s'",exchange.getIn().getBody().toString());
        Result result = (Result) exchange.getIn().getBody();
        ArrayList<Detection> detections = result.getDetections();
        
        // DecisionModel commandCalculator = decisionModels.getDecisionModel("https://github.com/kiegroup/drools/kie-dmn/_ABFBE1B1-087F-4764-BBCC-72EDAE11A092", "command");
        // Map<String, Object> context = new HashMap<>();
        // context.put("detections", detections);
        // router.evaluateAll(commandCalculator.newContext(context));

        // LOGGER.infof("Transformed to JSON : '%s'",jsonObject.toString());
        if((detections == null) || detections.size() == 0){
            classId = "-1";
        } else {
            classId = String.valueOf(((Detection)detections.get(0)).getClassId());
        }
        exchange.getIn().setBody(classId);   
        //exchange.getMessage().ack();
    }
}