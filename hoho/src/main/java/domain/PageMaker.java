package domain;

public class PageMaker {
	
	private int totalCount;    //전체 데이터 갯수
	private int startPage;     //첫 페이지
	private int endPage;       //마지막 페이지
	private boolean prev;      // 이전 페이지
	private boolean next;      // 다음페이지
	private int displayPageNum = 10;		//이전 페이지 다음페이지 사이의 몇개를 보여줄건지
	private SearchCriteria scri;
	
	
	public SearchCriteria getScri() {
		return scri;
	}
	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	
	
	public void calcData() {
		// 10 20 30
		endPage  = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum);
		
		startPage = (endPage-displayPageNum)+1;
		
		//tempEndPage -> Math.ceil : 반올림 시켜준다 마지막페이지 총게시글 288 / 한 게시글당 15개의 게시글 = 19.2 0.2반올림해서 20게의 페이지
		//tempEndPage 한 페이지당 15개씩 지정해놨는데 마지막페이지는 갯수가 상관없음 ex)13개의 게시글
		int tempEndPage =  (int)(Math.ceil(totalCount/(double)scri.getPerPageNum()));	
		
		if (endPage > tempEndPage) {			//ex) endPage 21> tempEndPage 20 일떄		 
			endPage = tempEndPage;				//ex) endPage 21 tempEndPage 20 일떄
		}										//endPage = tempEndPage -> 21 = 20 ->20으로 맞춰줌 21로 해봤자 내용이 없기때문 
		
		
		
		//이전페이지 ==> 1일때 거짓 아니면 진실
		prev = startPage ==1 ? false:true;
		
		
		//다음페이지
		//PerPageNum 한페이지에 보여줄 게시물 
		next = endPage*scri.getPerPageNum() >=totalCount ? false:true;
	}
	
	

}
