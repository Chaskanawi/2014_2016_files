/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author n0516005
 */

/*
Name: Profile
Description:
The profile class is responsible for handling all of the users credentials and
details. The server will send the profile all of the usernames associated data.
The data is read into variables that can be used to populate the users wall.


*/


public class Profile
{
    private String Firstname;
    private String Surname;
    private String Username;
    private String DOB;
    private String Gender;
    private String Interests;
    private String Job;
    private String School;
    private String City;
    
    ScreenManager screenManager;
    
    public Profile(ScreenManager screen)
    {
        screenManager = screen;
    }
    
    public void Initialise(String[] profile)
    {
        Username =  profile[0];
        Firstname = profile[1];
        Surname = profile[2];
        DOB = profile[3];
        Gender = profile[4];
        Interests = profile[5];
        Job = profile[6];
        School = profile[7];
        City = profile[8];
        //friends list button = profile friend array.
    }
    /*
    Name: getProfile
    Description:
    Sends a request to get the profiles credentials.
    */
    
    
    public void getProfile()
    {
        try
        {
            screenManager.clientServer.netIO.sendRequest("GetProfile", "");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Name: currentUser
    Description:
    This function is called whenever the curren user logged in needs to use its usernames for requests
    */
    public String currentUser()
    {
        return Username;
    }
    
    /*
    Name: getFullProfile
    Description:
    When the client needs the full profile to be displayed, the array is returned
    to where it is called.
    */
    public String[] getFullProfile()
    {
        String[] profileData = new String[8];
        profileData[0] = Firstname;
        profileData[1] = Surname;
        profileData[2] = DOB;
        profileData[3] = Gender;
        profileData[4] = Interests;
        profileData[5] = Job;
        profileData[6] = School;
        profileData[7] = City;
        
        return profileData;
    }
    
    
    public String getSurname()
    {
        return Surname;
    }
    
    public String getDOB()
    {
        return DOB;
    }
    
    public String getGender()
    {
        return Gender;
    }
    
    //Reset all of the variables to an empty string. Called on Logout.
    public void Reset()
    {
        Username = "";
        Firstname = "";
        Surname = "";
        DOB = "";
        Gender = "";
    }
    
}
