package com.project;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Controller1 implements initialize {

    @FXML
    private Button button0, button1, button2;
    @FXML
    private ImageView img0, img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, 
    img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23;
    @FXML
    private AnchorPane container;
    @FXML
    private Label progresslabel;
    @FXML
    private ProgressBar progressbar;

    @FXML
    public void initialize() {
        progresslabel.setVisible(false);
    }

    @FXML
    private void animateToView0(ActionEvent event) {
        UtilsViews.setViewAnimating("View0");
    }

    @FXML
    private void loadImage() {
        System.out.println("Loading image...");
        progresslabel.setVisible(true);
        img0.setImage(null);
        loadImageBackground((image) -> {
            System.out.println("Image loaded");
            img0.setImage(image);
            progresslabel.setVisible(false);
        });
    }

    public void loadImageBackground(Consumer<Image> callBack) {
        // Use a thread to avoid blocking the UI
        CompletableFuture<Image> futureImage = CompletableFuture.supplyAsync(() -> {
            try {
                // Wait a second to simulate a long loading time
                Thread.sleep(1000);

                // Load the data from the assets file
                Image image = new Image(getClass().getResource("/assets/image.png").toString());
                return image;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        futureImage.thenAcceptAsync(result -> {
            callBack.accept(result);
        }, Platform::runLater);
    }
}