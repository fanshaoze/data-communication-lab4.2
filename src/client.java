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
		//�ͻ���
		//1������������ĵ�ַ���˿ںš����� 
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
		 
			//2���������ݱ����������͵�������Ϣ
		 DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
		 //3������DatagramSocket����
		 
		try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//4�����������������
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
			//���ܷ���������Ӧ����
			//======================================
			//1���������ݱ������ڽ��ܷ���������Ӧ����
			int t = 0;
			byte[] data2 = new byte[1024];
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
			//2�����ܷ�������Ӧ������
			try {
				socket.receive(packet2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String reply = new String(data2,0,packet2.getLength());
			if (reply.equals("no book you need")){
				System.out.println("���ǿͻ��ˣ�������˵��"+reply);
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
		//4���ر���Դ
		
		
	
	public static void Close() {
		//�ر���Դ
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
//				System.out.println("���͸��ڵ�����ָ��" + Count);
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
