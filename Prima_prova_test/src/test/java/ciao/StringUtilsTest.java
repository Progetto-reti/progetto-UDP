package ciao;

import org.example.StringUtils;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    @Test
    void ReverseTest() {
        //test quando l'input è nullo
        assertThrows(IllegalArgumentException.class, () -> StringUtils.reverse(null));
        //test quando l'input è empty
        assertEquals("", StringUtils.reverse(""));
        //test quando la lunghezza di input è uguale a 1
        assertEquals("o", StringUtils.reverse("o"));
        //test quando la lunghezza di input è maggiore di 1
        assertEquals("olleh", StringUtils.reverse("hello"));
        // test quando la stringa presenta un carattere speciale
        assertEquals("buonanott@",StringUtils.reverse("@ttonanoub"));
        //test quando la stringa presenta uno spazio
        assertEquals("ciao mondo",StringUtils.reverse("odnom oaic"));
    }
    @Test
        void IsPalindromeTest (){
        //test quando input è null
        assertThrows(IllegalArgumentException.class, () -> StringUtils.isPalindrome(null));
        //test quando input è null
        assertTrue(StringUtils.isPalindrome(""));
        //test quando input ha lunghezza uguale a 1
        assertTrue(StringUtils.isPalindrome("a"));
        //test quando input ha una lettera maiuscola
        assertFalse(StringUtils.isPalindrome("Anna"));
        //test quando input ha lunghezza maggiore di 1 ed è palindroma
        assertTrue(StringUtils.isPalindrome("anna"));
        //test quando input ha lunghezza maggiore di 1 e non è palindroma
        assertFalse(StringUtils.isPalindrome("ciao"));
        //test quando input è una frase
        assertFalse(StringUtils.isPalindrome("amo ridere di roma"));
    }
    @Test
    void ConcatenateTest(){
        //test quando str1 è null e str2 non-null
        assertThrows(IllegalArgumentException.class, () -> StringUtils.concatenate(null,"hello"));
        //test quando str1 non-null e str2 null
        assertThrows(IllegalArgumentException.class, () -> StringUtils.concatenate("hello",null));
        //test quando str1 è empty e str2 non-empty
        assertEquals("meraviglioso", StringUtils.concatenate("","meraviglioso"));
        //test quando str1 non-empty e str2 empty
        assertEquals("petaloso", StringUtils.concatenate("petaloso",""));
        //test str1 e str2 lunghezza 1 non nulle
        assertEquals("ab",StringUtils.concatenate("a","b"));
        //test str1 e str2 lunghezza maggiore di 1 non nulle
        assertEquals("buonNatale",StringUtils.concatenate("buon","Natale"));
        //test str1 lunghezza 1 e str2 lunghezza maggiore di 1
        assertEquals("escappa",StringUtils.concatenate("e","scappa"));
        //test str1 lunghezza maggiore di 1 e str2 lunghezza 1
        assertEquals("pianob",StringUtils.concatenate("piano","b"));
        //test str1 e str2 presentano spazi
        assertEquals("ciao  amo",StringUtils.concatenate("ciao "," amo"));
        //test str1 presenta lo spazio e str2 no
        assertEquals("ciao mondo",StringUtils.concatenate("ciao ","mondo"));
        //test str1 non presenta lo spazio e str2 sì
        assertEquals("ciao MONDO",StringUtils.concatenate("ciao", " MONDO"));

    }
@Test
    void CountOccurrencesTest(){
        //test input null e target non-null
    assertThrows(IllegalArgumentException.class, () -> StringUtils.countOccurrences(null,'h'));
        //test input empty e target non-null
    assertEquals(0,StringUtils.countOccurrences("",'y'));
        //test input non-null e target non-null presente in input
    assertEquals(1,StringUtils.countOccurrences("trapezio",'r'));
       //test input non-null e target non-null non presente in input
    assertEquals(0,StringUtils.countOccurrences("fiaba",'c'));
       //test input lunghezza 1 target non null presente nella stringa
    assertEquals(1,StringUtils.countOccurrences("a",'a'));
       //test input lunghezza 1 target non null non presente nella stringa
    assertEquals(0,StringUtils.countOccurrences("b",'o'));
    //test input lunghezza 1 target spazio presente nella stringa
    assertEquals(1,StringUtils.countOccurrences("ciao mondo",' '));
    //test input lunghezza 1 target spazio non presente nella stringa
    assertEquals(0,StringUtils.countOccurrences("ciaospettatori",' '));
     //test input lunghezza 1 target carattere speciale non presente nella stringa
    assertEquals(0,StringUtils.countOccurrences("ciao a tutti",'#'));
    //test input lunghezza 1 target carattere speciale presente nella stringa
    assertEquals(1,StringUtils.countOccurrences("ciao@mondo",'@'));
    //test input lunghezza maggiore di 1 e target presente più volte
    assertEquals(2, StringUtils.countOccurrences("hello", 'l'));
}

}
