package com.intaapp.imageprocessing;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;


import java.io.ByteArrayOutputStream;
import android.util.Log;

public class ImageProcessingModule extends ReactContextBaseJavaModule {

    private ReadableArray firstLower,firstUpper, secondLower,secondUpper;
    private ImageProcessingOperations imageProcessingOperations;

    public ImageProcessingModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        imageProcessingOperations = new ImageProcessingOperations();
    }

    @NonNull
    @Override
    public String getName() {
        return "NativeOpenCV";
    }

    @ReactMethod
    public void processImage(String fileUri, ReadableArray firstRange, ReadableArray secondRange, Promise promise) {

        try {
            setRanges(firstRange,secondRange);
            ByteArrayOutputStream imageBase64  = imageProcessingOperations.getBase64Image(fileUri,getReactApplicationContext(),firstLower,firstUpper,secondLower,secondUpper);
            String imageLocation = imageProcessingOperations.getImageLocation(imageBase64);
            WritableMap resultMap = imageProcessingOperations.createResultMap(imageLocation);
            promise.resolve(resultMap);

        } catch (Exception e) {
            Log.d("OURERROR",e.getMessage());
            promise.reject("OpenCv","Error processing image in java module");
            e.printStackTrace();
        }

    }


    private void setRanges(ReadableArray firstRange, ReadableArray secondRange) {
        firstLower = firstRange.getArray(0);
        firstUpper = firstRange.getArray(1);
        secondLower = secondRange.getArray(0);
        secondUpper = secondRange.getArray(1);
    }


}
