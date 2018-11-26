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
	
	public static BigInteger sqrt(BigInteger value) {
		String x = value.toString();
        int mlen = x.length();    //���������ĳ���
        int len;    //������ĳ���
        BigInteger beSqrtNum = new BigInteger(x);//��������
        BigInteger sqrtOfNum;    //�洢���������
        BigInteger sqrtOfNumMul;    //��������ƽ��
        String sString;//�洢sArrayת������ַ���
        if(mlen%2 == 0)    len = mlen/2;
        else    len = mlen/2+1;
        char[] sArray = new char[len];
        Arrays.fill(sArray, '0');//��������ʼ��Ϊ0
        for(int pos=0; pos<len; pos++){
         //����߿�ʼ�������飬ÿһλ��ת��Ϊ������ƽ����պò����ڱ��������ĳ̶�
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
	/*
	public static final void main(String args[]) {
		BigInteger two = new BigInteger("2");
		BigInteger result = new BigInteger("0");
		BigInteger re = new BigInteger("340282366881324382213159967663442624512");
		//result= result.add(pow(two,256)).subtract(pow(two,224)).add(pow(two,192)).add(pow(two,96)).subtract(BigInteger.ONE);
		re = pow(two,256);
		result = sqrt(re);
		re = pow(result,2);
		
		System.out.println(pow(two,256).toString());
		System.out.println(re.toString());
		
	}
	*/
}
