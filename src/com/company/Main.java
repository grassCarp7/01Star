package com.company;
import com.company.entity.References;

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        showLoginPage();
    }
    public static int selectPrimaryKey(int uid)
    {
        //查找对应下标，下标=主键
        //下标从0开始
        int length = References.ids.length;
        int index = -1;
        for(int i = 0;i < length;i++)
        {
            if(uid == References.ids[i])
            {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
    功能: 根据id 查询用户是否存在, 如果存在返回用户名,  如果不存在返回空
    参数:
        uid: 用户id
    返回值:
        如果用户存在, 返回用户名.
        如果用户不存在, 返回NULL
*/
    public static String selectUserById(int uid)
    {
        int index = selectPrimaryKey(uid);
        //判断输入的用户id是否合法
        if(index >= 0)
        {
            //通过主键获取用户名
            String userName = References.names[index];
            return userName;
        }
        return null;
    }

    /*
        功能: 根据id 查询用户密码, 如果存在返回用户密码,  如果不存在返回空
        参数:
            uid: 用户id
        返回值:
            如果密码存在, 返回密码.
            如果密码不存在, 返回NULL
    */
    public static String selectPassById(int uid)
    {
        int index = selectPrimaryKey(uid);
        if(index >= 0)
        {
            //通过主键获取用户名
            String password = References.passwords[index];
            return password;
        }
        return null;
    }

    /*
    功能: 传入用户id和密码, 根据上面两个函数(selectUserById, selectPassById)来获取相应用户数据, 并判断是否登录成功
        传入用户id 查询用户名是否存在, 并获取用户密码
        如果用户存在  则判断密码是否正确
    参数:
        uid: 用户账户
        password: 用户密码
    返回值:
        如果账号不存在, 返回1
        如果密码错误, 返回2
        如果登录成功, 返回0
*/
    public static int login(int uid,String password)
    {
        String userName = selectUserById(uid);
        if(userName == null)
        {
            return 1;
        }
        String passwordCur = selectPassById(uid);

        //判断密码是否正确
        //如果密码为空，说明有用户没密码，不用特别判断
        if(!passwordCur.equals(password))
        {
            return 2;
        }
        //登录成功
        return 0;
    }

    /*
    功能: 提示用户输入账号密码, 根据login函数判断是否登录成功,
    如果登录成功提示正在进入首页
    如果登录失败
        密码错误: 提示密码错误, 并让用户重新登录
        账号不存在: 提示账号不存在, 并提示正在进入注册界面
    参数: 无
    返回值: 无
*/
    public static void showLoginPage()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入账号：");
        int uid = in.nextInt();
        //吸收换行
        in.nextLine();
        System.out.println("请输入密码：");
        String password = in.nextLine();

        int code = login(uid,password);
        if(code == 1)
        {
            System.out.println("账号不存在, 正在进入注册界面");
            showRegister();
        }
        else if(code == 2)
        {
            System.out.println("密码错误, 请重新登录");
        }
        else if(code == 0)
        {
            System.out.println("登录成功, 正在进入首页");
        }
    }


    /*
     功能: 创建新用户
    参数:
        uid: 用户id，password：用户密码
    返回值:
        创建成功，返回用户名
     */
    public static String addUserBy(int uid, String userName,String password)
    {
        //新增id
        int length = References.ids.length;
        int[] ids1 = new int[length + 1];
        for(int i = 0;i < length + 1;i++)
        {
            if(i < length)
                ids1[i] = References.ids[i];
            else if(i == length)
            {
                ids1[i] = uid;
            }
        }
        References.ids = ids1;

        //新增用户名
        int length_name = References.names.length;
        String[] names1 = new String[length_name + 1];
        for(int i = 0;i < length_name + 1;i++)
        {
            if(i < length_name)
            {
                names1[i] = References.names[i];
            }
            else if(i == length_name)
            {
                names1[i] = userName;
            }
        }
        References.names = names1;

        //新增密码
        int length_pw = References.passwords.length;
        String[] pw = new String[length_pw + 1];
        for(int i = 0;i < length_pw + 1;i++)
        {
            if(i < length_pw)
            {
                pw[i] = References.passwords[i];
            }
            else if(i == length_pw)
            {
                pw[i] = password;
            }
        }
        References.passwords = pw;
        return userName;
    }
    /*
        功能: 传入用户id和密码, 根据上面两个函数(selectUserById，addUserBy)来获取相应用户数据,并注册用户
        传入用户id 查询用户名是否存在
        如果用户不存在 则注册新用户
    参数:
        uid: 用户账户
        password: 用户密码
    返回值:
        如果账号已存在, 返回1
        如果注册失败，返回2
        如果注册成功, 返回0
     */
    public static int register(int uid,String userName,String password)
    {
        if(selectUserById(uid) != null)
            return 1;
        String userName_1 = addUserBy(uid,userName,password);
        if(userName_1 == null)
            return 2;
        return 0;
    }

     /*
    功能: 提示用户输入账号用户名和密码, 根据register函数判断是否注册成功,
    如果成功提示进入登录页面
    如果注册失败
        账号已存在: 提示账号已存在。
    参数: 无
    返回值: 无
    */
    public static void showRegister()
    {
        System.out.println("进入注册页面。");
        Scanner in = new Scanner(System.in);
        System.out.println("请输入账号：");
        int uid = in.nextInt();
        //吸收换行
        in.nextLine();

        System.out.println("请输入用户名：");
        String userName = in.nextLine();

        System.out.println("请输入密码：");
        String password = in.nextLine();

        Integer registerCode = register(uid,userName,password);
        if(registerCode == 1)
        {
            System.out.println("账号已存在，注册失败");
        }
        else if(registerCode == 2)
        {
            System.out.println("注册失败");
        }
        else if(registerCode == 0)
        {
            System.out.println("注册成功，跳转至登录页面");
            showLoginPage();
        }
    }
}
