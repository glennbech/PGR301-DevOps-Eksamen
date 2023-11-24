package com.example.s3rekognition.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.s3rekognition.FacesClassificationResponse;
import com.example.s3rekognition.FacesResponse;
import com.example.s3rekognition.PPEClassificationResponse;
import com.example.s3rekognition.PPEResponse;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@RestController
public class RekognitionController implements ApplicationListener<ApplicationReadyEvent> {

    private final AmazonS3 s3Client;
    private final AmazonRekognition rekognitionClient;
    private final MeterRegistry meterRegistry;
    private int numberOfHands = 0;
    private int numberOfMaskViolation = 0;
    private int numberOfMaskPassed = 0;
    private int numberOfHandsNotInImage = 0;
    private int numberOfHandsInImage = 0;

    private Map<String, Integer> faces = new HashMap<>();
    private Map<String, Integer> hands = new HashMap<>();

    private static final Logger logger = Logger.getLogger(RekognitionController.class.getName());
    @Autowired
    public RekognitionController(MeterRegistry meterRegistry) {
        this.s3Client = AmazonS3ClientBuilder.standard().withRegion("eu-west-1").build();
        this.rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("eu-west-1").build();
        this.meterRegistry = meterRegistry;
    }

    /**
     * This endpoint takes an S3 bucket name in as an argument, scans all the
     * Files in the bucket for Protective Gear Violations.
     * <p>
     *
     * @param bucketName
     * @return
     */
    @Timed(value = "face_check_latency", description = "Latency of ppe")
    @GetMapping(value = "/scan-ppe", consumes = "*/*", produces = "application/json")
    @ResponseBody
    public ResponseEntity<PPEResponse> scanForPPE(@RequestParam String bucketName) {
        numberOfMaskViolation = 0;
        numberOfMaskPassed = 0;
        // List all objects in the S3 bucket
        ListObjectsV2Result imageList = s3Client.listObjectsV2(bucketName);

        // This will hold all of our classifications
        List<PPEClassificationResponse> classificationResponses = new ArrayList<>();

        // This is all the images in the bucket
        List<S3ObjectSummary> images = imageList.getObjectSummaries();

        // Iterate over each object and scan for PPE
        for (S3ObjectSummary image : images) {
            logger.info("scanning " + image.getKey());


            // This is where the magic happens, use AWS rekognition to detect PPE
            DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
                    .withImage(new Image()
                            .withS3Object(new S3Object()
                                    .withBucket(bucketName)
                                    .withName(image.getKey())))
                    .withSummarizationAttributes(new ProtectiveEquipmentSummarizationAttributes()
                            .withMinConfidence(80f)
                            .withRequiredEquipmentTypes("FACE_COVER"));

            DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);

            // If any person on an image lacks PPE on the face, it's a violation of regulations

            boolean violation = isViolation(result);

            if (violation){
                numberOfMaskViolation++;

            }else{
                numberOfMaskPassed++;
            }

            logger.info("scanning " + image.getKey() + ", violation result " + violation);
            // Categorize the current image as a violation or not.
            int personCount = result.getPersons().size();
            PPEClassificationResponse classification = new PPEClassificationResponse(image.getKey(), personCount, violation);
            classificationResponses.add(classification);
        }

        faces.put("maskViolation", numberOfMaskViolation);
        faces.put("maskPassed", numberOfMaskPassed);
        Gauge.builder("mask_violation", faces, map -> map.get("maskViolation")).register(meterRegistry);
        Gauge.builder("mask_passed", faces, map -> map.get("maskPassed")).register(meterRegistry);
        //logger.info("nummer: " + numberOfMaskViolation);
        PPEResponse ppeResponse = new PPEResponse(bucketName, classificationResponses);
        return ResponseEntity.ok(ppeResponse);



    }

    // sjekk hvor mange som har på hjelm
