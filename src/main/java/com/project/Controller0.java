package com.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class Controller0 {

    @FXML
    private Button button0, button1, button2, button3;
    @FXML
    private AnchorPane container;
    @FXML
    private Label percentatge0, percentatge1, percentatge2;
    @FXML
    private ProgressBar taskbar1, taskbar2, taskbar3;
    
    private ExecutorService executor = Executors.newFixedThreadPool(3); // Creem una pool de dos fils

    private Future<?> future0, future1, future2;
    private int progress1 = 0, progress2 = 0;

    @FXML
    private void animateToView1(ActionEvent event) {
        UtilsViews.setViewAnimating("View1");
    }

    @FXML
    private void runTask1() {
        if (button1.getText().equals("Iniciar")) {
            future0 = backgroundTask(0);
            button1.setText("Aturar");
        } else if (button1.getText().equals("Aturar")) {
            future0.cancel(true);
            button1.setText("Iniciar");
            
        }
    }

    @FXML
    private void runTask2() {
        if (button2.getText().equals("Iniciar")) {
            progress1 = 0;
            future1 = backgroundTask(1);
            button2.setText("Aturar");
        } else if (button2.getText().equals("Aturar")) {
            future1.cancel(true);
            button2.setText("Iniciar");
            progress1 = 0;
        }
    }

    @FXML
    private void runTask3() {
        if (button3.getText().equals("Iniciar")) {
            progress2 = 0;
            future2 = backgroundTask(2);
            button3.setText("Aturar");
        } else if (button3.getText().equals("Aturar")) {
            future2.cancel(true);
            button3.setText("Iniciar"); 
        }
    }

    private Future<?> backgroundTask(int index) {
        return executor.submit(() -> {
            try {
                Random rand = new Random();

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                for (int i = 0; i <= 100; i++) {
                    final int currentValue = i;
    
                    if (index == 0) {
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            percentatge0.setText(String.valueOf(currentValue) + "%");
                            taskbar1.setProgress(currentValue/100.0);
                            if (currentValue == 100) {
                                button1.setText("Iniciar");
                            }
                        });
                        Thread.sleep(1000);

                    }

                    if (index == 1) {
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            progress1 += rand.nextInt(3) + 2;
                            percentatge1.setText(String.valueOf(progress1) + "%");
                            taskbar2.setProgress(progress1/100.0);
                            if (progress1 >= 100) {
                                progress1 = 100;
                                percentatge1.setText(String.valueOf(progress1) + "%");
                                taskbar2.setProgress(progress1/100.0);
                                button2.setText("Iniciar");
                            }
                        });
                        Thread.sleep(rand.nextInt(2001) + 3000);
                    }

                    if (index == 2) {
                        // Actualitzar el Label en el fil d'aplicació de l'UI
                        Platform.runLater(() -> {
                            progress2 += rand.nextInt(3) + 4;
                            percentatge2.setText(String.valueOf(progress2) + "%");
                            taskbar3.setProgress(progress2/100.0);
                            if (progress2 >= 100) {
                                progress2 = 100;
                                percentatge2.setText(String.valueOf(progress2) + "%");
                                taskbar3.setProgress(progress2/100.0);
                                button3.setText("Iniciar");
                            }
                        });
                        Thread.sleep(rand.nextInt(5001) + 3000);
                    }



                    System.out.println("Updating label: " + index + ", Value: " + currentValue);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    // Aquesta funció la cridaries quan vulguis tancar l'executor (per exemple, quan tanquis la teva aplicació)
    public void stopExecutor() {
        executor.shutdown();
    }

}