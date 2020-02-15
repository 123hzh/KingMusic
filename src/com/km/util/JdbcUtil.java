package com.km.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

public class JdbcUtil {
	private Connection conn = null;
	private PreparedStatement pst=null;
	private ResultSet rs=null;
	private String url="jdbc:mysql://localhost:3306/kmusic";
	private String username="root";
	private String password="123456";
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Context ctx=new InitialContext();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��ȡ����
	public Connection getConn(){
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//�� ɾ ��
	public void executeUpdate(String sql,Object... obj){
		getConn();
		try {
			pst = conn.prepareStatement(sql);
			//��ռλ����ֵ
			if(obj!=null){
				for(int i=0;i<obj.length;i++){
					pst.setObject(i+1, obj[i]);
				}
			}
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}
	}
	public int update(String sql,Object... param){
		getConn();
		try {
			pst = conn.prepareStatement(sql);
			if(param!=null){
				for(int i=0;i<param.length;i++){
					pst.setObject(i+1, param[i]);
				}
			}
			int i = pst.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//��ѯ�Ĳ���
	public ResultSet executeQuery(String sql,Object... obj){
		getConn();
		try {
			pst = conn.prepareStatement(sql);
			//��ռλ����ֵ
			if(obj!=null){
				for(int i=0;i<obj.length;i++){
					pst.setObject(i+1, obj[i]);
				}
			}
			rs =pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//������Ϣ
		public int add(String sql,Object... param){
			getConn();
			try {
				pst = conn.prepareStatement(sql);
				//��ֵ��SQL����ռλ��,˳���Ӧ
				if(param!=null){
					for(int i=0;i<param.length;i++){
						pst.setObject(i+1, param[i]);
					}
				}
				int i = pst.executeUpdate();
				return i;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}

	//�ر�����
	public void close(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pst!=null){
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
