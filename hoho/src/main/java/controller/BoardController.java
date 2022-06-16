package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.BoardVo;
import domain.PageMaker;
import domain.SearchCriteria;
import service.BoardDao;


@WebServlet("/BoardController") 
public class BoardController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		
		String hoho = request.getContextPath();	
		
		String ho = uri.substring(hoho.length());
		
		
		
		request.setCharacterEncoding("utf-8");
		
		//파일 업로드하기
		
		int sizeLimit = 1024*1024*15; //파일 사이즈 크기 지정
		
		String uploadPath = "D:\\DEV\\hoho\\src\\main\\webapp\\";							//파일 업로드시 저장할 위치
				
		String saveFolder = "image";										//위치에 있는 폴더이름
		
		String saveFullPath = uploadPath + saveFolder; //위치 + 폴더이름
		
		if(ho.equals("/board/boardWrite.do")) {	//boardWrite시작점
			
			System.out.println("작성페이지");
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			System.out.println("Rd="+rd);
			rd.forward(request, response);
		
		}else if (ho.equals("/board/boardWriteAction.do")) {
			
			
			MultipartRequest multipartRequest = null;		//mutipartRequest = request  같은 역할인데 파일 업로드를 위해서 필요한것
			
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"UTF-8",new DefaultFileRenamePolicy());
			
			//열거자에 저장될 파일을 담는 객체
			//Enumeration ==> 데이터를 한번에 출력하게 도와줌
			
			//폼에서 전송된 파라미터 타입의 file 이 아닌 이름 들을 Enumeration=>리턴 ?
			
			String subject = multipartRequest.getParameter("subject");
			String content = multipartRequest.getParameter("content");
			String writer = multipartRequest.getParameter("writer");
			
			
			Enumeration files = multipartRequest.getFileNames();
			
			//실행순서
			//Enumeration =>
			//hasMoreElements() = > Enumneration 내용이 있는지 확인
			//nextElement(); => 내용 값을 가져옴
			
			
			String file = (String)files.nextElement();
			
			// 글쓰기 페이지에 업로드한 파일이 서버에 업로드된 파일의 이름을 가져온다. 서버에서 인식한 이름
			
			
			String fileName = multipartRequest.getFilesystemName(file);

			//클라이언트가 업로드한 파일의 원본이름을 반환한다. 내가올린 이름
			
			String originalFileName = multipartRequest.getOriginalFileName(file);
			
			
			//System.out.println(subject+";"+content+";"+writer);
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			int midx = 2;
//			HttpSession session = request.getSession();
//			int midx = (int)session.getAttribute("midx");
			
			
			BoardDao bd = new BoardDao();
			int value = bd.insertBoard(subject, content, writer, ip, fileName, midx);
			
			if (value==1) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");				
			}else {
				response.sendRedirect(request.getContextPath()+"/board/boardWrite.do");
			}
			
		}else if (ho.equals("/board/boardList.do")) {	//boardList시작점
				
			//게시판 글쓰기	
			String page =request.getParameter("page");		//글쓰기 게시판 페이지!
			if(page==null)page="1";							//page가 0일때 값이 없을때 1로 
			int pagex = Integer.parseInt(page);				//page는 문자열을 숫자열로 바꿔줌
			
			
			String keyword=request.getParameter("keyword");
			if(keyword==null)keyword ="";					//""; 빈값으로 놔두겠다.
			
			String searchType = request.getParameter("searchType");
			if(searchType==null)searchType = "";
			
			SearchCriteria scri = new SearchCriteria();			//scri에 있는 pagex, keyword, searchtype을 사용하겠다.
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			scri.setSearchType(searchType);
			
			
			BoardDao bd = new BoardDao();  // BoardDao로직 실행하는 연결책
			int cnt =bd.boardTotal(scri);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			
			ArrayList<BoardVo> alist  = bd.boardSelectAll(scri);
			//setAttribute:저장소 ("aa",aa) "aa"를 선택하면 aa 결과값이 나온다
			
			request.setAttribute("alist", alist);
			request.setAttribute("pm", pm);	
			System.out.println("pm="+pm);
				System.out.println("alist="+alist);
				
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");	//board/boardList시작점
			rd.forward(request, response);			
		
				
		
		}else if (ho.equals("/board/boardContent.do")) {	//board/boardContent시작점
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			
			BoardDao bd =new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			request.setAttribute("bv", bv);		
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
		
	}else if (ho.equals("/board/boardModify.do")) {
			
			System.out.println("���� ������");
			
			String bidx = request.getParameter("bidx");
			int bidx_ = Integer.parseInt(bidx);
			
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidx_);
			
			request.setAttribute("bv", bv);			
			
						
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");			
			rd.forward(request, response);	
	
	
	
	}else if (ho.equals("/board/boardModifyAction.do")) {
		
		String subject = request.getParameter("subject");
		System.out.println("subject="+subject);
		String content = request.getParameter("content");
		System.out.println("content="+content);
		String writer = request.getParameter("writer");
		System.out.println("writer="+writer);
		String bidx = request.getParameter("bidx");
		System.out.println("bidx="+bidx);
		int bidx_ = Integer.parseInt(bidx);
		
		String ip = InetAddress.getLocalHost().getHostAddress();
//		HttpSession session = request.getSession();
//		int midx = (int)session.getAttribute("midx");
		int midx= 1;
		
		BoardDao bd = new BoardDao();
		int value = bd.updateBoard(subject, content, writer, ip, midx,bidx_);
		System.out.println(value);
		if (value ==1) {
			response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);				
		}else {
			response.sendRedirect(request.getContextPath()+"/board/boardModify.do?bidx="+bidx);				
		}	
		}else if (ho.equals("/board/boardDelete.do")) {
		String bidx = request.getParameter("bidx");
		int bidx_ = Integer.parseInt(bidx);
		
		
		
		request.setAttribute("bidx", bidx);
		
		System.out.println("bidx="+bidx);
		
		RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");					
		rd.forward(request, response);
	}else if (ho.equals("/board/boardDeleteAction.do")) {
		System.out.println("삭제");
		
		String bidx = request.getParameter("bidx");
		
		System.out.println("bidx="+bidx);
		int bidx_ = Integer.parseInt(bidx);
					
		BoardDao bd = new BoardDao();
		int value = bd.deleteBoard(bidx_);

		if (value ==1) {
			response.sendRedirect(request.getContextPath()+"/board/boardList.do");				
		}else {
			response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="
					+bidx);				
		}	
	}else if (ho.equals("/board/boardReply.do")) {
		
		String bidx = request.getParameter("bidx");
		String originbidx = request.getParameter("originbidx");
		String depth = request.getParameter("depth");
		String level_ = request.getParameter("level_");
		
		BoardVo bv = new BoardVo();
		bv.setBIDX(Integer.parseInt(bidx));
		bv.setORIGINBIDX(Integer.parseInt(originbidx));
		bv.setDEPTH(Integer.parseInt(depth));
		bv.setLEVEL_(Integer.parseInt(level_));
		
		request.setAttribute("bv", bv);			
		
		RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");					
		rd.forward(request, response);
	}else if (ho.equals("/board/boardReplyAction.do")) {
		
		String bidx = request.getParameter("bidx");
		String originbidx = request.getParameter("originbidx");
		String depth = request.getParameter("depth");
		String level_ = request.getParameter("level_");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		String ip = InetAddress.getLocalHost().getHostAddress();
		HttpSession session = request.getSession();
		//int midx = (int)session.getAttribute("midx");
		int midx = 6;
		
		BoardVo bv = new BoardVo();
		bv.setBIDX(Integer.parseInt(bidx));
		bv.setORIGINBIDX(Integer.parseInt(originbidx));
		bv.setDEPTH(Integer.parseInt(depth));
		bv.setLEVEL_(Integer.parseInt(level_)); 
		bv.setSUBJECT(subject);
		bv.setCONTENT(content);
		bv.setWRITER(writer);
		bv.setIP(ip);
		bv.setMIDX(midx);
		
		
		
		BoardDao bd = new BoardDao();
		int value = bd.replyBoard(bv);
		
		if (value ==1) {
			response.sendRedirect(request.getContextPath()+"/board/boardList.do");				
		}else {
			response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);				
		}			

	
	
	}else if (ho.equals("/board/fileDownload.do")) {		
		
		//boardWrite에 있는 파일 이름값을 가져온다
	
	String filename = request.getParameter("filename");
	
	System.out.println("filename="+filename);
	
	//파일 전체 경로를 뽑기
	// saveFullPATH +/+ filename ==> rkqtdla
	
	String filePath = saveFullPath+File.separator + filename;
	
	//파일 리턴 값 ==> ?
	Path source = Paths.get(filePath);
	
	//파일을 텍스트 형태로 전환해서 전달하기 위해 변환함..
	//probeContentType ==>
	String mimeType = Files.probeContentType(source);
	
	System.out.println("mimeType="+mimeType);
	
	//브라우저로 보낼때 파일로 보낼 수 없으니 마임타입으로 웹루트 저장소에 읽혀서 보내야 함
	response.setContentType(mimeType);
	
	//파일 업로드를 구현하고 파일을 다운하면 되는데
	//파일이 깨지는 현상이 발생함
	//크롬 : 파일명 특수문자 깨짐, 익스플로우 : 크롬 + 한글파일 다운 X
	//파이어폭스 : 파일 공백있으면 공백기준으로 뒤에 짤림...
	
	//폼에서 전송 된 파일이름을 가져와서 인코딩을 = UTF-8로 지정해줌..
	
	String encodingfileName = new String(filename.getBytes("UTF-8"));
	
	System.out.println("encondingfileName="+encodingfileName);
	
	//파일을 헤더 정보에 담는다
	//--> Content-Disposition ==> 다운로드 되거나 로컬에 저장되는 형식
	//Content-Disposition ==> inline(기본값, 웹페이지 안에서 또는 웹페이지로 나타남)
	//attachment => 반드시 다운로드 받아야 한다는 내용
	//filename => UTF-8로 인코딩 시키자.
	
	response.setHeader("Content-Disposition", "attachment; fileName="+encodingfileName);
	// 해당 위체에 있는파일을 읽어드린다 ==> filePath 위치
	FileInputStream fileInputStream = new FileInputStream(filePath);
	
	System.out.println("fileInputStream=" + fileInputStream);
	
	//서버에서 클라이언트 서버로 뿌려줘야함
	ServletOutputStream servletOutStream =response.getOutputStream();
	
	System.out.println("servletOutputStream="+servletOutStream);
	
	//바이트 자를때 많이 쓴다 크기를 어떻게 주냐에 늦게 올때도 있고 빠르게 올 수 도 있음.
	byte[] b = new byte[4096];
	
	int read = 0;
	
	while((read=fileInputStream.read(b,0,b.length))!=-1) {
		
		servletOutStream.write(b,0,read);
			
	}
		servletOutStream.flush();	//강제출력
		servletOutStream.close();	//닫아줌
		fileInputStream.close();	//외부자로 닫아줌.
		
	
	}
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
