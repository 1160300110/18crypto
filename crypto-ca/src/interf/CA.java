package interf;

/**
 * this interface will be implemented and be used by others in my group.
 * @author dzj
 * @version 1.1
 */
public interface CA {
	
	
	/**
	  *  �����������ע����CA�е���֤��ݡ�
	 * @param ע����Ϣ
	 * @return ע��ɹ������Ѿ�ע���������Ϣ�Ŀ����Ա��ܾ�
	 */
	public void signUp();
	
	
	/**
	  * ���������������ݵ���CA�С�
	  * @param ��¼��֤����
	  * @return Boolean ��¼�ɹ���ʧ��
	 */
	public Boolean signIn();
	
	
	/**
	 *  ���Ѿ�����CA֮�󣬵��������������ȡ����Ĺ�˽Կ�ԡ�
	 */
	public void getKeys();
	
	
}
