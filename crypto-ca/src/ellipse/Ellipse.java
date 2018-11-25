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
	private Boolean ellipseBuild() {
		System.out.println("y^2 = x^3 + "+this.a+"x + "+this.b+" (mod "+this.p.getValue()+")");
		return true;
	}
	

	public Ellipse() {
		BigInteger bp = new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951");
		this.p = new Prime(bp);
		this.a = BigInteger.valueOf(-3);
		this.b = new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291");
		this.ellipseBuild();
	}
	public Ellipse(Prime p) {
		this.p = p;
		this.a = BigInteger.valueOf(-3);
		this.b = new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291");
		this.ellipseBuild();
	}
	public Ellipse(Prime p ,int a, int b) {
		this.a = BigInteger.valueOf(a);
		this.b = BigInteger.valueOf(b);
		this.p = p;
		this.ellipseBuild();
	}
	/*
	 * @param two ENode
	 * @returns their sum in ENode form
	 * 
	 */
	public ENode nodeAdd(ENode alice, ENode bob) {
		BigInteger p = this.p.getValue();
		BigInteger k;
		if(alice.getN().compareTo(BigInteger.ONE)==0) {//如果这俩其中一个是零元
			return bob;
		}else if(bob.getN().compareTo(BigInteger.ONE)==0) {
			return alice;
		}
		
		if(isEqual(alice, bob)) {
			//k = ((3*alice.getX()*alice.getX()+this.a)%p * fermat(2*alice.getY(),p)).reminder(p);
			k = theory.pow(alice.getX(),2).multiply(new BigInteger("3")).add(this.a).remainder(p);
			k = k.multiply(fermat(alice.getY().multiply(new BigInteger("2")),p)).remainder(p);
		}else if(alice.getX().compareTo(bob.getX())==0&&alice.getY().compareTo(bob.getY())!=0){//两个相反点相加得到零元
			return new ENode(0);
		}else {
			//k = (double)(((bob.getY() - alice.getY())%p * fermat(bob.getX() - alice.getX(),p))%p);
			k = bob.getY().subtract(alice.getY()).remainder(p).multiply(fermat(bob.getX().subtract(alice.getX()),p));
			k.remainder(p);
		}
		k = k.add(p).remainder(p);
		if(k.compareTo(BigInteger.ZERO)==0) {//双保险
			return new ENode(0);
		}
		//System.out.println("k = "+k);
		//double newX = ((k*k - alice.getX() - bob.getX())%p+p)%p;
		//System.out.println(theory.pow(k, 2).toString());
		BigInteger newX = theory.pow(k, 2).subtract(alice.getX()).subtract(bob.getX());
		newX = newX.remainder(p).add(p).remainder(p);
		//double newY = ((k*(alice.getX() - newX) - alice.getY())%p+p)%p;
		BigInteger newY = k.multiply(alice.getX().subtract(newX)).subtract(alice.getY());
		newY = newY.remainder(p).add(p).remainder(p);
		ENode result = new ENode(newX, newY);
		return result;
	}
	/*
	 * 计算某点alice的阶
	 */
	public BigInteger nodeInitN(ENode alice) {
		BigInteger result = new BigInteger("0");
		if(alice.getN().compareTo(BigInteger.ONE)!=0) {
			//迭代自加直到得到零元
			ENode sum = new ENode(alice.getX(),alice.getY());
			result.add(BigInteger.ONE);
			while(true) {
				result = result.add(BigInteger.ONE);
				sum = nodeAdd(sum, alice);
				if(sum.getN()==BigInteger.ONE) break;
			}
		}
		alice.setN(result);
		System.out.println("re = "+result.toString());
		return result;
	}
	
	public ENode nodePow(ENode alice, BigInteger times) throws IllegalArgumentException{
		ENode result = new ENode(0);
		//先看看这个点的阶是不是已经被正确初始化了
		if(alice.getN().compareTo(BigInteger.ZERO)==0) {//等于0的话就是还没有初始化
			nodeInitN(alice);
		}
		if(alice.getN().compareTo(BigInteger.ONE)==0) {//等于1就是拿零元做乘法
			return new ENode(0);
		}else {//都不是 看来是已经初始化过的普通点
			//看看想要乘的次数是不是在这个点的阶之内
			if(times.compareTo(alice.getN())>0) {
				throw new IllegalArgumentException("指数 "+times.toString()+" 大于点"+alice.toString());
			}else if(times.compareTo(alice.getN())==0){//刚好是这个点的阶
				return new ENode(0);
			}
		}
		//先将次数转换成二进制
		char[] btimes = times.toString(2).toCharArray();
		ENode[] ary = new ENode[btimes.length];
		ary[btimes.length-1] = alice;//将最后一位设置为alice本身
		Boolean flag = true;
		//然后根据二进制数码执行加法
		for(int i = 0;i<btimes.length;i++) {
			if(btimes[i]=='1') {
				if(flag==true) {//第一次发现1时，计算所有数位的点值
					flag=false;
					for(int j = btimes.length -2;j>=0;j--) {//从倒数第二位开始直到第0位
						ary[j] = nodeAdd(ary[j+1],ary[j+1]);
						System.out.println("ary: "+ary[j].toString());
					}
				}
				result = nodeAdd(result,ary[i]);
			}
		}
				return result;
	}
	/*
	 * Fermat's little theorem
	 * so cool
	 */
	private static BigInteger fermat(BigInteger x,BigInteger MOD){
	    BigInteger ans=BigInteger.ONE;
	    for(int i=1;i<=Integer.valueOf(MOD.subtract(new BigInteger("2")).toString());i++) {
	    	ans = ans.multiply(x).remainder(MOD);
	    }
	    return ans;
	}
	
	public static Boolean isEqual(ENode alice, ENode bob) throws IllegalArgumentException{
		if(alice.getN().compareTo(BigInteger.ONE)==0) {
			throw new IllegalArgumentException("第一个参数"+alice.toString()+"是零元，不合法");
		}
		if(bob.getN().compareTo(BigInteger.ONE)==0) {
			throw new IllegalArgumentException("第二个参数"+bob.toString()+"是零元，不合法");
		}
		return (alice.getX().compareTo(bob.getX())==0&&alice.getY().compareTo(bob.getY())==0);
	}
	
	public ENode[] nodeCal(BigInteger x) {
		System.out.println("开始对x等于"+x.toString()+"求解");
		BigInteger p = this.p.getValue();
		x = x.add(p).remainder(p);//首先对x进行模p
		BigInteger sum = theory.pow(x, 3).add(x.multiply(a)).add(b);//y^2 = x^3 + ax + b
		sum = sum.add(p).remainder(p);
		System.out.println("sum ="+sum.toString());
		ArrayList<BigInteger> ans = new ArrayList<BigInteger>();//解集,通常有两个解
		/*
		for(BigInteger i = BigInteger.ZERO;i.compareTo(this.p.getValue())<0;i=i.add(BigInteger.ONE)) {
			if(theory.pow(i,2).remainder(p).compareTo(sum)==0) {
				ans.add(i);
				System.out.println("找到了解："+i.toString());
			}
		}
		*/
		
		if(ans.size()==0) {//没有找到解
			System.out.println("对x等于"+x.toString()+"求解没有找到解");
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
	/*
	 * 需要优化！
	 */
	public ArrayList<ENode> getAllENodes(){
		ArrayList<ENode> result = new ArrayList<ENode>();
		for(BigInteger i = BigInteger.ZERO;i.compareTo(this.p.getValue())<0;i=i.add(BigInteger.ONE)) {
			ENode[] res = this.nodeCal(i);
			if(res!=null) {
				for(ENode e:res) {
					System.out.println(e.toString());
					result.add(e);
				}
			}
		}
		return result;
	}
	public static final void main(String args[]) {
		Ellipse a = new Ellipse(new Prime(23),-3,1);
		Ellipse b = new Ellipse();
		ENode alice = new ENode(3,10);
		BigInteger i = new BigInteger("0");
		while(true) {
			System.out.println(i.toString());
			i = i.add(BigInteger.ONE);
		}
		/*ArrayList<ENode> r =a.getAllENodes();
		System.out.println(r);
	
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