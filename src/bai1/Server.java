package bai1;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server đã được khởi động và đang chờ kết nối...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client đã kết nối.");

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                for (int i = 1; i <= 1000; i++) {
                    out.println(i);
                    Thread.sleep(1000); // Mỗi giây gửi một số
                }

                socket.close();
                System.out.println("Client đã ngắt kết nối.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
