package prime;
import java.math.BigInteger;
import java.util.ArrayList;

import prime.Prime;

/*
 * this class is for making & storing prime numbers
 */

public class PrimeLib {
	private ArrayList<Prime> Lib = new ArrayList<Prime>();
	private Prime MaxPrime;
	private Boolean add(Prime newPrime) {
		if(this.Lib.isEmpty()) {
			this.Lib.add(newPrime);
			//this.MaxPrime = newPrime;
			//System.out.println("添加了"+newPrime.getInt());
			return true;
		}else {
			for(Prime p : this.Lib) {
				if(p.getValue().compareTo(newPrime.getValue())==0) {//两个一个样
					return false;
				}else if(newPrime.getValue().mod(p.getValue()).compareTo(BigInteger.ZERO)==0) {//不是质数
					return false;
				}
			}
			this.Lib.add(newPrime);
			//System.out.println("添加了"+newPrime.getInt());
		}
		return true;
	}
	
	/*
	 * 
	 */
	
	public static void main(String args[]) {
		
	}
}
