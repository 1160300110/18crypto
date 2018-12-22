package ellipse;

import ellipse.ENode;
import prime.Prime;
import ellipse.theory;
import java.math.BigInteger;
import java.util.*;

public class Ellipse {
	private BigInteger a;
	private BigInteger b;
	private Prime p;
	private ENode G;
	private Boolean ellipseBuild() {
		System.out.println("y^2 = x^3 + "+this.a+"x + "+this.b+" (mod "+this.p.getValue()+")");
		return true;
	}
	

	public Ellipse(String s) {//什么都不输入的时候，选NIST P-256
		if(s.equals("NIST P-256")) {
			BigInteger bp = new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951");
			this.p = new Prime(bp);
			this.a = BigInteger.valueOf(-3);
			this.b = new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291");
			this.ellipseBuild();
		}
		if(s.equals("Secp256k1")) {
			this.a=BigInteger.ZERO;
			this.b = new BigInteger("7");
			this.p = new Prime(new BigInteger("115792089237316195423570985008687907853269984665640564039457584007908834671663"));
			this.G = new ENode(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"),
					   new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424"));
			this.G.setN(new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337"));
			this.ellipseBuild();
		}
		if(s.equals("test")) {
			this.a=BigInteger.ONE;
			this.b=BigInteger.ONE;
			this.p=new Prime(23);
			this.G= new ENode(3,23);
			this.G.setN(BigInteger.valueOf(28));
			this.ellipseBuild();
		}
	}
	public Ellipse(Prime p) {
		this.p = p;
		this.a = BigInteger.valueOf(-3);
		this.b = new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291");
		this.ellipseBuild();
	}
	public Ellipse(int a, int b, Prime p) throws IllegalArgumentException{
		this.a = BigInteger.valueOf(a);
		this.b = BigInteger.valueOf(b);
		BigInteger cal = theory.pow(this.a,4).multiply(new BigInteger("4"));
		cal = cal.add(theory.pow(this.b, 2).multiply(new BigInteger("27")));
		cal = cal.remainder(p.getValue());
		if(cal.compareTo(BigInteger.ZERO)==0) {
			throw new IllegalArgumentException(this.a.toString()+", "+
					this.b.toString()+" need to fill the form: 4a^3+27b^2 mod p != 0");
		}
		this.p = p;
		this.ellipseBuild();
	}
	
	/**
	 * 两点的加法，也就是P+Q=R的过程
	 * @param two ENode
	 * @return their sum in ENode form
	 * 
	 */
	public ENode nodeAdd(ENode alice, ENode bob) {
		BigInteger p = this.p.getValue();
		BigInteger k;
		if(alice.getN().compareTo(BigInteger.ONE)==0&&bob.getN().compareTo(BigInteger.ONE)!=0) {
			//System.out.println("发现零元，返回"+bob.toString());
			return bob;
		}else if(bob.getN().compareTo(BigInteger.ONE)==0&&alice.getN().compareTo(BigInteger.ONE)!=0) {
			//System.out.println("发现零元"+alice.toString());
			return alice;
		}else if(alice.getN().compareTo(BigInteger.ONE)==0&&bob.getN().compareTo(BigInteger.ONE)==0) {
			System.out.println("两个都是零元，这是怎么回事？");
			return this.G;
		}
		
		if(isEqual(alice, bob)) {
			//System.out.println("这俩点等"+alice.toString());
			k = theory.pow(alice.getX(),2).multiply(new BigInteger("3")).add(this.a).remainder(p);
			k = k.multiply(fermat(alice.getY().multiply(new BigInteger("2")),p)).remainder(p);
		}else if(alice.getX().compareTo(bob.getX())==0&&alice.getY().compareTo(bob.getY())!=0){//下一个点是零元
			return new ENode(0);
		}else {
			k = bob.getY().subtract(alice.getY()).remainder(p).multiply(fermat(bob.getX().subtract(alice.getX()),p));
			k.remainder(p);
		}
		k = k.add(p).remainder(p);
		if(k.compareTo(BigInteger.ZERO)==0) {//is ZERO
			//System.out.println("加完得到了零元"+alice.toString()+bob.toString());
			return new ENode(0);
		}
		
		BigInteger newX = theory.pow(k, 2).subtract(alice.getX()).subtract(bob.getX());
		newX = newX.remainder(p).add(p).remainder(p);
		BigInteger newY = k.multiply(alice.getX().subtract(newX)).subtract(alice.getY());
		newY = newY.remainder(p).add(p).remainder(p);
		ENode result = new ENode(newX, newY);
		//在这里对结果进行正确性验证，看看是不是零元 
		BigInteger[] cal = calY(result.getX());
		if(cal[0].compareTo(BigInteger.valueOf(-1))!=0) {
			if(result.getY().compareTo(cal[0])!=0&&result.getY().compareTo(cal[1])!=0) {//都不等，说明这个点是零元
				//System.out.println("发现这个点"+result.toString()+"不在曲线上，改为零元");
				return new ENode(0);
			}
		}
		
		//System.out.println(result.toString());
		return result;
	}
	
	/**
	 * 对Alice进行初始化，如果Alice是零元则返回阶1 
	 * @param ENode alice 想要初始化的点
	 * @return BigInteger Alice的阶 n
	 */
	public BigInteger nodeInitN(ENode alice) {
		BigInteger result = new BigInteger("0");
		if(alice.getN().compareTo(BigInteger.ONE)!=0) {
			//这不是零元
			ENode sum = new ENode(alice.getX(),alice.getY());
			result.add(BigInteger.ONE);
			while(true) {
				result = result.add(BigInteger.ONE);
				sum = nodeAdd(sum, alice);
				if(sum.getN()==BigInteger.ONE) break;
				//System.out.println(sum.getX().toString()+", "+sum.getY().toString());
			}
		}else {
			return BigInteger.ONE;
		}
		alice.setN(result);
		System.out.println("re = "+result.toString());
		return result;
	}
	
	public ENode nodePow(ENode alice, BigInteger times) throws IllegalArgumentException{
		ENode result = new ENode(0);
		//这个函数中，将alice乘方0次将会直接报错。
		if(times.compareTo(BigInteger.ZERO)==0) {
			throw new IllegalArgumentException("指数不能为零。");
		}
		if(times.compareTo(BigInteger.ONE)==0) {//如果是不知为何要pow1的时候，直接返回
			return alice;
		}
		/*
		if(alice.getN().compareTo(BigInteger.ZERO)==0) {//这点还没初始化
			//nodeInitN(alice);
		}
		*/
		if(alice.getN().compareTo(BigInteger.ONE)==0) {//这点是零元
			return new ENode(0);
		}else {//正常情况下
			//������Ҫ�˵Ĵ����ǲ����������Ľ�֮��
			if(times.compareTo(alice.getN())>0) {
				//throw new IllegalArgumentException("ָ�� "+times.toString()+" ���ڵ�"+alice.toString());
			}else if(times.compareTo(alice.getN())==0){//�պ��������Ľ�
				return new ENode(0);
			}
		}
		
		//如果是正常的
		char[] btimes = times.toString(2).toCharArray();
		ENode[] ary = new ENode[btimes.length];
		ary[btimes.length-1] = alice;//最后一位设为alice
		
		Boolean flag = true;
		//btimes一定是2位以上的长度的
		//System.out.println(btimes);
		for(int i = 0;i<btimes.length;i++) {
			if(btimes[i]=='1') {
				if(flag==true) {//说明这是第一次发现1ֵ
					flag=false;
					for(int j = btimes.length -2;j>=0;j--) {//从倒数第二位开始，往回依次计算迭代
						//System.out.println("ary: "+ary[j+1].toString());
						ary[j] = nodeAdd(ary[j+1],ary[j+1]);
						//System.out.println("ary1: "+ary[j].toString());
					}
					break;
				}
			}
		}for(int i = 0;i<btimes.length;i++) {
			if(btimes[i]=='1') {
				result = nodeAdd(result,ary[i]);
				//System.out.println("RESULT: "+result.toString());
			}
		}
			return result;
	}
	
	/**
	 * 已知某点的x坐标求y的过程，加密的第二步
	 * @param x
	 * @return y[]
	 */
	public BigInteger[] calY(BigInteger x) {
		BigInteger y = x.pow(3).add(x.multiply(this.a)).add(this.b);
		return theory.GetMoSqrt(y, this.p.getValue());
	}
	
	/**
	 * Fermat's little theorem
	 * so cool
	 * @param 欲求的x，其模值MOD
	 * @return x在mod MOD下的乘法逆元
	 */
	public static BigInteger fermat(BigInteger x,BigInteger MOD){
	    BigInteger ans=BigInteger.ONE;
	    BigInteger i;
	    ans = theory.pow(x, MOD.subtract(new BigInteger("2")),MOD);
	    return ans;
	}
	
	public static Boolean isEqual(ENode alice, ENode bob) throws IllegalArgumentException{
		if(alice.getN().compareTo(BigInteger.ONE)==0) {
			throw new IllegalArgumentException("��һ������"+alice.toString()+"����Ԫ�����Ϸ�");
		}
		if(bob.getN().compareTo(BigInteger.ONE)==0) {
			throw new IllegalArgumentException("�ڶ�������"+bob.toString()+"����Ԫ�����Ϸ�");
		}
		//System.out.println(alice.getN());
		return (alice.getX().compareTo(bob.getX())==0&&alice.getY().compareTo(bob.getY())==0);
	}
	
	/**
	 * 计算某个点在曲线上的坐标，如果不在曲线上则返回[-1, -1]；如果出现未知错误则返回[-2, -2]
	 * @param x
	 * @return [y1, y2]
	 */
	public ENode[] nodeCal(BigInteger x) {
		System.out.println("对x为"+x.toString()+"求解");
		BigInteger p = this.p.getValue();
		x = x.add(p).remainder(p);//���ȶ�x����ģp
		BigInteger sum = theory.pow(x, 3).add(x.multiply(a)).add(b);//y^2 = x^3 + ax + b
		sum = sum.add(p).remainder(p);
		System.out.println("sum ="+sum.toString());
		ArrayList<BigInteger> ans = new ArrayList<BigInteger>();//�⼯,ͨ����������
		/*
		for(BigInteger i = BigInteger.ZERO;i.compareTo(this.p.getValue())<0;i=i.add(BigInteger.ONE)) {
			if(theory.pow(i,2).remainder(p).compareTo(sum)==0) {
				ans.add(i);
				System.out.println("�ҵ��˽⣺"+i.toString());
			}
		}
		*/
		
		if(ans.size()==0) {//û���ҵ���
			System.out.println("��x����"+x.toString()+"���û���ҵ���");
			return null;
		}
		ENode result[] = new ENode[ans.size()];
		int i=0;
		for(BigInteger y: ans){
			result[i]=new ENode(x,y);
			i++;
		}
		return result;
	}
	
	/**
	 * 这个函数用于获得某个曲线上面的所有点。不过应该没什么实用性。
	 * @return 一个包含所有点的集合
	 */
	public ArrayList<ENode> getAllENodes(){
		ArrayList<ENode> result = new ArrayList<ENode>();
		for(BigInteger i = BigInteger.ZERO;i.compareTo(this.p.getValue())<0;i=i.add(BigInteger.ONE)) {
			BigInteger[] y = this.calY(i);
			if(!y[0].equals(new BigInteger("-1"))) {
				System.out.println(i.toString()+"  "+y[0].toString()+"; "+y[1].toString());
				
			}
			
		}
		return result;
	}
	
	/**
	 * 产生一个曲线的可选基点G
	 * @return 基点G
	 */
	public ENode nodeGen() {
		//尝试枚举G点x坐标，并计算n是否为质数
		for(BigInteger i = BigInteger.ZERO;i.compareTo(this.p.getValue())<0;i=i.add(BigInteger.ONE)) {
			BigInteger[] y = this.calY(i);
			if(!y[0].equals(new BigInteger("-1"))) {
				//System.out.println(i.toString()+"  "+y[0].toString()+"; "+y[1].toString());
				ENode someNode = new ENode(i,y[0]);
				ENode someNode1 = new ENode(i, y[1]);
				if(this.nodeInitN(someNode).isProbablePrime(1)) {
					return someNode;
				}else if(this.nodeInitN(someNode1).isProbablePrime(1)) {
					return someNode1;
				}
			}
		}
		ENode G = new ENode(0);
		System.out.println("未能发现G点");
		this.nodeInitN(G);
		return G;
	}
	public BigInteger getP() {
		return this.p.getValue();
	}
	public static final void main(String args[]) {
		Ellipse a = new Ellipse("Secp256k1");
		Ellipse b = new Ellipse("NIST P-256");
		ENode alice = new ENode(3,10);
		BigInteger i = new BigInteger("3");
		System.out.println(fermat(i,new BigInteger("11")));
		//ENode g = b.nodeGen();
		/*
		for(int j=0;;j++) {
			int k;
			int max = 1;
			i = BigInteger.valueOf(j);
			for(k=0;k<=max;k++) {
				BigInteger m = i.add(BigInteger.valueOf(k));
				if(a.calY(m)[0].compareTo(BigInteger.valueOf(-1))!=0)break;
			}
			if(k==max)
			System.out.println(i.toString()+" "+k);
		}
		
		//ArrayList<ENode> r =b.getAllENodes();
		//System.out.println(r);
		
		//System.out.println(a.nodeCal(alice.getX())[1].toString());
		//System.out.println("r:"+r.toString());
		//a.nodeInitN(alice);
		//System.out.println("after: "+alice.toString());
		ENode bob = new ENode(3,13);
		ENode re = a.nodeAdd(alice, alice);
		System.out.println(re.getX());
		System.out.println(re.getY());
		Boolean flag = true;
		
		//a.nodeAdd(alice, alice);
		
		*/
	}
}