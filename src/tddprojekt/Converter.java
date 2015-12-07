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

    public static final double PREFIX = 0.0, WEIGHT = 1.0, DISTANCE = 2.0;
    
    private static Unit[] unitBase = new Unit[]{
        new Unit("g", 1000, WEIGHT),
        new Unit("lb", 2.204623, WEIGHT),
        new Unit("km", 1, DISTANCE),
        new Unit("mi", 0.621371192, DISTANCE)
    };

    private static Unit[] prefixBase = new Unit[]{
        new Unit("m", 0.001, PREFIX),
        new Unit("c", 0.01, PREFIX),
        new Unit("da", 10, PREFIX), //'da' musi być przed 'd' z powodu sposobu działania wyr.regularnych
        new Unit("d", 0.1, PREFIX),
        new Unit("h", 100, PREFIX),
        new Unit("k", 1000, PREFIX),};

    public static double convert(double i, String from, String to) {
        if (from.equals(to)) {
            return i;
        }
        double[] fromValue = findConversionValue(from);
        double[] toValue = findConversionValue(to);
        if(fromValue[2] != toValue[2]){
                    throw new WrongCategoryException("Podane jednostki ( "+from+", "+ to +" ) są z różnych kategorii");
                }
        return i * ((toValue[1] * fromValue[0]) / (fromValue[1] * toValue[0]));
    }

    public static double[] convert(double [] wejscie, String from, String to){
        double[] wyjscie = new double[wejscie.length];
        double[] fromValue = findConversionValue(from);
        double[] toValue = findConversionValue(to);
        if(fromValue[2] != toValue[2]){
            throw new WrongCategoryException("Podane jednostki ( "+from+", "+ to +" ) są z różnych kategorii");
        }
        
        for(int i = 0; i < wejscie.length; i ++){
            wyjscie[i] = wejscie[i] * ((toValue[1] * fromValue[0]) / (fromValue[1] * toValue[0]));
        }
        return wyjscie;
    }
    
    private static double[] findConversionValue(String name) {
        double prefixValue = 1;
        double unitValue = findUnitValue(name);
        double category = findUnitCategory(name);

        if (unitValue == 0) {   //Nie ma takiej jednostki, szukamy przedrostka
            Unit prefix = findPrefix(name);
            if (prefix != null) {
                String shortName = name.substring(prefix.getName().length());   //Nazwa bez przedrostka
                prefixValue = prefix.getValue();
                unitValue = findUnitValue(shortName);   //Szukamy bez przedrostka
                category = findUnitCategory(shortName);
            }
            if (unitValue == 0) {
                throw new UnitNotFoundException("Nie ma takiej jednostki jak : '" + name + "'");
            }
        }
        return new double[] {prefixValue, unitValue, category};
    }
    
    private static double findUnitValue(String name) {
        for (Unit u : unitBase) {
            if (u.getName().equals(name)) {
                return u.getValue();
            }
        }
        return 0;
    }
    private static double findUnitCategory(String name) {
        for (Unit u : unitBase) {
            if (u.getName().equals(name)) {
                return u.getCategory();
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
        private final double category;

        private Unit(String name, double value, double category) {
            this.name = name;
            this.value = value;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public double getValue() {
            return value;
        }
        
        public double getCategory(){
            return category;
        }
    }
}
