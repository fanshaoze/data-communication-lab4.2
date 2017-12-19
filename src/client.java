import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;



public class client {
	static DatagramSocket socket=null;
	static Timer sendData = new Timer();
	static Timer receiveData = new Timer();
	
	public static void client() {
		//客户端
		//1、定义服务器的地址、端口号、数据 
		InetAddress address= null;
		try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int port =10010;
		 int i = 0;

		String bookname = "1.txt";
		byte[] data =bookname.getBytes();
		 
			//2、创建数据报，包含发送的数据信息
		 DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
		 //3、创建DatagramSocket对象
		 
		try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//4、向服务器发送数据
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = "return"+bookname;
        File file = new File(path);
//        if(!file.exists()){
//            file.getParentFile().mkdirs();          
//        }
        try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true){
			//接受服务器端响应数据
			//======================================
			//1、创建数据报，用于接受服务器端响应数据
			int t = 0;
			byte[] data2 = new byte[1024];
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
			//2、接受服务器响应的数据
			try {
				socket.receive(packet2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String reply = new String(data2,0,packet2.getLength());
			if (reply.equals("no book you need")){
				System.out.println("我是客户端，服务器说："+reply);
				break;
			}
			else{				
		        try {

			        FileWriter fw = new FileWriter(file, true);
			        BufferedWriter bw = new BufferedWriter(fw);
			        bw.write(reply+"\r\n");
			        bw.flush();
			        bw.close();
			        fw.close();
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		socket.close();
	}	
		//4、关闭资源
		
		
	
	public static void Close() {
		//关闭资源
		socket.close();
		
	}

	
	public static void main(String args[]) {
		client();
	
	}

}


//CorrectTimer.schedule(new TimerTask() {
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		try {
//
//			CurrentTime currentTime = Util.getCurrentDateTime(Util.getCurrentDateTime());
//			if(Count < MaxCount){
//			nioCorrectTime.sendto(
//					Util.getCorrectTimeMessage2(0x13,currentTime.getHour(), currentTime.getMinute(),
//							currentTime.getSecond()),
//					getSocketAddressByName(parameter.getRootAddr(), CorrectTimePort));
//			Count++;
//			System.out.println("correct time " + currentTime.getSecond());
//			}else{
//				System.out.println("发送跟节点重启指令" + Count);
//				nioCorrectTime.sendto(
//						Util.getCorrectTimeMessage2(0x14,currentTime.getHour(), currentTime.getMinute(),
//								currentTime.getSecond()),
//						getSocketAddressByName(parameter.getRootAddr(), CorrectTimePort));
//				Count = 0;
//			}
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}, 0, 1000 * currect_rate);
