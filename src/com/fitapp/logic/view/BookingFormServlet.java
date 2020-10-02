package com.fitapp.logic.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitapp.logic.bean.BookingOnMapBean;
import com.fitapp.logic.bean.UserBean;
import com.fitapp.logic.controller.BookingFormController;
import com.fitapp.logic.model.BookingOnMapModel;

/**
 * Servlet implementation class BookingFormServlet
 */
@WebServlet("/BookingFormServlet")
public class BookingFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBean userBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingFormServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("homePageBtn")!=null) {
			response.sendRedirect("UserPageServlet");
			return;
		}
		userBean = (UserBean) request.getSession().getAttribute("userBean");
		request.setAttribute("username", userBean.getUserUsername().get());
		request.setAttribute("userStreet", userBean.getUserPosition().get());
		
		RequestDispatcher dis= getServletContext().getRequestDispatcher("/BookingForm.jsp");
		dis.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("goBooking")!=null) {
			String radious= (String) request.getParameter("radiousBooking");
			String date = (String) request.getParameter("dateBooking");
			String time = (String) request.getParameter("timeBooking");
			if(radious!=null && date!=null && time!=null) {
				
				request.getSession().setAttribute("userId", userBean.getUserId());
				request.getSession().setAttribute("userStreet", userBean.getUserPosition().get());
				request.getSession().setAttribute("username", userBean.getUserUsername().get());
				BookingOnMapBean bookingOnMapBean = new BookingOnMapBean();
				BookingOnMapModel bookingOnMapModel = new BookingOnMapModel(bookingOnMapBean);
				BookingFormController bookingFormController = new BookingFormController(bookingOnMapModel);
				bookingFormController.setSearchParameters(LocalDate.parse(date), LocalTime.parse(time), Double.parseDouble(radious));
				
				request.getSession().setAttribute("bookingOnMapBean", bookingOnMapBean);
				request.getSession().setAttribute("bookingOnMapModel", bookingFormController.getBookingModel());
				response.sendRedirect("BookingOnMapServlet");
			
			}else {
				response.getWriter().println("<script type=\"text/javascript\">");
				response.getWriter().println("alert('Please compile all fields.');");
				 response.getWriter().println("location='BookingFormServlet';");
				 response.getWriter().println("</script>");
			}
		}
	}

}
