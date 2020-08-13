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
	
	//데이터 베이스의 커넥션풀을 사용하도록 설정하는 메소드
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
		
		//빈 클래스에 넘어오지 않았던 데이터들을 초기화해주어야 한다.
		int ref=0;//글그룹을 의미 = 쿼리를 실행시켜서 가장 큰 ref값을 가져온 후 +1을 더해주면 됨
		int re_step = 1; //새글이기에 = 부모글이기에
		int re_level = 1; 
		try {
			//가장 큰 ref값을 읽어오는 쿼리 준비
			String refsql = "select max(ref) from board";
			//쿼리실행 객체
			pstmt = con.prepareStatement(refsql);
			//쿼리실행후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1;//최대값에 +1을 더해서 글그룹을 설정
			}
			//실제로 게시글 전체값을 테이블에 저장
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값을 맵핑
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
	 * //모든 게시글을 리턴해주는 메서드 public Vector<BoardBean> getAllBoard(){
	 * 
	 * //리턴할 객체 선언 Vector<BoardBean> v = new Vector<>(); getCon();
	 * 
	 * try { //쿼리 준비 String sql =
	 * "select * from board order by ref desc,re_level asc"; //쿼리를 실행할 객체 선언 pstmt =
	 * con.prepareStatement(sql); //쿼리실행후 결과 저장 rs = pstmt.executeQuery(); //데이터 개수가
	 * 몇개인지 모르기에 반복문을 이용하여 데이터를 추출 while(rs.next()) { //데이터를 패키징 BoardBean bean =
	 * new BoardBean(); bean.setNum(rs.getInt(1)); bean.setWriter(rs.getString(2));
	 * bean.setEmail(rs.getString(3)); bean.setSubject(rs.getString(4));
	 * bean.setPassword(rs.getString(5));
	 * bean.setReg_date(rs.getDate(6).toString()); bean.setRef(rs.getInt(7));
	 * bean.setRe_step(rs.getInt(8)); bean.setRe_level(rs.getInt(9));
	 * bean.setReadcount(rs.getInt(10)); bean.setContent(rs.getString(11)); //패키징한
	 * 데이터를 벡터에 저장 v.add(bean); } con.close(); } catch(Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return v;
	 * 
	 * }
	 */
	
	
	//모든 게시글을 리턴해주는 메서드. 페이징
		public Vector<BoardBean> getAllBoard(int start, int end){
			
			//리턴할 객체 선언
			Vector<BoardBean> v = new Vector<>();
			getCon();
			
			try {
				//쿼리 준비
				String sql = "select * from (select A.*, Rownum Rnum from(select * from board order by ref desc,re_level asc)A)"
						+ "where Rnum > ? and Rnum <= ?";
				//쿼리를 실행할 객체 선언
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,start);
				pstmt.setInt(2,end);
				//쿼리실행후 결과 저장
				rs = pstmt.executeQuery();
				//데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
				while(rs.next()) {
					//데이터를 패키징
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
					//패키징한 데이터를 벡터에 저장
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
			//조회수 증가쿼리
			String readsql = "update board set readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			
			//쿼리 준비
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
			//원글, 부모글 제외한 다른글들 re_level +1씩 해줌
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
		String sp = "[수정]";
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
	
	//전체 글의 갯수를 리턴하는 메소드
	public int getAllCount() {
		getCon();
		
		//게시글 전체수를 저장하는 변수
		int count = 0;
		
		try {
			//쿼리준비
			String sql = "select count(*) from board";
			//쿼리를 실행할 객체선언
			pstmt = con.prepareStatement(sql);
			//쿼리 실행후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//전체 게시글 수
			}
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
