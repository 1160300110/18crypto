#!/usr/bin/python
#coding=gbk
import math
def quick_algorithm(a,b,c):  #y=a^b%c,a��b�������c
    a = a % c
    ans = 1
    #�������ǲ���Ҫ����b<0����Ϊ����û��ȡģ����
    while b != 0:  #����С����
        if b & 1:
            ans = (ans * a) % c
        b>>=1
        a = (a * a) % c
    return ans

def IsHaveMoSqrt(x,P):#�Ƿ���ģƽ����y*y=x mod p����֪x��p���ж��Ƿ����y
     ret = quick_algorithm(x,(P-1)//2,P)
     if ret==1:
        return True
     else:
        return False
def GetMoSqrt(x,P):#��ģƽ����y*y=x mod p����֪x��p��y
    if(IsHaveMoSqrt(x,P)==1):
        t=0
        s=P-1#P-1=(2^t)*s //s������
        while s%2==0:
            s=s//2
            t=t+1
        if(t==1):
            ret = quick_algorithm(x,(s+1)//2,P)
            return (ret,P-ret)
        elif (t>=2):
            x_ = quick_algorithm(x,P-2,P)
            n = 1
            while(IsHaveMoSqrt(n,P)==1):
                n=n+1
            b=quick_algorithm(n,s,P)
            print(b)
            ret = quick_algorithm(x,(s+1)//2,P)#t-1
            t_=0
            while(t-1>0):
                if(quick_algorithm(x_ * ret*ret,2**(t-2),P)==1):
                    ret = ret
                else:
                    ret=ret*(b**(2**t_))%P
                t=t-1
                t_=t_+1
                print("t",t)
            return (ret, P-ret)
        else:
            return (-2,-2)
    else:
        return (-1,-1)
def Secp256k1GetYByX(x):#y^2=x^3+7 (mod p)����x��y
    a=(x*x*x+7)%0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F
    ret = GetMoSqrt(a,0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F)
    return ret

def mySqrt(x):
    a = (x * x * x + x + 1) % 23
    ret = GetMoSqrt(a, 23)
    return ret
if __name__ == "__main__":
    if True:
        nn = mySqrt(4)
        print(nn)
        x=0x79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798#˽ԿΪ1,��Ӧ�Ĺ�Կx
        ret = Secp256k1GetYByX(x)#secp256k1������x��y
        print("x=%x" % (x))
        print(ret)
        print("y=%x" % (ret[0]))
        print("y=%x" % (ret[1]))
        print("")
        x=1#x��Сֵ
        ret = Secp256k1GetYByX(x)#secp256k1������x��y
        print("x=%x" % (x))
        print("y=%x" % (ret[0]))
        print("y=%x" % (ret[1]))
        print("")
        x=0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F-3#x���ֵ
        ret = Secp256k1GetYByX(x)#secp256k1������x��y
        print("x=%x" % (x))
        print("y=%x" % (ret[0]))
        print("y=%x" % (ret[1]))
        print("")
    input()