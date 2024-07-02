
package org.redhat.demo.crazytrain.model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class Detection {
    @JsonProperty("class_id")
    private int classId;
    @JsonProperty("class_name")
    private String className;
    private double confidence;
    private ArrayList<Double> box;

    
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public double getConfidence() {
        return confidence;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    public ArrayList<Double> getBox() {
        return box;
    }
    public void setBox(ArrayList<Double> box) {
        this.box = box;
    }
    @Override
    public String toString() {
        return "Detection [classId=" + classId + ", className=" + className + ", confidence=" + confidence + ", box="
                + box + "]";
    }
   


}
