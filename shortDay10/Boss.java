package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;
/**
 * boss
 * @author Thinkpad
 *
 */
public class Boss extends FlyingObject{
	private int xspeed;
	private int yspeed;
	private static BufferedImage[] images;
	private int bosslife;
	
	static{
		images=new BufferedImage[7];
		for(int i=0;i<7;i++){
			images[i]=loadImage("de"+i+".png");
		}
	}
	
	public Boss(){
		super(162,153);
		this.xspeed=1;
		this.yspeed=1;
		bosslife=20;
	}

	//boss移动
	public void step() {
		this.x+=xspeed;
		this.y+=yspeed;
		if(this.y>=0){
			this.y=0;
		}
		
		if(this.x<=0||this.x>=World.WIDTH-this.width){
			xspeed*=-1;
		}
		
	}

	//根据不同的状态，获取不同boss的状态
	private int bossindex=0;
	public BufferedImage getImage() {
		if(islife()){
			return images[0];
		}else if(isdead()){
			BufferedImage img=images[bossindex++];
			if(bossindex==images.length){
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	//boss发射左子弹
	public Bossbullet bossleftshoot(){
		int xstep=this.width/6;
		int ystep=-2;
		Bossbullet b=new Bossbullet(this.x+2*xstep, this.y+this.height+ystep);
		return b;
	}
	
	//boss发射中间子弹
	public Bossbullet bossshoot(){
		int xstep=this.width/6;
		int ystep=-2;
		Bossbullet b=new Bossbullet(this.x+3*xstep, this.y+this.height+ystep);
		return b;
	}
		
	//boss发射右子弹
	public Bossbullet bossrightshoot(){
		int xstep=this.width/6;
		int ystep=-2;
		Bossbullet b=new Bossbullet(this.x+4*xstep, this.y+this.height+ystep);
		return b;
	}

	//判断boss是否越界
	public boolean flyingobjectout() {
		return false;//不越界
	}
	
	
	//boss减命
	public void subbosslife(){
		 bosslife--;
	}
	
	
	//boss的命
	public int getbosslife(){
		return bosslife;
	}
	
	
}
