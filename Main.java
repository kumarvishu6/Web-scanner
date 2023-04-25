import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.*;
import java.io.FileReader;
public class Main {
    public static boolean isConnected() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80), 3000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
   
    public static void PORT(String input){
        try {
            URL url = new URL(input);
            InetAddress address = InetAddress.getByName(url.getHost());
            String fileName = "ports.csv";
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                br.readLine();
                System.out.println("PORT | STATE | SERVICE");
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    String Sn = values[0];
                    String Pn = values[1];

                    String host = address.getHostAddress();
                    int port = Integer.valueOf(Pn);

                    try (Socket socket = new Socket()) {
                        socket.connect(new InetSocketAddress(host, port), 1000);
                        System.out.println(Pn+" | "+"OPEN"+" | "+Sn);
                    } catch (IOException e) {
                        System.out.println(Pn+" | "+"CLOSED"+" | "+Sn);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Could not resolve host: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        if (isConnected()==true){
            System.out.println("////////////////////////////////////////");
            System.out.println("/                                      /");
            System.out.println("/        Web Vulnerability Scanner     /");
            System.out.println("/                                      /");
            System.out.println("////////////////////////////////////////");
            System.out.println("/                                      /");
            System.out.println("/        (1) Port Scan                 /");
            System.out.println("/        (0) Exit                      /");
            System.out.println("/                                      /");
            System.out.println("////////////////////////////////////////");


            Scanner scan = new Scanner(System.in);
            System.out.print("Enter Your Choice : ");
            int choice = scan.nextInt();

            switch (choice) {
                
                case 1:
                    System.out.println("[ Port Scan ]");
                    Scanner s3= new Scanner(System.in);
                    System.out.print("Enter URL : ");
                    String url3= s3.nextLine();
                    PORT(url3);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Choice\nExiting...");
                    break;
            }
        } else{
            System.out.println("No Internet Connection.");
        }
    }
}
