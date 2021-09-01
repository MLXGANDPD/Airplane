package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

//boss子弹
public class Bossbullet extends FlyingObject{
	private int xspeed;
	private int yspeed;
	private static BufferedImage image;
	
	
	static{
		image=loadImage("bossBullet.png");
	}
	
	//boss子弹随着boss得移动而移动
	public Bossbullet(int x,int y){
		super(36,36,x,y);
		this.xspeed=2;
		this.yspeed=2;
	}
	
	//boss子弹左移动
	public void leftstep(){
		this.x-=this.xspeed;
		this.y+=this.yspeed;
		if(this.x<=0||this.x>=World.WIDTH-this.width){
			this.xspeed*=-1;
		}
	}
	
	//boss子弹中间移动
	public void step(){
		this.y+=this.yspeed;
		
	}
		
	//boss子弹右移动
	public void rightstep(){
		this.x+=this.xspeed;
		this.y+=this.yspeed;
		if(this.x<=0||this.x>=World.WIDTH-this.width){
			this.xspeed*=-1;
		}
	}

	//根据你不同状态获取不同的图片
	public BufferedImage getImage() {
		if(islife()){
			return image;
		}
		return null;
	}

	//判断是否越界
	public boolean flyingobjectout() {
		return this.y>=World.HEIGHT;//越界
	}
}
