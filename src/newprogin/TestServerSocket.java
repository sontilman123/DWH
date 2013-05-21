/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newprogin;

/**
 *
 * @author Asus
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestServerSocket {
    
        static String pwd = "null";
    
        public static boolean CheckUsername(String usr)throws Exception{
            
            boolean isUser = false;
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/progin";
            Connection con = (Connection) DriverManager.getConnection(url, "progin", "progin");
            Statement statement = (Statement) con.createStatement();
            
            ResultSet rs = statement.executeQuery("select * from user where username='"+usr+"'");
            while (rs.next()){
                pwd = rs.getString(2);
                isUser = true;
            }
        
            
            return isUser;
        }

        
	public static void main(String args[]) throws IOException, Exception {
                boolean signin = false;
		final int portNumber = 81;
                String pertanyaan="Apa yang ingin anda lakukan ?";
		System.out.println("Creating server socket on port " + portNumber);
		ServerSocket serverSocket = new ServerSocket(portNumber);
		while (true) {
			Socket socket = serverSocket.accept();
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			pw.println(pertanyaan);

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = br.readLine();
                        if (str.equals("login")){
                        pertanyaan = "Masukkan username anda :";
			pw.println("Anda akan login");
                        }
                        else if(pertanyaan.equals("Masukkan username anda :")){
                        
                            if(CheckUsername(str)==true){
                               pertanyaan = "Masukkan password anda : "; 
                               pw.println("username terdaftar"); 
                            }else{
                               pw.println("username tidak terdaftar");
                            }
                        
                        }else if(pertanyaan.equals("Masukkan password anda : ")){
                        
                            if(str.equals(pwd)){
                                signin = true;
                                pertanyaan="Apa yang ingin anda lakukan ?";
                                pw.println("login berhasil");
                            }else {
                                pw.println("Password salah");
                            }
                        }else if(str.equals("exit")){
                        pw.println("Terima kasih");   
                        }else{
                        pw.println("Hello, " + str);   
                        }
			pw.close();
			socket.close();

			System.out.println("Just said hello to:" + str);
		}
	}
}