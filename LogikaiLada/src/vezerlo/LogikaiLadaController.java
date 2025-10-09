package vezerlo;

import modell.LadaModell;
import nezet.GuiNezet;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogikaiLadaController {
    private final LadaModell modell;
    private final GuiNezet nezet;

    public LogikaiLadaController(LadaModell modell, GuiNezet nezet) {
        this.modell = modell;
        this.nezet = nezet;
        init();
    }

    private void init() {
        nezet.getBtnUjra().addActionListener(e -> ujraindit());

        nezet.getRdbArany().addActionListener(new LadaValaszto());
        nezet.getRdbEzust().addActionListener(new LadaValaszto());
        nezet.getRdbBronz().addActionListener(new LadaValaszto());

        ujraindit();
    }

    private void ujraindit() {
        int kincsIndex = (int) (Math.random() * 3);
        switch (kincsIndex) {
            case 0 -> modell.setNev("Arany");
            case 1 -> modell.setNev("Ezüst");
            case 2 -> modell.setNev("Bronz");
        }
        modell.setTartalmazKincset(true);

        nezet.getButtonGroup1().clearSelection();
        nezet.mutat("Válaszd ki, melyik ládában van a kincs!");
    }

    private class LadaValaszto implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String valasztott = "";
            if (nezet.getRdbArany().isSelected()){
                valasztott = "Arany";
            }else if (nezet.getRdbEzust().isSelected()){
                valasztott = "Ezüst";
            }else if (nezet.getRdbBronz().isSelected()){
                valasztott = "Bronz";
            }

            String uzenet;
            if (valasztott.equals(modell.getNev())) {
                uzenet = "🎉 Gratulálok! A(z) " + valasztott + " ládában volt a kincs!";
            } else {
                uzenet = "😢 Sajnos nem talált! A kincs a " + modell.getNev() + " ládában volt.";
            }

            nezet.mutat(uzenet);
        }
    }
}
