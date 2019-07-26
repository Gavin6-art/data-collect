package com.iwhale.congestion.index.dto;

import lombok.Data;

/**
 * 微信上报事故数据
 * Created by sll on 2019/6/28.
 */
@Data
public class AcciInfoDataDto {

    //事故编号
    private String xh;

    //上报时间
    private String sbsj;

    //事故地点
    private String sgdd;

    //事故经度
    private String sgjd;

    //事故纬度
    private String sgwd;

    //上报用户
    private String sbyh;

    //当事人手机号码
    private String sjhm;

    //备注/民警警号或协警警号
    private String bz;

    //预留字段1
    private String ylzd1;

    //预留字段2
    private String ylzd2;

    //预留字段3
    private String ylzd3;

    //预留字段4
    private String ylzd4;

    //预留字段5
    private String ylzd5;

    //事故发生行政区域
    private String sgxzq;

    //微信处理流程 普通户用：0确认功能 1选择行政 2手机号 3前景照 4后景照 5损坏照 6结束； 民警：0确认功能 1选择行政 2警号 3手机号 4前景照 5后景照 6损坏照 7证件保险单照 8认定意见 9结束
    private String cllc;

    //民警认定意见
    private String rdyj;

}
