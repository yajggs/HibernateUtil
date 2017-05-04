package com.yajgss.common;

import com.yajgss.dao.CommonDAO;
import com.yajgss.dao.impl.CommonDAOImpl;
import com.yajgss.user.Student;
import com.yajgss.user.StudentAddress;
import com.yajgss.user.StudentDetail;
import com.yajgss.user.StudentGroup;
import com.yajgss.user.StudentGroupId;
import com.yajgss.user.StudentRecord;
import com.yajgss.util.HibernateUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AppTest extends TestCase {

    private static Logger LOGGER = Logger.getLogger(AppTest.class);

    private static Session session = HibernateUtil.getSessionFactory().openSession();

    public AppTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testGetEntity() {

        try {
            CommonDAO commonDAO = new CommonDAOImpl(session);

            List<Student> students = commonDAO.getList(Student.class);

            for(Student student: students) {
                LOGGER.info("ID:"+student.getStudentId());
                LOGGER.info("NAME:"+student.getStudentName());
                LOGGER.info("AGE:"+student.getStudentDetail().getAge());
                LOGGER.info("CONTACT NUMBER:"+student.getStudentDetail().getContactNumber());
                for(StudentRecord studentRecord: student.getStudentRecords()) {
                    LOGGER.info("SUBJECT NAME:"+studentRecord.getSubjectName());
                    LOGGER.info("MARK SCORED:"+studentRecord.getMarkScored());
                    LOGGER.info("CLASS NAME:"+studentRecord.getClassName());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testEntityAccess() {

        Map<String, List<String>> projections = new LinkedMap();

        projections.put("", Arrays.asList("studentId"));
        projections.put("^studentDetail", Arrays.asList("detailId","age"));
        projections.put("^studentRecords", Arrays.asList("recordId","subjectName","markScored"));

        try {

            CommonDAO commonDAO = new CommonDAOImpl(session);

            List<Student> students = commonDAO.getColumnsAsEntity(Student.class, projections);

            for(Student student: students) {
                LOGGER.info("ID:"+student.getStudentId());
                LOGGER.info("NAME:"+student.getStudentName());
                LOGGER.info("AGE:"+student.getStudentDetail().getAge());
                LOGGER.info("CONTACT NUMBER:"+student.getStudentDetail().getContactNumber());
                for(StudentRecord studentRecord: student.getStudentRecords()) {
                    LOGGER.info("SUBJECT NAME:"+studentRecord.getSubjectName());
                    LOGGER.info("MARK SCORED:"+studentRecord.getMarkScored());
                    LOGGER.info("CLASS NAME:"+studentRecord.getClassName());
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testShutDown(){
        if(null != session) {
            session.close();
        }
        HibernateUtil.shutdown();
    }

    public void testProps(){
        String s = "abc.def";
        int idx = 0;
        if((idx = s.indexOf(".")) > 0){
            s = s.substring(idx);
            System.out.println(s);
        }
    }
}
