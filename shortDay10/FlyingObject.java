package com.ykt.ykt2020OPP.shortDay10;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * 飞行物的父类
 */
public abstract class FlyingObject {
	protected int width;//宽
	protected int height;//高
	protected int x;//坐标
	protected int y;//坐标
	public static final int LIFE=0;//活着状态
	public static final int DEAD=1;//死掉状态
	public static final int REMOVE=2;//移除状态
	public int state=LIFE;//默认状态（活着）
	
	//大敌机，小敌机，直升机初始化：
	public FlyingObject(int width,int height){
		this.width=width;
		this.height=height;
		Random random=new Random();
		this.x=random.nextInt(World.WIDTH-this.width+1);//敌机x的坐标=窗口宽度-当前敌机宽度+1
		this.y=-this.height;
	}
	
	//英雄机，子弹，天空
	public FlyingObject(int width,int height,int x,int y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
	}
	
	//飞行物移动
	public abstract void step();//抽象方法
	
	//读图片
	public static BufferedImage loadImage(String fileName){
		//读图片的路径： ImageIo：读写图片的工具
		try {
			BufferedImage img=ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (Exception e) {
			throw new RuntimeException("加载失败");
		}
	}
	
	//判断飞行物是否活着
	public boolean islife(){
		boolean b=state==LIFE;
		return b;
	}
	
	//判断飞行物是否死掉
	public boolean isdead(){
		boolean b=state==DEAD;
		return b;
	}
	
	//判断飞行物是否移除
	public boolean isremove(){
		boolean b=state==REMOVE;
		return b;
	}
	
	/*
	 * 获取图片
	 */
	public abstract BufferedImage getImage();
	
	//画对象图片   g：画笔
	public void paintobject(Graphics g){
		g.drawImage(getImage(),this.x,this.y,null);
	}
	
	/*英雄机子弹，敌机相撞（other代表英雄级子弹）*/
	public boolean hit(FlyingObject other){
		int x=other.x;
		int y=other.y;
		int x1=this.x-other.width;
		int x2=this.x+this.width;
		int y1=this.y-other.height;
		int y2=this.y+this.height;
		return x>=x1 &&x<=x2 &&y>=y1&& y<=y2;//若为true，则发生碰撞
	}
	
	//飞行物去死
	public void godead(){
		this.state=DEAD;
	}
	
	
	//判断飞行物是否越界
	public abstract boolean flyingobjectout();
	
	
}
