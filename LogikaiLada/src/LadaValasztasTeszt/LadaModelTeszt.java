package LadaValasztasTeszt;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LadaModelTeszt {

    public static void main(String[] args) {


        /*Boti*/
        tesztFeliratokTartalmazzakASzuksegesSzovegeket();
        tesztLadakSzama();
        tesztCsakEgyLadaTartalmazKincset();
        tesztCsak2DbLada();
        tesztMasSzovegetTartalmazLada();

        /*Bence*/
        tesztNevHibasKivetel();
        tesztFeliratHibasKivetel();
        tesztToString();

        /*Tomi*/
        tesztHelyesValasztas();
        tesztHelytelenValasztas();
        tesztMegfeleloSzobeg();

        System.out.println("\nMinden teszt lefutott hiba nélkül!");
    }

    private static void tesztFeliratokTartalmazzakASzuksegesSzovegeket() {
        LadaModell arany = new LadaModell();
        arany.setNev("Arany");
        arany.setFelirat("Én rejtem a kincset.");
        arany.setTartalmazKincset(false);

        LadaModell ezust = new LadaModell();
        ezust.setNev("Ezüst");
        ezust.setFelirat("Nem én rejtem a kincset.");
        ezust.setTartalmazKincset(true);

        LadaModell bronz = new LadaModell();
        bronz.setNev("Bronz");
        bronz.setFelirat("Az arany láda hazudik.");
        bronz.setTartalmazKincset(false);

        assert arany.getFelirat().toLowerCase().contains("rejtem") : "Az arany láda felirata hibás";
        assert ezust.getFelirat().toLowerCase().contains("nem") : "Az ezüst láda felirata hibás";
        assert bronz.getFelirat().toLowerCase().contains("hazudik") : "A bronz láda felirata hibás";

        System.out.println("tesztFeliratokTartalmazzakASzuksegesSzovegeket lefutott");
    }

    private static void tesztLadakSzama() {
        LadaModell[] ladak = new LadaModell[3];
        assert ladak.length == 3 : "Pontosan 3 ládának kell lennie.";
        System.out.println("tesztLadakSzama lefutott");
    }

    private static void tesztCsakEgyLadaTartalmazKincset() {
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", true);
        LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", false);

        LadaModell[] ladak = {arany, ezust, bronz};
        int kincsesDb = 0;
        for (LadaModell lada : ladak) {
            if (lada.isTartalmazKincset()) {
                kincsesDb++;
            }
        }

        assert kincsesDb == 1 : "Csak egy ládában lehet kincs, de " + kincsesDb + " ládában van!";
        System.out.println("tesztCsakEgyLadaTartalmazKincset lefutott");
    }

    private static void tesztCsak2DbLada() {
        LadaModell[] hibasLadak = {
            new LadaModell("Arany", "Én rejtem a kincset!", true),
            new LadaModell("Ezüst", "Nem én rejtem a kincset!", false)
        };
        try {
            assert hibasLadak.length == 3 : "Pontosan 3 ládának kell lennie, de csak " + hibasLadak.length;
        } catch (AssertionError e) {
            System.out.println("HIBA elkapva (tesztCsak2DbLada): " + e.getMessage());
        }
    }

    private static void tesztMasSzovegetTartalmazLada() {
        LadaModell hibas = new LadaModell("Arany", "Valami más szöveg", true);
        try {
            assert hibas.getFelirat().contains("rejtem") : "Hibás felirat: " + hibas.getFelirat();
        } catch (AssertionError e) {
            System.out.println("HIBA elkapva (tesztMasSzovegetTartalmazLada): " + e.getMessage());
        }
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

        System.out.println("tesztNevHibasKivetel lefutott");
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

        System.out.println("tesztFeliratHibasKivetel lefutott");
    }

    public static void tesztToString() {
        LadaModell lada = new LadaModell();
        lada.setNev("Arany");
        lada.setFelirat("Én rejtem a kincset.");
        lada.setTartalmazKincset(false);

        String szoveg = lada.toString();
        assert szoveg.contains("Arany") : "toString nem tartalmazza a nevet";
        assert szoveg.contains("rejtem") : "toString nem tartalmazza a feliratot";
        assert !szoveg.contains("[Kincs itt van]") : "toString tévesen jelzi a kincset";

        System.out.println("tesztToString lefutott");
    }

    private static void tesztHelyesValasztas() {
        TestGuiNezet helyesv = new TestGuiNezet();
        LogikaiLadaController controller = new LogikaiLadaController(null, helyesv);

        helyesv.rdbArany.setSelected(true);
        helyesv.rdbArany.doClick();

        assert helyesv.utolsoUzenet.contains("Gratulálok") : "A helyes választásnál gratulációt kellett volna kapni.";
        System.out.println("tesztHelyesValasztas lefutott");
    }

    private static void tesztHelytelenValasztas() {
        TestGuiNezet helytelenv = new TestGuiNezet();
        LogikaiLadaController controller = new LogikaiLadaController(null, helytelenv);

        helytelenv.rdbBronz.setSelected(true);
        helytelenv.rdbBronz.doClick();

        assert helytelenv.utolsoUzenet.contains("Sajnos") : "A helytelen választásnál 'Sajnos' üzenetnek kell megjelennie.";
        System.out.println("tesztHelytelenValasztas lefutott");
    }

    private static void tesztMegfeleloSzobeg() {
        TestGuiNezet mfszov = new TestGuiNezet();
        LogikaiLadaController controller = new LogikaiLadaController(null, mfszov);

        mfszov.rdbEzust.setSelected(true);
        mfszov.rdbEzust.doClick();

        String uzenet = mfszov.utolsoUzenet;
        assert (uzenet.startsWith("🎉") || uzenet.startsWith("😢")) : "Az üzenetnek emojival kell kezdődnie.";
        assert uzenet.contains("láda") : "Az üzenetnek tartalmaznia kell a 'láda' szót.";
        System.out.println("tesztMegfeleloSzobeg lefutott");
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
