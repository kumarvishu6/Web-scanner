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
    public static void XSS(String input){
        String payload ="<script>alert('XSS')</script>";
        String FullURL=input+payload;
        try {
            String url = FullURL;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                if (response.toString().contains("<script>") && response.toString().contains("alert(")) {
                    System.out.println("URL IS VULNERABLE TO XSS");
                } else {
                    System.out.println("URL IS SAFE");
                }
            } else {
                System.out.println("Request Failed | Response Code " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    public static void SQL(String input){
        String payload ="'";
        String FullURL=input+payload;
        String url = FullURL;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                if (response.toString().contains("SQL syntax") || response.toString().contains("Warning") || response.toString().contains("Unknown table")) {
                    System.out.println("URL IS VULNERABLE TO SQL INJECTION");
                } else {
                    System.out.println("URL IS SAFE");
                }
            } else {
                System.out.println("Request Failed | Response Code " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
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
            System.out.println("/        (1) XSS Scan                  /");
            System.out.println("/        (2) SQL Injection Scan        /");
            System.out.println("/        (3) Port Scan                 /");
            System.out.println("/        (0) Exit                      /");
            System.out.println("/                                      /");
            System.out.println("////////////////////////////////////////");


            Scanner scan = new Scanner(System.in);
            System.out.print("Enter Your Choice : ");
            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("");
                    System.out.println("[ XSS Scan ]");
                    System.out.println("( Example : https://example.com/index.php?id= )");
                    Scanner s1= new Scanner(System.in);
                    System.out.print("Enter URL : ");
                    String url1= s1.nextLine();
                    XSS(url1);
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("[ SQL Injection Scan ]");
                    System.out.println("( Example : https://example.com/index.php?id=55 )");
                    Scanner s2= new Scanner(System.in);
                    System.out.print("Enter URL : ");
                    String url2= s2.nextLine();
                    SQL(url2);
                    break;
                case 3:
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
