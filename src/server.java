import java.io.*;
import java.net.*;

import javax.sound.midi.MidiDevice.Info;

//import frames.Iframes;


public class server {
	
	static String[] outlook={"1.txt","2.txt","3.txt","4.txt"};
	public static void main(String args[]) {
		DatagramSocket socket = null;
		int i = 0;
		try {
			socket = new DatagramSocket(10010);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//2、创建数据报，用于接受客户端发送的数据
		
		//服务器端，实现基于UDP的用户登录
		//1、创建服务器端DatagramSocket，指定端口

		byte[] data =new byte[5];//
		DatagramPacket packet =new DatagramPacket(data,data.length);
		String info = null;
		//3、接受客户端发送的数据
		try {
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//此方法在接受数据报之前会一致阻塞
		//4、读取数据
		info =new String(data,0,data.length);
		System.out.println("the book clinet want is:"+info);

			
		
		//String high = info.toUpperCase();
		//System.out.println(high);
		//向客户端响应数据
		int j = 0;
		for(j = 0;j<outlook.length;j++){
			if ((info.equals(outlook[j]))){
				System.out.println(info+" "+outlook[i]);
				break;
			}
			else{
				System.out.println(info+" "+outlook[i]);
			}
		}
		if(j == outlook.length){
			byte[] data2 = "no book you need".getBytes();
			InetAddress address = packet.getAddress();
			//System.out.println("AAAAA"+address);
			int port = packet.getPort();
			System.out.println("send back");
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length,address,port);
			try {
				socket.send(packet2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			 // 逐行读取数据   
			 FileReader fr = null;
			try {
				fr = new FileReader(info);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 BufferedReader br = new BufferedReader(fr);   
			 String str2;
			try {
				str2 = br.readLine();
			  
				while (str2 != null) {   			
				    byte[] data2 = str2.getBytes();
					InetAddress address = packet.getAddress();
					//System.out.println("AAAAA"+address);
					int port = packet.getPort();
					//System.out.println("AAAAA"+port);
					System.out.println("file back");
					DatagramPacket packet2 = new DatagramPacket(data2,data2.length,address,port);
					//3、响应客户端
					try {
						socket.send(packet2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					str2 = br.readLine(); 
				}   
				br.close();   
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 			
		}
		//4、关闭资源
		socket.close();
		
	}
}
	
