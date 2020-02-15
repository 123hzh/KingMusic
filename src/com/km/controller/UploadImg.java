package com.km.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.km.util.JdbcUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;


@WebServlet("/UploadImg")
public class UploadImg extends HttpServlet {
	JdbcUtil jdbc=new JdbcUtil();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream ins = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			// ����һ��input��ǩ��Ӧһ��FileItem
			List<FileItem> items = upload.parseRequest(request);
			// ѭ������ÿ�����򣬲���������Ӧ����
			for (FileItem item : items) {
				// ��ͨ�ı���
				if (item==null) {
					request.getRequestDispatcher("homepage.jsp").forward(request, response);
				} else {
					ins = item.getInputStream();// ��ȡ�ļ��������������ݣ�
					String filename = item.getName();
					//�ļ���

					// 1 ��ʼ���û������Ϣ(secretId, secretKey)
					COSCredentials cred = new BasicCOSCredentials("AKIDymV4S0ZTyLYZVHqrdhfZIuLl5IevkykM",
							"eMy1ucBZq1DNQLUUN8jEcufR4EE2f9h8");
					// 2 ����bucket������, COS����ļ�������
					// https://cloud.tencent.com/document/product/436/6224
					ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
					// 3 ����cos�ͻ���
					COSClient cosclient = new COSClient(cred, clientConfig);
					// bucket����������Ϊ{name}-{appid} ���˴���д�Ĵ洢Ͱ���Ʊ���Ϊ�˸�ʽ
					String bucketName = "ssss-999-1257238099";

					String key = item.getName();

					ObjectMetadata objectMetadata = new ObjectMetadata();
					// ��������������Ϊ 500
					objectMetadata.setContentLength(item.getSize());
					PutObjectResult putObjectResult = cosclient.putObject(bucketName, key, ins, objectMetadata);

					// ����һ�����е���ʱ�����Ƶ�����
					COSCredentials creded = new AnonymousCOSCredentials();
					ClientConfig clientConfiged = new ClientConfig(new Region("ap-beijing"));
					COSClient coscliented = new COSClient(creded, clientConfiged);
					String name = filename;
					GeneratePresignedUrlRequest req =
					        new GeneratePresignedUrlRequest(bucketName, name, HttpMethodName.GET);
					URL url = coscliented.generatePresignedUrl(req);

					String img = url.toString();
					request.getSession().setAttribute("head_img", img);
					request.getRequestDispatcher("homepage.jsp").forward(request, response);
					coscliented.shutdown();
					//�������ӵ�ַ
					String username = (String)request.getSession().getAttribute("username");
					String sql="update tb_users set head=? where username=?";
					jdbc.executeUpdate(sql,img,username);
					/*boolean s=update_img(src,username);*/

				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ins != null) {
				ins.close();
			}

		}
	}
	public boolean add_music(String username,String src) {
		try {
			/*String sql="update tb_users set src=? where username=?";
			int rs=jdbc.add(sql, src, username);
			
			while(rs!=0){
				return true;
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
