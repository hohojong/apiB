package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "/FrontController" , urlPatterns = "*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
 

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String uri = request.getRequestURI();
	//
	String hoho = request.getContextPath();	
	//command값
	String ho = uri.substring(hoho.length());
	
	// /를 기준으로 0을 잡는다 
	String[] subpath = ho.split("/");
	
	
	String location = subpath[1];
	
	System.out.println("들어옴");
	
	// location값과 memeber값이 같을때 	MemberController로 이동	
	if(location.equals("member")) {
		MemberController mc = new MemberController();
		mc.doGet(request,response);
	
		// location값과 board값이 같을때 	BoardController로 이동
		}else if(location.equals("board")) {
		BoardController bc = new BoardController();
		bc.doGet(request,response);
	
	
	
	}
}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
