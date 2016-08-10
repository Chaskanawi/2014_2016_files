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
 * @author N0499622, N0516005
 */
public class NetworkIO
{
    private Socket Sock;
    private InputStream In;
    private OutputStream Out;
    private DataOutputStream dOut;
    private BufferedReader bIn;
    
    /*Name: NetworkIO (Constructor)
    Description: Creates a class for input and output
    on a network.
    This works on a request system with strings
    Notes: This class has been developed to work on both the client
    and server side*/
    public NetworkIO(Socket sock) throws Exception
    {
        Sock = sock;
        Out = Sock.getOutputStream();
        In = Sock.getInputStream();
        dOut = new DataOutputStream(Out);
        bIn = new BufferedReader(new InputStreamReader(In));
    }
    
    public void close() throws IOException
    {
        dOut.close();
        bIn.close();
        Out.close();
        In.close();
        Sock.close();
    }
    
    /*Name: readRequest
    Description: Reads a request name
    Reads a request size
    Reads all the details for the request
    Returns an array with the request and the request details*/
    public String[] readRequest() throws Exception
    {
        //Get the request
        String request = bIn.readLine();
        System.out.println("Request recived: " + request);
        //Get the request size
        int requestSize = Integer.parseInt(bIn.readLine());
        System.out.println("Request size: " + requestSize);
        //Get the details for the request
        String[] requestDetails = null;
        if (requestSize >= 0)
            requestDetails = new String[requestSize+1];
        requestDetails[0] = request;
        for (int i = 0; i < requestSize; i ++)
        {
            requestDetails[i+1] = bIn.readLine();
            System.out.println("Request detail: " + i+1 + ": " + requestDetails[i+1]);
        }
        return requestDetails;
    }
    
    /*Name: sendRequest
    Description: Sends a request string
    a request size string (1)
    a single detail string*/
    public void sendRequest(String request, String detail) throws IOException
    {
        System.out.println("Sending single request");
        try
        {
            dOut = new DataOutputStream(Out);
            dOut.writeBytes(request + "\n");
            int length = 1;
            dOut.writeBytes(Integer.toString(length) + "\n");
            dOut.writeBytes(detail + "\n");
        }
        catch (Exception ex)
        {
            System.out.println("Single send request caught");
            Logger.getLogger(NetworkIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Name: sendRequest
    Description: Sends a request string
    a request size string (the length of the array)
    an array of details strings*/
    public void sendRequest(String request, String[] details) throws IOException
    {
        System.out.println("Sending array request");
        try
        {
            dOut.writeBytes(request + "\n");
            int length = details.length;
            dOut.writeBytes(Integer.toString(length) + "\n");
            for (int i = 0; i < length; i ++)
            {
                dOut.writeBytes(details[i] + "\n");
            }
        }
        catch (Exception ex)
        {
            System.out.println("Array send request caught");
            Logger.getLogger(NetworkIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Name: recieveFile
    Description: Reads in an array of bytes
    Writes the bytes to a file stream to create a file
    Notes: This function isn't fully working*/
    public String recieveFile(String fileDest) throws IOException
    {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try
        {
            //Set up the buffer and output streams
            byte[] myByteArray = new byte[6022386];
            fos = new FileOutputStream(fileDest);
            bos = new BufferedOutputStream(fos);
            bytesRead = In.read(myByteArray, 0, myByteArray.length);
            int total = bytesRead;
            System.out.println("Total bytes: " + bytesRead + " of " + myByteArray.length);
            current = bytesRead;
            
            //Read the bytes to the output
            System.out.println("Reading bytes");
            do
            {
                bytesRead = In.read(myByteArray, current, (myByteArray.length - current));
                if (bytesRead >= 0)
                    current += bytesRead;
            }
            while (bytesRead > -1);
            
            //Check if anything was sent
            if (total > -1)
            {
                //Write the file
                System.out.println("Writing file");
                bos.write(myByteArray, 0, current);
                System.out.println("Flushing file");
                bos.flush();
                System.out.println("File " + fileDest + " downloaded (" + current + " bytes read)");
            }
            else
            {
                System.out.println("Failed to read file");
                fileDest = "";
            }
        }
        finally
        {
            //close the outputstreams
            if (fos != null)
                fos.close();
            if (bos != null)
                bos.close();
        }
        return fileDest;
    }
    
    /*Name:
    Description: Creates a few file and byte array
    Read a byte from the file
    Send the file to the client
    Notes: This function works with a basic file recieving client
    However because the recieve file isn't working then this
    isn't used in the main program*/
    public void sendFile(String toSend) throws IOException
    {
        try
        {
            //Create the file streams and the array of bytes
            File myFile = new File (toSend);
            byte[] byteArray = new byte[(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //Read in the bytes
            bis.read(byteArray, 0, byteArray.length);
            System.out.println("Sending " + toSend + "(" + byteArray.length + " bytes)");
            //Write the output and send it
            Out.write(byteArray, 0, byteArray.length);
            Out.flush();
            System.out.println("Done.");
        }
        catch (Exception ex)
        {
        }
    }
}
