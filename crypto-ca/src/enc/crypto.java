package enc;


import java.math.BigInteger;
import java.util.Random;

import ellipse.*;
import enc.SHA1;
import prime.Prime;
public class crypto implements Encypt<ENode> {

	public ENode[] Enc(int plain, Crypto cp) {
		//首先把string转换成一串数字
		int iplain;
		//然后对明文进行映射到曲线上
		ENode M = code(plain,cp.E);
		BigInteger r = BigInteger.ZERO;
		while(r.compareTo(BigInteger.ZERO)==0) {
			//r = BigInteger.valueOf((long) (Math.random()*27));
			r = new BigInteger(100,new Random());//一个最大是2^100的随机数
		}
		System.out.println("r: "+r);
		ENode C1 = cp.E.nodeAdd(M, cp.E.nodePow(cp.K, r));
		//System.out.println("M: "+M.toString());
		//System.out.println("MMl: "+cp.E.nodePow(cp.K, r).toString());
		ENode C2 = cp.E.nodePow(cp.G, r);
		C2.setN(r);
		ENode c[] = new ENode[2];
		c[0]=C1;
		c[1]=C2;
		//System.out.println("C1: "+C1.toString());
		//System.out.println(cp.E.nodePow(cp.G, r).getX());
		return c;
	}
	
	/**
	 * 将明文plain映射到椭圆曲线ellipse上的一点并返回。
	 * @param int, Ellipse
	 * @return ENode 
	 */
	public ENode code(int plain, Ellipse ellipse){
		ENode result;
		//定义一些参数
		final int MAX_BUFFER = 10;//这个是最大偏移量
		int buffer=0;//这个用来存偏移量
		BigInteger x = BigInteger.valueOf(-2);
		BigInteger y = BigInteger.valueOf(-2);//这个是返回值
		BigInteger bMax = BigInteger.valueOf(MAX_BUFFER);
		BigInteger bplain = BigInteger.valueOf(plain).multiply(bMax);
		//收到数字串之后，直接嵌入x坐标
		for(buffer=0;buffer<=MAX_BUFFER;buffer++) {
			if(buffer==MAX_BUFFER) {//如果这是最后一次，也就是没找到
				System.out.println(bMax.toString()+" 不够大。");
				return null;
			}
			x = bplain.add(BigInteger.valueOf(buffer));//每个循环中，将偏移buffer位尝试进行求解
			y= ellipse.calY(x)[0];//求个y试试
			if(y.compareTo(BigInteger.valueOf(-1))!=0)break;//如果求出来结果不是-1，就跳出循环
		}
		result = new ENode(x.mod(ellipse.getP()),y);
		result.buffer = buffer;
		return result;
	}
	
	public int Dec(ENode[] cipher,Crypto cp) {
		ENode m1 = cp.E.nodePow(cipher[1], cp.key);
		ENode M;
		if(m1.getX()!=null) {
			M = cp.E.nodeAdd(cipher[0], m1.reverse());
		}else {
			M = cipher[0];
		}
		
		System.out.println("M: "+M.toString());
		return Integer.valueOf(M.getX().toString())-M.buffer;
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
	
	public static int bti(byte[] b) {   
		return   b[3] & 0xFF |   
		            (b[2] & 0xFF) << 8 |   
		            (b[1] & 0xFF) << 16 |   
		            (b[0] & 0xFF) << 24;   
		}   
		public static byte[] itb(int a) {   
		return new byte[] {   
		        (byte) ((a >> 24) & 0xFF),   
		        (byte) ((a >> 16) & 0xFF),      
		        (byte) ((a >> 8) & 0xFF),      
		        (byte) (a & 0xFF)   
		    };   
		} 
	
	public static final void main(String args[]) {
		SHA1 a = new SHA1();
		String b = a.encode("Hello");
		int k = 0;
		//System.out.println(b);
		/*String word = new String("9999");
			System.out.println(word.getBytes().length);
			k=bti(word.getBytes());
			System.out.println(k);
		
		
			System.out.println("itb:"+itb(k));
			System.out.println(new String(itb(k)));
		*/
		Ellipse ep = new Ellipse("Secp256k1");
		//Ellipse test = new Ellipse(1,1,new Prime(23));
		crypto e = new crypto();
		//ENode node = e.code(1,test);
		Crypto cp = new Crypto();
		ENode[] ee = e.Enc(10000, cp);
		//System.out.println(ee[1].toString());
		int r = e.Dec(ee, cp);
		System.out.println("R: "+r);
		
	}

	public ENode Enc(String plain) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public ENode Enc(String plain, int k, ENode G, ENode K) {
		// TODO 自动生成的方法存根
		return null;
	}

	
}
