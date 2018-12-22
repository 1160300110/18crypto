package web;

public abstract class ServerIO extends Thread {

	/*
	 * 这个类是对服务器的操作的父类，包含创建对服务器数据进行预约的函数
	 */
	public abstract void init(Server server, int timeout);

	/**
	 * 这个函数标志着进行的开始。
	 */
	public abstract void run();

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
