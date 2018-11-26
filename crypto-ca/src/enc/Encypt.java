/**
 * 
 */
package enc;

/**
 * @author dzj
 *
 */
public interface Encypt<N> {
	
	/**
	 * @param plain-text which you are going to encrypt
	 * @return cipher-text  Enc(plain)
	 */
	public N Enc(N plain);
	
	/**
	 * this function
	 * @param cipher-text
	 * @return plain-text Dec(cipher)
	 */
	public N Dec(N cipher);
	
}
