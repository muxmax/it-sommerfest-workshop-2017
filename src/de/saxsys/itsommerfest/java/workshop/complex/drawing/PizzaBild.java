package de.saxsys.itsommerfest.java.workshop.complex.drawing;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Repräsentiert die Darstellung (gezeichnet) einer Pizza.
 */
public class PizzaBild {

    private static final int SKALIERUNGSFAKTOR = 3;
    private static final int RAND_BREITE = 10;
    private static final int SCHEIBEN_ANZAHL = 6;

    private final StackPane root;

    public PizzaBild(double durchmesser) {
        root = erzeugePizza(durchmesser);
    }

    private StackPane erzeugePizza(double durchmesser) {
        StackPane blech = new StackPane();

        double aussenRadius = durchmesser / 2 * SKALIERUNGSFAKTOR;

        Circle boden = new Circle(aussenRadius);
        boden.setStyle("-fx-stroke: #f1c232; " +
                "-fx-stroke-width: " + RAND_BREITE + "px; " +
                "-fx-fill: #ffe599");

        blech.getChildren().add(boden);

        for (int i = 0; i < SCHEIBEN_ANZAHL; i++) {
            Circle wurstScheibe = erzeugeWurstScheibeInBereich(boden);
            blech.getChildren().add(wurstScheibe);
        }

        return blech;
    }

    private Circle erzeugeWurstScheibeInBereich(Circle belag) {

        double schreibenRadius = belag.getRadius() / 5;
        Circle wurstScheibe = new Circle(schreibenRadius);

        wurstScheibe.setStyle("-fx-fill: #cc4125; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke: #990000");

        int platzierungsGrenze = (int) (belag.getRadius() - RAND_BREITE - schreibenRadius);

        // platziere Scheibe relativ zur Mitte
        wurstScheibe.setTranslateX(naechstePosition(platzierungsGrenze));
        wurstScheibe.setTranslateY(naechstePosition(platzierungsGrenze));

        return wurstScheibe;
    }

    private int naechstePosition(int belagRadius) {

        int sign = new Random().nextBoolean() ? -1 : 1;

        return sign * (new Random().nextInt(belagRadius));
    }

    public Pane getDarstellung() {
        return root;
    }
}
