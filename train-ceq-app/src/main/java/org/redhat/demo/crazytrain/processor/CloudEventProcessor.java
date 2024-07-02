package org.redhat.demo.crazytrain.processor;

import org.jboss.logging.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.net.URI;
import java.util.Base64;
import java.util.Calendar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import jakarta.inject.Named;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;
import io.cloudevents.CloudEvent;
import io.cloudevents.SpecVersion;
import io.cloudevents.core.builder.CloudEventBuilder;
import java.net.URI;
import java.util.UUID;
import org.apache.camel.Processor;

@Named("CloudEventProcessor") 
@ApplicationScoped
public class CloudEventProcessor implements Processor{
    private static final Logger LOGGER = Logger.getLogger(CloudEventProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getIn().getBody().toString();
        LOGGER.debugf("Received : "+message);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        JsonNode no = null;
        JsonNode data = null;
        ObjectNode node = null;
        String base64ImageResult = null;
		try {
			data = mapper.readTree(message);
            String imageBytesBase64  = data.get("image").asText();
            JsonNode detections  = data.get("detections");
            if(detections != null && detections.size()>0 && detections.isArray()){
                byte[] imageBytes = Base64.getDecoder().decode(imageBytesBase64);
                    // Convert the byte array into a MatOfByte
                MatOfByte matOfByte = new MatOfByte(imageBytes);
                // Decode the MatOfByte into a Mat
                Mat image = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_UNCHANGED);
                image = addSquareToimage(image, detections);
                MatOfByte matOfByteReduced = new MatOfByte();
                // Convert the Mat object to a WEBP image
               Imgcodecs.imencode(".webp", image, matOfByteReduced, new MatOfInt(Imgcodecs.IMWRITE_WEBP_QUALITY, 80));
                // Convert the MatOfByte to a byte array
                byte[] imgBytes = matOfByteReduced.toArray();
                base64ImageResult = Base64.getEncoder().encodeToString(imgBytes);
            } else  base64ImageResult = imageBytesBase64;
        
            LOGGER.debugf("Base64 Image : '%d'",base64ImageResult.length());
            LOGGER.debug("Length of Data before deep copy "+data.toString().length());
            data = data.deepCopy();
            ((ObjectNode)data).put("image", "data:image/webp;base64,"+base64ImageResult);
            if(data == null)
                LOGGER.debug("Data is null");
            else LOGGER.debugf("Length of Data after copy image : '%d'",data.toString().length());
            ObjectMapper mp = new ObjectMapper();
            node = mp.createObjectNode()
                .put("id", UUID.randomUUID().toString())
                .put("specversion", "1.0")
                .put("source", "http://example.com")
                .put("type", "result")
                .put("subject", "result-message")
                .put("time", Calendar.getInstance().getTime().toString())
                .put("datacontenttype", "application/json");
            no = node.set("data", data);
        } catch (Exception e) {
            LOGGER.error("Error processing message", e);
        }
        LOGGER.debugf("CloudEvent : '%s'",no.toString());

        exchange.getIn().setBody(no.toString());

    }

     private Mat addSquareToimage(Mat image, JsonNode detections){
        if(detections == null || detections.size()==0 || !detections.isArray())
        return image;
        for(JsonNode detection : detections){
        double x = detection.get("box").get(0).asDouble();
        double y = detection.get("box").get(1).asDouble();
        double width = detection.get("box").get(2).asDouble();
        double height = detection.get("box").get(3).asDouble();
        // Create a rectangle from the detected box coordinates
        Rect rect = new Rect(new Point(x, y), new Size(width, height));
        // Draw the rectangle on the image
        Scalar color = new Scalar(0, 0, 255);  // Red color
        int thickness = 2;  // Thickness of the rectangle border
        Imgproc.rectangle(image, rect, color, thickness);
        // Add a label
        String label = detection.get("class_name").asText();  // Replace with your actual label
        int fontFace = Imgproc.FONT_ITALIC;
        double fontScale = 0.5;
        Scalar textColor = new Scalar(255, 0, 0);  // Red color
        int textThickness = 2;
        Imgproc.putText(image, label, new Point(x, y - 20), fontFace, fontScale, textColor, textThickness);
        String confidence = "Confidence: "+detection.get("confidence").asText();
        Imgproc.putText(image, confidence, new Point(x, y - 5), fontFace, fontScale, textColor, textThickness);
        }
        return image;
    }
}