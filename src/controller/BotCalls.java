package controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
        public static List<BOT_ACTIONS> translateCartesianToMovement(List<BOT_ACTIONS> cartesian){
            List<BOT_ACTIONS> movementSol = new ArrayList<>();
            //movementSol.add(BOT_ACTIONS.forward);
            //BOT_ACTIONS prevAction = BOT_ACTIONS.forward;
            BOT_ACTIONS prevAction = cartesian.get(0);
            
            for (BOT_ACTIONS bot_actions : cartesian) {
                if(bot_actions == prevAction){
                    movementSol.add(BOT_ACTIONS.forward);
                }
                else {
                    if(prevAction == BOT_ACTIONS.right && bot_actions == BOT_ACTIONS.forward){
                        movementSol.add(BOT_ACTIONS.left);
                    }
                    else if(prevAction == BOT_ACTIONS.left && bot_actions == BOT_ACTIONS.forward){
                        movementSol.add(BOT_ACTIONS.right);
                    }
                    else if(prevAction == BOT_ACTIONS.right && bot_actions == BOT_ACTIONS.backward){
                        movementSol.add(BOT_ACTIONS.right);
                    }
                    else if(prevAction == BOT_ACTIONS.left && bot_actions == BOT_ACTIONS.backward){
                        movementSol.add(BOT_ACTIONS.left);
                    }
                    else if(prevAction == BOT_ACTIONS.forward && bot_actions == BOT_ACTIONS.right){
                        movementSol.add(BOT_ACTIONS.right);
                    }
                    else if(prevAction == BOT_ACTIONS.backward && bot_actions == BOT_ACTIONS.right){
                        movementSol.add(BOT_ACTIONS.left);
                    }
                    else if(prevAction == BOT_ACTIONS.forward && bot_actions == BOT_ACTIONS.left){
                        movementSol.add(BOT_ACTIONS.left);
                    }
                    else if(prevAction == BOT_ACTIONS.backward && bot_actions == BOT_ACTIONS.left){
                        movementSol.add(BOT_ACTIONS.right);
                    }
                    else {
                        movementSol.add(BOT_ACTIONS.stop);
                        System.out.println("Error!");
                    }
                    movementSol.add(BOT_ACTIONS.forward);
                }
                prevAction = bot_actions;
            }
            return movementSol;
        }
        
	public enum BOT_ACTIONS{
        forward,
        backward,
        stop,
        left,
        right
    }
}
