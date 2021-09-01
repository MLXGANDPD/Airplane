package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

//boss�ӵ�
public class Bossbullet extends FlyingObject{
	private int xspeed;
	private int yspeed;
	private static BufferedImage image;
	
	
	static{
		image=loadImage("bossBullet.png");
	}
	
	//boss�ӵ�����boss���ƶ����ƶ�
	public Bossbullet(int x,int y){
		super(36,36,x,y);
		this.xspeed=2;
		this.yspeed=2;
	}
	
	//boss�ӵ����ƶ�
	public void leftstep(){
		this.x-=this.xspeed;
		this.y+=this.yspeed;
		if(this.x<=0||this.x>=World.WIDTH-this.width){
			this.xspeed*=-1;
		}
	}
	
	//boss�ӵ��м��ƶ�
	public void step(){
		this.y+=this.yspeed;
		
	}
		
	//boss�ӵ����ƶ�
	public void rightstep(){
		this.x+=this.xspeed;
		this.y+=this.yspeed;
		if(this.x<=0||this.x>=World.WIDTH-this.width){
			this.xspeed*=-1;
		}
	}

	//�����㲻ͬ״̬��ȡ��ͬ��ͼƬ
	public BufferedImage getImage() {
		if(islife()){
			return image;
		}
		return null;
	}

	//�ж��Ƿ�Խ��
	public boolean flyingobjectout() {
		return this.y>=World.HEIGHT;//Խ��
	}
}
