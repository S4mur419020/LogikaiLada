package LadaValasztasTeszt;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LadaModelTeszt {

    public static void main(String[] args) {


        /*Boti*/
        //tesztFeliratokTartalmazzakASzuksegesSzovegeket();
        //tesztHibasHossz();
        //tesztMelyikAKincs();
        //tesztMasSzovegetTartalmazLada();
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
        try {
            GuiNezet nezet = new GuiNezet();

            JRadioButton arany = nezet.getRdbArany();
            JRadioButton ezust = nezet.getRdbEzust();
            JRadioButton bronz = nezet.getRdbBronz();

            assert arany.getText().equals("rany") : "Az arany láda felirata hibás";
            assert ezust.getText().equals("ezüst") : "Az ezüst láda felirata hibás";
            assert bronz.getText().equals("bronz") : "A bronz láda felirata hibás";

            JLabel aranySzoveg = nezet.getjLabel1();
            JLabel ezustSzoveg = nezet.getjLabel2();
            JLabel bronzSzoveg = nezet.getjLabel3();

            assert aranySzoveg.getText().equals("rejtem a kincset!") : "Az arany láda felirata hibás";
            assert ezustSzoveg.getText().equals("Nem én rejtem a kincset!") : "Az ezüst láda felirata hibás";
            assert bronzSzoveg.getText().equals("Az arany láda hazudik!") : "A bronz láda felirata hibás";

        } catch (AssertionError hiba) {
            System.err.println("❌ Hiba a teszt során: " + hiba.getMessage());
        } catch (Exception hiba) {
            System.err.println("⚠️ Váratlan hiba történt: " + hiba.getMessage());
        }
        System.out.println("✅ tesztFeliratokTartalmazzakASzuksegesSzovegeket lefutott");
    }

    public static void tesztHibasHossz() {
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", true);
        LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", false);

        LadaModell[] ladak = {arany, ezust, bronz};

        int darab = 1;//hiba
        int ladaDb = ladak.length;

        assert ladaDb == darab : "A ládák száma hibás! (" + ladaDb + " db láda kell, nem " + darab + ")";
        System.out.println("tesztHibasHossz() hiba nélkül lefutott");
    }

    public static void tesztMelyikAKincs() {
        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", false);
        LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", true);

        LadaModell[] ladak = {arany, ezust, bronz};

        int kincsesDb = 0;
        //for-each
        for (LadaModell lada : ladak) {
            if (lada.isTartalmazKincset()) {
                kincsesDb++;
                assert lada.getNev().equals("Ezüst") : "Nem az ezüst ládában van a kincs!";
            }
        }
    }

    private static void tesztMasSzovegetTartalmazLada() {
        try {
            LadaModell hibasLada = new LadaModell("Arany", "Valami más szöveg", true);
            String szoveg = "Én rejtem a kincset!";

            assert hibasLada.getFelirat().equals(szoveg) : "Hibás felirat az " + hibasLada.getNev() + " ládán: \"" + hibasLada.getFelirat() + "\" (Nem a " + szoveg + " a szöveg)";
        } catch (AssertionError hiba) {
            System.err.println("❌ HIBA elkapva (tesztMasSzovegetTartalmazLada): " + hiba.getMessage());
        }
        System.out.println("✅ tesztMasSzovegetTartalmazLada sikeresen lefutott (helyes felirat).");
    }

   private static void tesztHibasLadaHivatkozas() {
    LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
    LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset.", true);
    LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", false);
    LadaModell[] ladak = {arany, ezust, bronz};

    boolean tortentHiba = false;

    try {
        int hivatkozas = 3; // Ez hibás index (létező ládák: 0,1,2)
        LadaModell lada = ladak[hivatkozas]; // Itt fog IndexOutOfBoundsException keletkezni
    } catch (IndexOutOfBoundsException ex) {
        tortentHiba = true;
        System.err.println("❌ Hiba: Nem létező ládára hivatkoztunk! (" + ex.getMessage() + ")");
    }

    assert tortentHiba : "Hibát kellett volna dobnia, de nem történt!";
    System.out.println("✅ tesztHibasLadaHivatkozas lefutott");
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
