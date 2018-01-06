package blog.utils.pagination;

/**
 * 分页类
 * @author SolitaryEagle
 *
 */
public class Pagination {
	private Long firstPage = 1L;	//首页
	private Long lastPage;			//尾页
	private Long beginPage;			//开始页
	private Long endPage;			//结束页
	private Long curPage;			//当前页
	private Long countRecord;		//总记录数
	private Long queryBegin;			//查找记录的起始值
	private Long queryLength = 10L;	//应该查询的记录数

	public Long getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Long firstPage) {
		this.firstPage = firstPage;
	}

	public Long getLastPage() {
		return lastPage;
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}

	public Long getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(Long beginPage) {
		this.beginPage = beginPage;
	}

	public Long getEndPage() {
		return endPage;
	}

	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}

	public Long getCurPage() {
		return curPage;
	}

	public void setCurPage(Long curPage) {
		this.curPage = curPage;
	}

	public Long getCountRecord() {
		return countRecord;
	}

	public void setCountRecord(Long countRecord) {
		this.countRecord = countRecord;
	}

	public Long getQueryBegin() {
		return queryBegin;
	}

	public void setQueryBegin(Long queryBegin) {
		this.queryBegin = queryBegin;
	}

	public Long getQueryLength() {
		return queryLength;
	}

	@Override
	public String toString() {
		return "Pagination [firstPage=" + firstPage + ", lastPage=" + lastPage
				+ ", beginPage=" + beginPage + ", endPage=" + endPage + ", curPage="
				+ curPage + ", countRecord=" + countRecord + ", queryBegin=" + queryBegin
				+ ", queryLength=" + queryLength + "]";
	}

	public void setQueryLength(Long queryLength) {
		this.queryLength = queryLength;
	}

	public Pagination(Long firstPage, Long lastPage, Long beginPage, Long endPage,
			Long curPage, Long countRecord, Long queryBegin, Long queryLength) {
		super();
		this.firstPage = firstPage;
		this.lastPage = lastPage;
		this.beginPage = beginPage;
		this.endPage = endPage;
		this.curPage = curPage;
		this.countRecord = countRecord;
		this.queryBegin = queryBegin;
		this.queryLength = queryLength;
	}

	public Pagination() {
		super();
	}

	public Pagination(long countRecord, long curPage) {
		this.countRecord = countRecord;
		this.curPage = curPage;
		this.lastPage =
				countRecord == 0 ? 1 : countRecord / 10 + (countRecord % 10 == 0 ? 0 : 1);

		if (curPage <= 3) {
			this.beginPage = 1L;
			this.endPage = lastPage <= 5 ? lastPage : beginPage + 4;
		}
		else if (lastPage - curPage <= 2) {
			this.beginPage = lastPage <= 5 ? 1 : lastPage - 4;
			this.endPage = lastPage;
		}
		else {
			this.beginPage = curPage - 2;
			this.endPage = curPage + 2;
		}
		if (curPage == lastPage || countRecord == 0) {
			this.queryLength = (countRecord - 1) % 10 + 1;
		}
		this.queryBegin = (curPage - 1) * 10;
	}

}
