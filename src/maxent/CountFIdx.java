package maxent;
/**
 * 
 */


/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class CountFIdx {
	protected int count = 0;
	protected int fidx = -1;

	/**
	 * Class constructor.
	 * 
	 * @param count
	 *            the count of feature
	 * @param fidx
	 *            the feature's index
	 */
	public CountFIdx(int count, int fidx) {
		this.count = count;
		this.fidx = fidx;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "CountFIdx (count = " + Integer.toString(count) + ", fidx = " + Integer.toString(fidx) + ")";
		return str;
		
	}
}
