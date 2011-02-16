package org.cvpcs.bukkit.economyshop;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransactionDB {
	
	// 14 days! woohoo!
	private static final int CVALUE = 60 * 60 * 24 * 14;
	private static final String DBNAME = "transactions.db";
	
	private static final int BUY_TYPE = 0;
	private static final int SELL_TYPE = 1;
	
	private EconomyShop mPlugin;
	
	public TransactionDB(EconomyShop plugin) {
		mPlugin = plugin;
	}
	
	public double getPurchaseQValue(int itemid) {
		return getQValue(itemid, BUY_TYPE);
	}
	
	public double getSaleQValue(int itemid) {
		return getQValue(itemid, SELL_TYPE);
	}
	
	public void savePurchase(int itemid, int quantity) {
		saveTransaction(itemid, quantity, BUY_TYPE);
	}
	
	public void saveSale(int itemid, int quantity) {
		saveTransaction(itemid, quantity, SELL_TYPE);
	}
	
	private double getQValue(int itemid, int type) {
		double Q = 0.0;
		
    	Connection sqlConn = null;
        File dbfile = new File(mPlugin.getDataFolder(), DBNAME);
        try {
        	sqlConn = DriverManager.getConnection("jdbc:sqlite:" + dbfile.getAbsolutePath());

        	PreparedStatement pstmt = sqlConn.prepareStatement(
        			"select " +
        				"quantity as q, " +
        				"strftime('%s','now') - time as T " +
        			"from transactions " +
        			"where " +
        				"itemid = ? AND " +
        				"type = ? AND " +
        				"?/T > 1;");
        	pstmt.setInt(1, CVALUE);

        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		int q = rs.getInt("q");
        		long T = rs.getLong("T");
        		double M = Math.ceil(Math.log((double)CVALUE/(double)T)/Math.log(2));
        		Q += M * q;
        	}
        	rs.close();
        } catch(Exception e) {
        	// SHINANIGANS!
        } finally {
        	if(sqlConn != null) {
        		try {
        			sqlConn.close();
        		} catch(Exception e) { }
        	}
        }
		
		return Q;
	}
	
	private void saveTransaction(int itemid, int quantity, int type) {
    	Connection sqlConn = null;
        File dbfile = new File(mPlugin.getDataFolder(), DBNAME);
        try {
        	sqlConn = DriverManager.getConnection("jdbc:sqlite:" + dbfile.getAbsolutePath());

        	Statement stmt = sqlConn.createStatement();
        	stmt.executeUpdate("create table if not exists transactions ("
        			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
        			+ "itemid INTEGER NOT NULL, "
        			+ "time INTEGER NOT NULL, "
        			+ "quantity INTEGER NOT NULL, "
        			+ "type INTEGER NOT NULL);");
        	stmt.executeUpdate("create index if not exists transactions_itemid on transactions (itemid);");
        	stmt.executeUpdate("create index if not exists transactions_time on transactions (time);");
        	stmt.executeUpdate("create index if not exists transactions_type on transactions (type);");

        	PreparedStatement pstmt = sqlConn.prepareStatement(
        			"insert into transactions (itemid, time, quantity, type) values (?, strftime('%s','now'), ?, ?);");
        	pstmt.setInt(0, itemid);
        	pstmt.setInt(1, quantity);
        	pstmt.setInt(2, type);
        	pstmt.executeUpdate();
        	
        	// delete anything that we won't use anymore anyway
        	pstmt = sqlConn.prepareStatement("delete from transactions "
        			+ "where ?/(strftime('%s','now') - time) <= 1;");
        	pstmt.setLong(1, CVALUE);
        	pstmt.executeUpdate();
        } catch(Exception e) {
        } finally {
        	if(sqlConn != null) {
        		try {
        			sqlConn.close();
        		} catch(Exception e) { }
        	}
        }
	}
}