package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.maze.BotMovement.BOT_ACTIONS;

public class BotCalls {
	private String IP_ADDRESS;
	private int PORT;
	private static Socket socket;
	private static DataOutputStream dout;
        private static int messagesSent=0;
        private static DataInputStream dinp;
	
	public BotCalls(String ip, int port) {
		this.IP_ADDRESS = ip;
		this.PORT = port;
		messagesSent=0;
		try {
            socket = new Socket(this.IP_ADDRESS, this.PORT);  
            dout=new DataOutputStream(socket.getOutputStream());  	
            dinp = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SendMovement(BOT_ACTIONS actions) {
		switch(actions) {
                case forward:
	            SendMessage(" M:forward");
	            break;
	        case backward:
	            SendMessage(" M:backward");
	            break;
	        case left:
	            SendMessage(" M:left");
	            break;
	        case right:
	            SendMessage(" M:right");
	            break;
	        case stop:
	            SendMessage(" M:stop");
	            break;
	        default:
	            //invalid message. Do nothing.
	            System.out.println("Invalid action. Continue...");
	            break;
		}
	}
        
        public boolean checkQueue(){
            try {
                dout.flush();
                dout.writeBytes(" Q:count");
                byte[] bytes = new byte[255];
                socket.setSoTimeout(3000);
                dinp.read(bytes);
                String input = new String(bytes).trim();
                System.out.println(input);
                int queue = Integer.valueOf(input.split(":")[1]);
                if(queue == 0){
                    return true;
                }
                else{
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(BotCalls.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
	
	private void SendMessage(String message) {
            try {
                dout.flush();
                dout.writeBytes(message);
                System.out.println("Message sent: " + message);
                byte[] bytes = new byte[255];
                socket.setSoTimeout(3000);
                dinp.read(bytes);
                String input = new String(bytes).trim();
                System.out.println(input);
                messagesSent++;
            } catch (SocketTimeoutException e) {
                try {
                    dout.flush();
                    dout.writeBytes(message);
                    System.out.println("Message resent: " + message);
                    byte[] bytes = new byte[255];
                    socket.setSoTimeout(3000);
                    dinp.read(bytes);
                    String input = new String(bytes).trim();
                    System.out.println(input);
                    messagesSent++;
                } catch (IOException ex) {
                    Logger.getLogger(BotCalls.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(BotCalls.class.getName()).log(Level.SEVERE, null, ex);
                
            } 
            
	}
	
	public void CloseClient() {
        try {
			dout.flush();
	        dout.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
            System.out.println("Messages sent in session: " + messagesSent);
	}
}
