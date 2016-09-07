package com.chinaredstar.longyan.common;


import java.lang.reflect.Field;

/**
 * Created by lenovo on 2016/6/13.
 */
public class EntityUtil {

    /**
     * @param o 操作对象
     * @param c 操作类，用于获取类中的方法
     * @return
     * @MethodName : getString
     * @Description : 获取类中所有属性及属性值
     */
    public static String getString(Object o, Class<?> c) {
        String result = c.getSimpleName() + ":";

        // 获取父类，判断是否为实体类
        if (c.getSuperclass().getName().indexOf("entity") >= 0) {
            result += "\n<" + getString(o, c.getSuperclass()) + ">,\n";
        }

        // 获取类中的所有定义字段
        Field[] fields = c.getDeclaredFields();

        // 循环遍历字段，获取字段对应的属性值
        for (Field field : fields) {
            // 如果不为空，设置可见性，然后返回
            field.setAccessible(true);

            try {
                // 设置字段可见，即可用get方法获取属性值。
//                result += field.getName() + "=" + field.get(o) + ",\n";
                result += field.getName() + ",";
            } catch (Exception e) {
                // System.out.println("error--------"+methodName+".Reason is:"+e.getMessage());
            }
        }
        if (result.indexOf(",") >= 0) result = result.substring(0, result.length() - 2);
        return result;
    }

//    public static void main(String[] args) {
//        System.out.println(EntityUtil.getString(new RedstarCommunity(), RedstarCommunity.class));
//    }
}
