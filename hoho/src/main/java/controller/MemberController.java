package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.MemberVo;
import service.MemberDao;




@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		System.out.println("command:"+command);
		
		if(command.equals("/member/memberJoinAction.do")) {
			
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			String memberName = request.getParameter("memberName");
			String memberEmail = request.getParameter("memberEmail");
			String memberPhone = request.getParameter("memberPhone");
			
			
			
			
			String ip = InetAddress.getLocalHost().getHostAddress();	
			
			MemberDao md = new MemberDao();
			int value = md.insertMember(memberId,memberPwd,memberName,memberPhone,memberEmail,ip);
			
		//	PrintWriter out = response.getWriter();
			//�Է��� �Ǿ�����
			if (value ==1){
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
				
			//	out.println("<script>alert('ȸ�����Լ���'); location.href='"+request.getContextPath()+"/'</script>");		
			}else{
				response.sendRedirect(request.getContextPath()+"/member/memberJoin.do");
			//	out.println("<script>alert('ȸ�����Խ���'); location.href='./memberJoin.html'</script>");
			}
			
		}else if (command.equals("/member/memberJoin.do")) {			
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
			rd.forward(request, response);
			
		}else if (command.equals("/member/memberList.do")) {
			
			MemberDao md = new MemberDao();	
			ArrayList<MemberVo> alist = md.memberSelectAll();
			
			request.setAttribute("alist", alist);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request, response);			
		}else if (command.equals("/member/memberLogin.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
			rd.forward(request, response);	
			
			
		}else if (command.equals("/member/memberLoginAction.do")) {
		
				//1. �Ѿ�°� 
				String memberId = request.getParameter("memberId");
				String memberPwd = request.getParameter("memberPwd");
				//2.ó��			
				MemberDao md = new MemberDao();
				MemberVo mv = md.memberLogin(memberId, memberPwd);
				System.out.println("mv"+mv);
				
				HttpSession session = request.getSession();
								
				//3.�̵�
				if (mv != null) {
					session.setAttribute("midx", mv.getMidx());
					session.setAttribute("memberId", mv.getMemberid());
					session.setAttribute("memberName", mv.getMembername());					
				
					if (session.getAttribute("saveUrl") != null) {
						response.sendRedirect((String)session.getAttribute("saveUrl"));	
					}else {
						response.sendRedirect(request.getContextPath()+"/member/memberList.do");
					}
				
				}else {
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
				}		
		}else if (command.equals("/member/memberLogout.do")) {
			
			HttpSession session = request.getSession();		
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/");
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
