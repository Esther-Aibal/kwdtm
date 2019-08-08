/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.movitech.mbox.common.utils.FileUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.util.ByteToInputStream;
import com.movitech.mbox.modules.util.FTPUtil;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;
import com.movitech.mbox.modules.wdt.service.WdtTFileInfoService;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

@Controller
@RequestMapping({ "${adminPath}/ajax/fileupload" })
public class UediterController  extends BaseController{
	
	@Value("${adminPath}")
	String adminPath;
	@Autowired
	private WdtTFileInfoService wdtTFileInfoService;
    @RequestMapping(value = { "/test" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET }, produces = { "text/html" })
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = "aaaa";
        return result;
    }

    @RequestMapping(value = { "/config" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET }, produces = { "text/html" })
    @ResponseBody
    public String config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = "";
        try {
            result = "{ \"imageFieldName\": \"upfile\",     \"imageMaxSize\": 2048000,     \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\"],     \"imageCompressEnable\": true,     \"imageCompressBorder\": 400,     \"imageInsertAlign\": \"none\",     \"imageUrlPrefix\":\""+request.getContextPath()+"\",     \"imagePathFormat\": \"/static/ueditor/image/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"scrawlActionName\": \"uploadscrawl\",     \"scrawlFieldName\": \"upfile\",     \"scrawlPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"scrawlMaxSize\": 2048000,     \"scrawlUrlPrefix\": \"\",     \"scrawlInsertAlign\": \"none\",     \"snapscreenActionName\": \"uploadimage\",     \"snapscreenPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"snapscreenUrlPrefix\": \"\",     \"snapscreenInsertAlign\": \"none\",     \"catcherLocalDomain\": [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],     \"catcherActionName\": \"catchimage\",     \"catcherFieldName\": \"source\",     \"catcherPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"catcherUrlPrefix\": \"\",     \"catcherMaxSize\": 2048000,     \"catcherAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],     \"videoActionName\": \"uploadvideo\",     \"videoFieldName\": \"upfile\",     \"videoPathFormat\": \"/static/ueditor/video/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"videoUrlPrefix\": \"/eTicketing\",     \"videoMaxSize\": 512000000,     \"videoAllowFiles\": [         \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",         \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"],   "
            		+ "  \"fileActionName\": \""+request.getContextPath()+adminPath+"/ajax/fileupload/img/uploadFile\",     \"fileFieldName\": \"upfile\",     \"filePathFormat\": \"/static/ueditor/file/{yyyy}{mm}{dd}/{time}{rand:6}\",     \"fileUrlPrefix\": \""+request.getContextPath()+"\",     \"fileMaxSize\": 51200000,     \"fileAllowFiles\": [         \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",         \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",         \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",         \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",         \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"     ],     \"imageManagerActionName\": \""+request.getContextPath()+adminPath+"/ajax/fileupload/img/uploadMuImage\",     \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\",     \"imageManagerListSize\": 20,     \"imageManagerUrlPrefix\": \""+request.getContextPath()+"\",     \"imageManagerInsertAlign\": \"none\",     \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],        \"fileManagerActionName\": \"listfile\",     \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\",     \"fileManagerUrlPrefix\": \"\",     \"fileManagerListSize\": 20,     \"fileManagerAllowFiles\": [         \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",         \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",         \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",         \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",         \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"     ] }";// config(request);
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    public String config(HttpServletRequest request) {
        String valueString = "";
        try {
            String url = UediterController.class.getClassLoader().getResource("config.json").getFile();
            valueString = FileUtils.readFileToString(new File(url), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueString;
    }

    @ResponseBody
    @RequestMapping(value = { "/img/upload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "text/html;charset=UTF-8" })
    public String imgUpload(MultipartFile img,HttpServletRequest request,HttpServletResponse response) {
    	String encoding = "UTF-8";
		try {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/plain; charset=" + encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
    	int luma=request.getHeader("User-Agent").toUpperCase().indexOf("MSIE");
    	String result = "";
        try {
            result = uploadImage(img,0,luma).toString();
        } catch (Exception e) {
            result = "";
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = { "/img/uploadFile" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, produces = { "text/html;charset=UTF-8" })
    public String imgUploadFile(@RequestParam(value = "file", required = false)MultipartFile upfile, HttpServletRequest request,HttpServletResponse response) {
    	String encoding = "UTF-8";
		try {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/plain; charset=" + encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
    	int luma=request.getHeader("User-Agent").toUpperCase().indexOf("MSIE");
    	System.out.println("乱码"+request.getHeader("User-Agent").toUpperCase().indexOf("MSIE"));
    	JSONObject result =new JSONObject();
        try {
        	List<MultipartFile> muFiles = getMultipartFileList(request);
        	if(muFiles!=null && muFiles.size()>0){
        		for (MultipartFile multipartFile : muFiles) {
        			JSONObject json = uploadImage(multipartFile,1,luma);
        			result =json;
				}
        	}
        } catch (Exception e) {
            
        }
        return result.toString();
    }
    protected List<MultipartFile> getMultipartFileList(  
            HttpServletRequest request) {  
        List<MultipartFile> files = new ArrayList<MultipartFile>();  
        try {  
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
                    request.getSession().getServletContext());  
            if (request instanceof MultipartHttpServletRequest) {  
                // 将request变成多部分request  
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
                Iterator<String> iter = multiRequest.getFileNames();  
                // 检查form中是否有enctype="multipart/form-data"  
                if (multipartResolver.isMultipart(request) && iter.hasNext()) {  
                    // 获取multiRequest 中所有的文件名  
                    while (iter.hasNext()) {  
                        // 一次遍历所有文件  
                        // MultipartFile file =  
                        // multiRequest.getFile(iter.next().toString());  
                        // if (file != null) {  
                        // files.add(file);  
                        // }  
                        // 适配名字重复的文件  
                        List<MultipartFile> fileRows = multiRequest  
                                .getFiles(iter.next().toString());  
                        if (fileRows != null && fileRows.size() != 0) {  
                            for (MultipartFile file : fileRows) {  
                                if (file != null && !file.isEmpty()) {  
                                    files.add(file);  
                                }  
                            }  
                        }  
                    }  
                }  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();
        }  
        return files;  
    }  
    /**
     * 上传到
     * @param multipartFile
     * @return
     * @throws Exception
     */
    private JSONObject uploadImage(MultipartFile multipartFile,int type,int luma) throws Exception {
        try {
        	 
            String fileOldName=multipartFile.getOriginalFilename();//URLEncoder.encode(multipartFile.getOriginalFilename());
            if (luma > 0) {  
            	//fileOldName = URLEncoder.encode(fileOldName, "UTF-8");  
        	} else {  
        		//fileOldName = new String(fileOldName.getBytes("UTF-8"), "ISO8859-1");  
        	}
            String fileExtension = FileUtils.getExtension(fileOldName);
            InputStream inputStream = null;
            long size = 0;
        	String fileType =fileOldName.substring(fileOldName.lastIndexOf("."), fileOldName.length());
        	String newfileName= IdGen.uuid();
            try {
                inputStream = multipartFile.getInputStream();
                FTPUtil ftpUtil=new FTPUtil();
                String fileNewPath = FTPUtil.FTP_BASE+"/"+newfileName+fileType;
                //如果是图片，进行压缩处理 --start
            	ByteArrayOutputStream os = new ByteArrayOutputStream();
            	if(fileExtension.equals(".jpg")||fileExtension.equals(".jpeg")||fileExtension.equals(".png")){
            		Thumbnails.of(inputStream).scale(1f).outputQuality(0.15f).toOutputStream(os);
            		byte[] inByte = os.toByteArray();
            		inputStream = ByteToInputStream.byte2Input(inByte);
            		size = inByte.length;
            	}else{
            		size = multipartFile.getSize();
            	}
            	//如果是图片，进行压缩处理 --end
            	ftpUtil.put(inputStream,fileNewPath);  
                inputStream.close();
                WdtTFileInfo wdtTFileInfo = new WdtTFileInfo();
                wdtTFileInfo.setId(newfileName);
                wdtTFileInfo.setFileType(fileType);
                wdtTFileInfo.setFileName(newfileName);
                wdtTFileInfo.setFileOldName(fileOldName);
                wdtTFileInfo.setFilePath(newfileName+fileType);
                wdtTFileInfo.setFileSize(String.valueOf(size));
                wdtTFileInfoService.insert(wdtTFileInfo);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if(inputStream!=null){
                        inputStream.close();
                        inputStream = null;
                    }
                } catch (IOException e) {
                }
            }
            
            JSONObject json =new JSONObject();
        	json.put("state", "SUCCESS");
        	json.put("size", String.valueOf(multipartFile.getSize()));
        	json.put("title", newfileName+fileType);
        	if(type==0){
        		json.put("url", adminPath+"/show?path="+ newfileName+fileType);
        	}else{
        		json.put("url", adminPath+"/download?path="+ newfileName+fileType);
        	}
        	
        	json.put("type", fileExtension.replace(".", ""));
        	
        	json.put("original", URLEncoder.encode(fileOldName, "UTF-8"));
            return json;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }
    public static boolean isImage(InputStream inputStream){  
        ImageInputStream iis = null;  
        try {  
            iis = ImageIO.createImageInputStream(inputStream);  
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);   
            if (iter.hasNext()) {//文件不是图片   
                return true;  
            }   
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(iis!=null){  
                try {  
                    iis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return false;  
    }  
    
}
