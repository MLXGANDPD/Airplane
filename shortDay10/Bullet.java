package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * �ӵ�
 */
public class Bullet  extends FlyingObject{
	
	private int speed;//�ٶ�
	public static BufferedImage img;
	
	//��ʼ��̬��Դ
	static{
		//��ȡ�ӵ�ͼƬ
		img=loadImage("bullet.png");
	}
	
	public Bullet(int x,int y){
		super(8,20,x,y);
		this.x=x;
		this.y=y;
		this.speed=4;
	}
	//�ƶ�
	public void step(){
		this.y-=speed;
		//System.out.println("�ӵ��ƶ���"+y);
	}
	
	//���ݲ�ͬ��״̬����ȡ��ͬ��״̬
		public BufferedImage getImage() {
			if(islife()){
				return img;
			}
			return null;
		}
		
		
		//�ж��ӵ��Ƿ�Խ��
		public boolean flyingobjectout() {
			return this.y<-this.height;//Խ��
		}
}
