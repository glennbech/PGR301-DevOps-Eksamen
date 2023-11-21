package com.example.s3rekognition;

import java.io.Serializable;

public class FacesClassificationResponse implements Serializable {
    private String fileName;
    private int personCount;

    public FacesClassificationResponse(String fileName, int personCount) {
        this.fileName = fileName;
        this.personCount = personCount;

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }
}
