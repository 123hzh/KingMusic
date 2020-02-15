package com.km.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.km.model.Music;
import com.km.service.MusicService;


/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/PageServlet")
public class PageServlet extends BaseServlet {
	private MusicService service=new MusicService();

	//��ѯ����ͼ��
	public void queryMusic(HttpServletRequest request,HttpServletResponse response){
		int pageindex=1;//ҳ��
		String index=request.getParameter("pageindex");
		if(index!=null){
			pageindex=Integer.parseInt(index);//��stringҳ��תΪint
			
		}
		
		//��ȡ��ѯ����
		String name=request.getParameter("name");
		Music music=new Music();
		music.setName(name);
		music.setPageindex(pageindex);
		int totalPage=service.getAllCount();
		List musics=service.queryMusic(music);
		request.setAttribute("musics", musics);//��musics����request��
		try {
			//����ǰҳ�봫��book.jsp
			request.setAttribute("pageindex", pageindex);
			request.setAttribute("totalPage", totalPage);
			request.getRequestDispatcher("query.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//��ѯָ��id��name��ͼ��
	public void queryMusic2(HttpServletRequest request,HttpServletResponse response){
		Music music=new Music();
		music.setPageindex(1);
		List musics=service.queryMusic(music);
		
		request.setAttribute("musics", musics);//��books����request��
		try {
			request.getRequestDispatcher("query.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

/*	public void updateBook(HttpServletRequest request,HttpServletResponse response){
		Book book=new Book();
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String author=request.getParameter("author");
		String price=request.getParameter("price");
		String des=request.getParameter("des");
		book.setId(id);
		book.setName(name);
		book.setAuthor(author);
		book.setPrice(Double.parseDouble(price));//���ַ���תdouble����
		book.setDes(des);
		Conversion.convert(book, request);
		service.updateBook(book);
		queryBook2(request,response);
	}*/
	

}
