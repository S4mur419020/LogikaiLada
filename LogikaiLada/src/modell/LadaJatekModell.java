package modell;

import java.util.ArrayList;
import java.util.List;

public class LadaJatekModell {

    private List<LadaModell> ladaLista;

    private void ladakBeallit() {
        if (ladaLista == null) {
            ladaLista = new ArrayList<>();
        }

        ladaLista.clear();

        ladaLista.add(new LadaModell("Arany láda", "Én rejtem a kincset!", false));
        ladaLista.add(new LadaModell("Ezüst láda", "Nem én rejtem a kincset!", true));
        ladaLista.add(new LadaModell("Bronz láda", "Az arany láda hazudik!", false));
    }

    public LadaJatekModell() {
        ladaLista = new ArrayList<>();
        ladakBeallit();
    }

    public void ujraindit() {
        ladaLista = new ArrayList<>();
        ladakBeallit();
    }

    public List<LadaModell> getLadaLista() {
        return ladaLista;
    }

    public LadaModell getLada(String valasztottNev) {
        for (LadaModell lada : ladaLista) {
            if (lada.getNev().contains(valasztottNev)) {
                return lada;
            }
        }
        return null;
    }
}
