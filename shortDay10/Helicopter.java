package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * 直升机
 */
public class Helicopter extends FlyingObject implements Award{
	private int xspeed;
	private int yspeed;
	public static BufferedImage img;
	public static BufferedImage[] images;
	private int type;//奖励类型
	
	//初始化静态资源
	static{
		//读取活着的图片
		img=loadImage("Helicopter.png");
		//获取死掉的6张图片(爆炸效果图)
		images=new BufferedImage[6];
		for(int i=0;i<images.length;i++){
			images[i]=loadImage("boms"+i+".png");
		}
	}
	
	public Helicopter(){
		super(61,69);
		this.xspeed=2;
		this.yspeed=2;
		this.type=(int)(Math.random()*2);
	}
	
	
	//移动
	public void step(){
		this.y+=yspeed;
		this.x+=xspeed;
		//当x坐标宽度大于窗口宽度或者小于窗口宽度，将其x速度变为负值；
		if(x>=World.WIDTH-this.width||x<=0){
			xspeed*=-1;
		}
		//System.out.println("直升机x方向移动了"+x+"直升机y方向移动了"+y);
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


		//直升机获取奖励
		public int getaward() {
			// TODO Auto-generated method stub
			return type;
		}
		
		
		//判断直升机是否越界
		public boolean flyingobjectout() {
			return this.y>World.HEIGHT;//越界
		}
}
