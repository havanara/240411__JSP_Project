package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 로그 객체 생성
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	// jsp에서 받은 요청을 처리, 처리 결과를 다른 jsp로 보내는 역할을 하는 객체
	private RequestDispatcher rdp;
	private String destPage; //목적지 주소를 저장하는 변수
	private int isOK; //DB구문 체크값 저장 변수
	private BoardService bsv; //interface 생성
       
    public BoardController() {
    	// 생성자
    	bsv = new BoardServiceImpl(); //class 생성 bsv를 구현할 객체
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 실제 처리 메서드
		log.info("잘 들어오는지 테스트...");
		System.out.println("sysout으로 찍은 로그 테스트...");
		
		//매개변수 객체의 인코딩 설정
		request.setCharacterEncoding("utf-8"); //request 요청객체 인코딩
		response.setCharacterEncoding("utf-8"); //response 응답객체 인코딩
		
		//응답객체 -> 화면을 만들어서 응답 -> jsp 형식으로
		//응답 객체가 html 객체로 전송
		response.setContentType("text/html; charset=UTF-8"); //이 컨텐츠 타입으로 response 객체를 만들어서 보내줘!
		
		String uri = request.getRequestURI(); //jsp에서 오는 요청 주소를 받는 객체
		//info에 String만 찍을 수 있음!
		log.info(uri); // /brd/register
		
		String path = uri.substring(uri.lastIndexOf("/")+1); //register 
		log.info(path);
		
		//destPage = 목적지 주소를 저장하는 변수
		switch(path) {
		case "register" : 
			destPage = "/board/register.jsp";
			break;
		case "insert" :
			try {
				//jsp 화면에서 보내온 파라미터를 저장
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");
				
				BoardVO bvo = new BoardVO(title,writer,content);
				log.info("insert 객체 >>>>> {}", bvo);
				int isOK = bsv.register(bvo);
				log.info("insert "+(isOK>0? "성공":"실패 -> " )+ isOK);
				
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list" :
			try {
				// index에서 list 버튼을 클릭하면
				// 컨트롤러에서 DB의 전체 리스트를 요청
				// 전체 리스트를 list.jsp로 가져가서 뿌리기
				List<BoardVO> list = bsv.getList();
				log.info("list >>>>> {}", list);
				request.setAttribute("list", list);
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			// int가 아닌 String이기 때문에 형변환 필요
		case "detail" : case "modify" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				BoardVO bvo = bsv.getDetail(bno);
				log.info("detail bvo >>>>> {}", bvo);
				request.setAttribute("bvo", bvo);
				destPage = "/board/"+path+".jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
//		case "modify" :
//			try {
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				BoardVO bvo = bsv.getDetail(bno);
//				log.info("detail bvo >>>>> {}", bvo);
//				request.setAttribute("bvo", bvo);
//				destPage = "/board/modify.jsp";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			break;
			
		case "update" : 
			try {
				//update : bno, title, content
				int bno = Integer.parseInt(request.getParameter("bno"));
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				BoardVO bvo = new BoardVO(bno, title, content);
				
				int isOK = bsv.update(bvo);
				
				log.info("update "+(isOK>0? "성공":"실패 -> " )+ isOK);
				
				destPage = "list"; //내부 list 케이스를 한번 타고 실행
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				int isOK = bsv.getRemove(bno);
				log.info("remove bvo >>>>> {}", isOK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
		// switch가 끝나고 난 후
		// 목적지 주소(destPage)로 데이터를 전달(RequestDispatcher)
		rdp = request.getRequestDispatcher(destPage);
		
		// 요청에 필요한 객체를 가지고 destPage에 적힌 경로로 이동
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// service 메서드 호출하여 처리
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// service 메서드 호출하여 처리
		service(request, response);
	}

}
