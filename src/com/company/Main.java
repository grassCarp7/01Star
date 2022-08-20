package com.company;
import java.lang.*;
import java.util.Scanner;
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
        int uid = in.nextInt();
        //吸收换行
        in.nextLine();
        String password = in.nextLine();

        int code = login(uid,password);
        if(code == 1)
        {
            System.out.println("账号不存在, 正在进入注册界面");
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
}
