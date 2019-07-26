package com.iwhale.congestion.index.dto;

import lombok.Data;

/**
 * 违法数据
 * Created by sll on 2019/6/28.
 */
@Data
public class VioDataDto {

    //流水号
    private String lsh;

    private String xh;

    //校验状态
    private String jyzt;

    //违法时间
    private String wfsj;

    //违法地点
    private String wfdd;

    //违法地址
    private String wfdz;

    //违法速度
    private String wfsd;

    //违法限速
    private String wfxs;

    //违法类型
    private String wflx;

    //采集机构
    private String cjjg;

    private String cjmj;

    //数据来源
    private String sjly;

    //号牌号码
    private String hphm;

    //号牌种类
    private String hpzl;

    //设备编号
    private String sbbh;

    private String ftp;

    //FTP地址
    private String ftpurl;

    private String lxid;

    private String fx;

    private String zqmj;

    private String dljg;

    //文件路径
    private String filepath1;

    private String filepath2;

    private String filepath3;

    private String filepath4;

    private String filepath5;

    private String ftpzt;

    private String wzsj;

    //图片时间
    private String jpgsj;

    //上传用户
    private String upuser;

    private String jpgzt;

    private String spotsn;

    private String zjlb1;

    private String zjlb2;

    private String zjlb3;

    private String zjlb4;

    private String zjlb5;

    private String ycsj;

    private Double sbjd;

    private Double sbwd;

}
