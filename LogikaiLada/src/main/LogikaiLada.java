package main;

import modell.LadaModell;
import nezet.GuiNezet;
import vezerlo.LogikaiLadaController;

public class LogikaiLada {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            GuiNezet nezet = new GuiNezet();
            LadaModell modell = new LadaModell();
            new LogikaiLadaController(modell, nezet);
            nezet.setVisible(true);
        });
    }
}
