package com.example.s3rekognition;

import java.io.Serializable;
import java.util.List;

public class FacesResponse  implements Serializable {
    String bucketName;
    List<FacesClassificationResponse> results;

    public FacesResponse() {
    }

    public FacesResponse(String bucketName, List<FacesClassificationResponse> results) {
        this.bucketName = bucketName;
        this.results = results;
    }

    public String getBucketName() {
        return bucketName;
    }

    public List<FacesClassificationResponse> getResults() {
        return results;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setResults(List<FacesClassificationResponse> results) {
        this.results = results;
    }
}
