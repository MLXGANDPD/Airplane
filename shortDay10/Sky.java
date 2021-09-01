package com.ykt.ykt2020OPP.shortDay10;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
 * ���
 */
public class Sky extends FlyingObject{
	private int speed;//�ٶ�
	private int y1;//�����������ͼƬ��
	public static BufferedImage img;
	
	//��ʼ����̬��Դ
	static{
		//��ȡ����ͼƬ
		img=loadImage("bg1.png");
	}
	
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		this.speed=1;
		y1=-height;
	}
	
	//�ƶ�
	public void step(){
		y+=speed;
		y1+=speed;
		//System.out.println("����ƶ���"+y+"y1�ƶ���"+y1);
		if(y>=World.HEIGHT){//��y>���ڵø߶�ʱ����y�ĳɸ��ø߶�
			y=-height;
		}
		if(y1>=World.HEIGHT){//��y1>���ڵø߶�ʱ����y1�ĳɸ��ø߶�
			y1=-height;
		}
	}
	
	//���ݲ�ͬ��״̬����ȡ��ͬ��״̬(����ͼ�����ڻ��ź�����)
		public BufferedImage getImage() {
				return img;
		}
		
		//��д����Ļ����󷽷�
		public void paintobject(Graphics g){
			g.drawImage(getImage(),this.x,this.y,null);
			g.drawImage(getImage(),this.x,this.y1,null);
		}

		//��ղ�Խ��
		public boolean flyingobjectout() {
			return false;//��Խ��
		}
}
