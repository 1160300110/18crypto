package web;

public class getMessage extends ServerIO {

	private Server target;
	private int timeout;
	private Boolean isDone;

	@Override
	public void init(Server server, int timeout) {
		this.isDone = false;
		this.target = server;
		this.timeout = timeout;
		System.out.println("初始化完成");

	}

	public synchronized String getOneMessage() {
		if (this.target.isRunning()) {// 如果服务器正常运行中
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根

	}

}
