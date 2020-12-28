import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static void main ( String[] args) throws SocketException {
        try {
            //make a client socket no parameters it will choose a random port
            DatagramSocket Clientsocket = new DatagramSocket();
            System.out.println("Client active");
            //server IP we will get it by name ( in same device so we used localhost)
            InetAddress serverIP = InetAddress.getByName("localhost");
            //we choose the port of server =4000
            int port = 4000;
            //array to receive data from server
            byte [] response = new byte[4096];
            //array to send data to server
            byte [] request;
            Scanner sc = new Scanner(System.in);
            //loop to send more one msg
            while (true) {
                System.out.println("You are read to communicate");
                //string that will send to server
                String input = sc.nextLine();
                //convert string into request array
                request = input.getBytes();
                //check the input string if it = "close" it will close the program
                if(input.toLowerCase().equals("close"))
                {
                    System.out.println("connection is closed");
                    Clientsocket.close();
                    break;
                }
                //packet that send to server with ip and port = 4000
                DatagramPacket myclientpacket = new DatagramPacket(request, request.length, serverIP, port);
                Clientsocket.send(myclientpacket);
                //packet received from server ( server replay )
                DatagramPacket Serverpacket = new DatagramPacket(response, response.length);
                Clientsocket.receive(Serverpacket);
                //convert the data to string
                String Server_replay = new String(Serverpacket.getData());
                System.out.println("Server: " + Server_replay);
                //delete data that  received from server
                response = new byte[ 4096 ];

            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
