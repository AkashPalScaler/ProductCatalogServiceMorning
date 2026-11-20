package com.scaler.productcatalogservice.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Naming rule: write the thing(e.g function), the situation(e.g input), and what we expect(the output).
// getElementByIndex_indexEqualToLength_throws
// AAA : Arrange, Act, Assert
// Arrange: Prepare the input and prerequisites
// Act: Invoke the function to test
// Assert: Check the output
public class ArrayUtilTest {

    @Test
    @DisplayName("Calling arrayUtil.getElementByIndex for middle element should returns element successfully")
    public void getElementByIndex_middleIndex_returnsElementSuccessfully(){
        // Arrange
        Integer[] shelf = {10, 20, 30};

        // Act
        Integer result = ArrayUtil.getElementByIndex(shelf, 1);

        // Assert
        assertThat(result).isEqualTo(20);
    }

    @Test
    @DisplayName("Calling arrayUtil.getElementByIndex for index out of bounds(on edge) should throw IllegalArgumentException")
    public void getElementByIndex_indexOutOfBoundsOnEdge_throwsIllegalArgumentException(){
        Integer[] shelf = {10, 20, 30};

//        Integer result = ArrayUtil.getElementByIndex(shelf, 3);

        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.getElementByIndex(shelf, 3));
    }

    @Test
    @DisplayName("Calling arrayUtil.getElementByIndex for index out of bounds(far) should throw IllegalArgumentException")
    public void getElementByIndex_indexOutOfBoundsFar_throwsIllegalArgumentException(){
        Integer[] shelf = {10, 20, 30};

        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.getElementByIndex(shelf, 99));
    }

    @ParameterizedTest
    @CsvSource({"0, 10", "1, 20", "2, 30"})
    public void getElementByIndex_goodIndex_returnsElementSuccessfully(int index, int expected){
        // Arrange
        Integer[] shelf = {10, 20, 30};

        // Act
        Integer result = ArrayUtil.getElementByIndex(shelf, index);

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"-1", "3" , "99", "10010101"})
    public void getElementByIndex_badIndex_throwsIllegalArgumentException(int index){
        Integer[] shelf = {10, 20, 30};
        assertThrows(IllegalArgumentException.class, () -> ArrayUtil.getElementByIndex(shelf, index));
    }
}


// 1, 10
// 2, 20