package com.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
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

    boolean imageloading = false;
    Random rand = new Random();
    private ArrayList<ImageView> imageviewarray = new ArrayList<ImageView>();

    @FXML
    public void initialize() {
        button1.setDisable(false);
        button2.setDisable(true);
        progresslabel.setText("0");
        Collections.addAll(imageviewarray, img0, img1, img2, img3, img4, img5, img6, img7, 
    img8, img9, img10, img11, img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23);
    }

    @FXML
    private void animateToView0(ActionEvent event) {
        UtilsViews.setViewAnimating("View0");
    }

    @FXML
    private void loadImage() {
        imageloading = true;
        button1.setDisable(true);
        button2.setDisable(false);

        loadImageThread(0);
    }

    @FXML
    private void stopLoad() {
        button1.setDisable(false);
        button2.setDisable(true);
        imageloading = false;
    }

    private void loadImageThread(int index) {
        if (!imageloading) {
            progresslabel.setText("0");
            progressbar.setProgress(0);
            for (int i=0; i < imageviewarray.size(); i++) {
                imageviewarray.get(i).setImage(null);
            }
        }
        else if (imageloading) {
            if (index < imageviewarray.size()) {
                loadImageBackground((image) -> {
                    imageviewarray.get(index).setImage(image);
                    progressbar.setProgress((index+1.0)/24.0);
                    int imageIndex = index + 1; 
                    Platform.runLater(() -> {
                        progresslabel.setText(String.valueOf(index + 1));
                        loadImageThread(imageIndex);
                    });
                }, "img" + index + ".png");
                
            }
        }
    }

    public void loadImageBackground(Consumer<Image> callBack, String imageName) {
        // Use a thread to avoid blocking the UI
        CompletableFuture<Image> futureImage = CompletableFuture.supplyAsync(() -> {
            try {
                // Wait a second to simulate a long loading time
                Thread.sleep(1000);

                // Load the data from the assets file
                Image newImage = new Image(getClass().getResource("/assets/" + imageName).toString());
                return newImage;

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