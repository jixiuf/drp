package org.jixiuf.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jixiuf.util.UploadUtil;


public class PictureServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String ext = FilenameUtils.getExtension(fileName);

		Pattern p = Pattern.compile("(jpeg)||(gif)|(png)|(bmp)",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(ext);
		if (m.matches()) {
			response.setContentType("image/" + ext);
		} else if ("jpg".equalsIgnoreCase(ext)) {
			response.setContentType("image/jpeg");
		} else {
			return;
		}
	File f = new File(UploadUtil.getInstance().getDestinationPath(),
				fileName);
	 InputStream is=new FileInputStream(f);
		IOUtils.copy(new FileInputStream(f), response.getOutputStream());
		IOUtils.closeQuietly(is);

	}

}
