package util;



public class SimplePage implements Paginable {
	public static final int DEF_COUNT = 20;

	/**
	 * 妫�煡椤电爜 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public SimplePage() {
	}

	/**
	 * 鏋勯�鍣�
	 * 
	 * @param pageNo
	 *            椤电爜
	 * @param pageSize
	 *            姣忛〉鍑犳潯鏁版嵁
	 * @param totalCount
	 *            鎬诲叡鍑犳潯鏁版嵁
	 */
	public SimplePage(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	/**
	 * 璋冩暣椤电爜锛屼娇涓嶈秴杩囨渶澶ч〉鏁�
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	/**
	 * 鑾峰緱椤电爜
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 姣忛〉鍑犳潯鏁版嵁
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 鎬诲叡鍑犳潯鏁版嵁
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 鎬诲叡鍑犻〉
	 */
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	/**
	 * 鏄惁绗竴椤�
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * 鏄惁鏈�悗涓�〉
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * 涓嬩竴椤甸〉鐮�
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 涓婁竴椤甸〉鐮�
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;

	/**
	 * if totalCount<0 then totalCount=0
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1
	 * 
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
	
}
