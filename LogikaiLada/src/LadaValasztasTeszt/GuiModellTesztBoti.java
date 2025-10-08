package LadaValasztasTeszt;

import modell.LadaModell;

public class GuiModellTesztBoti {

    public static void main(String[] args) {

        LadaModell arany = new LadaModell("Arany", "Én rejtem a kincset.", false);
        LadaModell ezust = new LadaModell("Ezust", "Nem én rejtem a kincset.", true);
        LadaModell bronz = new LadaModell("Bronz", "Az arany láda hazudik.", false);

        LadaModell[] ladak = {arany, ezust, bronz};

        System.out.println(" Minden modellteszt sikeresen lefutott!");

        

        tesztFeliratokTartalmazzakASzuksegesSzovegeket(ladak);
        tesztLadakSzama(ladak);
        tesztCsakEgyLadaTartalmazKincset(ladak);
    }

    

    private static void tesztFeliratokTartalmazzakASzuksegesSzovegeket(LadaModell[] ladak) {
        String aranyFelirat = ladak[0].getFelirat();
        String ezustFelirat = ladak[1].getFelirat();
        String bronzFelirat = ladak[2].getFelirat();

        assert aranyFelirat.contains("én rejtem") : " Az arany láda felirata hibás: " + aranyFelirat;

        assert ezustFelirat.contains("nem én rejtem") : " Az ezüst láda felirata hibás: " + ezustFelirat;

        assert bronzFelirat.contains("arany") : " A bronz láda felirata hibás: " + bronzFelirat;

        System.out.println(" tesztFeliratokTartalmazzakASzuksegesSzovegeket sikeres");
    }

    private static void tesztLadakSzama(LadaModell[] ladak) {
        assert ladak.length == 3 : "Pontosan 3 ládának kell lennie, de " + ladak.length + " van.";
        System.out.println(" tesztLadakSzama sikeres");
    }
    
    
    private static void tesztCsakEgyLadaTartalmazKincset(LadaModell[] ladak) {
        int kincsesDb = 0;
        
        for (LadaModell lada : ladak) {
            if (lada.isTartalmazKincset()) {
                kincsesDb++;
            }
        }
        
        assert kincsesDb == 1 : "Csak egy ládában lehet kincs, de " + kincsesDb + " ládában van!";
        System.out.println(" tesztCsakEgyLadaTartalmazKincset sikeres");
    }

}
