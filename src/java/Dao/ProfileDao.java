/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entities.Amavuna;
import Entities.Attendance;
import Entities.Profile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextField;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Treasure
 */
public class ProfileDao {
    public void createProfile(Profile p){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        ses.save(p);
        ses.getTransaction().commit();
        ses.close();
    }
    public void updateProfile(Profile p){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        ses.update(p);
        ses.getTransaction().commit();
        ses.close();
    }
    public Profile findProfile(String s){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Profile p = (Profile) ses.get(Profile.class, s);
        ses.getTransaction().commit();
        ses.close();
        return p;
    }
    public List<Profile> readProfile(){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Query qry = ses.createQuery("select p from Profile p");
        List<Profile> listOfPatients = qry.list();
        ses.close();
        return listOfPatients;
    }
    public void createAttendance(Attendance p){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        ses.save(p);
        ses.getTransaction().commit();
        ses.close();
    }
    public void createAmavuna(Amavuna p){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        ses.save(p);
        ses.getTransaction().commit();
        ses.close();
    }
    public void updateAttendance(Attendance p){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        ses.update(p);
        ses.getTransaction().commit();
        ses.close();
    }
    public Attendance findAttendance(String s){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Attendance p = (Attendance) ses.get(Attendance.class, s);
        ses.getTransaction().commit();
        ses.close();
        return p;
    }
    public Amavuna findAmavuna(String s){ 
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Amavuna p = (Amavuna) ses.get(Amavuna.class, s);
        ses.getTransaction().commit();
        ses.close();
        return p;
    }
    public List<Attendance> readAttendance(String s){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Query qry = ses.createQuery("select p from Attendance p");
        List<Attendance> listOfPatients = qry.list(),attendList = new ArrayList<>();
        Iterator<Attendance> iterator = listOfPatients.iterator();
        while(iterator.hasNext()){
            Attendance attend = iterator.next();
            if(attend.getStudusername().equals(s)){
                attendList.add(attend);
            }
        }
        ses.close();
        return attendList;
    }
    public List<Amavuna> readAmavuna(){
        Session ses = HibernateUtil.getSessionFactory().openSession();
        Query qry = ses.createQuery("select p from Amavuna p");
        List<Amavuna> listOfPatients = qry.list();
        ses.close();
        return listOfPatients;
    }
}
