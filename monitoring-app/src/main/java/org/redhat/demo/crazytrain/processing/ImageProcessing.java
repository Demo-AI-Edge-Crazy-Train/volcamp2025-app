package org.redhat.demo.crazytrain.processing;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jboss.logging.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.redhat.demo.crazytrain.services.SaveService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;

@Path("/train-monitoring")
public class ImageProcessing {
  private static final Logger LOGGER = Logger.getLogger(ImageProcessing.class);
  private final BroadcastProcessor<String> broadcastProcessor = BroadcastProcessor.create();
  @Inject
  SaveService saveService;
  @ConfigProperty(name = "monitoring.saveImage")
  boolean saveImage;

  @ConfigProperty(name = "monitoring.tmpFolder") 
  String tmpFolder;

    @Inject
    MeterRegistry registry;

    Timer timer;

    @PostConstruct
    void init() {
        timer = Timer.builder("image.processing.time")
            .description("Time taken to get a message from Kafka and process it")
            .register(registry);
    }

  /* add the annotation here */
  public void process(String result) {
    LOGGER.debug("Consumer kafka recived  : "+result);
    long start = System.nanoTime();

    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode;
    try {
          jsonNode = mapper.readTree(result);
          JsonNode data = jsonNode.get("data");
          String imageBytesBase64  = data.get("image").asText();
            broadcastProcessor.onNext(imageBytesBase64);
            long end = System.nanoTime();
            timer.record(end - start, TimeUnit.NANOSECONDS);
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }  

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public Multi<String> stream() {
      return broadcastProcessor.toHotStream();
  }

  
}
