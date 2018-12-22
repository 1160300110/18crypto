package ellipse;
import ellipse.ENode;
import ellipse.Ellipse;
import ellipse.theory;
import java.math.BigInteger;
import java.util.Random;

import prime.Prime;

public class Crypto {
	public ENode G;//基点G
	public BigInteger key;//私钥key
	public ENode K;//公钥点K
	public Ellipse E;//使用的曲线E
	public BigInteger N;//G点的阶数
	
	/**
	 * 对曲线上的点进行加密，首先需要初始化一些参数。这个函数会产生一个可用的公私钥加密方案
	 * 这种不带参数的情况，会自动选择默认值
	 */
	public Crypto() {
		Ellipse E = new Ellipse("Secp256k1");//默认的椭圆曲线
		this.E = E;
		this.G = new ENode(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"),
						   new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424"));
		this.G.setN(new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337"));
		this.N = this.G.getN();
		int k=0;
		while(k==0) {
			 k= (int) (Math.random()*999999);
		}
		
		//this.key = BigInteger.valueOf(k);
		this.key = new BigInteger(100,new Random());
		this.K = E.nodePow(G, key);
		System.out.println("K: "+this.K.toString()+" \n"+this.key.toString());
	}
	public Crypto(int a) {
		Ellipse E = new Ellipse("Secp256k1");//默认的椭圆曲线
		this.E = E;
		this.G = new ENode(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"),
						   new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424"));
		this.G.setN(new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337"));
		this.N = this.G.getN();
		this.key = BigInteger.valueOf(a);
		this.K = E.nodePow(G, key);
		System.out.println("K: "+this.K.toString()+" \n"+this.key.toString());
	}
	
	public Crypto(char a) {
		Ellipse E = new Ellipse("test");//测试
		this.E = E;
		this.G = new ENode(new BigInteger("3"),
						   new BigInteger("10"));
		this.G.setN(BigInteger.valueOf(28));
		this.N= this.G.getN();
		System.out.println("N:"+this.N);
		int k=0;
		while(k==0) {
			 //k= (int) (Math.random()*27);
			k= 4;
		}
		this.key = BigInteger.valueOf(k);
		System.out.println("key: "+this.key.toString());
		this.K = E.nodePow(G, key);
		System.out.println("K: "+this.K.toString());
	}
	
	public static final void main(String args[]) {
		Crypto c = new Crypto();
	}
	
}
