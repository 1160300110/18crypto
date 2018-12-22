package ellipse;

import java.math.BigInteger;
public final class ENode {
	private BigInteger x;
	private BigInteger y;
	private BigInteger n;
	public int buffer = 0;
	public ENode() {
		
	}
	public ENode(BigInteger a, BigInteger b) {
		this.x = a;
		this.y = b;
		this.n = BigInteger.ZERO;
	}
	public ENode(int a, int b) {
		this.x = BigInteger.valueOf(a);
		this.y = BigInteger.valueOf(b);
		this.n = BigInteger.ZERO;
	}
	public ENode(int a) {
		if(a==0) {
			this.n=BigInteger.ONE;
		}
	}
	public BigInteger getX() {
		return this.x;
	}
	
	public BigInteger getY() {
		return this.y;
	}
	public BigInteger getN() {
		return this.n;
	}
	public void setN(BigInteger value) {
		this.n = value;
	}
	public ENode reverse() {
		return new ENode(this.getX(), this.getY().negate());
	}
	@Override
	public String toString() {
		if(this.x==null)return "零元";
		return "("+this.getX().toString()+", "+this.getY().toString()+", "+this.getN().toString()+")";
	}
}
