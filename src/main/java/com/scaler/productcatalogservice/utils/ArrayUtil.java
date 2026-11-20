package com.scaler.productcatalogservice.utils;

public class ArrayUtil {
    public static Integer getElementByIndex(Integer[] arr, int index){
        if(arr == null || index < 0 || index >= arr.length){
            throw new IllegalArgumentException("Bad Input");
        }
        return arr[index];
    }
}
