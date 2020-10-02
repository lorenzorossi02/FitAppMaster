package com.fitapp.logic.view;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.CalendarBean;
import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.controller.GymPageController;
import com.fitapp.logic.model.CalendarGymModel;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

/**
 * Servlet implementation class GymPageServlet
 */
@WebServlet("/GymPageServlet")
public class GymPageServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GymPageServlet.class.getName());

	private static final long serialVersionUID = 1L;
	private ManagerUserBean managerUserBean;
	private GymPageController gymPageController;

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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
		managerUserBean = (ManagerUserBean) request.getSession().getAttribute("ManagerUserBean");
		request.setAttribute("managerUserName", managerUserBean.getManagerName().get());
		request.setAttribute("managerGymName", managerUserBean.getGym().getGymName().get());
		request.setAttribute("managerGymStreet", managerUserBean.getGym().getStreet().get());
		gymPageController = (GymPageController) request.getSession().getAttribute("GymPageController");
		gymPageController.initializeTrainers();
		List<Trainer> listGymTrainers = managerUserBean.getManagerTrainerList();
		request.setAttribute("managerTrainerList", listGymTrainers);
		CalendarBean calendarBean = new CalendarBean();
		CalendarGymModel calendarGymModel = new CalendarGymModel(calendarBean);

		gymPageController.setModel(calendarGymModel, managerUserBean.getManagerId().get(), true,
				managerUserBean.getGym());
		request.setAttribute("avaiableSessions", gymPageController.getAvaiableSession());
		RequestDispatcher dis = getServletContext().getRequestDispatcher("/GymPage.jsp");
		dis.forward(request, response);
		}catch(ServletException| IOException ex) {
			LOGGER.log(Level.SEVERE,"exception.",ex);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
		String deleteSession = request.getParameter("deleteSession");
		if (request.getParameter("manageTrainerBtn") != null) {
			request.getSession().setAttribute("ManagerUserBean", managerUserBean);
			request.getSession().setAttribute("GymPageController", gymPageController);
			String nextPage = "/ManageTrainerServlet";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);

		} else if (request.getParameter("viewReviewBtn") != null) {
			/*
			 * 
			 * New feature to be implemented.
			 * 
			 */
		} else if (request.getParameter("setNewSession") != null) {
			if (request.getParameter("timeStart") != null && request.getParameter("timeEnd") != null
					&& request.getParameter("date") != null && request.getParameter("selectCourse") != null
					&& request.getParameter("selectTrainer") != null && request.getParameter("individualValue") != null
					&&

					gymPageController.addNewSession(request.getParameter("timeStart"), request.getParameter("timeEnd"),
							request.getParameter("date"), request.getParameter("selectCourse"),
							request.getParameter("selectTrainer"), request.getParameter("individualValue"),
							request.getParameter("description"))) {
				doGet(request, response);

			}

			response.sendRedirect("GymPageServlet");

		} else if (deleteSession != null) {
			String sessionToRemoveString = deleteSession;
			int sessionIndex = Integer.parseInt(sessionToRemoveString.replace("deleteSession", "").trim());

			List<Session> avaiabSessions = gymPageController.getAvaiableSession();
			Session sessionToRemove = avaiabSessions.get(sessionIndex);
			if (gymPageController.sessionBooked(sessionToRemove)) {
				response.sendRedirect("GymPageServlet");
			} else {
				gymPageController.removeSession(sessionToRemove);
				doGet(request, response);

			}
		}
		}catch(ServletException|IOException|NumberFormatException e) {
			LOGGER.log(Level.SEVERE,"exception.",e);
		}
	}

}
