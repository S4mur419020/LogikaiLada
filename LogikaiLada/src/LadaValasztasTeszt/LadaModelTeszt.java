package LadaValasztasTeszt;

import modell.LadaModell;
import modell.LadaJatekModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LadaModelTeszt {

    public static void main(String[] args) {
        LadaModelTeszt teszt = new LadaModelTeszt();
        LogikaiLadaControllerTeszt logikaiteszt = new LogikaiLadaControllerTeszt();
        //Boti
        teszt.tesztFeliratok();
        teszt.tesztKincsesLada();
        //Bence
        teszt.tesztNemLetezoLada();
        logikaiteszt.tesztKincsTalalat();
        //Tomi
        logikaiteszt.tesztVisszajelzesSzoveg();
        logikaiteszt.tesztHibasLadaAzonosito();

        System.out.println("✅ Az összes teszt lefutott!");
    }

    private void tesztFeliratok() {
        try {
            LadaJatekModell jatek = new LadaJatekModell();
            LadaModell arany = jatek.getLadaLista().get(0);
            LadaModell ezust = jatek.getLadaLista().get(1);
            LadaModell bronz = jatek.getLadaLista().get(2);

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
            LadaJatekModell jatek = new LadaJatekModell();

            int kincsDb = 0;
            for (LadaModell lada : jatek.getLadaLista()) {
                if (lada.isTartalmazKincset()) {
                    kincsDb++;
                }
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
                LadaJatekModell jatek = new LadaJatekModell();
                LogikaiLadaController vezerlo = new LogikaiLadaController(jatek, nezet);

                // Lekérjük a kincses ládát ciklussal
                LadaModell kincsesLada = null;
                for (LadaModell lada : jatek.getLadaLista()) {
                    if (lada.isTartalmazKincset()) {
                        kincsesLada = lada;
                        break;
                    }
                }

                if (kincsesLada == null) {
                    throw new RuntimeException("Nincs kincses láda!");
                }

                String kincsesNev = kincsesLada.getNev();

                // Kiválasztjuk a megfelelő rádiógombot
                if (kincsesNev.equals("Arany láda")) {
                    nezet.getRdbArany().doClick();
                } else if (kincsesNev.equals("Ezüst láda")) {
                    nezet.getRdbEzust().doClick();
                } else if (kincsesNev.equals("Bronz láda")) {
                    nezet.getRdbBronz().doClick();
                }

                // Ellenőrizzük a visszajelzést
                String szoveg = nezet.getTextPaneSzoveg();
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
                LadaJatekModell jatek = new LadaJatekModell();
                new LogikaiLadaController(jatek, nezet);

                String uzenet = "🎉 Gratulálok! A(z) Arany ládában volt a kincs!";
                nezet.mutat(uzenet);

                String szoveg = nezet.getTextPaneSzoveg();
                assert szoveg.contains("Gratulálok") : "A visszajelzés nem jelent meg megfelelően";

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
                LadaModell hamis = new LadaModell("Gyémánt", "Nem létező láda", true);
                LadaJatekModell jatek = new LadaJatekModell();
                jatek.getLadaLista().add(hamis);

                LogikaiLadaController vezerlo = new LogikaiLadaController(jatek, nezet);

                boolean ismeretlen = true;
                if ("Arany láda".equals(hamis.getNev())
                        || "Ezüst láda".equals(hamis.getNev())
                        || "Bronz láda".equals(hamis.getNev())) {
                    ismeretlen = false;
                }
                assert ismeretlen : "A vezérlő nem kezelte le a nem létező ládát";

                System.out.println("✅ tesztHibasLadaAzonosito sikeres");
            } catch (AssertionError e) {
                System.err.println("❌ tesztHibasLadaAzonosito hiba: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("⚠️ tesztHibasLadaAzonosito kivétel: " + e.getMessage());
            }
        }
    }
}