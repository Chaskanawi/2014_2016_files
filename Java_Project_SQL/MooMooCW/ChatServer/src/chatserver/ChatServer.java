/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author N0499622
 */
public class ChatServer implements Runnable
{
    private ServerSocket server;
    private TreeMap<String, ChatServerExt> users;
    private int clientNum;
    public ArrayList<String> activeUsers;
    public Connection DBconnection;
    public boolean active;
    /**
     * @param args the command line arguments
     */
    
    /*Name: ChatServer (Constructor)
    Description: Opens a new server on the specified port number*/
    public ChatServer(int port)
    {
        try
        {
            clientNum = 0;
            server = new ServerSocket(port);
            users = new TreeMap();
            activeUsers = new ArrayList();
            connectDatabase("jdbc:derby://localhost:1527/Users", "username", "password");
        }
        catch (IOException | SQLException ex)
        {
        }
    }
    
    /*Name: Close
    Description: Closes the server*/
    public void Close()
    {
        try
        {
            active = false;
            server.close();
            System.out.println("Closing Server");
        }
        catch (IOException ex)
        {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Name: Listen
    Description: This function waits for a connection
    then adds a client when a connection comes through*/
    public void run()
    {
        active = true;
        try
        {
            while(active)
            {
                System.out.println("Waiting for client." + clientNum);
                addClient(server.accept());
            }
        }
        catch (Exception ex)
        {
            
        }
    }
    
    /*Name: sendMessageToUser
    Description: Relays a message from one user to another
    Notes: ID - the reciever
    message contains the message sender, reciever and actual message*/
    public void sendMessageToUser(String ID, String[] message)
    {
        try
        {
            //Check if the user is online
            if (activeUsers.contains(ID))
                users.get(ID).netIO.sendRequest("Chat", message);
            else
                System.out.println("User not active");
        }
        catch (IOException ex)
        {
            System.out.println("Chat error!");
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Name: changeID
    Description: Changes the ID on an online user
    It deals with making sure the user ID is in the online list and client list*/
    public void changeID(ChatServerExt client, String newID)
    {
        System.out.println("Client: " + client.ID + " changing to " + newID);
        users.remove(client.ID);
        System.out.println("Client removed ");
        users.put(newID, client);
        System.out.println("Client added ");
        users.get(newID).ID = newID;
        System.out.println("Client set ");
    }
    
    /*Name: activateClient
    Description: Adds the user ID to the active user list*/
    public void activateClient(String ID)
    {
        System.out.println("Activating client: " + ID);
        activeUsers.add(ID);
        System.out.println("Activated");
    }
    
    /*Name: deactivateClient
    Description: Removes the user ID from the active user list*/
    public void deactivateClient(String ID)
    {
        System.out.println("Deactivating client: " + ID);
        activeUsers.remove(ID);
        System.out.println("Deactivated");
    }
    
    /*Name: removeClient
    Description: Removes the client from the user list*/
    public void removeClient(String ID)
    {
        users.remove(ID);
    }
    
    /*Name: addClient
    Description: Creates and starts a new thread for a client
    and adds the client to the list of users*/
    private void addClient(Socket connection)
    {
        System.out.println("Client Connected.");
        System.out.println("Establishing thread.");
        ChatServerExt client = new ChatServerExt(this, connection);
        Thread thClient = new Thread(client);
        String clientNumStr = Integer.toString(clientNum);
        users.put(clientNumStr, client);
        users.get(Integer.toString(clientNum)).ID = clientNumStr;
        clientNum ++;
        thClient.start();
    }
    
    /*Name: connectDatabase
    Description: Opens a connection with the dataBase
    with username and password*/
    private void connectDatabase(String dataBase, String username, String password) throws SQLException
    {
        /*This function connects to the database*/
        DBconnection = DriverManager.getConnection(
            dataBase,
            username,
            password);
    }
}
