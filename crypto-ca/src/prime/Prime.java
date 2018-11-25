package prime;

import java.math.BigInteger;
public final class Prime {
	private BigInteger value = BigInteger.ZERO;
	
	public Prime(BigInteger x){
		this.value = x;
	}
	public Prime(int x) {
		this.value = BigInteger.valueOf(x);
	}
	
	public BigInteger getValue() {
		return this.value;
	}
	public static final void main(String args[]) {
		
	}
}
