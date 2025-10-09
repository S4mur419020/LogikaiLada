package LadaValasztasTeszt;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LadaModelTeszt {
    
    
    public static void main(String[] args){
        
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezust", "Nem én rejtem a kincset.", true);
        LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", false);

        LadaModell[] ladak = {arany, ezust, bronz};
        
//        /*Boti*/
//        tesztFeliratokTartalmazzakASzuksegesSzovegeket(ladak);
//        tesztLadakSzama(ladak);
//        tesztCsakEgyLadaTartalmazKincset(ladak);
        
        /*Bence*/
        tesztNevHibasKivetel();
        tesztFeliratHibasKivetel();
        tesztToString();
        
        /*Tomi*/
        
//        tesztHelyesValasztas();
//        tesztHelytelenValasztas();
//        tesztMegfeleloSzobeg();
//    
    }
     private static void tesztFeliratokTartalmazzakASzuksegesSzovegeket(LadaModell[] ladak) {
        String aranyFelirat = ladak[0].getFelirat();
        String ezustFelirat = ladak[1].getFelirat();
        String bronzFelirat = ladak[2].getFelirat();

        assert aranyFelirat.contains("én rejtem") : " Az arany láda felirata hibás: " + aranyFelirat;

        assert ezustFelirat.contains("nem én rejtem") : " Az ezüst láda felirata hibás: " + ezustFelirat;

        assert bronzFelirat.contains("arany") : " A bronz láda felirata hibás: " + bronzFelirat;
        
         System.out.println("A teszt lefutott");

    }

    private static void tesztLadakSzama(LadaModell[] ladak) {
        assert ladak.length == 3 : "Pontosan 3 ládának kell lennie, de " + ladak.length + " van.";
         System.out.println("A teszt lefutott");
    }
    
    
    private static void tesztCsakEgyLadaTartalmazKincset(LadaModell[] ladak) {
        int kincsesDb = 0;
        
        for (LadaModell lada : ladak) {
            if (lada.isTartalmazKincset()) {
                kincsesDb++;
            }
        }
        
        assert kincsesDb == 1 : "Csak egy ládában lehet kincs, de " + kincsesDb + " ládában van!";
         System.out.println("A teszt lefutott");
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
         System.out.println("A teszt lefutott");
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
         System.out.println("A teszt lefutott");
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
         System.out.println("A teszt lefutott");
    }
    
    private static void tesztHelyesValasztas() {
        TestGuiNezet jvalasz = new TestGuiNezet();
        LadaModell modell = new LadaModell("Arany", "Én rejtem a kincset.", true);
        LogikaiLadaController controller = new LogikaiLadaController(modell, jvalasz);

        jvalasz.rdbArany.setSelected(true);
        jvalasz.rdbArany.doClick();

        assert jvalasz.utolsoUzenet.contains("Gratulálok") 
        : "A helyes választásnál gratulációt kellett volna kapni, de ezt kaptuk: " + jvalasz.utolsoUzenet;
         System.out.println("A teszt lefutott");
    }

    private static void tesztHelytelenValasztas() {
        TestGuiNezet rvalasz = new TestGuiNezet();
        LadaModell modell = new LadaModell("Ezüst", "Nem én rejtem a kincset!", true);
        LogikaiLadaController controller = new LogikaiLadaController(modell, rvalasz);

        rvalasz.rdbBronz.setSelected(true);
        rvalasz.rdbBronz.doClick();

        assert rvalasz.utolsoUzenet.contains("Sajnos")
        : "A helytelen választásnál 'Sajnos' üzenetnek kell megjelennie.";
         System.out.println("A teszt lefutott");
    }

    private static void tesztMegfeleloSzobeg() {
        TestGuiNezet mszoveg = new TestGuiNezet();
        LadaModell modell = new LadaModell("Bronz", "Az arany láda hazudik!", true);
        LogikaiLadaController controller = new LogikaiLadaController(modell, mszoveg);

        mszoveg.rdbBronz.setSelected(true);
        mszoveg.rdbBronz.doClick();

        String uzenet = mszoveg.utolsoUzenet;

        assert (uzenet.startsWith("🎉") || uzenet.startsWith("😢"))
        : "Az üzenetnek emojival kell kezdődnie: " + uzenet;
        
        assert uzenet.contains("láda")
        : "Az üzenetnek tartalmaznia kell a 'láda' szót: " + uzenet;
         System.out.println("A teszt lefutott");
    }

    private static class TestGuiNezet extends GuiNezet {

        String utolsoUzenet = "";
        JButton ujra = new JButton();
        JRadioButton rdbArany = new JRadioButton("Arany láda");
        JRadioButton rdbEzust = new JRadioButton("Ezüst láda");
        JRadioButton rdbBronz = new JRadioButton("Bronz láda");
        ButtonGroup group = new ButtonGroup();

        TestGuiNezet() {
            group.add(rdbArany);
            group.add(rdbEzust);
            group.add(rdbBronz);
        }

        @Override
        public JButton getBtnUjra() {
            return ujra;
        }

        @Override
        public JRadioButton getRdbArany() {
            return rdbArany;
        }

        @Override
        public JRadioButton getRdbEzust() {
            return rdbEzust;
        }

        @Override
        public JRadioButton getRdbBronz() {
            return rdbBronz;
        }

        @Override
        public ButtonGroup getButtonGroup1() {
            return group;
        }

        @Override
        public void mutat(String uzenet) {
            utolsoUzenet = uzenet;
        }
    }

}
