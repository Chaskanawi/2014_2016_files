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
public class ChatServerExt implements Runnable
{
    private Socket Sock;
    public NetworkIO netIO;
    private ChatServer chatServer;
    public String ID;
    private boolean connected;
    
    /*Name: ChatServerExt (Constructor)
    Description: Sets the socket, chat server and netIO classes for the server extension.*/
    public ChatServerExt(ChatServer server, Socket socket)
    {
        chatServer = server;
        Sock = socket;
        ID = "";
        try
        {
            netIO = new NetworkIO(Sock);
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: run()
    Description: Gets a request from the user.
    Gets the details related to the request.
    Then calls the function that relates to the user request
    and passes in the details for the request*/
    public void run()
    {
        connected = true;
        try
        {
            System.out.println("Socket Established from: " + Sock.getInetAddress());
            while (connected)
            {
                //Get the request and details
                String[] request = netIO.readRequest();
                System.out.println("Request Read. Copying details ");
                String[] requestDetails = Arrays.copyOfRange(request, 1, request.length);
                
                //Call the function that corresponds to the request
                System.out.println("Request Read. Switching " + request[0]);
                switch (request[0])
                {
                    case "Logout" :
                        logout();
                        break;
                    case "Login" :
                        System.out.println("Login");
                        login(requestDetails);
                        break;
                    case "Register" :
                        System.out.println("Register");
                        register(requestDetails);
                        break;
                    case "GetProfile" :
                        System.out.println("GetProfile");
                        getProfile(requestDetails);
                        break;
                    case "UpProfile" :
                        System.out.println("UpdateProfile");
                        updateProfile(requestDetails);
                        break;
                        
                    case "FriendRequest" :
                        System.out.println("RequestFriend");
                        requestFriend(requestDetails);
                        break;
                    case "AcceptFriend" :
                        System.out.println("AcceptFriend");
                        acceptFriend(requestDetails);
                        break;
                    case "GetFriendsReq" :
                        System.out.println("GetFriendRequest");
                        getFriendRequest(requestDetails);
                        break;
                    case "GetFriends" :
                        System.out.println("GetFriendList");
                        getFriendList(requestDetails);
                        break;
                        
                    case "activeUsers" :
                        System.out.println("activeUsers");
                        findActiveUsers();
                        break;
                    case "Chat" :
                        System.out.println("Chat");
                        chatSend(requestDetails);
                        break;
                                                                       
                    case "FileRec" :
                        System.out.println("FileTrans");
                        netIO.recieveFile("C:/Users/N0499622/Documents/Received.jpg");
                        break;
                    case "FileGet" :
                        System.out.println("FileTrans");
                        netIO.sendFile("C:/Users/N0499622/Pictures/TOSEND.jpg");
                        break;
                        
                    default :
                        System.out.println("Invalid request");
                        break;
                }
            }
            Sock.close();
        }
        catch (Exception ex)
        {
            chatServer.deactivateClient(ID);
            chatServer.removeClient(ID);
            System.out.println("Thread error!");
        }
    }
    
    /*Name: logout
    Description: Removes the client from the active user list
    the client list and them sends a logout confirmation
    finally it sets the connection to be false in order to disconnect the socket*/
    public void logout()
    {
        /*Remove the user from the active user list
        the client list and then send a logout confirmation*/
        chatServer.deactivateClient(ID);
        chatServer.removeClient(ID);
        try
        {
            netIO.sendRequest("Logout", "true");
        }
        catch (Exception ex)
        {
            
        }
        connected = false;
    }
    
    /*Name: login
    Description: Looks for the username in the user login table
    Then checks the password against the password for that user
    It sends a login confirmation to the user.
    Notes: loginDetails[0] - Username being checked
    loginDetails[1] - Password being checked*/
    public void login(String[] loginDetails)
    {
        String[] result = new String[2];
        result[0] = "false";
        try
        {
            System.out.println("Creating statement");
            Statement stmt = chatServer.DBconnection.createStatement();
            System.out.println("Starting query");
            ResultSet rs = stmt.executeQuery("SELECT Password FROM User_Logins WHERE Username = '" + loginDetails[0] + "'");
            System.out.println("Query completed");
            //Check if the user exists
            if (rs.next())
            {
                System.out.println("User found!");
                //Check the passwords match
                if (rs.getString("Password").equals(loginDetails[1]))
                {
                    System.out.println("Passwords Match!");
                    result[0] = "true";
                    result[1] = loginDetails[0];
                    chatServer.changeID(this, loginDetails[0]);
                    chatServer.activateClient(ID);
                }
                else
                {
                    System.out.println("Password wrong.");
                }
            }
            else
            {
                System.out.println("User not found.");
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ChatServerExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            System.out.println("Login: " + result[0]);
            netIO.sendRequest("Login", result);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ChatServerExt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Name: register
    Description: Looks if a user is registered in the user login table
    If they don't exist then it creates a new entry in the table & the user profile table
    A registration confirmation is send to the user.
    Notes: userInfo[0] - Username given
    userInfo[1] - Password given
    userInfo[2] - First name given
    userInfo[3] - Last name given
    userInfo[4] - Date of Birth given
    userInfo[5] - Gender given*/
    public void register(String[] userInfo)
    {
        String[] result = new String[3];
        result[0] = "false";
        try
        {
            System.out.println("Opening DB");
            Statement stmt = chatServer.DBconnection.createStatement();
            System.out.println("Executing query");
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM User_Logins WHERE Username='" + userInfo[0] + "'");
            System.out.println("Checking results");
            //Check if the user exists
            if (!rs.next())
            {
                System.out.println("User doesn't exist");
                stmt.executeUpdate(
                    "INSERT INTO User_Logins (Username, Password)\n" +
                    "VALUES ('" + userInfo[0] + "', '" + userInfo[1] + "')");
                stmt.executeUpdate(
                    "INSERT INTO User_Profiles (ProfileUsername, First_Name, Last_Name, Date_Of_Birth, Gender)\n" +
                    "VALUES ('" + userInfo[0] + "', '" + userInfo[2] + "', '" + userInfo[3] + "', '" + userInfo[4] + "', '" + userInfo[5] + "')");
                result[0] = "true";
                result[1] = userInfo[0];
                result[2] = userInfo[1];
                System.out.println("Register Successful.");
            }
            else
            {
                System.out.println("Register Failed.");
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ChatServerExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            System.out.println("Register: " + result[0]);
            netIO.sendRequest("Register", result);
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: getProfile
    Description: Checks the user exists
    then sends the the user info back to the user
    Notes: userInfo[0] - Username of profile it returns*/
    public void getProfile(String[] userInfo) //Send image needs to be added
    {
        String[] profileInfo = new String[1];
        profileInfo[0] = "false";
        String user = userInfo[0];
        System.out.println(user);
        //If the username provided is empty then make it this current user
        if (user.equals(""))
            user = ID;
        System.out.println(user);
        System.out.println(ID);
        try
        {
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM User_Profiles WHERE ProfileUsername='" + user + "'");
            //Check if the user exists
            if (rs.next())
            {
                System.out.println("User found!");
                profileInfo = new String[10];
                profileInfo[0] = "true";
                profileInfo[1] = rs.getString("ProfileUsername");
                profileInfo[2] = rs.getString("First_Name");
                profileInfo[3] = rs.getString("Last_Name");
                profileInfo[4] = rs.getString("Date_Of_Birth");
                profileInfo[5] = rs.getString("Gender");
                profileInfo[6] = rs.getString("Interests");
                profileInfo[7] = rs.getString("Job");
                profileInfo[8] = rs.getString("School");
                profileInfo[9] = rs.getString("City");
                for (String profileInfo1 : profileInfo)
                    System.out.println(profileInfo1);
            }
            else
            {
                System.out.println("User not found.");
            }
        }
        catch (Exception Ex)
        {
        }
            
        try
        {
            System.out.println("Sending profile");
            netIO.sendRequest("GetProfile", profileInfo);
            System.out.println("Sent profile");
        }
        catch (Exception ex)
        {
            System.out.println("Try failed");
        }
    }
     
    /*Name: updateProfile
    Description: Checks the user profile table for the user
    then updates the profile table with the new information provided
    then sends an update profile confirmation to the user.
    Notes: userInfo[0] - Username
    userInfo[1] - First name
    userInfo[2] - Last name
    userInfo[3] - Date of Birth
    userInfo[4] - Gender
    userInfo[5] - Interests
    userInfo[6] - Work/Job
    userInfo[7] - Education/School
    userInfo[8] - City*/
    public void updateProfile(String[] userInfo)
    {
        String result = "false";
        try
        {
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ProfileUsername FROM User_Profiles WHERE ProfileUsername='" + userInfo[0] + "'");
            //Check the user exists
            if (rs.next())
            {
                System.out.println("User found!");
                result = "true";
                System.out.println("preparing statement");
                stmt.executeUpdate("UPDATE User_Profiles "
                        + "SET First_Name='" + userInfo[1] + "', "
                        + "Last_Name='" + userInfo[2] + "', "
                        + "Date_Of_Birth='" + userInfo[3] + "', "
                        + "Gender='" + userInfo[4] + "', "
                        + "Interests='" + userInfo[5] + "', "
                        + "Job='" + userInfo[6] + "', "
                        + "School='" + userInfo[7] + "', "
                        + "City='" + userInfo[8] + "' "
                        + "WHERE ProfileUsername='" + userInfo[0] + "'");
                System.out.println("User Updated");
            }
            else
            {
                System.out.println("User not found.");
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Profile update failed");
            Logger.getLogger(ChatServerExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            netIO.sendRequest("UpProfile", result);
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: requestFriend
    Description: Checks if there is a relationship between the users
    If there is then it checks if it is a friend request or a friendship
        If it is a friend request then it accepts the request
    If there isn't then it checks if you already send a friend request
        If you haven't then it adds the send request
    Then it sends a request friend confirmation to the user
    Notes: userInfo[0] - Username of sender
    userInfo[1] - Username of reciever*/
    public void requestFriend(String[] userInfo)
    {
        String[] result = new String[3];
        result[0] = "false";
        result[1] = userInfo[0];
        result[2] = userInfo[1];
        try
        {
            System.out.println("Searching for user");
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User_Logins "
                    + "WHERE Username='" + userInfo[0] + "'");
            //Check I exist
            if (rs.next())
            {
                System.out.println("Searching for relationship");
                rs = stmt.executeQuery("SELECT Status FROM User_Friends "
                        + "WHERE FriendUsername='" + userInfo[0] + "' "
                        + "And Friend='" + userInfo[1] + "'");
                //Check if I have a relationship with you
                if (!rs.next())
                {
                    rs = stmt.executeQuery("SELECT Status FROM User_Friends "
                            + "WHERE FriendUsername='" + userInfo[1] + "' "
                            + "AND Friend='" + userInfo[0]+ "'");
                    //Check if I already sent a request
                    if (!rs.next())
                    {
                        //Add request
                        System.out.println("Sending request");
                        stmt.executeUpdate("INSERT INTO User_Friends "
                                + "(FriendUsername, Friend, Status) "
                                + "VALUES ('" + userInfo[1] + "', '" + userInfo[0] + "', 0)");
                        result[0] = "Requested";
                    }
                    else
                        result[0] = "AlreadyRequested";
                }
                else //No relationship
                {
                    //Check if you already sent me a request
                    if (rs.getInt("Status") == 0)
                    {
                        //Accept the request for both users
                        System.out.println("Accepting request");
                        stmt.executeUpdate("UPDATE User_Friends "
                                + "SET Status=1 "
                                + "WHERE FriendUsername='" + userInfo[0] + "' "
                                + "And Friend='" + userInfo[1] + "'");
                        stmt.executeUpdate("INSERT INTO User_Friends "
                                + "(FriendUsername, Friend, Status) "
                                + "VALUES ('" + userInfo[1] + "', '" + userInfo[0] + "', 1)");
                        result[0] = "Friends";
                    }
                    else
                        result[0] = "AlreadyFriends";
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("SQL Error");
        }
        
        try
        {
            netIO.sendRequest("FriendRequest", result);
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: acceptFriend
    Description: Looks for a friend request from a user
    then accepts it
    Notes: userInfo[0] - Username of sender
    userInfo[1] - Username of reciever*/
    public void acceptFriend(String[] userInfo)
    {
        try
        {
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Friend FROM User_Friends "
                    + "WHERE FriendUsername='" + userInfo[0] + "' "
                    + "And Friend='" + userInfo[1] + "' "
                    + "And Status=0");
            //If there is a friend request
            if (rs.next())
            {
                //Accept the request for both users
                stmt.executeUpdate("UPDATE User_Friends "
                        + "SET Status=1 "
                        + "WHERE FriendUsername='" + userInfo[0] + "' "
                        + "And Friend='" + userInfo[1] + "'");
                stmt.executeUpdate("INSERT INTO User_Friends "
                        + "(ProfileUsername, Friend, Status) "
                        + "VALUES ('" + userInfo[1] + "', '" + userInfo[0] + "', 1)");
            }
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: getFriendRequest
    Description: Looks for all friend requests
    then sends an array of friend requests to the user
    Notes: userInfo[0] - Username of whom you want to find requests*/
    public void getFriendRequest(String[] userInfo)
    {
        ArrayList<String> friends = new ArrayList();
        try
        {
            System.out.println("Checking friend requests");
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Friend FROM User_Friends "
                    + "WHERE FriendUsername='" + userInfo[0] + "' "
                    + "And Status=0");
            //Get all friend requests
            while (rs.next())
            {
                String request = rs.getString("Friend");
                System.out.println("Sending: " + request);
                friends.add(request);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Friend request list caught");
        }
        
        try
        {
            //Send the friend requests to the user
            if (friends.size() > 0)
            {
                System.out.println("Sending true");
                friends.add(0, "true");
            }
            else
            {
                System.out.println("Sending false");
                friends.add("false");
            }
            netIO.sendRequest("GetFriendsReq", friends.toArray(new String[friends.size()]));
        }
        catch (Exception ex)
        {
            System.out.println("Friend request list reply caught");
        }
    }
    
    /*Name: getFriendList
    Description: Looks for all friends
    then sends an array of friends to the user
    Notes: userInfo[0] - Username of whom you want to find friends*/
    public void getFriendList(String[] userInfo)
    {
        ArrayList<String> friends = new ArrayList();
        try
        {
            System.out.println("Checking friends");
            Statement stmt = chatServer.DBconnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Friend FROM User_Friends "
                    + "WHERE FriendUsername='" + userInfo[0] + "' "
                    + "And Status=1");
            //Get all friends
            while (rs.next())
            {
                String request = rs.getString("Friend");
                System.out.println("Found: " + request);
                friends.add(request);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Friends list caught");
        }
        
        try
        {
            //Send friends to the user
            if (friends.size() > 0)
            {
                System.out.println("Sending true");
                friends.add(0, "true");
            }
            else
            {
                System.out.println("Sending false");
                friends.add("false");
            }
            netIO.sendRequest("GetFriends", friends.toArray(new String[friends.size()]));
        }
        catch (Exception ex)
        {
            System.out.println("Friends list reply caught");
        }
    }
    
    /*Name: findActiveUsers
    Description: Sends a list of all the active users to the user*/
    public void findActiveUsers()
    {
        try
        {
            netIO.sendRequest("activeUsers", chatServer.activeUsers.toArray(new String[chatServer.activeUsers.size()]));
        }
        catch (Exception ex)
        {
        }
    }
    
    /*Name: chatSend
    Description: Relays a message from one user to another.
    Notes: messageDetails[0] - the reciever
    messageDetails[1] - the sender
    messageDetails[2] - the message*/
    public void chatSend(String[] messageDetails)
    {
        /*MessageDetails[0] is who the message is being send to
        MessageDetails[1] is the user that sent the message
        MessageDetails[2] is the message being send*/
        System.out.println(messageDetails[1] + " says " + messageDetails[2] + " to " + messageDetails[0]);
        chatServer.sendMessageToUser(messageDetails[0], messageDetails);
    }
}
