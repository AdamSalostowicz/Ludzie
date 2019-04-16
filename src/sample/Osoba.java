package sample;

import javafx.beans.property.SimpleStringProperty;

public class Osoba {
    private final SimpleStringProperty ajdi, imie, nazwisko, ppesel, data, photoName;


    public Osoba(String ajdi, String imie, String nazwisko, String ppesel, String data, String photoName) {
        this.ajdi = new SimpleStringProperty(ajdi);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.ppesel = new SimpleStringProperty(ppesel);
        this.data = new SimpleStringProperty(data);
        this.photoName = new SimpleStringProperty(photoName);
    }

    public String getImie() {
        return imie.get();
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getPpesel() {
        return ppesel.get();
    }

    public void setPpesel(String ppesel) {
        this.ppesel.set(ppesel);
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getPhotoName() {
        return photoName.get();
    }

    public void setPhotoName(String photoName) {
        this.photoName.set(photoName);
    }

    public String getId() {
        return ajdi.get();
    }
    public void setId(String id) {
        this.ajdi.set(id);
    }
}
