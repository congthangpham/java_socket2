package bai1;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String number;
            while ((number = in.readLine()) != null) {
                System.out.println("Received: " + number);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
