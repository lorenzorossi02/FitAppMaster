package com.fitapp.logic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.CalendarBean;
import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

/**
 * Servlet implementation class GymPageServlet
 */
@WebServlet("/GymPageServlet")
public class GymPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GymPageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		ManagerUserBean managerUserBean = (ManagerUserBean) request.getSession().getAttribute("ManagerUserBean");
		request.setAttribute("managerUserName", managerUserBean.getManagerName().get());
		request.setAttribute("managerGymName", managerUserBean.getGym().getGymName().get());
		request.setAttribute("managerGymStreet", managerUserBean.getGym().getStreet().get());
		GymPageController gymPageController = (GymPageController) request.getSession()
				.getAttribute("GymPageController");
		gymPageController.initializeTrainers();
		List<Trainer> listGymTrainers = managerUserBean.getManagerTrainerList();
		request.setAttribute("managerTrainerList", listGymTrainers);
		CalendarBean calendarBean = new CalendarBean();
		CalendarGymModel calendarGymModel = new CalendarGymModel(calendarBean);

		gymPageController.setModel(calendarGymModel, managerUserBean.getManagerId().get(), true,
				managerUserBean.getGym());
		request.setAttribute("avaiableSessions", gymPageController.getAvaiableSession());
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/GymPage.jsp");
		dis.forward(request, response);
		return;


		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("manageTrainerBtn") != null) {
			ManagerUserBean managerUserBean = (ManagerUserBean) request.getSession().getAttribute("ManagerUserBean");
			request.getSession().setAttribute("ManagerUserBean", managerUserBean);
			request.getSession().setAttribute("GymPageController", (GymPageController) request.getSession()
					.getAttribute("GymPageController"));
			String nextPage = "/ManageTrainerServlet";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
			return;
			
		} else if (request.getParameter("viewReviewBtn") != null) {
			/*
			 * 
			 * New feature to be implemented.
			 * 
			 */
		} else if (request.getParameter("setNewSession") != null) {
			if(request.getParameter("timeStart")!=null && request.getParameter("timeEnd")!=null&&
					request.getParameter("date")!=null&& request.getParameter("selectCourse")!=null &&
					request.getParameter("selectTrainer")!=null && request.getParameter("individualValue")!=null) {
				GymPageController gymPageController = (GymPageController) request.getSession()
						.getAttribute("GymPageController");
				if(gymPageController.addNewSession(request.getParameter("timeStart"), request.getParameter("timeEnd"),
						request.getParameter("date"), request.getParameter("selectCourse"),
						request.getParameter("selectTrainer"), request.getParameter("individualValue"),request.getParameter("description"))) {
					doGet(request,response);

				}else {
					response.getWriter().println("<script type=\"text/javascript\">");
					response.getWriter().println("alert('failed to add new session');");
					 response.getWriter().println("location='GymPage.jsp';");
					 response.getWriter().println("</script>");
				}
			
				
			}else {
				response.getWriter().println("<script type=\"text/javascript\">");
				 response.getWriter().println("window.location.reload();");

				 response.getWriter().println("</script>");
			}
		} else if (request.getParameter("deleteSession") != null) {
			String sessionToRemoveString = request.getParameter("deleteSession");
			int sessionIndex = Integer.parseInt(sessionToRemoveString.replace("deleteSession", "").trim());
			GymPageController gymPageController = (GymPageController) request.getSession()
					.getAttribute("GymPageController");
			List<Session> avaiabSessions = gymPageController.getAvaiableSession();
			Session sessionToRemove = avaiabSessions.get(sessionIndex);
			if (gymPageController.sessionBooked(sessionToRemove)) {
				 response.getWriter().println("<script type=\"text/javascript\">");
				 response.getWriter().println("alert('Session already booked ');");
				 response.getWriter().println("location='GymPage.jsp';");
				 response.getWriter().println("</script>");
				 
			}
			else {
				gymPageController.removeSession(sessionToRemove);
				doGet(request,response);

			}	
		}

	}


}
