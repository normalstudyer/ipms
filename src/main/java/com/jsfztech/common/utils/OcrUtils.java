package com.jsfztech.common.utils;

import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.request.IdcardDetectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/31.
 * 为什么可以这样识别图片中的文字呢？
 */
public class OcrUtils {
    String appId = "1257231901";
    String secretId = "AKIDyra7YnHwnU0PdmOhPzhKpDNWUI5GsDKL";
    String secretKey = "0tdBdu1xyugMtEPRM6gKo7LOFuNfs14w";
    String bucketName = "bucketName";
    MultipartFile multipartFile =null;
    ImageClient imageClient = new ImageClient(appId,secretId,secretKey,ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
    public void setFile(MultipartFile multipartFile){
        this.multipartFile = multipartFile;
    }

    /**
     * 身份证ocr识别操作
     */
    public String ocrIdCard(ImageClient imageClient, String bucketName, String picPath, String fileName) {
        String ret = null;
        // 1. url方式,识别身份证正面
        /*System.out.println("====================================================");
        String[] idcardUrlList = new String[2];
        idcardUrlList[0] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_01.jpg";
        idcardUrlList[1] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_02.jpg";
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 0);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        idcardUrlList[0] = "https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/314e251f95cad1c89e04bea2763e6709c83d51f3.jpg";
        idcardUrlList[1] = "http://image2.sina.com.cn/dy/c/2004-03-29/U48P1T1D3073262F23DT20040329135445.jpg";
        idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 1);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);*/

        //2. 图片内容方式,识别身份证正面
        System.out.println("====================================================");
        File[] idcardImageList = new File[1];
        idcardImageList[0] = new File(picPath,fileName);

        //idcardImageList[1] = new File("assets", "icon_id_02.jpg");
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName,idcardImageList, 0);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);
        //System.out.println("id:"+ret.indexOf("'id'"));
        return ret;
        //识别身份证反面
        /*idcardImageList[0] = new File(multipartFile.getName());
        //idcardImageList[1] = new File("assets", "icon_id_04.jpg");
        idReq = new IdcardDetectRequest(bucketName,  idcardImageList, 1);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);*/
    }
}
