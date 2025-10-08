package modell;

public class LadaModell {
    private String nev;          
    private String felirat;      
    private boolean tartalmazKincset;
   
    public LadaModell() {
        this("Ismeretlen láda", "Nincs felirat", false);
    }

    
    public LadaModell(String nev) {
        this(nev, "Nincs felirat", false);
    }
    
    public LadaModell(String nev, String felirat, boolean tartalmazKincset) {
        this.nev = nev;
        this.felirat = felirat;
        this.tartalmazKincset = tartalmazKincset;
    }
    
    public String getNev() {
        return nev;
    }

    public String getFelirat() {
        return felirat;
    }

    public boolean isTartalmazKincset() {
        return tartalmazKincset;
    }

   
    public void setNev(String nev) {
        if (nev == null || nev.isBlank()) {
            throw new IllegalArgumentException("A láda neve nem lehet üres!");
        }
        this.nev = nev;
    }

    public void setFelirat(String felirat) {
        if (felirat == null || felirat.isBlank()) {
            throw new IllegalArgumentException("A felirat nem lehet üres!");
        }
        this.felirat = felirat;
    }

    public void setTartalmazKincset(boolean tartalmazKincset) {
        this.tartalmazKincset = tartalmazKincset;
    }

    @Override
    public String toString() {
        return nev + " láda - \"" + felirat + "\"" 
                + (tartalmazKincset ? " [Kincs itt van]" : "");
    }
}
