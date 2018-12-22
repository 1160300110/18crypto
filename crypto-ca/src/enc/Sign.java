package enc;
import java.math.BigInteger;
import java.util.Random;
import enc.SHA256;
import ellipse.*;

public class Sign {
	private Ellipse E;
	private BigInteger r;
	private BigInteger s;
	
	public Sign(Ellipse E, BigInteger r, BigInteger s) {
		this.E = E;
		this.r = r;
		this.s = s;
	}
	public static Sign eccSign(String Message, Crypto cp) {
		//定义一些参数
		Sign result = null;
		BigInteger k = BigInteger.ZERO;
		BigInteger r = BigInteger.ZERO;
		BigInteger s = BigInteger.ZERO;
		ENode g = null;
		//首先算出消息的哈希
		String hash = SHA256.encode(Message);
		System.out.println("HASH: "+hash);
		BigInteger H = new BigInteger(hash,16);
		//H = BigInteger.ONE;
		BigInteger z =H;
		System.out.println("z: "+z);
		//然后是求出一个合适的r，这个r不能为0
		while(r.compareTo(BigInteger.ZERO)==0||s.compareTo(BigInteger.ZERO)==0) {
			//选取一个1到n-1的整数k
			while(k.compareTo(BigInteger.ZERO)==0) {
				k= new BigInteger(100,new Random());
				//k=BigInteger.ONE;
			}
			g = cp.E.nodePow(cp.G, k);
			//System.out.println(g.toString());
			r = g.getX().mod(cp.N);
			if(r.compareTo(BigInteger.ZERO)==0) {
				System.out.println("r为0");
				continue;
			}
			s = z.add(r.multiply(cp.key)).multiply(Ellipse.fermat(k, cp.N)).mod(cp.N);
		}
		result = new Sign(cp.E,r,s);
		return result;
	}
	
	public Boolean eccDesg(String Message, Crypto cp) {
		ENode result = new ENode();
		//首先算出消息的哈希
		String hash = SHA256.encode(Message);
		System.out.println("HASH: "+hash);
		BigInteger H = new BigInteger(hash,16);
		//H = BigInteger.ONE;
		BigInteger z = H;
		System.out.println("z: "+z);
		
		BigInteger w = Ellipse.fermat(this.s, cp.N);
		System.out.println("W: "+w);
		BigInteger u1 = z.multiply(w).mod(cp.N);
		BigInteger u2 = r.multiply(w).mod(cp.N);
		ENode r1 = this.E.nodePow(cp.G,u1);
		ENode r2 = this.E.nodePow(cp.K,u2);
		System.out.println(r1);
		System.out.println(r2);
		result = this.E.nodeAdd(r1,r2);
		if(result.getN().compareTo(BigInteger.ONE)==0) {
			return false;
		}
		if(r.mod(cp.N).compareTo(result.getX().mod(cp.N))!=0) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return this.r.toString()+" "+this.s.toString();
	}
	public static final void main(String args[]) {
		Crypto cp = new Crypto(29982);
		Crypto cp1 = new Crypto(668959);
		String m = "什么意思哟？啦啦啦啦爱U哟谁打的奥I好";
		String m1 = "是那种艺术字";
		Sign s = Sign.eccSign(m, cp);
		Sign ss = Sign.eccSign(m1, cp);
		System.out.println(s.toString());
		System.out.println();
		Boolean r = s.eccDesg(m, cp);
		System.out.println(r.toString());
		//System.out.println(r.getX().mod(s.cp.E.getP()));
	}
}
