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

    //W1
    
    @Test
    public void theSameUnitsTest() {
        double number = 5;
        double theSame = Converter.convert(number, "g", "g");
        assertEquals(number, theSame, delta);
    }

    //W2
    
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

    //W3
    
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

    //W4
    
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

    //W5

    @Test
    public void matrixTest() {
        double wejscie[] = {10, 20, 46};
        double wyjscie[] = Converter.convert(wejscie, "g", "lb");
        assertEquals(wyjscie[0], 0.022, delta);
        assertEquals(wyjscie[1], 0.044, delta);
        assertEquals(wyjscie[2], 0.101, delta);
    }

    //W6

    @Test
    public void kilometersToMile() {
        double kilometr = 5;
        double mila = Converter.convert(kilometr, "km", "mi");
        assertEquals(mila, 3.107, delta);
    }

    @Test
    public void milesToKilometers() {
        double mila = 5;
        double kilometr = Converter.convert(mila, "mi", "km");
        assertEquals(kilometr, 8.046, delta);
    }

    //W7

    @Test(expected = WrongCategoryException.class)
    public void fromKilometersToGrams() {
        Converter.convert(1, "km", "g");
    }

    @Test(expected = WrongCategoryException.class)
    public void fromFuntsToMiles() {
        Converter.convert(1, "lb", "mi");
    }

}
