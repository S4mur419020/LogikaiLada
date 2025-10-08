package LadaValasztasTeszt;

import modell.LadaModell;

public class LadaModelTeszt {
    
    
    public static void main(String[] args){
        tesztFeliratSzovegresz();
        tesztCsakEgyLadaTartalmazKincset();
        tesztHaromLadaVan();
        tesztNevHibasKivetel();
        tesztFeliratHibasKivetel();
        tesztToString();
        System.out.println("Minden teszt lefutott hiba nélkül!");
    
    }
     public static void tesztFeliratSzovegresz() {
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", false);
        LadaModell bronz = new LadaModell("Bronz", "Az arany hazudik.", true);

        assert arany.getFelirat().toLowerCase().contains("én rejtem") : "Arany felirat hibás";
        assert ezust.getFelirat().toLowerCase().contains("nem én rejtem") : "Ezüst felirat hibás";
        assert bronz.getFelirat().toLowerCase().contains("arany hazudik") : "Bronz felirat hibás";
    }

    public static void tesztCsakEgyLadaTartalmazKincset() {
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", false);
        LadaModell bronz = new LadaModell("Bronz", "Az arany hazudik.", true);

        LadaModell[] ladak = {arany, ezust, bronz};
        int kincsesDb = 0;
        for (LadaModell lada : ladak) {
            if (lada.isTartalmazKincset()) kincsesDb++;
        }

        assert kincsesDb == 1 : "Pontosan egy ládában kell legyen kincs";
    }
    
    public static void tesztHaromLadaVan() {
        LadaModell[] ladak = {
            new LadaModell("Arany", "Én rejtem a kincset.", false),
            new LadaModell("Ezüst", "Nem én rejtem a kincset.", false),
            new LadaModell("Bronz", "Az arany hazudik.", true)
        };

        assert ladak.length == 3 : "Pontosan három ládának kell lennie";
    }

    public static void tesztNevHibasKivetel() {
        LadaModell lada = new LadaModell();
        boolean dobott = false;

        try {
            lada.setNev(null);
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Null név esetén kivételt kell dobni";

        dobott = false;
        try {
            lada.setNev("");
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Üres név esetén kivételt kell dobni";

        dobott = false;
        try {
            lada.setNev("   ");
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Csak szóköz név esetén kivételt kell dobni";
    }

    public static void tesztFeliratHibasKivetel() {
        LadaModell lada = new LadaModell();
        boolean dobott = false;

        try {
            lada.setFelirat(null);
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Null felirat esetén kivételt kell dobni";

        dobott = false;
        try {
            lada.setFelirat("");
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Üres felirat esetén kivételt kell dobni";

        dobott = false;
        try {
            lada.setFelirat("   ");
        } catch (IllegalArgumentException e) {
            dobott = true;
        }
        assert dobott : "Csak szóköz felirat esetén kivételt kell dobni";
    }

    public static void tesztToString() {
        LadaModell lada1 = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell lada2 = new LadaModell("Bronz", "Az arany hazudik.", true);

        String szoveg1 = lada1.toString();
        String szoveg2 = lada2.toString();

        assert szoveg1.contains("Arany") : "toString nem tartalmazza a nevet";
        assert szoveg1.contains("Én rejtem") : "toString nem tartalmazza a feliratot";
        assert !szoveg1.contains("[Kincs itt van]") : "toString jelzi a kincset, de nincs ott";

        assert szoveg2.contains("Bronz") : "toString nem tartalmazza a nevet";
        assert szoveg2.contains("arany hazudik") : "toString nem tartalmazza a feliratot";
        assert szoveg2.contains("[Kincs itt van]") : "toString nem jelzi a kincset";
    }

}
