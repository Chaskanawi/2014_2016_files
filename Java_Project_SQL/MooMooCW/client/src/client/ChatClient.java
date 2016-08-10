/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author N0499622
 */
/*
Name: ChatClient class
Description:
Define a Socket, networkIO stream, ScreenManage instance, username (logged in one)
Host and port number.

For information on netIO see NetowrkIO.java

Set up client to listen to the server for requests. Set up the NetworkIO,
create a thread to listen to the server. Then starts listening.

The close function, closes the connection by closing the NetworkIO and the 
Socket.
*/

public class ChatClient
{  
    private Socket Sock;
    public NetworkIO netIO;
    ScreenManager ScreenManage;
    public String username;
    String Host;
    int Port;
    
    public ChatClient(String host, int port, ScreenManager screenManage) throws Exception
    {
        Host = host;
        Port = port;
        Sock = new Socket(host, port);
        try
        {
            netIO = new NetworkIO(Sock);
        }
        catch (Exception ex)
        {
            System.out.println("NetIO failed to set up");
        }
        ScreenManage = screenManage;
        ServerListener listen = new ServerListener(this);
        Thread thListen = new Thread(listen);
        thListen.start();
        System.out.println("Done");
    }
    
    //this function closes the socket and the streams.
    public void close()
    {
        try
        {
            netIO.close();
            Sock.close();
        }
        catch (Exception ex)
        {
            
        }
    }
    
    /*
    When a user logs out, or boots up again the socket needs to be closed, the NetIO
    Will need to be reset. The connection re-opens.
    */
    public void Reset()
    {
        try
        {
            System.out.println("Closing and opening socket");
            Sock.close();
            Sock = new Socket(Host, Port);
            netIO = new NetworkIO(Sock);
            System.out.println("Done");
        }
        catch (Exception ex)
        {
        }
    }
}
