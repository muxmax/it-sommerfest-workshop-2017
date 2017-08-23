package de.saxsys.itsommerfest.java.workshop.complex.adding;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PizzaCalculator extends Application {

    private final List<PizzaFormular> allePizzaFormulare = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox formulare = new HBox(30);
        formulare.getChildren().add(weiteresPizzaFormular(allePizzaFormulare.size() + 1));

        Button buttonBerechnePreis = new Button("Preis je cm² berechnen");
        buttonBerechnePreis.setOnAction(e -> {

            for (PizzaFormular pizzaFormular : allePizzaFormulare) {

                if (pizzaFormular.eingabenValide()) {
                    pizzaFormular.berechneUndSetzeFlaechenPreis();
                } else {
                    zeigeFehlermeldungAn();
                }
            }
        });

        Button buttonWeiterePizza = new Button("Weitere Pizza ...");
        buttonWeiterePizza.setOnAction(e -> {

            PizzaFormular formular = new PizzaFormular(allePizzaFormulare.size() + 1);
            allePizzaFormulare.add(formular);

            formulare.getChildren().add(formular.getFormularZeilen());
        });

        HBox buttons = erzeugeButtonLeiste(buttonBerechnePreis, buttonWeiterePizza);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(formulare, buttons);

        VBox.setVgrow(formulare, Priority.ALWAYS);

        primaryStage.setScene(new Scene(root, 800, 300));
        primaryStage.show();

    }

    private VBox weiteresPizzaFormular(int pizzaNummer) {

        PizzaFormular formular = new PizzaFormular(pizzaNummer);
        allePizzaFormulare.add(formular);

        return formular.getFormularZeilen();
    }


    private void zeigeFehlermeldungAn() {

        new Alert(Alert.AlertType.ERROR, "Bitte nur Zahlen eingegeben!", ButtonType.OK).show();
    }


    private HBox erzeugeButtonLeiste(Button... buttons) {

        HBox buttonLeiste = new HBox(5);
        buttonLeiste.getChildren().addAll(buttons);

        return buttonLeiste;
    }

}
