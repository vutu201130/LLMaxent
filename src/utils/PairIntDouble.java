/**
 * 
 */

package utils;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class PairIntDouble implements Comparable<Object> {
	public Integer first;
	public Double second;

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (int)
	 * @param s
	 *            the second element (double)
	 */
	public PairIntDouble(int f, double s) {
		this.first = f;
		this.second = s;
	}

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (Integer)
	 * @param s
	 *            the second element (Double)
	 */
	public PairIntDouble(Integer f, Double s) {
		this.first = f;
		this.second = s;
	}

	@Override
	public int compareTo(Object o) {
		double val1 = second.doubleValue();
		double val2 = ((PairIntDouble) o).second.doubleValue();

		if (val1 > val2) {
			return 1;
		} else if (val1 == val2) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Display pair.
	 */
	public void print() {
		System.out.println(this.first + "\t" + this.second);
	}
}
