package de.saxsys.itsommerfest.java.workshop.simple;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PizzaCalculator extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextField textFieldFormularDurchmesser = new TextField();
        TextField textFieldFormularPreis = new TextField();
        Label labelFormularFlaechenPreis = new Label();
        labelFormularFlaechenPreis.prefWidthProperty().bind(textFieldFormularPreis.widthProperty());

        VBox formularZeilen = new VBox(10);
        formularZeilen.getChildren().addAll(
                erzeugeFormularZeile("Durchmesser in cm", textFieldFormularDurchmesser),
                erzeugeFormularZeile("Preis in €", textFieldFormularPreis),
                erzeugeFormularZeile("Preis je cm² in €", labelFormularFlaechenPreis));


        HBox formulare = new HBox(30);
        formulare.getChildren().add(formularZeilen);

        Button buttonBerechnePreis = new Button("Preis je cm² berechnen");
        buttonBerechnePreis.setOnAction(e -> {

            boolean eingabenValide = eingabenValide(textFieldFormularDurchmesser.getText(), textFieldFormularPreis.getText());

            if (eingabenValide) {
                berechneUndSetzeFlaechenpreis(textFieldFormularDurchmesser, textFieldFormularPreis, labelFormularFlaechenPreis);
            } else {
                zeigeFehlermeldungAn();
            }

        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(formulare, buttonBerechnePreis);

        VBox.setVgrow(formulare, Priority.ALWAYS);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

    }

    private boolean eingabenValide(String... expectedNumbersAsText) {

        for (String text : expectedNumbersAsText) {
            if (text.isEmpty()) {
                return false;
            }

            double number = konvertiereZuDouble(text);
            if (Double.isNaN(number)) {
                return false;
            }
        }

        return true;
    }

    private void zeigeFehlermeldungAn() {

        new Alert(Alert.AlertType.ERROR, "Bitte nur Zahlen eingegeben!", ButtonType.OK)
                .show();
    }

    private void berechneUndSetzeFlaechenpreis(TextField textFieldDurchmesser, TextField textFieldPreis, Label labelFlaechenPreis) {

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

    private HBox erzeugeFormularZeile(String label, Node valueField) {

        valueField.prefWidth(50);

        HBox formularZeile = new HBox(20);
        formularZeile.setAlignment(Pos.BASELINE_RIGHT);
        formularZeile.getChildren().addAll(new Label(label), valueField);

        return formularZeile;
    }
}
