package com.gn.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gn.user.service.UserService;
import com.gn.user.vo.User;

@WebServlet(name="userLoginEnd", urlPatterns="/user/loginEnd")
public class UserLoginEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserLoginEndServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 -> 비밀번호 확인(사용자가 입력한 비밀번호 == 회원가입 시 입력한 비밀번호)
		// 단, 회원가입 시 비밀번호를 암호화해 놓음(DB) -> 사용자가 입력한 비밀번호와 어떻게 비교?
		// 해결 방법: 사용자가 입력한 비밀번호를 암호화해서 암호화된 키끼리 비교!
		
		String id = request.getParameter("user_id");
		String pw = request.getParameter("user_pw");
		
//		User u = new UserService().loginUser(id, pw);
		User u = new User(1,"pepper15", "1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==", "후추");
//		User u = null;
		
		if(u != null) {
			HttpSession session = request.getSession(true);
			if(session.isNew() || session.getAttribute("user") == null){
				session.setAttribute("user", u);
				session.setMaxInactiveInterval(60*30);
			}
			response.sendRedirect("/");
		}else {
			RequestDispatcher view 
			= request.getRequestDispatcher("/views/user/login_fail.jsp");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
