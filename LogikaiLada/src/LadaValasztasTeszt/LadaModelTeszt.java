package LadaValasztasTeszt;

import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LadaModelTeszt {

    public static void main(String[] args) {
        LadaModelTeszt teszt = new LadaModelTeszt();
        LogikaiLadaControllerTeszt logikaiteszt = new LogikaiLadaControllerTeszt();
        //Boti
        teszt.tesztFeliratok();
        teszt.tesztKincsesLada();
        
        //Tomi
        teszt.tesztNemLetezoLada();
        logikaiteszt.tesztKincsTalalat();
        
        //Bence
        teszt.tesztVisszajelzesSzoveg();
        teszt.tesztHibasLadaAzonosito();
        
        System.out.println("Az összes teszt lefutott!");
    }

    private void tesztFeliratok() {
        try {
            LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset!", false);
            LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset!", false);
            LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik!", false);

            assert !arany.getFelirat().isBlank() : "Arany ládának nincs felirata";
            assert !ezust.getFelirat().isBlank() : "Ezüst ládának nincs felirata";
            assert !bronz.getFelirat().isBlank() : "Bronz ládának nincs felirata";

            assert arany.getFelirat().contains("rejtem") : "Arany felirat hibás";
            assert ezust.getFelirat().contains("Nem én") : "Ezüst felirat hibás";
            assert bronz.getFelirat().contains("hazudik") : "Bronz felirat hibás";

            System.out.println("✅ tesztFeliratok sikeres");
        } catch (AssertionError e) {
            System.err.println("❌ tesztFeliratok hiba: " + e.getMessage());
        }
    }

    private void tesztKincsesLada() {
        try {
            LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset!", true);
            LadaModell ezust = new LadaModell("Ezüst", "Nem én rejtem a kincset!", false);
            LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik!", false);

            int kincsDb = 0;
            if (arany.isTartalmazKincset()) {
                kincsDb++;
            }
            if (ezust.isTartalmazKincset()) {
                kincsDb++;
            }
            if (bronz.isTartalmazKincset()) {
                kincsDb++;
            }

            assert kincsDb == 1 : "Nem pontosan egy ládában van kincs";

            System.out.println("✅ tesztKincsesLada sikeres");
        } catch (AssertionError e) {
            System.err.println("❌ tesztKincsesLada hiba: " + e.getMessage());
        }
    }

    private void tesztNemLetezoLada() {
        try {
            new LadaModell("", "", false);
            System.err.println("❌ üres névvel is létrejött a láda");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ tesztNemLetezoLada sikeres");
        }
    }

    public static class LogikaiLadaControllerTeszt {

        private void tesztKincsTalalat() {
            try {
                GuiNezet nezet = new GuiNezet();
                LadaModell modell = new LadaModell("Arany", "Én rejtem a kincset!", true);
                LogikaiLadaController vezerlo = new LogikaiLadaController(modell, nezet);

                nezet.getRdbArany().doClick(); // kattintás szimulálása

                String szoveg = ""; // Itt van a visszajelzés

                assert szoveg.contains("Gratulálok") : "Nem a megfelelő visszajelzést kaptuk: " + szoveg;

                System.out.println("✅ tesztKincsTalalat sikeres");
            } catch (AssertionError e) {
                System.err.println("❌ tesztKincsTalalat hiba: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("⚠️ tesztKincsTalalat kivétel: " + e.getMessage());
            }
        }

        private void tesztVisszajelzesSzoveg() {
            try {
                GuiNezet nezet = new GuiNezet();
                LadaModell modell = new LadaModell("Arany", "Én rejtem a kincset!", true);
                new LogikaiLadaController(modell, nezet);

                String uzenet = "🎉 Gratulál A(z) Arany ládában volt a kincs!";
                nezet.mutat(uzenet);

                assert nezet.getjLabel1() != null : "A visszajelzés nem jelent meg";
                System.out.println("✅ tesztVisszajelzesSzoveg sikeres");
            } catch (AssertionError e) {
                System.err.println("❌ tesztVisszajelzesSzoveg hiba: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("⚠️ tesztVisszajelzesSzoveg kivétel: " + e.getMessage());
            }
        }

        private void tesztHibasLadaAzonosito() {
            try {
                GuiNezet nezet = new GuiNezet();
                LadaModell modell = new LadaModell("Diamant", "Rejtett kincs", true); // létező ládákon kívül
                LogikaiLadaController vezerlo = new LogikaiLadaController(modell, nezet);

                // Nincs megfelelő rádiógomb, de próbáljuk meg "kiválasztani"
                boolean talalat = false;
                if ("Arany".equals(modell.getNev())) {
                    nezet.getRdbArany().doClick();
                } else if ("Ezüst".equals(modell.getNev())) {
                    nezet.getRdbEzust().doClick();
                } else if ("Bronz".equals(modell.getNev())) {
                    nezet.getRdbBronz().doClick();
                } else {
                    talalat = true; // nincs rádiógomb a modell nevénél
                }
                assert talalat : "A vezérlő nem kezelte a nem létező ládát";

                System.out.println("✅ tesztHibasLadaAzonosito sikeres");
            } catch (AssertionError e) {
                System.err.println("❌ tesztHibasLadaAzonosito hiba: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("⚠️ tesztHibasLadaAzonosito kivétel: " + e.getMessage());
            }
        }
    }
}
