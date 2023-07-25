package com.sentimentanalysis.daoimpl;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import com.sentimentanalysis.pojo.User;
import java.sql.ResultSet;
import java.sql.Connection;
import com.sentimentanalysis.util.MySQLUtility;
import java.util.ArrayList;
import java.util.List;
import com.sentimentanalysis.dao.UserDAO;

public class UserDAOImpl implements UserDAO
{
    @Override
    public List<String> getAllUsers() throws Exception {
        Connection con = null;
        final List<String> result = new ArrayList<String>();
        try {
            con = MySQLUtility.connect();
            final ResultSet rs = con.createStatement().executeQuery("select * from user");
            while (rs.next()) {
                result.add(rs.getString("email"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            con.close();
        }
        con.close();
        return result;
    }
    
    @Override
    public void register(final User user) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("insert into user values (?,?,?,?,?,?,?,?) ");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getFname());
            ps.setString(6, user.getLname());
            ps.setString(7, user.getGender());
            ps.setString(8, user.getMobile());
            ps.setString(3, user.getAddr());
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            con.close();
        }
        con.close();
    }
    
    @Override
    public User getUserDetails(final String email, final String password) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("select * from user where email=? and password=?");
            final Timestamp t1 = new Timestamp(System.currentTimeMillis());
            final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            final Timestamp t2 = new Timestamp(sdf.parse("01072019").getTime());
            ps.setString(1, email);
            ps.setString(2, password);
            final ResultSet rs = ps.executeQuery();
            rs.next();
            final User user = new User();
            user.setAddr(rs.getString("addr"));
            user.setEmail(rs.getString("email"));
            user.setFname(rs.getString("fname"));
            user.setLname(rs.getString("lname"));
            user.setGender(rs.getString("gender"));
            user.setMobile(rs.getString("mobile"));
            user.setRole(rs.getString("role"));
            return user;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            con.close();
        }
    }
    
    @Override
    public void updateProfile(final User user) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement(" update user set role=?, fname=?, lname=?, gender=?, mobile=?, addr=? where email=?");
            ps.setString(1, user.getRole());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getMobile());
            ps.setString(6, user.getAddr());
            ps.setString(7, user.getEmail());
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            con.close();
        }
        con.close();
    }
    
    @Override
    public void deleteProfile(final String email) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("delete from user where email=?");
            ps.setString(1, email);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            con.close();
        }
        con.close();
    }
    
    @Override
    public boolean changePassword(final String email, final String oldpassword, final String newpassword) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("select password from user where email=?");
            ps.setString(1, email);
            final ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString(1).equals(oldpassword)) {
                final PreparedStatement ps2 = con.prepareStatement("update user set password=? where email=?");
                ps2.setString(1, newpassword);
                ps2.setString(2, email);
                ps2.execute();
                return true;
            }
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            con.close();
        }
    }
    
    @Override
    public String forgotPassword(final String email) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("select password from user where email=?");
            ps.setString(1, email);
            final ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            con.close();
        }
    }
    
    @Override
    public User getUserDetails(final String email) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("select * from user where email=? ");
            ps.setString(1, email);
            final ResultSet rs = ps.executeQuery();
            rs.next();
            final User user = new User();
            user.setAddr(rs.getString("addr"));
            user.setEmail(rs.getString("email"));
            user.setFname(rs.getString("fname"));
            user.setLname(rs.getString("lname"));
            user.setGender(rs.getString("gender"));
            user.setMobile(rs.getString("mobile"));
            user.setRole(rs.getString("role"));
            return user;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            con.close();
        }
    }
    
    @Override
    public boolean adminlogin(final String adminid, final String adminpassword) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final ResultSet rs = con.createStatement().executeQuery("select count(*) from admin where adminid='" + adminid + "' and adminpassword='" + adminpassword + "'  ");
            rs.next();
            return rs.getInt(1) > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return false;
    }
}