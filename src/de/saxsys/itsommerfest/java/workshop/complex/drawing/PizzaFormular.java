package de.saxsys.itsommerfest.java.workshop.complex.drawing;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Repräsentiert ein Formular, das für eine Pizza im UI angezeigt wird.
 */
public class PizzaFormular {

    private int nummer;

    private Label labelUeberschrift;
    private TextField textFieldDurchmesser;
    private TextField textFieldPreis;
    private Label labelFlaechenPreis;

    private StackPane pizzaBildPlatzhalter;

    private VBox formularZeilen;

    /**
     * Erzeugt ein Formular mit all seinen Feldern.
     *
     * @param nummer Die Nummer der Pizza.
     */
    public PizzaFormular(int nummer) {
        this.nummer = nummer;

        formularZeilen = setzeFormularZusammen();
    }

    private VBox setzeFormularZusammen() {

        labelUeberschrift = new Label("Pizza " + nummer);
        labelUeberschrift.setStyle("-fx-font-weight: bold;");

        textFieldDurchmesser = new TextField();
        textFieldPreis = new TextField();

        labelFlaechenPreis = new Label();
        labelFlaechenPreis.prefWidthProperty().bind(textFieldPreis.widthProperty());

        pizzaBildPlatzhalter = new StackPane();

        VBox formularZeilen = new VBox(10);

        formularZeilen.getChildren().addAll(
                labelUeberschrift,
                erzeugeFormularZeile("Durchmesser in cm", textFieldDurchmesser),
                erzeugeFormularZeile("Preis in €", textFieldPreis),
                erzeugeFormularZeile("Preis je cm² in €", labelFlaechenPreis),
                pizzaBildPlatzhalter);

        return formularZeilen;
    }

    public void erzeugeUndSetzePizzaBild() {

        double durchmesser = konvertiereZuDouble(textFieldDurchmesser.getText());
        pizzaBildPlatzhalter.getChildren().clear();
        pizzaBildPlatzhalter.getChildren().add(new PizzaBild(durchmesser).getDarstellung());
    }

    private HBox erzeugeFormularZeile(String label, Node valueField) {

        valueField.prefWidth(50);

        HBox formularZeile = new HBox(20);
        formularZeile.setAlignment(Pos.BASELINE_RIGHT);
        formularZeile.getChildren().addAll(new Label(label), valueField);

        return formularZeile;
    }

    /**
     * Validiert die Daten der Eingabefelder des Formulars.
     *
     * @return false, wenn der Inhalt leer ist oder keine Zahl ist. true, sonst.
     */
    public boolean eingabenValide() {

        return kannZuZahlKonvertiertWerden(textFieldDurchmesser.getText(), textFieldPreis.getText());
    }

    private boolean kannZuZahlKonvertiertWerden(String... expectedNumbersAsText) {

        for (String text : expectedNumbersAsText) {
            if (text.isEmpty()) {
                return false;
            }

            double number = Double.parseDouble(text.replace(",", "."));
            if (Double.isNaN(number)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Berechnet den Flächenpreis der Pizza, derren Daten in dieses Formular eingegeben wurden und setzt ihn im Ergebnisfeld.
     */
    public void berechneUndSetzeFlaechenPreis() {

        double durchmesser = konvertiereZuDouble(textFieldDurchmesser.getText());
        double preis = konvertiereZuDouble(textFieldPreis.getText());

        Double flaechenPreis = berechneFlaechenPreis(durchmesser, preis);

        labelFlaechenPreis.setText(gerundet(flaechenPreis));
    }

    private double konvertiereZuDouble(String text) {

        return Double.parseDouble(text.replace(",", "."));
    }

    private String gerundet(Double flaechenPreis) {
        return String.format("%1$.4f", flaechenPreis);
    }

    private double berechneFlaechenPreis(double durchmesser, double preis) {

        double radius = durchmesser / 2;
        double flaeche = Math.PI * Math.pow(radius, 2);

        return preis / flaeche;
    }

    public VBox getFormularZeilen() {
        return formularZeilen;
    }

}
