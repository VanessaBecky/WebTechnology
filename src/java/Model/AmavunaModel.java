/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Dao.ProfileDao;
import Entities.Amavuna;
import Entities.Attendance;
import Entities.Attended;
import Entities.Profile;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Treasure
 */
@ManagedBean(name = "mr")
@SessionScoped
public class AmavunaModel {
    private Attendance attendance = new Attendance();
    private Amavuna amavuna = new Amavuna();
    Amavuna amv = new Amavuna();
    private Profile profile = new Profile();
    private Profile profileData = new Profile();
    private ProfileDao proDao = new ProfileDao();
    
    public String registerAttendance(){
        FacesMessage msg = null;
        try{
            attendance.setAttendanceDate(amv.getTime());
            if(amv.getAmavunaId().equals(profile.getPassword())){
                proDao.createAttendance(attendance);
            }else{
                msg = new FacesMessage("Wrong keyword");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }catch(Exception ex){
            msg = new FacesMessage(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "home";
    }
    
    public String registerAmavuna(){
        FacesMessage msg = null;
        try{
            proDao.createAmavuna(amavuna);
        }catch(Exception ex){
            msg = new FacesMessage(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "setup";
    }
    
    public String registerProfile(){
        createUser();
        FacesMessage msg = null;
        if(!profile.getPassword().equals(attendance.getStudId())){
            msg = new FacesMessage("Password doesn't match!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "signup";
        }
        try{
            profile.setRole("student");
            proDao.createProfile(profile);
            return "signin";
        }catch(Exception ex){
            msg = new FacesMessage(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "signup";
        }
    }
    
    public String updateProfile(){
        FacesMessage msg = null;
        try{
            proDao.updateProfile(profileData);
            return "home";
        }catch(Exception ex){
            msg = new FacesMessage(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "user";
        }
    }
    
    public String loginProfile(){
        createUser();
        FacesMessage msg = null;
        try{
            String pass = profile.getPassword();
            profile=proDao.findProfile(profile.getUsername());
            profileData=profile;
            if(profile.getPassword().equals(pass)){
                if(profile.getRole().equals("student")){
                    HttpSession session = SessionUtils.getSession();
                    session.setAttribute("username", profile.getUsername());
                    return "home";
                }else{
                    return "setup";
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
                return "signin";
            }
        }catch(Exception ex){
            msg = new FacesMessage(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "signin";
        }
    }
    
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "signin";
    }

    public Amavuna getAmavuna() {
        return amavuna;
    }
    
    public Amavuna getAmavunaData() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        String strDate = dateFormat.format(date);
        amv = proDao.findAmavuna(strDate);
        return amv;
    }
    
    public Profile getProfile() {
        return profile;
    }

    public Attendance getAttendance() {
        return attendance;
    }
    
    public List<Attended> getAttendanceList() {
        List<Attended> attended = new ArrayList<>();
        Iterator<Amavuna> iterator = proDao.readAmavuna().iterator();
        while(iterator.hasNext()){
            Attended atte = new Attended();
            Amavuna att = iterator.next();
            atte.setAttendanceDate(att.getTime());
            atte.setScript(att.getScript());
            atte.setAttend(seeIfAttend(att.getTime()));
            attended.add(atte);
        }
        return attended;
    }
    
    public String seeIfAttend(String s){
        String qry = "select from attendance where attendancedate='"+s+"' and studusername='"+profile.getUsername()+"'";
        try{
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AMAVUNA_DB","postgres","skanking");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(qry);
            System.out.println(s+" -- "+profile.getUsername());
            if(rs.next()){
                return "Attended";
            }else{
                return "Absent";
            }
        }catch(Exception ex){
            return ex.toString();
        }
    }
    
    public Profile getProfileData(){
        return proDao.findProfile(profile.getUsername());
    }
    
    public void createUser(){
        FacesMessage msg = null;
        Profile profileAdmin = new Profile();
        try{
            profileAdmin.setUsername("admin");
            profileAdmin.setRole("admin");
            profileAdmin.setPassword("admin");
            proDao.createProfile(profileAdmin);
        }catch(Exception ex){
        }
    }
}
