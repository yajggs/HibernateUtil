package com.yajgss;

import java.util.Date;
import java.util.Random;

import com.yajgss.dao.CommonDAO;
import com.yajgss.dao.impl.CommonDAOImpl;
import com.yajgss.user.Student;
import com.yajgss.user.StudentAddress;
import com.yajgss.user.StudentDetail;
import com.yajgss.user.StudentGroup;
import com.yajgss.user.StudentGroupId;
import com.yajgss.user.StudentRecord;
import org.hibernate.Session;
import com.yajgss.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class App {
	public static void main(String[] args) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Random random = new Random();

			for(int i=1; i<=5 ;i++) {

				session.beginTransaction();

				CommonDAO commonDAO = new CommonDAOImpl(session);

				Student student = new Student("Student "+i);

				StudentDetail studentDetail = new StudentDetail(16, new Date(), "A+", random.nextInt(100000)+"");
				studentDetail.setStudent(student);
				student.setStudentDetail(studentDetail);

				StudentAddress address1 = new StudentAddress("Address "+i, "Communcation", "Bangalore", "Karnataka", "India");
				StudentAddress address2 = new StudentAddress("Address "+(i+1), "Permanent", "Bangalore", "Karnataka", "India");
				student.getStudentAddresses().add(address1);
				student.getStudentAddresses().add(address2);

				StudentRecord studentRecord1 = new StudentRecord("Maths", random.nextInt(100), "X");
				StudentRecord studentRecord2 = new StudentRecord("Biology", random.nextInt(100), "X");
				StudentRecord studentRecord3 = new StudentRecord("Computer Science", random.nextInt(100), "X");
				StudentRecord studentRecord4 = new StudentRecord("Physics", random.nextInt(100), "X");
				StudentRecord studentRecord5 = new StudentRecord("Chemistry", random.nextInt(100), "X");

				studentRecord1.setStudent(student);
				studentRecord2.setStudent(student);
				studentRecord3.setStudent(student);
				studentRecord4.setStudent(student);
				studentRecord5.setStudent(student);


				commonDAO.create(studentRecord1);
				commonDAO.create(studentRecord2);
				commonDAO.create(studentRecord3);
				commonDAO.create(studentRecord4);
				commonDAO.create(studentRecord5);

				try {
					StudentGroup studentGroup = new StudentGroup();
					studentGroup.setStudentGroupId(new StudentGroupId((i % 5)+1, (i % 5)+1));
					student.getStudentGroups().add(studentGroup);
					studentGroup.setStudent(student);
					commonDAO.create(studentGroup);
				}catch(Exception e){}

				commonDAO.create(student);

				session.getTransaction().commit();
			}

		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
				HibernateUtil.shutdown();
			}
		}
	}
}
