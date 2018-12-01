package enc;

import ellipse.ENode;
public class crypto implements Encypt<ENode> {

	@Override
	public ENode Enc(String plain) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String Dec(ENode cipher) {
		// TODO 自动生成的方法存根
		return null;
	}

	/**
	 * 提供检测明文是否在允许的长度范围内的函数
	 * 必须在对明文进行加密之前进行调用，否则可能产生隐患
	 * @param 明文 String plain
	 * @return Boolean 是否合法
	 */
	public Boolean isPlainLegal(String plain) {
		return true;
	}
}
