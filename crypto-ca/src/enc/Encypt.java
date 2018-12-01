
package enc;

/**
 * 这个接口的目的，是定义将明文内容映射到椭圆曲线上的点的方式。
 * @author dzj
 *
 */
public interface Encypt<N> {
	
	/**
	 * @param plain-text 想要加密的明文，以字符串格式
	 * @return cipher-text  Enc(plain)
	 */
	public N Enc(String plain);
	
	/**
	 * this function
	 * @param cipher-text
	 * @return plain-text Dec(cipher)
	 */
	public String Dec(N cipher);
	
}
