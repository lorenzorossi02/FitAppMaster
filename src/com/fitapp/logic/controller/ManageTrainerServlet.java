package com.fitapp.logic.controller;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.ManagerUserBean;
import com.fitapp.logic.model.entity.Course;
import com.fitapp.logic.model.entity.Trainer;



/**
 * Servlet implementation class ManageTrainerServlet
 */
@WebServlet("/ManageTrainerServlet")
public class ManageTrainerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GymPageController gymPageController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageTrainerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ManagerUserBean managerUserBean = (ManagerUserBean) request.getSession().getAttribute("ManagerUserBean");
		request.setAttribute("managerUserName", managerUserBean.getManagerName().get());
		request.setAttribute("managerGymName", managerUserBean.getGym().getGymName().get());
		request.setAttribute("managerGymStreet", managerUserBean.getGym().getStreet().get());
		gymPageController = (GymPageController) request.getSession()
				.getAttribute("GymPageController");
		gymPageController.initializeTrainers();
		List<Trainer> listGymTrainers = managerUserBean.getManagerTrainerList();
		request.setAttribute("managerTrainerList", listGymTrainers);
		request.getRequestDispatcher("/ManageTrainer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("addNewTrainer")!=null) {
			Map<Course, Boolean> course = new EnumMap<>(Course.class);
			course.put(Course.KICKBOXING, checkBox(request.getParameter("kickBoxing")));
			course.put(Course.PUGILATO, checkBox(request.getParameter("pugilato")));
			course.put(Course.ZUMBA, checkBox(request.getParameter("zumba")));
			course.put(Course.SALSA, checkBox(request.getParameter("salsa")));
			course.put(Course.FUNCTIONAL, checkBox(request.getParameter("funzionale")));
			course.put(Course.WALKING, checkBox(request.getParameter("walking")));
			course.put(Course.PUMP, checkBox(request.getParameter("pump")));

			
			Trainer newTrainer = new Trainer(request.getParameter("trainerName"),0 ,gymPageController.getGymId(),course);

			gymPageController.addTrainer(newTrainer);
			doGet(request,response);

			return;
		}else if(request.getParameter("deleteTrainer")!=null) {
			System.out.println("\n\n\n\n\n DELETE TRAINER");
			String trainerIdString = request.getParameter("deleteTrainer");
			

		
			
			int trainerId = Integer.parseInt(trainerIdString);
			boolean hasSession = gymPageController.trainerHasSession(trainerId);
			if(!hasSession) {
				Trainer trainerToDelete = new Trainer();
			trainerToDelete.setTrainerId(trainerId);
			gymPageController.deleteTrainer(trainerToDelete);
			doGet(request,response);

			 return;
			}else {
				
				 response.sendRedirect("ManageTrainerServlet");
			}
		}else if(request.getParameter("homePage")!=null) {
			System.out.println("HOME PAGE");
			response.sendRedirect("GymPageServlet");
			return;
		}
	}
	
	
	private boolean checkBox(String booleanValue) {
		System.out.println("\n\n\n");
		if(booleanValue == null || booleanValue.contentEquals("off")) {
			System.out.println("FALSE"+booleanValue);
			return false;
		}else if(booleanValue.contentEquals("on")) {
			System.out.println("TRUE"+booleanValue);

			return true;}
		System.out.println("\n\n\n");

		return false;
		
	}
	

}
