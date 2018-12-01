package interf;

/**
 * this interface will be implemented and be used by others in my group.
 * @author dzj
 * @version 1.1
 */
public interface CA {
	
	
	/**
	  *  这个函数用来注册在CA中的认证身份。
	 * @param 注册信息
	 * @return 注册成功；或已经注册过；或信息的可信性被拒绝
	 */
	public void signUp();
	
	
	/**
	  * 这个函数用来将身份登入CA中。
	  * @param 登录验证内容
	  * @return Boolean 登录成功或失败
	 */
	public Boolean signIn();
	
	
	/**
	 *  当已经登入CA之后，调用这个函数来获取最近的公私钥对。
	 */
	public void getKeys();
	
	
}
