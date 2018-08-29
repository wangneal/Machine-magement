package com.bcfou.business;

import com.bcfou.entity.Machine;
import com.bcfou.repository.MachineRepository;
import com.bcfou.utils.FontImageUtil;
import com.bcfou.utils.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 二维码生成测试类
 * @author 编程否
 * @date 2018/8/27 0:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class QRCodeCreate {
    /** 你的域名 如果是https请写https协议 */
    private static final String MAIN_DOMAIN = "http://www.bcfou.com";
    /** 要存放的物理磁盘位置 */
    private static final String FILE_Path = "e:\\2wima";
    /** LOGO 图片所在缓存位置，即我们看到的二维码中间的图片 */
    private static final String LOGO_PATH = "e:\\2wima\\logo\\";
    @Autowired
    private  MachineRepository machineRepository;
    //TODO 这个方法有一个bug，生成的二维码中间的文字过小
    @Test
    public  void create() throws Exception {
        List<Machine> machineList = machineRepository.findAll();
        long max = machineRepository.count();
        //获取ID
        List<Long> ids = machineList.stream()
                .map(Machine::getMachinesId)
                .collect(Collectors.toList());
        //获取设备编号
        List<String> types = machineList.stream()
                .map(Machine::getMachinesType)
                .collect(Collectors.toList());
        //获取设备名称
        List<String> names = machineList.stream()
                .map(Machine::getMachinesName)
                .collect(Collectors.toList());
        String text = null;
        for (int i=0; i < max; i++){
            //这里使用的数据库中ID，和设备编号名称等是匹配的
             text = MAIN_DOMAIN + "/order/" + ids.get(i);
            //为了避免重复在文件名前面加上随机字符串
            String fileName = RandomStringUtils.random(10,true, true)+ "_" + types.get(i);
            //生成logo,此处可以将logo换成设备照片,不过需要先将文件名进行编号 --> 与数据库中的ID一致
            FontImageUtil.getImage(names.get(i), names.get(i), 100,100,LOGO_PATH);
            String logoPath = LOGO_PATH + names.get(i) + ".jpg";
            //生成二维码
            QRCodeUtil.encode(text, logoPath, FILE_Path, fileName, false);
            //生成二维码后将logo删除
            File file = new File(logoPath);
            if(!file.exists()){
                log.info("删除文件失败:" + logoPath + "不存在！");
            }else {
                if (file.isFile()){
                    boolean b = file.delete();
                    if (b)
                        log.info("删除文件：" + logoPath + "成功！");
                }
            }
        }
        log.info("生成二维码成功！");
    }
}
