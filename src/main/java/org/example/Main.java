package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Encryption encryption = new Encryption();
        String clearText;
        String key;
        System.out.println("Należy podać:");
        System.out.println("Tekst jawny:");
        clearText = System.in.toString();
        key = System.in.toString();
        Converter converter = new Converter(clearText, key); //nalezy wpisać "walidność" klucza
        List<BitSet> inputText = converter.convertMessage(); //wprowadzamy listę bitsetów, ponieważ mamy, że dla tekstu/klucza może być kilka bitsetów (jeśli > 64b)
        List<BitSet> inputKey = converter.convertKey();
        List<BitSet> finalTextBinary = new ArrayList<>(); //finalny tekst PO szyfrowaniu (powinno wyglądać na to)
        for(int i = 0; i < inputText.size(); i++) {
            finalTextBinary.add(encryption.encryption(inputText.get(i), inputKey.get(i)));
        }

    }
}
