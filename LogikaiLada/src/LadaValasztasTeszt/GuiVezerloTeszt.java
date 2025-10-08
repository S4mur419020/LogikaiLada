package LadaValasztasTeszt;

import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

import javax.swing.*;

public class GuiVezerloTeszt {

    public static void main(String[] args) {

        tesztHelyesValasztas();
        tesztHelytelenValasztas();
        
        tesztMegfeleloSzobeg();

    }
    /*1.a kiválasztott megfelelő  visszajelzést kapunk (benne a kincs, nincs benne)
    2.visszajelzés szövege megfelelő("Gratulálunk ...", "Sajnos nem nyert")
    3.ládára való hivatkozásnál mi  történik, ha szöveget adunk meg a szám helyett (vagy fordítva)*/

    private static void tesztHelyesValasztas() {
        TestGuiNezet jvalasz = new TestGuiNezet();
        LadaModell modell = new LadaModell("Arany", "Én rejtem a kincset.", true);
        LogikaiLadaController controller = new LogikaiLadaController(modell, jvalasz);

        jvalasz.rdbArany.setSelected(true);
        jvalasz.rdbArany.doClick();

        assert jvalasz.utolsoUzenet.contains("Gratulálok") 
        : "A helyes választásnál gratulációt kellett volna kapni, de ezt kaptuk: " + jvalasz.utolsoUzenet;
    }

    private static void tesztHelytelenValasztas() {
        TestGuiNezet rvalasz = new TestGuiNezet();
        LadaModell modell = new LadaModell("Ezüst", "Nem én rejtem a kincset!", true);
        LogikaiLadaController controller = new LogikaiLadaController(modell, rvalasz);

        rvalasz.rdbBronz.setSelected(true);
        rvalasz.rdbBronz.doClick();

        assert rvalasz.utolsoUzenet.contains("Sajnos")
        : "A helytelen választásnál 'Sajnos' üzenetnek kell megjelennie.";
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
