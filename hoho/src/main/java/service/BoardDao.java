package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbcon.dbconn;
import domain.BoardVo;
import domain.SearchCriteria;


public class BoardDao {
	//연결
	private Connection conn;
	//실행
	private PreparedStatement pstmt;
	
	
	public BoardDao() {
		dbconn db = new dbconn();
		db.getConnection();
		this.conn = db.getConnection();		
		
	}


public int insertBoard(String subject,String content,String writer,String ip,  String fileName, int midx) {
	int value= 0;
	
	String sql="INSERT INTO b_board(midx,originbidx,depth,level_,subject,content,writer,ip,fileName)"
			+ " select max(bidx)+1,0,0,?,?,?,?,?,? from b_board"; //실행순서
	try {				//실행(실행시킬것) ex)prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subject);
		pstmt.setString(2, content);
		pstmt.setString(3, writer);
		pstmt.setString(4, ip);
		pstmt.setString(5, fileName);
		pstmt.setInt(6, midx);
		value = pstmt.executeUpdate();
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}	
	}	
	
	return value;
	}

public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
	ArrayList<BoardVo> alist = new ArrayList<BoardVo>();
	
	ResultSet rs = null;
	
	String str = "";
		if(scri.getSearchType().equals("subject")) {
			str="and subject like ?";
		}else {
			str="and writer like ?";
			
		}
	
	//	"SELECT * FROM BOARD WHERE delyn='N' ORDER BY originbidx DESC,depth asc";(기존 로직)
	String mysql =  "select * from b_board where delyn= 'N' "+str+" order by originbidx desc, depth ASC limit ?,?";			
	
	
	try {
		pstmt = conn.prepareStatement(mysql);
		pstmt.setString(1, "%"+scri.getKeyword()+"%");
		pstmt.setInt(2, (scri.getPage()-1)*15+1);
		pstmt.setInt(3, scri.getPage()*15);
		
		rs = pstmt.executeQuery();

		while(rs.next()) {								//반복문	//~~동안 ex)값이 진행하는 동안
			BoardVo bv = new BoardVo();
				
			bv.setBIDX(rs.getInt("bidx"));
			bv.setSUBJECT(rs.getString("subject"));
			bv.setWRITER(rs.getString("writer"));
			bv.setWRITEDAY(rs.getString("writeday"));
			bv.setLEVEL_(rs.getInt("level_"));
			bv.setFilename(rs.getString("filename"));
			alist.add(bv);
			
		}		
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}				
	}
	
	return alist;
}
public BoardVo boardSelectOne(int bidx) {
	BoardVo bv = null;
	ResultSet rs= null;
	String sql="select * from b_board where bidx=?";
	
	try {
		pstmt = conn.prepareStatement(sql);   
		pstmt.setInt(1, bidx);
					
		rs  = pstmt.executeQuery(); //결과값확인
					
		if(rs.next()) {  
			bv = new BoardVo();
			
			bv.setBIDX(rs.getInt("bidx"));   
			bv.setORIGINBIDX(rs.getInt("originbidx"));
			bv.setDEPTH(rs.getInt("depth"));
			bv.setLEVEL_(rs.getInt("level_"));
			bv.setFilename(rs.getString("filename"));
			bv.setSUBJECT(rs.getString("subject"));
			bv.setCONTENT(rs.getString("content"));
			bv.setWRITER(rs.getString("writer"));
			bv.setWRITEDAY(rs.getString("writeday"));				
		}			
		
	} catch (SQLException e) {			
		e.printStackTrace();
	} finally {
		
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		
	}	
	
	return bv;
}

public int updateBoard(String subject,String content,String writer,String ip,int midx,int bidx) {
	int value=0;
	
	String sql="update b_board set subject=?, content=?, writer=?, ip=?, midx=?, writeday=now() where bidx=?";
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subject);
		pstmt.setString(2, content);
		pstmt.setString(3, writer);
		pstmt.setString(4, ip);
		pstmt.setInt(5, midx);
		pstmt.setInt(6, bidx);
		value= pstmt.executeUpdate();
		
	} catch (SQLException e) {			
		e.printStackTrace();
	}finally {
		
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}			
	}		
	
	return value;
}

public int deleteBoard(int bidx) {
	int value=0;
	
	String sql="update b_board set delyn='Y', writeday=now() where bidx=?";
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bidx);
		value= pstmt.executeUpdate();
		
	} catch (SQLException e) {			
		e.printStackTrace();
	}finally {
		
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}			
	}		
	
	return value;
}

public int replyBoard(BoardVo bv) {
	int value= 0;
	
	String sql1= "update b_board set depth = depth+1 where originbidx=? and depth >?";
	
	String sql2="insert into b_board(originbidx,depth,level_,subject,content,writer,ip,midx)"
			+ " VALUES(?,?,?,?,?,?,?,?)";
	try {
		conn.setAutoCommit(false); //�ڵ�Ŀ�� ��� ��
		pstmt = conn.prepareStatement(sql1);
		pstmt.setInt(1, bv.getORIGINBIDX());
		pstmt.setInt(2, bv.getDEPTH());
		pstmt.executeUpdate();
		
		pstmt = conn.prepareStatement(sql2);
		pstmt.setInt(1, bv.getORIGINBIDX());
		pstmt.setInt(2, bv.getDEPTH()+1);
		pstmt.setInt(3, bv.getLEVEL_()+1);
		pstmt.setString(4, bv.getSUBJECT());
		pstmt.setString(5, bv.getCONTENT());
		pstmt.setString(6, bv.getWRITER());
		pstmt.setString(7, bv.getIP());
		pstmt.setInt(8, bv.getMIDX());
		value = pstmt.executeUpdate();	
		
		conn.commit();
		
	} catch (SQLException e) {
		try {
			conn.rollback();
		} catch (SQLException e1) {				
			e1.printStackTrace();
		}
		e.printStackTrace();
	} finally {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}	
	}	
	
	return value;
}

public int boardTotal(SearchCriteria scri) {
	int cnt= 0; //변환값
	ResultSet rs = null; //쿼리 실행값
	String str = ""; //검색 키워드, 서치 값
	if (scri.getSearchType().equals("subject")) {
		str = "and subject like ?";			
	}else {			
		str = "and writer like ?";	
	}		
	
	String sql="select count(*) as cnt from b_board where delyn='N'  "+str+" ";
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%"+scri.getKeyword()+"%");
		rs = pstmt.executeQuery();
		// rs.next --> rs가 실행됐을때 = rs = 1일때
		if (rs.next()) {
			cnt = rs.getInt("cnt");				
		}			
	} catch (SQLException e) {			
		e.printStackTrace();
	}	
	
	return cnt; //값을 받는다 이걸 = rs.getInt("cnt");
}

}


