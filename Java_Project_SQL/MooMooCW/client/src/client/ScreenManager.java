/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author n0516005
 */

public class ScreenManager
{
    // tree map of screens that are active.
    public TreeMap<String, Menu> screens;
    /**
     * @param args the command line arguments
     */
    
    // Main creats a new instance of screen manager and loads the login screen
    public static void main(String[] args)
    {
        ScreenManager screenie = new ScreenManager();
        // set screen is a function defined below that sets the currentMenu to login
        screenie.setScreen("Login");
        
    }
    
    
    Profile profile;
    ChatClient clientServer;
    Menu[] menuList;
    Menu currentMenu;
    public Wall wall;
    public Chat chatMenu;
    
    
    /*
    public ScreenManager()
    Description:
    
    ScreenManager sets up all the screens and adds them to a map so they can be used whenever the user
    wishes to navigate to them. The screenManager is responsible for connecting to the server and setting
    the screens of the clients GUI.
    
    clientServer creates a new client (ChatClient) and conntects to the server IP.
    set up the profile of the user, the screen (this = Login), a new TreeMap for screens,
    set the current menu (currentMenu) to login, and the chat Menu to a new chatMenu (Listen for messages)
    set up wall as a new wall:
    
    profile = new Profile(this);
    screens = new TreeMap();
    currentMenu = new Login(this);
    chatMenu = new Chat(this);
    wall = new Wall(this);
    
    put ProfileEdit, Login, Register and Wall screens into the map.
    
    screens.put("ProfileEdit", new ProfileEdit(this));
    screens.put("Login", currentMenu);
    screens.put("Register", new Register(this));
    screens.put("Wall", wall);
    */
    public ScreenManager()
    {
        try
        {
            clientServer = new ChatClient("MAE109-22.ads.ntu.ac.uk", 6789, this);
        }
        catch(Exception ex)
        {
            System.out.println("Client failed");
        }
        
        profile = new Profile(this);
        screens = new TreeMap();
        currentMenu = new Login(this);
        chatMenu = new Chat(this);
        wall = new Wall(this);
        
        screens.put("ProfileEdit", new ProfileEdit(this));
        screens.put("Login", currentMenu);
        screens.put("Register", new Register(this));
        screens.put("Wall", wall);
    }
    
    /*
    public void setScreen(String screenName)
    Description:
    
    
    currentMenu.setVisible(false);
    currentMenu = screens.get(screenName);
    currentMenu.setVisible(true);
    currentMenu.Initialise();
    */
    public void setScreen(String screenName)
    {
            currentMenu.setVisible(false);
            currentMenu = screens.get(screenName);
            currentMenu.setVisible(true);
            currentMenu.Initialise();
    }
    
    public void openChat()
    {
        chatMenu.setVisible(true);
        chatMenu.Initialise();
    }
    
    
    

    
}
