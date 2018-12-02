package ellipse;
import java.math.BigInteger;
import java.util.Arrays;

public class theory {
	
	public static BigInteger pow(BigInteger a, int b) {
		BigInteger result = new BigInteger("1");
		for(int i=1;i<=b;i++) {
			result = result.multiply(a);
		}
		//System.out.println(result.toString());
		return result;
	}
	
	
	/**
	 * 
	 * @param BigInteger value
	 * @return BigInteger sqrt(value)的近似值
	 */
	public static BigInteger sqrt(BigInteger value) {
		String x = value.toString();
        int mlen = x.length();
        int len;
        BigInteger beSqrtNum = new BigInteger(x);
        BigInteger sqrtOfNum;
        BigInteger sqrtOfNumMul;
        String sString;
        if(mlen%2 == 0)    len = mlen/2;
        else    len = mlen/2+1;
        char[] sArray = new char[len];
        Arrays.fill(sArray, '0');
        for(int pos=0; pos<len; pos++){
            for(char num='1'; num<='9'; num++){
                sArray[pos] = num;
                sString = String.valueOf(sArray);
                sqrtOfNum = new BigInteger(sString);
                sqrtOfNumMul = sqrtOfNum.multiply(sqrtOfNum);
                if(sqrtOfNumMul.compareTo(beSqrtNum) == 1){
                    sArray[pos]-=1;
                    break;    
                }    
            }
        }
        return new BigInteger(String.valueOf(sArray));
    }
	
	public static BigInteger quick_algorithm(BigInteger a, BigInteger b, BigInteger c) throws IllegalArgumentException{
		BigInteger result = BigInteger.ONE;
		a = a.remainder(c);
		if(b.compareTo(BigInteger.ZERO)<0) {
			throw new IllegalArgumentException();
		}
		while(b.compareTo(BigInteger.ZERO)!=0) {
			if(b.remainder(new BigInteger("2")).compareTo(BigInteger.ONE)==0) {//如果b是奇数
				result = result.multiply(a).mod(c);
			}
			b = b.shiftRight(1);
			a = a.multiply(a).mod(c);
		}
		return result;
	}
	
	public static Boolean isHaveMoSqrt(BigInteger x, BigInteger P) {
		BigInteger r = P.subtract(BigInteger.ONE).divideAndRemainder(new BigInteger("2"))[0];
		BigInteger ret = quick_algorithm(x, r,P);
		return ret.compareTo(BigInteger.ONE)==0?true:false;
	}
	
	public static BigInteger[] GetMoSqrt(BigInteger x, BigInteger P) {
		BigInteger two = new BigInteger("2");
		BigInteger ret[] = new BigInteger[2];
		if(isHaveMoSqrt(x, P)==true) {
			//BigInteger t = BigInteger.ZERO;
			int t = 0;
			BigInteger s = P.subtract(BigInteger.ONE);
			while(s.mod(new BigInteger("2")).compareTo(BigInteger.ZERO)==0){
				s = s.divideAndRemainder(two)[0];
				//t = t.add(BigInteger.ONE);
				t = t + 1;
			}
			//if(t.equals(BigInteger.ONE)) {
			if(t==1) {
				ret[0] = quick_algorithm(x, s.add(BigInteger.ONE).divideAndRemainder(two)[0], P);
				ret[1] = P.subtract(ret[0]);
				return ret;
			//}else if(t.compareTo(two)>=0) {
			}else if(t>=2) {
				BigInteger x_=quick_algorithm(x, P.subtract(two), P);
				BigInteger n = BigInteger.ONE;
				while(isHaveMoSqrt(n, P)==true){
					n = n.add(BigInteger.ONE);
				}
				BigInteger b = quick_algorithm(n, s, P);
				System.out.println(b.toString());
				ret[0] = quick_algorithm(x_, s.add(BigInteger.ONE).divideAndRemainder(two)[0], P);
				int t_ = 0;
				//while(t.subtract(BigInteger.ONE).compareTo(BigInteger.ZERO)>0) {
				while(t-1>0) {
					if(quick_algorithm(x_.multiply(ret[0]).multiply(ret[0]), two.pow(t-2), P).equals(BigInteger.ONE)) {
						//do nothing
					}else {
						ret[0] = ret[0].multiply(b.pow(Integer.valueOf(two.pow(t_).toString()))).mod(P);
					}
					t = t-1;
					t_ = t_ + 1; 
				}
				return ret;
			}else {
				BigInteger rr[] = new BigInteger[2];
				rr[0] = new BigInteger("-2");
				rr[1] = rr[0];
				return rr;
			}
			
		}else {
			BigInteger rr[] = new BigInteger[2];
			rr[0] = new BigInteger("-1");
			rr[1] = rr[0];
			return rr;
		}
	}
	
	private static BigInteger[] calY(BigInteger x) {
		BigInteger a = x.pow(3).add(x).add(BigInteger.ONE);
		return GetMoSqrt(a, new BigInteger("23"));
	}
	
	public static final void main(String args[]) {
		BigInteger two = new BigInteger("2");
		BigInteger result = new BigInteger("0");
		BigInteger re = new BigInteger("340282366881324382213159967663442624512");
		//result= result.add(pow(two,256)).subtract(pow(two,224)).add(pow(two,192)).add(pow(two,96)).subtract(BigInteger.ONE);
		re = pow(two,256);
		result = sqrt(re);
		re = pow(result,2);
		BigInteger a[] = calY(new BigInteger("3"));
		
		System.out.println(a[0].toString()+"  "+a[1].toString());
		//System.out.println(pow(two,256).toString());
		//System.out.println(re.toString());
		
	}
	
}
