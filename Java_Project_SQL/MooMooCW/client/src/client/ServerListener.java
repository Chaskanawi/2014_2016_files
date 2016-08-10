/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author N0499622, N0516005
 */

/*
Name: ServerListener
Description:
The server listener listens to the server for a request. When a request is recieved
a Switch statement evaluates the request with a case. For instance; if the server sends
the request "Login". The Login function will be called and the successor of the window
after login will be setup using the ScreenManager.

To see details on the ScreenManager, please see ScreenManager.Java for notes.


*/

public class ServerListener implements Runnable
{
    ChatClient Client;
    boolean active;
    public ServerListener(ChatClient client)
    {
        active = true;
        Client = client;
    }
    
    //Function Run listens for the requests and then reads them when it recieves them.
    //the request is determined by reading the first index in the array.
    //Switch statment is performed then the appropriate function is called.
    
    public void run()
    {
        try
        {
            while (active)
            {
                String[] request = Client.netIO.readRequest();
                String[] requestDetails = Arrays.copyOfRange(request, 1, request.length);
                
                switch (request[0])
                {
                    case "Login" :
                        Login(requestDetails);
                        break;
                    case "GetProfile":
                        GetProfile(requestDetails);
                        break;
                    case "UpProfile":
                        UpdateProfile(requestDetails);
                        break;
                    case "Register":
                        Register(requestDetails);
                        break;
                    case "GetFriends":
                        GetFriends(requestDetails);
                        break;
                    case "activeUsers":
                        GetActiveUsers(requestDetails);
                        break;
                    case "GetFriendsReq":
                        FriendsRequestList(requestDetails);
                        break;
                    case "FriendRequest":
                        SendFriendRequest(requestDetails);
                        break;
                    case "SendMessage":
                        //call send message function
                        break;
                    case "Chat":
                        chatSend(requestDetails);
                        break;
                    case "Logout":
                        Logout(requestDetails);
                        break;
                    case "Disconnect" :
                        Disconnect();
                        break;
                    default :
                        System.out.println("Invalid request");
                        break;
                }
            }
        }
        catch (Exception ex)
        {
        }
    }
    
    //if login is true, set the screen to the Wall.
    public void Login(String[] loginInfo)
    {
        if (Boolean.valueOf(loginInfo[0]))
        {
            Client.ScreenManage.setScreen("Wall");
        }
        //add in login counter for fails
    }
    /*
    if sending a friend request, switch to which request has been sent.
    then update the users wall with the notification.
    For example:
    case "Friends":
                Client.ScreenManage.wall.updateFeed("\n"
    + requestFriend[1] + " is now friends with " + requestFriend[2]);
    
    update the feed using screenManage and print out the message
    "your username " + " is now friends with " + " Someones Username";
    
    */
    public void SendFriendRequest(String[] requestFriend)
    {
        switch(requestFriend[0])
        {
            case "Friends":
                Client.ScreenManage.wall.updateFeed("\n" + requestFriend[1] + " is now friends with " + requestFriend[2]);
                break;
            case "Requested":
                Client.ScreenManage.wall.updateFeed("\nFriends request sent");
                break;
            case "AlreadyFriends":
                Client.ScreenManage.wall.updateFeed("\nYou are already friends.");
                break;
            case "AlreadyRequested":
                Client.ScreenManage.wall.updateFeed("\nYou have already sent a request.");
                break;
            default:
                Client.ScreenManage.wall.updateFeed("\nThis account has not been found.");
                break;
        }
    }
    
    // print the list of frined requests on the wall feed.
    public void FriendsRequestList(String[] userRequests)
    {
        if(Boolean.valueOf(userRequests[0]))
            Client.ScreenManage.wall.PrintList(Arrays.copyOfRange(userRequests, 1, userRequests.length));
    }
    
    //Populate a list of all the users information for the profile.
    //E.G. Username, Firstname, Interests, Education etc.
    public void GetProfile(String[] profileInfo)
    {
        if(Boolean.valueOf(profileInfo[0]))
            Client.ScreenManage.profile.Initialise(Arrays.copyOfRange(profileInfo, 1, profileInfo.length));
    }
    
    //if update profile is true (wants to update the profile) call the getProfile() Function.
    public void UpdateProfile(String[] profileInfo)
    {
        if(Boolean.valueOf(profileInfo[0]))
        {
            Client.ScreenManage.profile.getProfile();
        }
    }
    
    //If register is true, then the cliennt will log you in.
    public void Register(String[] registerInfo)
    {
        if(Boolean.valueOf(registerInfo[0]))
        {
            try
            {
                Client.netIO.sendRequest("Login", Arrays.copyOfRange(registerInfo, 1, registerInfo.length));
                //account already exists
                //failed text fields
            }
            catch (IOException ex)
            {
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //when logging out, display the Login menu, set the profile to null and reset the client.
    public void Logout(String[] LogoutInfo)
    {
        if(Boolean.valueOf(LogoutInfo[0]))
        {
            Client.ScreenManage.setScreen("Login");
            Client.ScreenManage.profile.Reset();
            Client.Reset();
        }
    }
    
    //Getting the message that has been sent then printing it on screen.
    public void chatSend(String[] messageDetails)
    {
        Client.ScreenManage.chatMenu.addMessage(messageDetails[1] + ": " + messageDetails[2] + "\n");
    }
    
    //Get friends is true, then update the friends array.
    public void GetFriends(String[] friendsList)
    {
        System.out.println("Getting friends");
        if(Boolean.valueOf(friendsList[0]))
            Client.ScreenManage.chatMenu.updateFriends(Arrays.copyOfRange(friendsList, 1, friendsList.length));
    }
    
    //Get active users, call the updateActive function which updates the onlineFriendsList with all users online.
    public void GetActiveUsers(String[] onlineFriendsList)
    {
        System.out.println("Getting active users");
        Client.ScreenManage.chatMenu.updateActive(onlineFriendsList);
    }
    
    public void Disconnect()
    {
        active = false;
    }
}
