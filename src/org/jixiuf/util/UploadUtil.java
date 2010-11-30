package org.jixiuf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadUtil {
	Properties props = new Properties();
	// 当上传文件超过限制时设定的临时文件位置
	// private String tempPath = "";

	// 文件上传目标目录
	private String destinationPath = "";
	// 命名新文件名的策略，可选 为uuid client
	private String nameFileNameMethod = "uuid";
	// 未了防止目录 下文件名的冲突，需要将文件名进行重命令，采用UUID作为新文件名，
	// 有时为了获得文件名列表存入数据库等，故提供此Map 其key 为原文件名，value 为新文件名，即uuid
	// 可以通过getFileNameMap()获得

	// 是一串以逗号隔开的allowFileNameSuffixs= txt,exe,jpeg 或者是* 表示匹配任何后缀名
	private String allowFileNameSuffixs = "*";

	// 设置最多只允许在内存中存储的数据,单位:字节
	private Integer sizeThreshold = 4096;
	// 设置允许用户上传文件大小,单位:字节
	// 共10M
	private Long sizeMax = 10 * 1024 * 1024l;
	private Long sizeMin = 0l;

	private String fileNameEncoding = "utf-8";
	private static UploadUtil instance = new UploadUtil();

	private UploadUtil() {
		init();
		configure();

	}

	public static UploadUtil getInstance() {
		return instance;
	}

	// 判断 fileName 的类型是否在fileNameSuffix 中,如
	// fileName=a.txt fileNameSuffixs=txt,exe,jpeg
	private boolean checkFileNameSuffixMatch(String fileName,
			String fileNameSuffixs) {
		boolean match = false;

		if (fileNameSuffixs.equals("*")) {
			return true;
		}
		for (String suffix : fileNameSuffixs.split(",")) {
			match = FilenameUtils.getExtension(fileName).equalsIgnoreCase(
					suffix);
			if (match) {
				break;
			}
		}
		return match;

	}

	public String getFileNameEncoding() {
		return fileNameEncoding;
	}

	public void setFileNameEncoding(String fileNameEncoding) {
		this.fileNameEncoding = fileNameEncoding.toLowerCase();
	}

	// 从upload.properties 文件中读数据填充此类中的属性
	public UploadUtil configure() {

		return configure("upload.properties");
	}

	// 传进来配置文件的名称，位于classpath 下，默认是upload.properties
	public UploadUtil configure(String configFilePath) {

		try {
			props.load(UploadUtil.class.getClassLoader().getResourceAsStream(
					configFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// updateProp("tempPath");
		updateProp("destinationPath");
		updateProp("allowFileNameSuffixs");
		updateProp("fileNameEncoding");
		updateProp("nameFileNameMethod");
		updateFileSize("sizeThreshold");
		updateFileSize("sizeMax");
		updateFileSize("sizeMin");
		return this;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public UploadUtil setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
		return UploadUtil.getInstance();
	}

	public String getAllowFileNameSuffixs() {
		return allowFileNameSuffixs;
	}

	public UploadUtil setAllowFileNameSuffixs(String allowFileNameSuffixs) {
		this.allowFileNameSuffixs = allowFileNameSuffixs;
		return UploadUtil.getInstance();
	}

	public Integer getSizeThreshold() {
		return sizeThreshold;
	}

	public UploadUtil setSizeThreshold(Integer sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
		return UploadUtil.getInstance();
	}

	public Long getSizeMax() {
		return sizeMax;
	}

	public UploadUtil setSizeMax(Long sizeMax) {
		this.sizeMax = sizeMax;
		return UploadUtil.getInstance();
	}

	public Long getSizeMin() {
		return sizeMin;
	}

	public UploadUtil setSizeMin(Long sizeMin) {
		this.sizeMin = sizeMin;
		return UploadUtil.getInstance();
	}

	// return 是否上传成功，成功返回null，否则返回出错信息
	@SuppressWarnings("unchecked")
	public String doUpload(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Map<String, String> fileNameMap = new HashMap<String, String>();
		session.setAttribute("upload_fileName_map", fileNameMap);
		StringBuffer msg = new StringBuffer("");
		File basePath = new File(destinationPath);
		DiskFileItemFactory fileItemF = new DiskFileItemFactory(
				this.sizeThreshold, basePath);

		ServletFileUpload dfu = new ServletFileUpload(fileItemF);
		dfu.setHeaderEncoding(this.fileNameEncoding);
		dfu.setSizeMax(this.sizeMax);

		if (!basePath.exists()) {
			basePath.mkdirs();
		} else if (!basePath.isDirectory()) {
			basePath.delete();
			basePath.mkdirs();
		}
		try {
			List<FileItem> fileItems = null;
			try {

				fileItems = dfu.parseRequest(request);
			} catch (FileUploadException e) {
				return e.getMessage();
			}

			for (FileItem fileItem : fileItems) {

				// 此处处理文件上传
				if (!fileItem.isFormField()) {
					boolean validate = validate(msg, fileItem);
					if (!validate) {
						// 如果上传的文件不符合要求，不进行上传文件的处理，直接返回
						return msg.toString();
					}
					String oldFileName = fileItem.getName();
					String newFileName = reName(oldFileName);
					if (fileNameMap.containsKey(oldFileName)) {
						fileItem.delete();// 删除重复文件的临时文件
						msg.append("上传了重复的文件，不予处理");
						continue;
					} else {
						fileNameMap.put(oldFileName, newFileName);
					}

					File f = new File(basePath, newFileName);

					fileItem.write(f);

				} else { // 普通filed 不进行处理
					// do nothing
				}
			}
		} catch (FileUploadException e) {
			msg.append(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			msg.append(e.getMessage());
			e.printStackTrace();
		}

		return msg.toString();
	}

	// 根据配置文件更新此类中相应的属性
	private void updateProp(String key) {

		try {
			// 如果props 中有相应于key 的配置(在upload.properties
			// 文件中进行配置)则将此值附给this相应的属性,否则保持 初始值
			if (props.getProperty(key) != null) {
				this.getClass().getDeclaredField(key).set(this,
						props.getProperty(key));
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

	// 将24K 25M 转化为纯数字，字节为单位24*1024 25*1024*1024
	// key 为本类中的属性（也就是配置文件中的key 值 ）
	// 如 :key="sizeMax"
	private void updateFileSize(String key) {

		String value = null;
		Long size = 0L;
		try {
			value = (String) (props.getProperty(key) == null ? this.getClass()
					.getDeclaredField(key).get(this) : props.get(key));
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		}
		try {
			size = Long.parseLong(value);
		} catch (NumberFormatException e) {
			char suffix = value.charAt(value.length() - 1);
			Long sizeBuf = 0L;
			try {
				sizeBuf = Long
						.parseLong(value.substring(0, value.length() - 1));
			} catch (NumberFormatException e2) {
			}
			if (suffix == 'k' || suffix == 'K') {
				size = sizeBuf * 1024l;
			} else if (suffix == 'M' || suffix == 'm') {
				size = sizeBuf * 1024 * 1024l;
			} else if (suffix == 'g' || suffix == 'G') {
				size = sizeBuf * 1024 * 1024 * 1024l;
			}
		}

		try {
			Field key_field = this.getClass().getDeclaredField(key);
			if (key_field.getType() == Integer.class) {
				key_field.set(this, size.intValue());

			} else if (key_field.getType() == Long.class) {
				key_field.set(this, size);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

	private void init() {
		// tempPath = System.getProperty("java.io.tmpdir");
		destinationPath = System.getProperty("java.io.tmpdir");
		// fileNameMap = new HashMap<String, String>();
	}

	// 验证上传的文件是否符合要求
	private boolean validate(StringBuffer msg, FileItem fileItem) {
		String fileName = fileItem.getName();
		long fileSize = fileItem.getSize();
		boolean isAllowedToUpload = this.checkFileNameSuffixMatch(fileName,
				allowFileNameSuffixs);
		if (fileSize <= sizeMin) {
			msg.append("文件太小,或者某个上传框没有内容");
			return false;
		} else if (fileSize > sizeMax) {
			msg.append("文件太大");
			return false;
		} else if (!isAllowedToUpload) {
			msg.append("只允许上传以下类型的文件" + allowFileNameSuffixs.toString());
			return false;
		}
		return true;
	}

	public String reName(String fileName) {
		if (this.nameFileNameMethod.equalsIgnoreCase("uuid")) {

			UUID uuid = UUID.randomUUID();
			return uuid.toString() + "." + FilenameUtils.getExtension(fileName);
		} else if (this.nameFileNameMethod.equalsIgnoreCase("client")) {
			return fileName;
		}
		return fileName;

	}

	public String getNameFileNameMethod() {
		return nameFileNameMethod;
	}

	public UploadUtil setNameFileNameMethod(String nameFileNameMethod) {
		this.nameFileNameMethod = nameFileNameMethod;
		return UploadUtil.getInstance();
	}

	@SuppressWarnings("unchecked")
	// 重命名前后的文件名列表，注意,此方法必须在运行过doUpload(request)过后才有返回值
	// ，只方法存在的目的是读出重命令后的文件名，并可能将它存到数据库中
	public Map<String, String> getFileNameMap(HttpServletRequest request) {
		return (Map<String, String>) request.getSession().getAttribute(
				"upload_fileName_map");
	}

	public String[] getOldFileNames(HttpServletRequest request) {
		String[] oldFileNames = new String[getFileNameMap(request).size()];
		return getFileNameMap(request).keySet().toArray(oldFileNames);
	}

	public String[] getNewFileNames(HttpServletRequest request) {

		Map<String, String> nameMap = getFileNameMap(request);
		String[] newFileNames = new String[nameMap.size()];
		int i = 0;
		for (String oldName : getOldFileNames(request)) {
			newFileNames[i] = nameMap.get(oldName);

			i++;
		}
		return newFileNames;

	}

	// 单文件上传时，重命名后的文件名
	public String getNewFileName(HttpServletRequest request) {
		return getFileNameMap(request).get(0);
	}

	// 单文件上传时， 从客户端传过来的文件名
	public String getOldFileName(HttpServletRequest request) {
		return getOldFileNames(request)[0];
	}

	public void clearFileNameMap(HttpServletRequest request) {
		request.getSession().removeAttribute("upload_fileName_map");
	}

	public File getFile(String fileName) {
		File f = new File(destinationPath, fileName);
		return f;
	}

	public InputStream getInput(String fileName) {

		try {
			return new FileInputStream(getFile(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 判断这个文件后缀名是否允许上传
	 *
	 * @param fileNameExt
	 *            "txt" 'jpeg'
	 * @return
	 */
	public boolean isFileNameSuffixAllowed(String fileNameSuffix) {
		if (this.allowFileNameSuffixs == "*") {
			return true;
		}
		return this.allowFileNameSuffixs.contains(fileNameSuffix.toLowerCase());

	}

	/**
	 * 获得文件后缀名
	 *
	 * @param fileName
	 * @return
	 */
	public String getFileNameSuffix(String fileName) {
		return FilenameUtils.getExtension(fileName);

	}
}
