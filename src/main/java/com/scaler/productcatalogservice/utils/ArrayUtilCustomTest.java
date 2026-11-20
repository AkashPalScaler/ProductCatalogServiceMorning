package com.scaler.productcatalogservice.utils;

import java.lang.reflect.Array;

public class ArrayUtilCustomTest {
    public static void main(String[] args) {
        Integer[] shelf = {10, 20, 30};
        // We will call function we want to test with inputs and check if it returns expected output
        // Happy (Success Test cases)
        // Fetching the middle element should work
        if(ArrayUtil.getElementByIndex(shelf, 1) == 20){
            System.out.println("Test Passed: Fetching the middle element works");
        }else{
            System.out.println("Test Failed: Fetching the middle element works");
        }
        // Sad (Failure Test cases)
        // Fetching the element out of bounds should throw IllegalArgumentException
        try{
            ArrayUtil.getElementByIndex(shelf, 3);
            System.out.println("Test Failed: Fetching the element out of bounds should throw IllegalArgumentException");
        } catch (Exception e){
            if(e instanceof IllegalArgumentException){
                System.out.println("Test Passed: Fetching the element out of bounds should throw IllegalArgumentException");
            }else{
                System.out.println("Test Failed: Fetching the element out of bounds should throw IllegalArgumentException");
            }

        }

        // Sad (Failure Test cases)
        // Fetching the element out of bounds should throw IllegalArgumentException
        try{
            ArrayUtil.getElementByIndex(shelf, 99);
            System.out.println("Test Failed: Fetching the element out of bounds should throw IllegalArgumentException");
        } catch (IllegalArgumentException e){
            System.out.println("Test Passed: Fetching the element out of bounds should throw IllegalArgumentException");
        }

    }
}
// Break till 8:20 AM.
