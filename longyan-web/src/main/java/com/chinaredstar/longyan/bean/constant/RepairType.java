package com.chinaredstar.longyan.bean.constant;

/**
 * 报修类型
 * Created by xiaobc on 2015/8/18.
 */
public enum RepairType {

    PersonRepair("个人报修"),
    PublicRepair("公共报修"),
    PersonRepair_Merchant("个人报修--开发商维保"),
    PublicRepair_Merchant("公共报修--开发商维保"),
    //个人报修类型
    Guandao("管道疏通"),
    Anzhuang("安装/拆卸"),
    Fangwu("房屋维修"),
    Shuidian("水电维修"),
    Kaisuo("开锁/换锁"),
    Jiadian("家电维修"),
    //公共报修类型
    Zhaoming("照明设施"),
    Zhufang("住房主体"),
    Dianti("电梯空调"),
    Gongshui("供水设施"),
    Qita("其他公共设施"),
    Menchuang("门窗维修");

    private String type;

    private RepairType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RepairType[] getTypes(){
        return RepairType.values();
    }
/*
    public static void main(String []args){
        System.out.println(RepairType.values()[0].getType());
    }*/

}
