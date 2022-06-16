package domain;

public class Criteria {
	
		private int page;           //페이지
		private int perPageNum;     //페이지번호출력
	
	
		public Criteria() {
			this.page = 1;				//한 페이지
			this.perPageNum = 15;		//15개의 글을 보여준다
		}


		public int getPage() {
			return page;
		}


		public void setPage(int page) {				//setPage 페이지 고정
			
			if (page<=1) {
				this.page = 1;
				return;
			}
			
			this.page = page;
		}


		public int getPerPageNum() {
			return perPageNum;
		}


		public void setPerPageNum(int perPageNum) {
			this.perPageNum = perPageNum;
		}		
	

}
