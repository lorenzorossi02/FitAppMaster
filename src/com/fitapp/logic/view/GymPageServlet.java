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
import com.fitapp.logic.model.ManagerUserModel;
import com.fitapp.logic.model.entity.Session;
import com.fitapp.logic.model.entity.Trainer;

/**
 * Servlet implementation class GymPageServlet
 */
@WebServlet("/GymPageServlet")
public class GymPageServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(GymPageServlet.class.getName());

	private static final long serialVersionUID = 1L;
	private static final ManagerUserBean  managerUserBean = new ManagerUserBean();
	private static final  ManagerUserModel managerUserModel = new ManagerUserModel(managerUserBean);
	private static final GymPageController gymPageController = new GymPageController(managerUserModel);

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
			
			managerUserModel.setManagerId((Integer) request.getSession().getAttribute("userId"));
			
			gymPageController.setGym((Integer) request.getSession().getAttribute("gymId"));
			String managerUsername = (String) request.getSession().getAttribute("username");
			String managerGymName = String.valueOf(managerUserModel.getGym().getGymName().get());
			String managerGymStreet = managerUserModel.getGym().getStreet().get();
			managerUserModel.setManagerName(managerUsername);

			request.setAttribute("managerUserName", managerUsername);
			request.setAttribute("managerGymName", managerGymName);
			request.setAttribute("managerGymStreet", managerGymStreet);
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
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);

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
				request.getSession().setAttribute("managerId", managerUserBean.getManagerId().get());
				request.getSession().setAttribute("managerUserName", managerUserBean.getManagerName().get());
				request.getSession().setAttribute("managerGymName", managerUserBean.getGym().getGymName().get());
				request.getSession().setAttribute("managerGymStreet", managerUserBean.getGym().getStreet().get());

				response.sendRedirect("ManageTrainerServlet");

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
		}catch(IOException|NumberFormatException ex) {
			LOGGER.log(Level.SEVERE,ex, ()->"Exception:"+ ex);
		}
	}

}
