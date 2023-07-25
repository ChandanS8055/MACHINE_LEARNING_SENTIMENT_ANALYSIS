package com.sentimentanalysis.daoimpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.sentimentanalysis.util.MySQLUtility;
import com.sentimentanalysis.pojo.TwitterKeyword;
import com.sentimentanalysis.dao.TwitterKeywordDAO;

public class TwitterKeywordDAOImpl implements TwitterKeywordDAO
{
    @Override
    public void create(final TwitterKeyword tk) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            final PreparedStatement ps = con.prepareStatement("insert into twitterkeywords values(?,?,?)");
            ps.setString(1, tk.getKeyword());
            ps.setString(2, tk.getUser());
            ps.setTimestamp(3, tk.getEntry_time());
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
    public List<TwitterKeyword> getTwitterKeywordsByUser(final String user) throws Exception {
        Connection con = null;
        final List<TwitterKeyword> result = new ArrayList<TwitterKeyword>();
        try {
            con = MySQLUtility.connect();
            final ResultSet rs = con.createStatement().executeQuery("select * from twitterkeywords where email='" + user + "' ");
            while (rs.next()) {
                final TwitterKeyword tk = new TwitterKeyword();
                tk.setEntry_time(rs.getTimestamp("entry_time"));
                tk.setKeyword(rs.getString("keyword"));
                tk.setUser(user);
                result.add(tk);
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
    public void delete(final String keyword, final String user) throws Exception {
        Connection con = null;
        try {
            con = MySQLUtility.connect();
            con.createStatement().execute("delete from twitterkeywords where keyword='" + keyword + "' and email='" + user + "' ");
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