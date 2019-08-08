package com.movitech.mbox.modules.wdt.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.movitech.mbox.common.mapper.JsonMapper;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.util.ByteToInputStream;
import com.movitech.mbox.modules.util.FTPUtil;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;
import com.movitech.mbox.modules.wdt.service.WdtTFileInfoService;

import net.coobird.thumbnailator.Thumbnails;
@Controller
public class FileController extends BaseController {
	
	@Autowired
	private WdtTFileInfoService wdtTFileInfoService;
    @RequestMapping(value = "${adminPath}/upload")  
    public void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response ) throws IOException {  
    	String encoding = "UTF-8";
		try {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html; charset=" + encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
    	HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
    	String fileOldName =file.getOriginalFilename();
    	String fileType =fileOldName.substring(fileOldName.lastIndexOf("."), fileOldName.length());
    	String newfileName= IdGen.uuid();
        try {  
            FTPUtil ftpUtil=new FTPUtil();
            String fileNewPath = FTPUtil.FTP_BASE+"/"+newfileName+fileType;
            
            //如果是图片，进行压缩处理 --start
            long size = 0;
            InputStream inputStream = file.getInputStream();
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	if(fileType.equals(".jpg")||fileType.equals(".jpeg")||fileType.equals(".png")){
        		Thumbnails.of(inputStream).scale(1f).outputQuality(0.15f).toOutputStream(os);
        		byte[] inByte = os.toByteArray();
        		inputStream = ByteToInputStream.byte2Input(inByte);
        		size = inByte.length;
        	}else{
        		size = file.getSize();
        	}
        	//如果是图片，进行压缩处理 --end
        	
            ftpUtil.put(inputStream,fileNewPath);
            
            Map<String,Object> modelMap = new HashMap<String,Object>();
             modelMap.put("fileName", fileOldName);
             modelMap.put("newfileName", newfileName+fileType);
             modelMap.put("id", newfileName);
             WdtTFileInfo wdtTFileInfo = new WdtTFileInfo();
             wdtTFileInfo.setId(newfileName);
             wdtTFileInfo.setFileType(fileType);
             wdtTFileInfo.setFileName(newfileName);
             wdtTFileInfo.setFileOldName(fileOldName);
             wdtTFileInfo.setFilePath(newfileName+fileType);
             wdtTFileInfo.setFileSize(String.valueOf(size));
             wdtTFileInfoService.insert(wdtTFileInfo);
             result.setResult(true);
             result.setValue(modelMap);
             
        } catch (IOException e) {  
            e.printStackTrace();  
            result.setResult(false);
        }
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(JsonMapper.toJsonString(result));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 多文件上传
     * @param file
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "${adminPath}/multiUpload")  
    public void multiUpload(@RequestParam(value = "file", required = false) MultipartFile[] files, HttpServletRequest request,HttpServletResponse response ) throws IOException {  
    	String encoding = "UTF-8";
		try {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html; charset=" + encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		result.setResult(false);
		try { 
	    	List<Map<String,Object> > list = new LinkedList<Map<String,Object>>();
	    	if(files!= null &&files.length>0){
	    		for (MultipartFile multipartFile : files) {
	    			String fileOldName =multipartFile.getOriginalFilename();
	    	    	String fileType =fileOldName.substring(fileOldName.lastIndexOf("."), fileOldName.length());
	    	    	String newfileName= IdGen.uuid();
	    	        
    	            FTPUtil ftpUtil=new FTPUtil();
    	            String fileNewPath = FTPUtil.FTP_BASE+"/"+newfileName+fileType;
    	            
    	            //如果是图片，进行压缩处理 --start
    	            long size = 0;
    	            InputStream inputStream = multipartFile.getInputStream();
                	ByteArrayOutputStream os = new ByteArrayOutputStream();
                	if(fileType.equals(".jpg")||fileType.equals(".jpeg")||fileType.equals(".png")){
                		Thumbnails.of(inputStream).scale(1f).outputQuality(0.15f).toOutputStream(os);
                		byte[] inByte = os.toByteArray();
                		inputStream = ByteToInputStream.byte2Input(inByte);
                		size = inByte.length;
                	}else{
                		size = multipartFile.getSize();
                	}
                	//如果是图片，进行压缩处理 --end
    	            
    	            ftpUtil.put(inputStream,fileNewPath);
    	            Map<String,Object> modelMap = new HashMap<String,Object>();
    	             modelMap.put("fileName", fileOldName);
    	             modelMap.put("newfileName", newfileName+fileType);
    	             modelMap.put("id", newfileName);
    	             WdtTFileInfo wdtTFileInfo = new WdtTFileInfo();
    	             wdtTFileInfo.setId(newfileName);
    	             wdtTFileInfo.setFileType(fileType);
    	             wdtTFileInfo.setFileName(newfileName);
    	             wdtTFileInfo.setFileOldName(fileOldName);
    	             wdtTFileInfo.setFilePath(newfileName+fileType);
    	             wdtTFileInfo.setFileSize(String.valueOf(size));
    	             wdtTFileInfoService.insert(wdtTFileInfo);
    	             list.add(modelMap);	
				}
	    		result.setResult(true);
		    	Map<String,Object> value = new HashMap<String,Object>();
		    	value.put("list", list);
		        result.setValue(value);
	    	}
    	
	    	
		 } catch (IOException e) {  
	            e.printStackTrace();  
	            result.setResult(false);
        }finally{
        	PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write(JsonMapper.toJsonString(result));
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	
    }
    /**
     * 下载文件
     */
    @RequestMapping(value = "${adminPath}/download")  
    @SuppressWarnings("unchecked")
    public void download(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
    	
        String path = request.getParameter("path");
        WdtTFileInfo fileByPath = wdtTFileInfoService.getFileByPath(path);
        response.setContentType("application/x-msdownload");
    	response.setHeader("Content-type","application/x-msdownload");
    	response.setHeader("Accept-Ranges","bytes");
    	response.setHeader("Content-Disposition","attachment; filename="+ java.net.URLEncoder.encode(fileByPath==null?path:fileByPath.getFileOldName(), "UTF-8"));
    	response.setHeader("Content-Length",fileByPath.getFileSize());
    	FTPUtil ftpUtil=new FTPUtil();
        ftpUtil.get(FTPUtil.FTP_BASE+"/"+path,response.getOutputStream());
              /*//得到文件大小
              int i=hFile.available();
              byte data[]=new byte[i];
              //读数据
              hFile.read(data);
              //得到向客户端输出二进制数据的对象
              OutputStream toClient=response.getOutputStream();
              //输出数据
              toClient.write(data);
              toClient.flush();
              toClient.close();
              hFile.close();*/
        
    }
    @RequestMapping(value = "${adminPath}/show")  
    @SuppressWarnings("unchecked")
    public void show(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
    	
        String path = request.getParameter("path");
        WdtTFileInfo fileByPath = wdtTFileInfoService.getFileByPath(path);
        FTPUtil ftpUtil=new FTPUtil();
        ftpUtil.get(FTPUtil.FTP_BASE+"/"+path,response.getOutputStream()); 
    }
    
    @RequestMapping(value = "${adminPath}/delete")  
    @ResponseBody
    public HResult<Boolean> delete(String path)
            throws  IOException {
    	try{
    		FTPUtil ftpUtil=new FTPUtil();
            ftpUtil.delete(FTPUtil.FTP_BASE+File.separator+path);
            return  new HResult(true);
    	}catch(Exception e){
    		e.printStackTrace();
    		return new HResult(false);
    	}      
        
    }
    /**
     * 上传单个图片
     * */
    /*public PictureWrapper uploadFileOne(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        //String fileName = (String)request.getAttribute("filename");
        PictureWrapper wrapper=new PictureWrapper();
        FTPUtil ftpUtil=new FTPUtil();
        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(fileNames.next());
        //如果使用firebug，或者chrome的开发者工具，可以看到，这个文件上传工具发送了两个文件名
        //分别是：name="Filedata"; filename="AVScanner.ini"
        //用这两个文件名获得文件内容都可以，只不过第一个没有后缀，需要自己处理
        //第二个是原始的文件名，但是这个文件名有可能是上传文件时的全路径
        //例如  C:/testssh/a.log，如果是全路径的话，也需要处理
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String pathRoot = request.getSession().getServletContext().getRealPath("");

        //String fileAlias = multipartFile.getName();
        //System.out.println("Spring MVC获得的文件名：" + fileAlias);
        //获得文件原始名称
        String name = multipartFile.getOriginalFilename();
        String[] str=name.split("\\.");
        String subprex=str[str.length-1];
        //System.out.println(multipartFile.getContentType());
        //String path=pathRoot+"/static/images/"+uuid+"."+subprex;
        //veFile(path, multipartFile.getBytes());
        ftpUtil.put(multipartFile.getInputStream(),FTPUtil.FTP_BASE+"/"+uuid+"."+subprex);
        //List<PicturesMap> picturesMaplist=new ArrayList<>();
       // new File(path).deleteOnExit();
        //String picpathftp= FTPUtil.FTP_BASE+"/"+uuid+"."+subprex;
        PicturesMap picturesMap=new PicturesMap();
        picturesMap.setPictureId(uuid+"."+subprex);
        picturesMap.setPictureOrder(10);
        picturesMap.setPicturesMapId(UUID.randomUUID().toString());
        picturesMap.setCreateDate(new Date());
        picturesMap.setCreateBy(new User(UserUtils.getUser().getId()));
        picturesMap.setDelFlag("0");
        pictureMapDao.insert(picturesMap);
        wrapper.setMessage("ok");
        wrapper.setStatus(200);
        Map<String,Object> map=new HashMap<>();
        map.put("pictureId",uuid+"."+subprex);map.put("url",picpathftp);
        wrapper.setValue(map);
        return  wrapper;
    }*/
    @RequestMapping(value = "${adminPath}/uploadDemo", method = RequestMethod.GET)
    public String index() {
         return "modules/wdt/uploadDemo";
    } 
}
