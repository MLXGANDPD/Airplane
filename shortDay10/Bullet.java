package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * 子弹
 */
public class Bullet  extends FlyingObject{
	
	private int speed;//速度
	public static BufferedImage img;
	
	//初始静态资源
	static{
		//获取子弹图片
		img=loadImage("bullet.png");
	}
	
	public Bullet(int x,int y){
		super(8,20,x,y);
		this.x=x;
		this.y=y;
		this.speed=4;
	}
	//移动
	public void step(){
		this.y-=speed;
		//System.out.println("子弹移动了"+y);
	}
	
	//根据不同的状态，获取不同的状态
		public BufferedImage getImage() {
			if(islife()){
				return img;
			}
			return null;
		}
		
		
		//判断子弹是否越界
		public boolean flyingobjectout() {
			return this.y<-this.height;//越界
		}
}
