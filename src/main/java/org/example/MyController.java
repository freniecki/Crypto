package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class MyController {
    Logger logger = Logger.getLogger(MyController.class.getName());

    private Stage primaryStage;

    @FXML
    private TextArea plainText;

    @FXML
    private TextArea signedText;

    @FXML
    private TextField privateKey;

    @FXML
    private TextField result;

    private Signature signature;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    protected void handleReadPlainFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                plainText.setText(content);
            } catch (IOException e) {
                logger.severe("cant read file");
            }
        }
    }

    @FXML
    protected void handleReadSignedFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                signedText.clear();
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                signedText.setText(content);
            } catch (IOException e) {
                logger.severe("cant read file");
            }
        }
    }


    @FXML
    protected void handleDeserialize(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                signature = (Signature) FileIO.getFile(file.getName()).readObject();
                logger.info("file read");
            } catch (IOException e) {
                logger.severe("deserialize error");
            }
        }
    }

    @FXML
    protected void handleSignButtonAction(ActionEvent event) throws IOException {
        Schnorr schnorr;
        String privateKeyText = privateKey.getText();
        BigInteger pK = new BigInteger(privateKeyText);

        String message = plainText.getText();
        byte[] messageBytes = message.getBytes();

        schnorr = new Schnorr(pK, messageBytes);
        BigInteger[] signature = schnorr.sign();

        signedText.appendText("\n\nSignature:\n");
        signedText.appendText("S1: " + signature[0] + "\n");
        signedText.appendText("S2: " + signature[1] + "\n");

        Signature result = new Signature(signature[0], signature[1], schnorr.getH(), schnorr.getV(), schnorr.getP());

        FileIO.getFile("signature").writeObject(result);

        FileIO.getFile("text").writeBytesAsASCII(plainText.getText().getBytes());
    }

    @FXML
    protected void handleVerifyButtonAction(ActionEvent event) {
        logger.info("Begin verifying");
        BigInteger[] signatureBI = new BigInteger[2];
        signatureBI[0] = signature.getS1();
        signatureBI[1] = signature.getS2();
        Schnorr schnorr1 = new Schnorr(FileIO.getFile("text").readBytesFromFile());
        boolean isVerified = schnorr1.verifySignature(signatureBI, signature.getH(), signature.getV(), signature.getP());


        // Wyświetlenie wyniku weryfikacji
        result.clear();
        if (isVerified) {
            result.setStyle("-fx-text-fill: green;"); // Ustawienie koloru tekstu na zielony
        } else {
            result.setStyle("-fx-text-fill: red;"); // Ustawienie koloru tekstu na czerwony
        }
        result.appendText("" + isVerified);

    }

}
