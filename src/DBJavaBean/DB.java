package DBJavaBean;

import JavaBean.UserNameBean;
import JavaBean.MyDayBean;
import JavaBean.MyFriBean;
import JavaBean.MyMessBean;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

public class DB implements ServletRequestAware{
	private String driverName="com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_junruitianlab2";//?useUnicode=true&characterEncoding=gbk
	private String user ="0wkx21253z";
	private String password="k5kj3lh5w533kxz031il5542i1hmh42l2m0wixl5";
	private Connection con = null;
	private Statement st =null;
	private ResultSet rs = null;
	private HttpServletRequest request;
	
	public DB(){
		
	}
	public String getDriverName(){
		return driverName;
	}
	public void setDriverName(String driverName)
	{
		this.driverName = driverName;
	}
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setServletRequest(HttpServletRequest hsr)
	{
		request = hsr;
	}
	
	//������ݿ����ӣ���������������
	public Statement getStatement()
	{
		try{
			Class.forName(getDriverName());
			con = DriverManager.getConnection(getUrl(),getUser(),getPassword());
			return con.createStatement();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String insertau(HttpServletRequest request,String userName,String authorname,String authornumber,String authorid,String authorage,String country)
	{
		//System.out.println("insert");
		try{
			String sure = null;
			rs = selectauthor(request,userName,authorid);
			if(rs.next())
			{
				sure = "one";
			}
			else{
				String sql ="insert into aythorinformation"+" (AuthorNumber,AuthorName,Authorid,Age,Country)"+" values("+"'"+authornumber+"'"+","+"'"+authorname+"'"+","+"'"+authorid+"'"+","+"'"+authorage+"'"+","+"'"+country+"'"+")";
				st = getStatement();
				int row=st.executeUpdate(sql);
				if(row==1)
				{
					//����myfriend����������session���鼮��Ϣ
					String fri = myDayAuthor1(request,userName);
					if(fri.equals("ok"))
					{
						sure = "ok";
					}
					else
					{
						sure = null;
					}
				}
				else
				{
					sure = null;
				}
			}
			return sure;
		}catch(Exception e){
			e.printStackTrace();
			return null;
					}
							
	}
	
	//����
	//public String insertMess(HttpServletRequest request,String uesrName,String password,String name,String sex)
	public String insertFri(HttpServletRequest request,String userName,String newBookName,String Price,String Date,String authorid,String publisher,String ISBN,String bookNumber)
	{
		//System.out.println("insert");
		try{
			String sure = null;
			rs = selectFri(request,userName,ISBN);
			if(rs.next())
			{
				sure = "one";
			}
			else{
				String sql ="insert into bookinformation"+" (userName,BookName,ISBN,publisher,Date,Price,Authorid,idbookinformation)"+" values("+"'"+userName+"'"+","+"'"+newBookName+"'"+","+"'"+ISBN+"'"+","+"'"+publisher+"'"+","+"'"+Date+"'"+","+"'"+Price+"'"+","+"'"+authorid+"'"+","+"'"+bookNumber+"'"+")";
				st = getStatement();
				int row=st.executeUpdate(sql);
				if(row==1)
				{
					//����myfriend����������session���鼮��Ϣ
					String fri = myFriends(request,userName);
					if(fri.equals("ok"))
					{
						sure = "ok";
					}
					else
					{
						sure = null;
					}
				}
				else
				{
					sure = null;
				}
			}
			return sure;
		}catch(Exception e){
			e.printStackTrace();
			return null;
					}
							
	}
	
	
	
	//ɾ��
	public String deleteFri(HttpServletRequest request,String userName,String ISBN)
	{
		try{
			String sure=null;
			String sql = "delete from bookinformation where ISBN='"+ISBN+"'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if(row==1)
			{
				//����myfriend����������session���鼮��Ϣ
				String fri = myFriends(request,userName);
				if(fri.equals("ok"))
				{
					sure = "ok";
				}
				else
				{
					sure = null;
				}
			}
			else
			{
				sure = null;
			}
		
		return sure;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
					}
	}
	
	//�޸�
	public String updateFri(HttpServletRequest request,String userName,String bookName,String Price,String Date,String authorid,String publisher,String ISBN,String bookNumber)
	{
		try{
			String sure = null;
			//��ɾ����Ϣ
			String del = deleteFri(request,userName,ISBN);
			
			if(del.equals("ok")){
				//System.out.println(del+"1");
				String in = insertFri(request,userName,bookName,ISBN,publisher,Date,Price,authorid,bookNumber);
				if(in.equals("ok"))
				{
					//System.out.println(del+"1");
					//����myfriends����������session
					String fri= myFriends(request,userName);
					if(fri.equals("ok")){
						sure = "ok";
					}else{
						sure = null;
					}
				}
				else
				{
					sure = null;
				}
			}
			else
			{
				sure = null;
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//��ѯ����ͷ
	public ResultSet selectauthor(HttpServletRequest request,String userName,String authorid)	
	{
		try{
			String sql = "select * from aythorinformation where Authorid='"+authorid+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet selectauthor444(HttpServletRequest request,String userName,String authorid)	
	{
		try{
			String sql = "select * from bookinformation where Authorid='"+authorid+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet selectauthorID(HttpServletRequest request,String userName,String authorname)	
	{
		try{
			String sql = "select * from aythorinformation where AuthorName='"+authorname+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String myFriendssearchID(HttpServletRequest request,String userName,String authorname)
	{
		try{
			ArrayList listName =null;
			HttpSession session =request.getSession();
			listName=new ArrayList();
			rs=selectauthorID(request,userName,authorname);
			
			if(rs.next())
			{
				rs=selectauthorID(request,userName,authorname);
				while(rs.next())
				{
					MyDayBean mess = new MyDayBean();
					
					mess.setauthorid(rs.getString("Authorid"));
					//System.out.println();
					//
					listName.add(mess);
					//listName.add(rs.getString("Authorid"));
					//System.out.println("2");
					session.setAttribute("friendsearchID",listName);
				}
			}
			else
			{
				session.setAttribute("friendsearchID", listName);
			}
			//System.out.println(listName);
			return "ok";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ResultSet selectauthor446(HttpServletRequest request,String userName,String authorid,String authorname)	
	{
		try{
			String sql = "select * from bookinformation where Authorid='"+authorid+"'"+" and AuthorName='"+authorname+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//��ѯ�鼮
	public ResultSet selectFri(HttpServletRequest request,String userName,String ISBN)	
	{
		try{
			String sql = "select * from bookinformation where ISBN='"+ISBN+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	//��ȡȫ���鼮��Ϣ
	public ResultSet selectFriAll(HttpServletRequest request,String userName)
	{
		try{
			//String sql="select * from bookinformation where userName = '"+userName+"'";
			String sql="select * from bookinformation ";
			st = getStatement();
			return st.executeQuery(sql);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//��ȡ������Ϣ�����浽session
	public String myFriends(HttpServletRequest request,String userName)
	{
		try{
			ArrayList listName =null;
			HttpSession session =request.getSession();
			listName=new ArrayList();
			rs=selectFriAll(request,userName);
			if(rs.next())
			{
				rs=selectFriAll(request,userName);
				while(rs.next())
				{
					MyFriBean mess = new MyFriBean();
					mess.setNumber(rs.getString("idbookinformation"));
					mess.setName(rs.getString("BookName"));
					mess.setISBN(rs.getString("ISBN"));
					mess.setpublisher(rs.getString("Publisher"));
					mess.setauthor(rs.getString("Authorid"));
					mess.setdate(rs.getString("Date"));
					mess.setprice(rs.getString("Price"));
					//
					listName.add(mess);
					//System.out.println("2");
					session.setAttribute("friends",listName);
				}
			}
			else
			{
				session.setAttribute("friends", listName);
			}
			return "ok";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String myFriends345(HttpServletRequest request,String userName)
	{
		try{
			ArrayList listName =null;
			HttpSession session =request.getSession();
			listName=new ArrayList();
			rs=selectFriAll(request,userName);
			if(rs.next())
			{
				rs=selectFriAll(request,userName);
				while(rs.next())
				{
					MyFriBean mess = new MyFriBean();
					mess.setNumber(rs.getString("idbookinformation"));
					mess.setName(rs.getString("BookName"));
					mess.setISBN(rs.getString("ISBN"));
					mess.setpublisher(rs.getString("Publisher"));
					mess.setauthor(rs.getString("Authorid"));
					mess.setdate(rs.getString("Date"));
					mess.setprice(rs.getString("Price"));
					//
					listName.add(mess);
					//System.out.println("2");
					session.setAttribute("friends345",listName);
				}
			}
			else
			{
				session.setAttribute("friends345", listName);
			}
			return "ok";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	//��ȡ����������Ʒ�����浽session
		public String myFriendssearch(HttpServletRequest request,String userName,String authorid)
		{
			try{
				ArrayList listName =null;
				HttpSession session =request.getSession();
				listName=new ArrayList();
				rs=selectauthor444(request,userName,authorid);
				if(rs.next())
				{
					rs=selectauthor444(request,userName,authorid);
					while(rs.next())
					{
						MyFriBean mess = new MyFriBean();
						mess.setName(rs.getString("BookName"));
						mess.setISBN(rs.getString("ISBN"));
						mess.setpublisher(rs.getString("Publisher"));
						mess.setauthor(rs.getString("Authorid"));
						mess.setdate(rs.getString("Date"));
						mess.setprice(rs.getString("Price"));
						//
						listName.add(mess);
						//System.out.println("2");
						session.setAttribute("friendsearch",listName);
					}
				}
				else
				{
					session.setAttribute("friendsearch", listName);
				}
				return "ok";
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
		}
		
		public String myFriendssearch446(HttpServletRequest request,String userName,String authorid,String authorname)
		{
			try{
				ArrayList listName =null;
				HttpSession session =request.getSession();
				listName=new ArrayList();
				rs=selectauthor446(request,userName,authorid,authorname);
				if(rs.next())
				{
					rs=selectauthor446(request,userName,authorid,authorname);
					while(rs.next())
					{
						MyFriBean mess = new MyFriBean();
						mess.setName(rs.getString("BookName"));
						mess.setISBN(rs.getString("ISBN"));
						mess.setpublisher(rs.getString("Publisher"));
						mess.setauthor(rs.getString("Authorid"));
						mess.setdate(rs.getString("Date"));
						mess.setprice(rs.getString("Price"));
						//
						listName.add(mess);
						//System.out.println("2");
						session.setAttribute("friendsearch446",listName);
					}
				}
				else
				{
					session.setAttribute("friendsearch446", listName);
				}
				return "ok";
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
		}
		
		public String myresultsearch(HttpServletRequest request,String userName,String ISBN)
		{
			try{
				ArrayList listName =null;
				HttpSession session =request.getSession();
				listName=new ArrayList();
				rs=selectFri(request,userName,ISBN);
				if(rs.next())
				{
					rs=selectFri(request,userName,ISBN);
					while(rs.next())
					{
						MyFriBean mess = new MyFriBean();
						mess.setNumber(rs.getString("idbookinformation"));
						mess.setName(rs.getString("BookName"));
						mess.setISBN(rs.getString("ISBN"));
						mess.setpublisher(rs.getString("Publisher"));
						mess.setauthor(rs.getString("Authorid"));
						mess.setdate(rs.getString("Date"));
						mess.setprice(rs.getString("Price"));
						//
						listName.add(mess);
						//System.out.println("2");
						session.setAttribute("friends90",listName);
					}
				}
				else
				{
					session.setAttribute("friends90", listName);
				}
				return "ok";
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
		}
		
	//��ѯ�����ճ���Ϣ��������session��
	
	
	//��ѯ�����ճ���Ϣ��������session��
		public String myDayAuthor(HttpServletRequest request,String userName,String authorid)
		{
			try{
				ArrayList listName =null;
				HttpSession session =request.getSession();
				listName=new ArrayList();
				//System.out.println("1");
				rs=selectDayAll(request,authorid);
				if(rs.next())
				{
					rs=selectDayAll(request,authorid);
					while(rs.next())
					{
						MyDayBean mess = new MyDayBean();
						mess.setName(rs.getString("AuthorName"));
						mess.setauthorid(rs.getString("Authorid"));
						mess.setauthorage(rs.getString("Age"));
						mess.setcountry(rs.getString("Country"));
						mess.setNumber(rs.getString("AuthorNumber"));
						//
						listName.add(mess);
						session.setAttribute("day",listName);
					}
				}
				else
				{
					session.setAttribute("day", listName);
				}
				return "ok";
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
		}
		
		
		public String myDayAuthor1(HttpServletRequest request,String userName)
		{
			try{
				ArrayList listName =null;
				HttpSession session =request.getSession();
				listName=new ArrayList();
				//System.out.println("1");
				rs=selectDayAlltrue(request);
				if(rs.next())
				{
					rs=selectDayAlltrue(request);
					while(rs.next())
					{
						MyDayBean mess = new MyDayBean();
						mess.setName(rs.getString("AuthorName"));
						mess.setauthorid(rs.getString("Authorid"));
						mess.setauthorage(rs.getString("Age"));
						mess.setcountry(rs.getString("Country"));
						mess.setNumber(rs.getString("AuthorNumber"));
						//
						listName.add(mess);
						session.setAttribute("day1",listName);
					}
				}
				else
				{
					session.setAttribute("day1", listName);
				}
				return "ok";
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
		}
		
		
		public ResultSet selectDayAlltrue(HttpServletRequest request)
		{
			try{
				String sql ="select * from aythorinformation ";
				//System.out.println("2");
				st = getStatement();
				return st.executeQuery(sql);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
				
			}
		}
	//��ѯ�����ճ���Ϣ�����ߣ�
	public ResultSet selectDayAll(HttpServletRequest request,String authorid)
	{
		try{
			String sql = "select * from aythorinformation where Authorid='"+authorid+"'";
			//System.out.println("2");
			st = getStatement();
			return st.executeQuery(sql);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
			
		}
	}
	
	//��ѯ��¼�������Ƿ����
	public ResultSet selectLogin(HttpServletRequest request,String userName,String password)
	{
		try{
			String sql = "select*from loginname where username=' "+userName+"' and password='"+password+"'";
			st = getStatement();
			return st.executeQuery(sql);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
			
		}
	}
	
	//���ص�¼�û����û���
	public String returnLogin(HttpServletRequest request)
	{
		String LoginName = null;
		HttpSession session = request.getSession();
		ArrayList login = (ArrayList)session.getAttribute("username");
		if(login == null || login.size() == 0)
		{
			LoginName = null;
		}
		else
		{
			for(int i= login.size()-1;i>=0;i--)
			{
				UserNameBean nm=(UserNameBean)login.get(i);
				LoginName = nm.getUserName();
			}
		}
		return LoginName;
	}
	
	//��½�û���Ϣ���浽session
	public String myLogin(HttpServletRequest request,String userName)
	{
		try{
			ArrayList listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList();
			rs=selectMess(request,userName);
			if(rs.next())
			{
				rs = selectMess(request,userName);
				while(rs.next())
				{
					UserNameBean mess = new UserNameBean();
					mess.setPassword(rs.getString("password"));
					listName.add(mess);
					session.setAttribute("userName", listName);
					
				}
			}
			else
			{
				session.setAttribute("userName", listName);
			}
			return "ok";
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	//�����Ĵ󷽷�
	public String addList(HttpServletRequest request,String userName)
	{
		String sure = null;
		String login = myLogin(request,userName);
		String mess = myMessage(request,userName);
		String fri = myFriends(request,userName);
		//String day = myDayTime(request,userName);
		//if(login.equals("ok") && mess.equals("ok") && fri.equals("ok") && day.equals("ok"))
		if(login.equals("ok") || mess.equals("ok") || fri.equals("ok")  )
		{
			sure = "ok";
		}
		else
		{
			sure = null;
		}
		return sure;
		
	}
	
	//��ѯ������Ϣ�����ؽ����rs
	public ResultSet selectMess(HttpServletRequest request,String userName)
	{
		try{
			String sql="select* from loginname where loginname='"+userName+"'";
			st = getStatement();
			return st.executeQuery(sql);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
			
		}
	}
	
	//������Ϣ
	public String myMessage(HttpServletRequest request,String userName)
	{
		try{
			ArrayList listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList();
			rs=selectMess(request,userName);
			while(rs.next())
			{
				rs = selectMess(request,userName);
				while(rs.next())
				{
					MyMessBean mess = new MyMessBean();
					//mess.setName(rs.getString(""));
					//
					listName.add(mess);
					session.setAttribute("MyMess", listName);
					
				}
			}
			
			return "ok";
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	//������ϵ�ˡ�����Ϣ���浽session��
	public String findFri(HttpServletRequest request,String userName,String name)
	{
		try{
			ArrayList listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList();
			rs=selectFri(request,userName,name);
			if(rs.next())
			{
				rs = selectFri(request,userName,name);
				while(rs.next())
				{
					MyFriBean mess = new MyFriBean();
					mess.setName(rs.getString("BookName"));
					mess.setISBN(rs.getString("ISBN"));
					mess.setpublisher(rs.getString("Publisher"));
					mess.setauthor(rs.getString("Author(ID"));
					mess.setdate(rs.getString("Date"));
					mess.setprice(rs.getString("Price"));
					listName.add(mess);
					session.setAttribute("findfriend", listName);
					
				}
			}
			else
			{
				session.setAttribute("findfriend", listName);
			}
			return "ok";
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//�Ӳ��ҵ���ϵ��session�����л�ȡ��ϵ��������������
	public String returnFri(HttpServletRequest request)
	{
		String FriendName = null;
		HttpSession session =request.getSession();
		ArrayList login =(ArrayList)session.getAttribute("findfriend");
		if(login == null || login.size() == 0)
			FriendName = null;
		else
		{
			for(int i=login.size()-1;i>=0;i--)
			{
				MyFriBean nm =(MyFriBean)login.get(i);
				FriendName = nm.getName();
			}
		}
		return FriendName;
	}
}