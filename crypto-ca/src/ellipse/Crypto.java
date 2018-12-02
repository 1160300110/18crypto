package ellipse;
import ellipse.ENode;
import ellipse.Ellipse;
import ellipse.theory;
import java.math.BigInteger;
import prime.Prime;

public class Crypto {
	private ENode G;//基点G
	private BigInteger key;//私钥key
	private ENode K;//公钥点K
	private Ellipse E;//使用的曲线E
	
	/**
	 * 对曲线上的点进行加密，首先需要初始化一些参数。这个函数会产生一个可用的公私钥加密方案
	 * 这种不带参数的情况，会自动选择默认值
	 */
	public Crypto() {
		Ellipse E = new Ellipse(1,1,new Prime(23));//默认的椭圆曲线
		this.E = E;
	}
	
	private void ellipseGen() {
		Ellipse E = new Ellipse();
		this.E = E;
	}
	private void nodeGen() {
		if(this.E!=null) {
		}
		//this.G;
	}
}
