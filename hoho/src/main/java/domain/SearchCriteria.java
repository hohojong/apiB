package domain;

public class SearchCriteria extends Criteria{
	
	private String searchType;
	private String keyword;
	
	public SearchCriteria() {
		this.searchType = "";				//공백
		this.keyword = "";					//공백
	}
	
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
		

	

}
