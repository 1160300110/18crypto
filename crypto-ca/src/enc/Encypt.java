
package enc;

import ellipse.ENode;

/**
 * 这个接口的目的，是定义将明文内容映射到椭圆曲线上的点的方式。
 * @author dzj
 *
 */
public interface Encypt<N> {
	
	
	ENode Enc(String plain, int k, ENode G, ENode K);
	
}
