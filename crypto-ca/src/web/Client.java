package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public String m = null;
	private Socket socket;
	// 搭建客户端

	private class listen implements Runnable{

		@Override
		public void run() {
			try {
				// 3、获取输入流，并读取服务器端的响应信息
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// 由Socket对象得到输入流，并构造相应的BufferedReader对象
				String readline;
				
				readline = in.readLine(); // 从系统标准输入读入一字符串
				System.out.println("开始监听");
				while (!readline.equals("end")) {
					// 若从标准输入读入的字符串为 "end"则停止循环
					System.out.println("服务:" + readline);
					readline = in.readLine(); // 从系统标准输入读入一字符串
					// 从Server读入一字符串，并打印到标准输出上
				} // 继续循环
			}catch(Exception e){
				System.out.println("can not listen to:" + e);// 出错，打印出错信息
			}
		}
		
	}
	private class send implements Runnable{

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				// 由系统标准输入设备构造BufferedReader对象
				PrintWriter write = new PrintWriter(socket.getOutputStream());
				// 由Socket对象得到输出流，并构造PrintWriter对象
				String readline;
				readline = br.readLine(); // 从系统标准输入读入一字符串
				while (!readline.equals("end")) {
					// 若从标准输入读入的字符串为 "end"则停止循环
					write.println(readline);
					// 将从系统标准输入读入的字符串输出到Server
					write.flush();
					// 刷新输出流，使Server马上收到该字符串
					System.out.println("客户:" + readline);
					// 在系统标准输出上打印读入的字符串
					readline = br.readLine(); // 从系统标准输入读入一字符串
				} // 继续循环
					// 4、关闭资源
				write.close(); // 关闭Socket输出流
				socket.close(); // 关闭Socket
			}catch(Exception e){
				System.out.println("can not listen to:" + e);// 出错，打印出错信息
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException {
		Client c = new Client();
		c.somefunc();
	}
	public void somefunc() {
	

			System.out.println("客户端线程");
				try{
					// 1、创建客户端Socket，指定服务器地址和端口

					// 下面是你要传输到另一台电脑的IP地址和端口
					System.out.println("尝试链接客户端");
					socket = new Socket("172.17.23.81", 8888);
					System.out.println("客户端启动成功");
					// 2、获取输出流，向服务器端发送信息
					new Thread(new listen()).start();
					new Thread(new send()).start();
					// 向本机的52000端口发出客户请求
					
				}catch(
				Exception e)
				{
					System.out.println("can not listen to:" + e);// 出错，打印出错信息
				}
			}
			
		
		
}


