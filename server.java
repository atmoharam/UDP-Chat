import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {
    public static void main ( String[] args) throws IOException {
        try {
            //UDP socket from Datagram in port 4000
            DatagramSocket serversocket = new DatagramSocket(4000);
            System.out.println("Server is up");
            //array to receive the data from client
            byte[] requstBytes = new byte[ 4096 ];
            //array to response msgs
            byte[] responseBytes;
            //inf loop to send more msgs
            while (true) {
                System.out.println("Server is read to communicate");
                //create a client packet for receiving (receiver (array name , array length))
                DatagramPacket clientPacket = new DatagramPacket(requstBytes, requstBytes.length);
                //receive client request to client packet
                serversocket.receive(clientPacket);
                //convert data to string and trim to remove any spaces
                String req = new String(clientPacket.getData()).trim();
                System.out.println("client: " + req);

                //second part (send replay from server to client)
                // take input from console to server
                Scanner sc = new Scanner(System.in);
                //string taken from console
                String input = sc.nextLine();
                //transform mt string to byte
                responseBytes = input.getBytes();
                //extract IP address and port number of client
                InetAddress clientIP = clientPacket.getAddress();
                int clientport = clientPacket.getPort();
                //packet send to IP and its port (sender (array name , array length , receiver ip , port))
                DatagramPacket myserverPacket = new DatagramPacket(responseBytes, responseBytes.length, clientIP, clientport);
                //call socket to send my server packet
                serversocket.send(myserverPacket);
                //delete all data
                requstBytes = new byte[ 4096 ];
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();

        }

    }
}
