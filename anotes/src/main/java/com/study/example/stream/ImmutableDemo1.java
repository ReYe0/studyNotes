package com.study.example.stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImmutableDemo1 {
    public static void main(String[] args) {
//        collect(Callector collector) 收集流中的数据,放到集合中 (List set Map)
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"张无忌-男-15","张无忌-男-15","周芷若-女-14","赵缴-女-13","张强-男-20","张三丰-男-10","张翠山-男-4","张良-男-35","王二麻了-男-37","谢广坤-男-41");
        //收集List集合当中
        //需求:
        //我要把所有的男性收集起来
        List<String> newList = list.stream()
                .filter(s -> "男".equals(s.split("-")[1]))
//                .forEach(s -> System.out.println(s));
                .collect(Collectors.toList());
        System.out.println(newList);

        //收集Set集合当中
        //需求:
        //我要把所有的男性收集起来
        Set<String> newList2 = list.stream()
                .filter(s -> "男".equals(s.split("-")[1]))
                .collect(Collectors.toSet());//set可以去重
        System.out.println(newList2);
    }

    public static void  test6(){
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "水品-15", "花养-14", "赵镇-13", "张-20", "张二卡-100", "张柴山-40", "张良-35", "王二嘛子-2");
        // toArray()
        // 收集流中的数据,放到数组中
        //Object[] arr1 = list.stream().toArray();//system.out.printIn(Arrays .tostring(arr1));
        //IntFunction的泛型:具体类型的数组
        //apply的形参:流中数据的个数,要跟数组的长度保持一致
        //apply的返回值:具体类型的数组
        //方法体:就是创建数组
        //toArray方法的参数的作用: 负责创建一个指定类型的数组
        // toArray方法的底层,会依次得到流里面的每一个数据,并把数据放到数组当中
        // toArray方法的返回值: 是一个装着流里面所有数据的致组
        String[] arr = list.stream().toArray(new IntFunction<String[]>() {
            @Override
            public String[] apply(int value) {
                return new String[value];
            }
        });

        //Lambda 表达式写法
        String[] arr2 = list.stream().toArray(value -> new String[value]);
        System.out.println(Arrays.toString(arr2));
    }

    public static void test5() {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "水品-15", "花养-14", "赵镇-13", "张-20", "张二卡-100", "张柴山-40", "张良-35", "王二嘛子-2");
        //需求:只获取里面的年龄并进行打印
        //String->int
        //第一个类型：流中原本你的数据类型
        //第二个类型：要转成之后的类型。

        //apply的形参s：依次表示流里面的每一个数据
        //返回值：表示转换之后的数据

        //当map方法执行完毕之后,流上的数据就变成了整数。
        //所以在下面的forEach当中,s一次表示流里面的每一个数据,这个数据现在就是整数了。
        list.stream().map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                String[] arr = s.split("-");
                String ageString = arr[1];
                int age = Integer.parseInt(ageString);
                return age;
            }
        }).forEach(s -> System.out.println(s));

        //Lambda表达式写法
        list.stream().map(s -> Integer.parseInt(s.split("-")[1])).forEach(s -> System.out.println(s));
    }

    public static void test4() {
        Stream.of(1, 2, 3, 4, 5).forEach(s -> System.out.println(s));
        Stream.of(" ", "b", "c", "d", "e").forEach(s -> System.out.println(s));
    }

    public static void test1() {
        //1.单列集合获取stream流
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "a", "b", "c", "d", "e");//获取到一条流水线,并把集合中的数据放到流水线上
        Stream<String> stream1 = list.stream();//使用终结方法打一下流水线上的是所有数据
        stream1.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);//s:依次表示流水线上的每一个数据
            }
        });
        list.stream().forEach(s -> System.out.println(s));

    }

    public static void test2() {
        //1.创建双列集合
        HashMap<String, Integer> hm = new HashMap<>();//2.添加数据
        hm.put("aaa", 111);
        hm.put("b66", 222);
        hm.put("ccc", 333);
        hm.put("ddd", 444);
        //3.第一种获收stream流
        //hm.keySet().stream().forEach(s -> System.out.println(s));
        //4.第二种获取stream流
        hm.entrySet().stream().forEach(s -> System.out.println(s));
    }

    public static void test3() {
        //1.创建数组
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 103};
        String[] arr2 = {"a", "b", "c"};
        //2.获取stream流
        Arrays.stream(arr1).forEach(s -> System.out.println(s));
        System.out.println("=================");
        Arrays.stream(arr2).forEach(s -> System.out.println(s));
    }

}
