package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//������ ���̽��� Ŀ�ؼ�Ǯ�� ����ϵ��� �����ϴ� �޼ҵ�
	public void getCon() {
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context)initctx.lookup("java:comp/env");
			DataSource ds = (DataSource)envctx.lookup("jdbc/pool");
			//data source
			con = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeBoard(BoardBean bean) {
		getCon();
		
		//�� Ŭ������ �Ѿ���� �ʾҴ� �����͵��� �ʱ�ȭ���־�� �Ѵ�.
		int ref=0;//�۱׷��� �ǹ� = ������ ������Ѽ� ���� ū ref���� ������ �� +1�� �����ָ� ��
		int re_step = 1; //�����̱⿡ = �θ���̱⿡
		int re_level = 1; 
		try {
			//���� ū ref���� �о���� ���� �غ�
			String refsql = "select max(ref) from board";
			//�������� ��ü
			pstmt = con.prepareStatement(refsql);
			//���������� ����� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1;//�ִ밪�� +1�� ���ؼ� �۱׷��� ����
			}
			//������ �Խñ� ��ü���� ���̺� ����
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?�� ���� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			
			pstmt.executeUpdate();
			
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * //��� �Խñ��� �������ִ� �޼��� public Vector<BoardBean> getAllBoard(){
	 * 
	 * //������ ��ü ���� Vector<BoardBean> v = new Vector<>(); getCon();
	 * 
	 * try { //���� �غ� String sql =
	 * "select * from board order by ref desc,re_level asc"; //������ ������ ��ü ���� pstmt =
	 * con.prepareStatement(sql); //���������� ��� ���� rs = pstmt.executeQuery(); //������ ������
	 * ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ���� while(rs.next()) { //�����͸� ��Ű¡ BoardBean bean =
	 * new BoardBean(); bean.setNum(rs.getInt(1)); bean.setWriter(rs.getString(2));
	 * bean.setEmail(rs.getString(3)); bean.setSubject(rs.getString(4));
	 * bean.setPassword(rs.getString(5));
	 * bean.setReg_date(rs.getDate(6).toString()); bean.setRef(rs.getInt(7));
	 * bean.setRe_step(rs.getInt(8)); bean.setRe_level(rs.getInt(9));
	 * bean.setReadcount(rs.getInt(10)); bean.setContent(rs.getString(11)); //��Ű¡��
	 * �����͸� ���Ϳ� ���� v.add(bean); } con.close(); } catch(Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return v;
	 * 
	 * }
	 */
	
	
	//��� �Խñ��� �������ִ� �޼���. ����¡
		public Vector<BoardBean> getAllBoard(int start, int end){
			
			//������ ��ü ����
			Vector<BoardBean> v = new Vector<>();
			getCon();
			
			try {
				//���� �غ�
				String sql = "select * from (select A.*, Rownum Rnum from(select * from board order by ref desc,re_level asc)A)"
						+ "where Rnum > ? and Rnum <= ?";
				//������ ������ ��ü ����
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,start);
				pstmt.setInt(2,end);
				//���������� ��� ����
				rs = pstmt.executeQuery();
				//������ ������ ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
				while(rs.next()) {
					//�����͸� ��Ű¡
					BoardBean bean = new BoardBean();
					bean.setNum(rs.getInt(1));
					bean.setWriter(rs.getString(2));
					bean.setEmail(rs.getString(3));
					bean.setSubject(rs.getString(4));
					bean.setPassword(rs.getString(5));
					bean.setReg_date(rs.getDate(6).toString());
					bean.setRef(rs.getInt(7));
					bean.setRe_step(rs.getInt(8));
					bean.setRe_level(rs.getInt(9));
					bean.setReadcount(rs.getInt(10));
					bean.setContent(rs.getString(11));
					//��Ű¡�� �����͸� ���Ϳ� ����
					v.add(bean);
				}
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return v;
			
		}
		
	
	
	public BoardBean boardInfo(int num) {
		getCon();
		BoardBean bean = new BoardBean();
		try {
			//��ȸ�� ��������
			String readsql = "update board set readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			
			//���� �غ�
			String sql = "select * from board where num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	
	public void reWrite(BoardBean bean) {
		getCon();
		String sp="";
		for(int i = 0; i<bean.getRe_step(); i++) {
			sp+="[re]";
		}
		try {
			//����, �θ�� ������ �ٸ��۵� re_level +1�� ����
			String upsql = "update board set re_level = re_level +1 where ref = ? and re_level > ?";
			pstmt = con.prepareStatement(upsql);
			pstmt.setInt(1, bean.getRef());
			pstmt.setInt(2, bean.getRe_level());
			pstmt.executeUpdate();
			
			
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, sp+bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, bean.getRef());
			pstmt.setInt(6, bean.getRe_step()+1);
			pstmt.setInt(7,bean.getRe_level()+1);
			pstmt.setString(8, bean.getContent());
			
			pstmt.executeUpdate();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPassword(int num) {
		String realPass = "";
		getCon();
		try {
			String sql = "select password from board  where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				realPass = rs.getString(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return realPass;
	}
	
	public void deleteOne(int num) {
		getCon();
		try {
			String sql = "delete from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(BoardBean bean) {
		getCon();
		String sp = "[����]";
		for(int i = 0; i<bean.getRe_step()-1; i++) {
			sp += "[re]";
		}
		try {
			String sql = "update board set writer = ?,email=?,subject=?,content=?  where num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, sp+bean.getSubject());
			pstmt.setString(4, bean.getContent());
			pstmt.setInt(5, bean.getNum());
			pstmt.executeUpdate();
			
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ü ���� ������ �����ϴ� �޼ҵ�
	public int getAllCount() {
		getCon();
		
		//�Խñ� ��ü���� �����ϴ� ����
		int count = 0;
		
		try {
			//�����غ�
			String sql = "select count(*) from board";
			//������ ������ ��ü����
			pstmt = con.prepareStatement(sql);
			//���� ������ ����� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//��ü �Խñ� ��
			}
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
