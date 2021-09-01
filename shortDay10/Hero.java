package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * 英雄机
 */
public class Hero extends FlyingObject{
	
	private int doubleFire;//火力
	private int life;//命
	public static BufferedImage[] images;
	
	//初始化静态资源
	static{
		//读取英雄机图片
		images=new BufferedImage[2];
		for(int i=0;i<images.length;i++){
			images[i]=loadImage("hero"+i+".png");
		}
	}
	
	
	public Hero(){
		super(97,139,200,400);
		this.doubleFire=0;
		this.life=3;
	}

	
	public void step() {//移动
		
		
	}
	//英雄机移动随着鼠标得x，y移动而移动
	
	//根据不同的状态，获取不同的状态
	public void herostep(int x,int y){
		this.x=x-this.width/2;//鼠标x坐标减去当前宽度的一半，就是当前对象x得坐标
		this.y=y-this.height/2;
		
	}
	private int index=0;
	public BufferedImage getImage() {
		if(islife()){
			BufferedImage img=images[index++%images.length];
			return img;
		}
		return null;
	}
	
	
	
	//英雄机发射子弹
	public Bullet[] heroshoot(){
		int xstep=this.width/4;
		int ystep=10;
		if(doubleFire==0){//单倍火力（1/2处）
			Bullet[] bullet=new Bullet[1];
			bullet[0]=new Bullet(this.x+2*xstep,this.y-ystep);
			return bullet;
			
		}else{//双倍火力（1/4）（3/4）处
			Bullet[] bullet=new Bullet[2];
			bullet[0]=new Bullet(this.x+1*xstep,this.y-ystep);
			bullet[1]=new Bullet(this.x+3*xstep,this.y-ystep);
			doubleFire-=2;//发射一次子弹，活力值减2
			return bullet;
		}
	}
	//英雄机减命
		public void sublife(){
			life--;
		}
		
	//英雄机火力清空
	public void cleaedoublefire(){
		doubleFire=0;
	}
	
	//获取英雄机得命
	public int getlife(){
		return life;
	}
	
	public int doublefire(){
		return doubleFire;
	}
	
	//增加英雄机命
	public void addlife(){
		life++;
	}
	
	//增加火力值
	public void adddoublefire(){
		doubleFire+=40;
	}


	//英雄机不越界
	public boolean flyingobjectout() {
		return false;//不越界
	}
}