//    @Timed(value = "check_helmet", description = "Scanning if person have helmet")
//    @GetMapping(value = "/scan-helmet", consumes = "*/*", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<FacesResponse> scanForFaces(@RequestParam String bucketName) {
//
//        boolean numberOfFaces = true;
//        // List all objects in the S3 bucket
//        ListObjectsV2Result imageList = s3Client.listObjectsV2(bucketName);
//
//        // This will hold all of our classifications
//        List<FacesClassificationResponse> facesClassificationResponses = new ArrayList<>();
//
//        // This is all the images in the bucket
//        List<S3ObjectSummary> images = imageList.getObjectSummaries();
//
//        // Iterate over each object and scan for PPE
//        for (S3ObjectSummary image : images) {
//            logger.info("scanning " + image.getKey());
//
//
//            DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
//                    .withImage(new Image()
//                            .withS3Object(new S3Object()
//                                    .withBucket(bucketName)
//                                    .withName(image.getKey())))
//                    .withSummarizationAttributes(new ProtectiveEquipmentSummarizationAttributes()
//                            .withMinConfidence(80f)
//                            .withRequiredEquipmentTypes("FACE_COVER", "HELMET"));
//
//            DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);
//
//        }
//        return null;
//    }

    @Timed(value = "check_hands", description = "Scanning for hands")
    @GetMapping(value = "/scan-hands", consumes = "*/*", produces = "application/json")
    @ResponseBody
    public ResponseEntity<FacesResponse> scanForHands(@RequestParam String bucketName) {
        numberOfHands = 0;
        numberOfHandsNotInImage = 0;
        numberOfHandsInImage = 0;
        // List all objects in the S3 bucket
        ListObjectsV2Result imageList = s3Client.listObjectsV2(bucketName);

        // This will hold all of our classifications
        List<FacesClassificationResponse> facesClassificationResponses = new ArrayList<>();

        // This is all the images in the bucket
        List<S3ObjectSummary> images = imageList.getObjectSummaries();

        // Iterate over each object and scan for PPE
        for (S3ObjectSummary image : images) {
            logger.info("scanning " + image.getKey());


            DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
                    .withImage(new Image()
                            .withS3Object(new S3Object()
                                    .withBucket(bucketName)
                                    .withName(image.getKey())))
                    .withSummarizationAttributes(new ProtectiveEquipmentSummarizationAttributes()
                            .withMinConfidence(80f)
                            .withRequiredEquipmentTypes("HAND_COVER"));

            DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);

            numberOfHands += countHandWithPPE(result);
        }
            hands.put("handsCount", numberOfHandsInImage);
            Gauge.builder("hands_count", () -> numberOfHands).register(meterRegistry);
            FacesResponse facesResponse = new FacesResponse(bucketName, facesClassificationResponses);
            return ResponseEntity.ok(facesResponse);
        }



            /**
             * Detects if the image has a protective gear violation for the FACE bodypart-
             * It does so by iterating over all persons in a picture, and then again over
             * each body part of the person. If the body part is a FACE and there is no
             * protective gear on it, a violation is recorded for the picture.
             *
             * @param result
             * @return
             */


    private static boolean isViolation(DetectProtectiveEquipmentResult result) {
        return result.getPersons().stream()
                .flatMap(p -> p.getBodyParts().stream())
                .anyMatch(bodyPart -> bodyPart.getName().equals("FACE")
                        && bodyPart.getEquipmentDetections().isEmpty());
    }

    /**
     *
     * result.getPersons() er tom ? hvordan kan jeg få den til å hente ned bucket
     *
     * */

    private static int countHandWithPPE(DetectProtectiveEquipmentResult result) {
        int handsCount = 0;

        for (ProtectiveEquipmentPerson person : result.getPersons()) {
            for (ProtectiveEquipmentBodyPart bodyPart : person.getBodyParts()) {
                if ((bodyPart.getName().equals("RIGHT_HAND") || bodyPart.getName().equals("LEFT_HAND"))  && !bodyPart.getEquipmentDetections().isEmpty()) {
                     handsCount++;  // Violation found
                }
            }
        }
        return handsCount;  // No violation found



//        for (ProtectiveEquipmentPerson protectiveEquipmentPerson : result.getPersons()) {
//            for (ProtectiveEquipmentBodyPart bodyPart : protectiveEquipmentPerson.getBodyParts()) {
//                if ((bodyPart.getEquipmentDetections().contains("LEFT_HAND") || bodyPart.getEquipmentDetections().contains("RIGHT_HAND"))){
//                    handsCount++;
//                }
//            }
//        }
//        logger.info("Jeg er her linje 267 " + " " + handsCount);
//        return handsCount;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        if (!faces.isEmpty()){
//            Gauge.builder("hands_count_gauge", hands, map -> map.get("handsCount")).register(meterRegistry);
//            Gauge.builder("mask_violation_gauge", faces, map -> map.get("maskViolation")).register(meterRegistry);
//            Gauge.builder("mask_passed_gauge", faces, map -> map.get("maskPassed")).register(meterRegistry);
//
//
//        }
    }
}
