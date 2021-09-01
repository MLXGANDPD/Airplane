package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;
/*
 * 小敌机
 */
public class Airplane extends FlyingObject implements Score{
	
	private int speed;//速度
	public static BufferedImage img;
	public static BufferedImage[] images;
	
	//初始化静态资源
	static{
		//读取活着的图片
		img=loadImage("ariplane.png");
		//读取死掉的图片
		images=new BufferedImage[6];
		for(int i=0;i<images.length;i++){
			images[i]=loadImage("boms"+i+".png");
		}
	}
	
	public Airplane(){
		super(72,82);
		this.speed=2;
	}
	//移动
	public void step(){
		y+=speed;
		//System.out.println("小敌机移动了"+y);
	}
	
	//根据不同的状态，获取不同的状态
	private int index=0;
	public BufferedImage getImage() {
		if(islife()){//如果活着，返回活着的状态
			return img;
		}else if(isdead()){//如果死掉，即返回6张爆炸效果图
			BufferedImage image=images[index++];
			if(index==images.length){//爆炸效果图切换完毕
				state=REMOVE;//改变为移除状态
			}
			return image;
		}
		return null;
	}

	//小敌机得分
	public int getScore() {
		
		return Score.ONE;
	}
	
	
	//判断小敌机是否越界
	public boolean flyingobjectout() {
		return this.y>World.HEIGHT;//越界
	}
	
}
