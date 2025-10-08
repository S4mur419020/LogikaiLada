
package LadaValasztasTeszt;

import nezet.GuiNezet;


public class GuiModellTeszt {
    public static void main(String[] args) {
        GuiNezet gui = new GuiNezet();
        
        tesztLadakFeliratainakEllenorzese(gui);
        
        
        
        tesztFeliratokSzovegreszEllenorzese();
        
        
        
    }

    private static void tesztFeliratokSzovegreszEllenorzese() {
        String arany = "Én rejtem a kincset.";
        String ezust = "Nem én rejtem a kincset.";
        String bronz = "Az arany láda hazudik.";
        
        
        assert arany.contains("én rejtem");
        assert ezust.contains("nem én rejtem");
        assert bronz.contains("arany");
    }

    
    private static void tesztLadakFeliratainakEllenorzese(GuiNezet gui) {
        assert gui.getRdbArany().getText().contains("Arany") : "Arany láda felirat hibás";
        assert gui.getRdbEzust().getText().contains("Ezüst") : "Ezüst láda felirat hibás";
        assert gui.getRdbBronz().getText().contains("Bronz") : "Bronz láda felirat hibás";
    }
}
