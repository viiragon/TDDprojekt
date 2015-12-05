/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tddprojekt;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wojtek
 */

/*
 WYMAGANIA
 Program : 
 1.zwraca tą samą wartość gdy jednostki są identyczne
 2.poprawnie przetwarza wartości funt <=> gram
 3.rozumie przedrostki miary (m, c, d, da, h, k)
 4.zgłasza wyjątek gdy jednostka nie istnieje w bazie jednostek
 5.potrafi przetworzyć tablicę wartości
 6.poprawnie przelicza wartości mila <=> metr
 7.zwraca wyjątek gdy jednostki wejściowe i wyjściowe nie są z tej samej kategorii
 */
public class ConverterTest {

    private final double delta = 0.001;

    @Test
    public void theSameUnitsTest() {
        double number = 5;
        double theSame = Converter.convert(number, "g", "g");
        assertEquals(number, theSame, delta);
    }

    @Test
    public void gramsToFuntsTest() {
        double gram = 1000;
        double funt = Converter.convert(gram, "g", "lb");
        assertEquals(funt, 2.205, delta);
    }

    @Test
    public void funtsToGramsTest() {
        double funt = 5;
        double gram = Converter.convert(funt, "lb", "g");
        assertEquals(gram, 2267.961, delta);
    }

    @Test
    public void metricPrefixesTest() {
        double gram = 1000;
        double miligram = Converter.convert(gram, "g", "mg");
        double centygram = Converter.convert(miligram, "mg", "cg");
        double decygram = Converter.convert(centygram, "cg", "dg");
        double dekagram = Converter.convert(decygram, "dg", "dag");
        double hektagram = Converter.convert(dekagram, "dag", "hg");
        double kilogram = Converter.convert(hektagram, "hg", "kg");
        assertEquals(miligram, 1000000, delta);
        assertEquals(centygram, 100000, delta);
        assertEquals(decygram, 10000, delta);
        assertEquals(dekagram, 100, delta);
        assertEquals(hektagram, 10, delta);
        assertEquals(kilogram, 1, delta);
    }

    @Test(expected = UnitNotFoundException.class)
    public void fromUnitDoesNotExistTest() {
        Converter.convert(1, "stołek", "mg");
    }

    @Test(expected = UnitNotFoundException.class)
    public void toUnitDoesNotExistTest() {
        Converter.convert(1, "lb", "zmywarka");
    }

    @Test(expected = UnitNotFoundException.class)
    public void fromUnitWithGoodPrefixDoesNotExistTest() {
        Converter.convert(1, "daCHÓWKA", "g");
    }
    
    @Test(expected = UnitNotFoundException.class)
    public void toUnitWithGoodPrefixDoesNotExistTest() {
        Converter.convert(1, "lb", "kALAFIOR");
    }
}
