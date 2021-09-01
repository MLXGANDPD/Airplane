package com.ykt.ykt2020OPP.shortDay10;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
 * 天空
 */
public class Sky extends FlyingObject{
	private int speed;//速度
	private int y1;//（下面的那张图片）
	public static BufferedImage img;
	
	//初始化静态资源
	static{
		//读取背景图片
		img=loadImage("bg1.png");
	}
	
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		this.speed=1;
		y1=-height;
	}
	
	//移动
	public void step(){
		y+=speed;
		y1+=speed;
		//System.out.println("天空移动了"+y+"y1移动了"+y1);
		if(y>=World.HEIGHT){//当y>窗口得高度时，把y改成负得高度
			y=-height;
		}
		if(y1>=World.HEIGHT){//当y1>窗口得高度时，把y1改成负得高度
			y1=-height;
		}
	}
	
	//根据不同的状态，获取不同的状态(背景图不存在活着和死的)
		public BufferedImage getImage() {
				return img;
		}
		
		//重写父类的画对象方法
		public void paintobject(Graphics g){
			g.drawImage(getImage(),this.x,this.y,null);
			g.drawImage(getImage(),this.x,this.y1,null);
		}

		//天空不越界
		public boolean flyingobjectout() {
			return false;//不越界
		}
}
