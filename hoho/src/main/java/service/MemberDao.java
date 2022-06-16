package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbcon.dbconn;
import domain.MemberVo;

public class MemberDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MemberDao() {
		dbconn db = new dbconn();
		this.conn = db.getConnection();		
	}	
	
	public int insertMember(String memberId,String memberPwd,String memberName,String memberPhone, String memberEmail,String ip){
		int value=0;	
		
		String sql = "insert into b_member(MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERPHONE,MEMBEREMAIL,MEMBERIP) " 
				+"values(?,?,?,?,?,?)";
		// ������ �����ϰ� ���ϰ����� ����Ǿ����� 1 �ƴϸ� 0�� value ������ ��´�
		
		//���ᰴü�� ���ؼ� Statement ������ü�� �����ؼ� stmt�� ��´�
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			pstmt.setString(3, memberName);			
			pstmt.setString(4, memberPhone);
			pstmt.setString(5, memberEmail);
			pstmt.setString(6,ip);
			value = pstmt.executeUpdate();			
	
		}catch(Exception e){
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


	public ArrayList<MemberVo> memberSelectAll(){
		//MemberVo ���� ��ü�� ��� ArrayList Ŭ������ ��ü�����Ѵ� 
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		
		ResultSet rs = null;
		//���������� ���ڿ��� �������´�
		String sql="select * from b_member where delyn='N' order by midx desc";
			
		try{
			//���ᰴü�� �ִ� prepareStatement �޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����
			pstmt =  conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
		
			while(rs.next()){
				//�ݺ��Ҷ����� ��ü�����Ѵ�
				MemberVo mv  =new MemberVo();
				// rs�� ��Ƴ��� �÷������� mv�� �Űܴ�´�
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberId"));
				mv.setMemeberpwd(rs.getString("memberPwd"));
				mv.setMemberphone(rs.getString("memberphone"));
				mv.setMembername(rs.getString("memberName"));
				mv.setMemberemail(rs.getString("memberEmail"));
				mv.setWriteday(rs.getString("writeday"));
				//alist�� ���� ���� ��ü�� �߰��Ѵ�
				alist.add(mv);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
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
	
	
	public MemberVo memberLogin(String memberId, String memberPwd) {
		MemberVo mv = null;
		ResultSet rs = null;
		String sql="select * from b_member where delyn='N' and memberId=? and memberPwd=? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mv = new MemberVo();
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberId"));
				mv.setMembername(rs.getString("memberName"));				
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
		return mv;
	}
	
	

}
