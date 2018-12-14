package com.jsfztech.modules.ips.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsfztech.common.utils.OcrUtils;
import com.qcloud.image.ImageClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsfztech.modules.ips.entity.MapInfoEntity;
import com.jsfztech.modules.ips.service.MapInfoService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;


/**
 * 地图表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@RestController
@RequestMapping("/ips/mapinfo")
public class MapInfoController {
	@Autowired
	private MapInfoService mapInfoService;
	private Map<String,Integer> map = new HashMap<>();
	/*@Value("${web.upload-path}")
	private String picPath;*/
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:mapinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MapInfoEntity> mapInfoList = mapInfoService.queryList(query);
		int total = mapInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(mapInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:mapinfo:info")
	public R info(@PathVariable("id") Integer id){
		MapInfoEntity mapInfo = mapInfoService.queryObject(id);
		
		return R.ok().put("mapInfo", mapInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:mapinfo:save")
	public R save(MapInfoEntity mapInfo) throws IOException{
		System.out.println("id="+mapInfo.toString());

		mapInfoService.save(mapInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:mapinfo:update")
	public R update(@RequestBody MapInfoEntity mapInfo){
		mapInfoService.update(mapInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:mapinfo:delete")
	public R delete(@RequestBody Integer[] ids){
		mapInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	/**
	 * 预览图片
	 */
	@RequestMapping("/preview")

	public R preview(HttpServletRequest request, MultipartFile file) throws IOException{
		System.out.println("id="+request.getParameter("id"));
		System.out.println("=="+file.getName());//文件名字
		System.out.println("1="+file.getContentType());
		System.out.println("2="+file.getOriginalFilename());
		System.out.println("3="+file.getSize());
		System.out.println("4="+file.getInputStream());
        String name = file.getOriginalFilename();
		String path1=request.getSession().getServletContext().getRealPath("/")+"upload/";
		/*System.out.println(path1);*/
		String appId = "1257231901";
		String secretId = "AKIDyra7YnHwnU0PdmOhPzhKpDNWUI5GsDKL";
		String secretKey = "0tdBdu1xyugMtEPRM6gKo7LOFuNfs14w";
		String bucketName = "bucketName";
		ImageClient imageClient = new ImageClient(appId,secretId,secretKey,ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
		OcrUtils ocrUtils = new OcrUtils();

		String picPath = "D:/Temp/";
		File f = new File(picPath);
		if(!f.exists()){
			f.mkdir();
		}
		//图片上传成功后，将图片的地址写到数据库
		String filePath = "E:\\Images\\pictures";//保存图片的路径
		//获取原始图片的拓展名
		String originalFilename = file.getOriginalFilename();
		//新的文件名字
		String newFileName = UUID.randomUUID()+originalFilename;

		//封装上传文件位置的全路径
		File targetFile = new File(picPath,newFileName);
		//判断地址是否存在
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdir();
		}
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		//把本地文件上传到封装上传文件位置的全路径
		file.transferTo(targetFile);
		String ret = ocrUtils.ocrIdCard(imageClient,bucketName,picPath,newFileName);
		JSONObject js = JSON.parseObject(ret);
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(targetFile));
			if (image != null) {//如果image=null 表示上传的不是图片格式
				/*System.out.println(image.getWidth());//获取图片宽度，单位px
				System.out.println(image.getHeight());//获取图片高度，单位px*/
				map.put("width",image.getWidth());
				map.put("height",image.getHeight());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = picPath+newFileName;
		System.out.println("图片上传成功");
        R r = R.ok();
		r.put("ret",js);
		r.put("url",path);
		return r;
	}
}
