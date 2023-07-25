package com.sentimentanalysis.daoimpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.sentimentanalysis.util.MySQLUtility;
import com.sentimentanalysis.pojo.TwitterHandle;
import com.sentimentanalysis.dao.TwitterHandleDAO;

public class TwitterHandleDAOImpl implements TwitterHandleDAO
{
    @Override
    public void create(final TwitterHandle th) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("insert into twitterhandles values(?,?,?)");
            ps.setString(1, th.getHandle());
            ps.setString(2, th.getUser());
            ps.setTimestamp(3, th.getEntry_time());
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
    public List<TwitterHandle> getTwitterHandlesByUser(final String user) throws Exception {
        Connection con = null;
        final List<TwitterHandle> result = new ArrayList<TwitterHandle>();
        try {
            con = MySQLUtility.connect();
            final ResultSet rs = con.createStatement().executeQuery("select * from twitterhandles where email='" + user + "' ");
            while (rs.next()) {
                final TwitterHandle th = new TwitterHandle();
                th.setEntry_time(rs.getTimestamp("entry_time"));
                th.setHandle(rs.getString("handle"));
                th.setUser(user);
                result.add(th);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            con.close();
        }
        con.close();
        return result;
    }
    
    @Override
    public void delete(final String handle, final String user) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            con.createStatement().execute("delete from twitterhandles where handle='" + handle + "' and email='" + user + "' ");
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
}