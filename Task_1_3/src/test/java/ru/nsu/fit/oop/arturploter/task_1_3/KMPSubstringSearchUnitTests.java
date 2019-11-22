package ru.nsu.fit.oop.arturploter.task_1_3;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KMPSubstringSearchUnitTests {

    @Test
    void should_Return382_When_AttemptedToFindPatternInTextFile1() {
        String pattern = "abaf";
        String filePath = "src/test/resources/text_file_1.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 382);
    }

    @Test
    void should_Return384_When_AttemptedToFindAbafInTextFile2() {
        String pattern = "abaf";
        String filePath = "src/test/resources/text_file_2.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 384);
    }

    @Test
    void should_Return28_When_AttemptedToFindFabaInTextFile3() {
        String pattern = "faba";
        String filePath = "src/test/resources/text_file_3.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 28);
    }

    @Test
    void should_Return8_When_AttemptedToFindBorInTextFile4() {
        String pattern = "bor";
        String filePath = "src/test/resources/text_file_4.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 8);
    }

    @Test
    void should_Return0_When_AttemptedToFindBorInTextFile5() {
        String pattern = "bor";
        String filePath = "src/test/resources/text_file_5.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 0);
    }

    @Test
    void should_Return1_When_AttemptedToFindBorInTextFile6() {
        String pattern = "bor";
        String filePath = "src/test/resources/text_file_6.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 1);
    }

    @Test
    void should_ReturnNegativeOne_When_AttemptedToFindBorInTextFile7() {
        String pattern = "bor";
        String filePath = "src/test/resources/text_file_7.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, -1);
    }

    @Test
    void should_ReturnNegativeOne_When_AttemptedToFindBorInTextFile8() {
        String pattern = "bor";
        String filePath = "src/test/resources/text_file_8.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, -1);
    }

    @Test
    void should_Return14_When_AttemptedToFindGInTextFile9() {
        String pattern = "g";
        String filePath = "src/test/resources/text_file_9.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 14);
    }

    @Test
    void should_Return15_When_AttemptedToFindGInTextFile10() {
        String pattern = "g";
        String filePath = "src/test/resources/text_file_10.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 15);
    }

    @Test
    void should_Return15_When_AttemptedToFind7InTextFile11() {
        String pattern = "7";
        String filePath = "src/test/resources/text_file_11.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
            assertEquals(offset, 15);
    }

    @Test
    void should_ReturnNegativeOne_When_AttemptedToFind7InTextFile12() {
        String pattern = "7";
        String filePath = "src/test/resources/text_file_12.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, -1);
    }

    @Test
    void should_Return47_When_AttemptedToFindPapirosaInTextFile13() {
        String pattern = "папироса";
        String filePath = "src/test/resources/text_file_13.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 47);
    }

    @Test
    void should_Return7_When_AttemptedToFindPirogInTextFile14() {
        String pattern = "пирог";
        String filePath = "src/test/resources/text_file_14.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 7);
    }

    @Disabled("Do not run unless you have the large_text_file1.txt file located in src\\test\\resources\\ directory")
    @Test
    void should_Return810000001_When_AttemptedToFindAbafInLargeTextFile1() {
        String pattern = "abaf";
        String filePath = "src/test/resources/large_text_file_1.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 810000001);
    }

    @Disabled("Do not run unless you have the large_text_file2.txt file located in src\\test\\resources\\ directory")
    @Test
    void should_Return2100000001_When_AttemptedToFindAbafInLargeTextFile2() {
        String pattern = "abaf";
        String filePath = "src/test/resources/large_text_file_2.txt";

        KMPSubstringSearch obj = new KMPSubstringSearch(pattern);
        int offset = obj.readInFileAndSearch(filePath);
        assertEquals(offset, 2100000001);
    }
}