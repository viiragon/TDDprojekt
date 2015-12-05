/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tddprojekt;

/**
 *
 * @author Wojtek
 */
public class Converter {

    private static Unit[] unitBase = new Unit[]{
        new Unit("g", 1000),
        new Unit("lb", 2.204623)
    };

    private static Unit[] prefixBase = new Unit[]{
        new Unit("m", 0.001),
        new Unit("c", 0.01),
        new Unit("da", 10), //'da' musi być przed 'd' z powodu sposobu działania wyr.regularnych
        new Unit("d", 0.1),
        new Unit("h", 100),
        new Unit("k", 1000),};

    public static double convert(double i, String from, String to) {
        if (from.equals(to)) {
            return i;
        }
        double[] fromValue = findConversionValue(from);
        double[] toValue = findConversionValue(to);

        return i * ((toValue[1] * fromValue[0]) / (fromValue[1] * toValue[0]));
    }

    private static double[] findConversionValue(String name) {
        double prefixValue = 1;
        double unitValue = findUnitValue(name);

        if (unitValue == 0) {   //Nie ma takiej jednostki, szukamy przedrostka
            Unit prefix = findPrefix(name);
            if (prefix != null) {
                String shortName = name.substring(prefix.getName().length());   //Nazwa bez przedrostka
                prefixValue = prefix.getValue();
                unitValue = findUnitValue(shortName);   //Szukamy bez przedrostka
            }
            if (unitValue == 0) {
                throw new UnitNotFoundException("Nie ma takiej jednostki jak : '" + name + "'");
            }
        }
        return new double[] {prefixValue, unitValue};
    }
    
    private static double findUnitValue(String name) {
        for (Unit u : unitBase) {
            if (u.getName().equals(name)) {
                return u.getValue();
            }
        }
        return 0;
    }

    private static Unit findPrefix(String name) {
        for (Unit p : prefixBase) {
            if (name.matches("(" + p.getName() + ").+")) {
                return p;
            }
        }
        return null;
    }

    private static class Unit {

        private final String name;
        private final double value;

        private Unit(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public double getValue() {
            return value;
        }
    }
}
