import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server đã được khởi động và đang chờ kết nối...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client đã kết nối.");

                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Nhập tên của bạn:");
                username = in.readLine();
                System.out.println("Client " + username + " đã tham gia.");

                synchronized (clients) {
                    for (PrintWriter client : clients) {
                        client.println(username + " đã tham gia cuộc trò chuyện.");
                    }
                    clients.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    synchronized (clients) {
                        for (PrintWriter client : clients) {
                            client.println(username + ": " + message);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null) {
                    System.out.println(username + " đã rời khỏi cuộc trò chuyện.");
                    synchronized (clients) {
                        clients.remove(out);
                        for (PrintWriter client : clients) {
                            client.println(username + " đã rời khỏi cuộc trò chuyện.");
                        }
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
