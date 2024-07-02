package org.redhat.demo.crazytrain.model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Result {
    private String id;
    private ArrayList<Detection> detections;
    @JsonProperty("pre-process")
    private String preProcess;
    private String inference;
    @JsonProperty("post-process")
    private String postProcess;
    private String total;
    private double scale;
    private String image;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ArrayList<Detection> getDetections() {
        return detections;
    }
    public void setDetections(ArrayList<Detection> detections) {
        this.detections = detections;
    }
    public String getPreprocess() {
        return preProcess;
    }
    public void setPreprocess(String preProcess) {
        this.preProcess = preProcess;
    }
    public String getInference() {
        return inference;
    }
    public void setInference(String inference) {
        this.inference = inference;
    }
    public String getPostprocess() {
        return postProcess;
    }
    public void setPostprocess(String postProcess) {
        this.postProcess = postProcess;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public double getScale() {
        return scale;
    }
    public void setScale(double scale) {
        this.scale = scale;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Result [id=" + id + ", detections=" + detections + ", preProcess=" + preProcess + ", inference="
                + inference + ", postProcess=" + postProcess + ", total=" + total + ", scale=" + scale + ", image="
                + image + "]";
    }
            
}
