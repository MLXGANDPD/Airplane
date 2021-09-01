package com.ykt.ykt2020OPP.shortDay10;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * ������ĸ���
 */
public abstract class FlyingObject {
	protected int width;//��
	protected int height;//��
	protected int x;//����
	protected int y;//����
	public static final int LIFE=0;//����״̬
	public static final int DEAD=1;//����״̬
	public static final int REMOVE=2;//�Ƴ�״̬
	public int state=LIFE;//Ĭ��״̬�����ţ�
	
	//��л���С�л���ֱ������ʼ����
	public FlyingObject(int width,int height){
		this.width=width;
		this.height=height;
		Random random=new Random();
		this.x=random.nextInt(World.WIDTH-this.width+1);//�л�x������=���ڿ��-��ǰ�л����+1
		this.y=-this.height;
	}
	
	//Ӣ�ۻ����ӵ������
	public FlyingObject(int width,int height,int x,int y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
	}
	
	//�������ƶ�
	public abstract void step();//���󷽷�
	
	//��ͼƬ
	public static BufferedImage loadImage(String fileName){
		//��ͼƬ��·���� ImageIo����дͼƬ�Ĺ���
		try {
			BufferedImage img=ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (Exception e) {
			throw new RuntimeException("����ʧ��");
		}
	}
	
	//�жϷ������Ƿ����
	public boolean islife(){
		boolean b=state==LIFE;
		return b;
	}
	
	//�жϷ������Ƿ�����
	public boolean isdead(){
		boolean b=state==DEAD;
		return b;
	}
	
	//�жϷ������Ƿ��Ƴ�
	public boolean isremove(){
		boolean b=state==REMOVE;
		return b;
	}
	
	/*
	 * ��ȡͼƬ
	 */
	public abstract BufferedImage getImage();
	
	//������ͼƬ   g������
	public void paintobject(Graphics g){
		g.drawImage(getImage(),this.x,this.y,null);
	}
	
	/*Ӣ�ۻ��ӵ����л���ײ��other����Ӣ�ۼ��ӵ���*/
	public boolean hit(FlyingObject other){
		int x=other.x;
		int y=other.y;
		int x1=this.x-other.width;
		int x2=this.x+this.width;
		int y1=this.y-other.height;
		int y2=this.y+this.height;
		return x>=x1 &&x<=x2 &&y>=y1&& y<=y2;//��Ϊtrue��������ײ
	}
	
	//������ȥ��
	public void godead(){
		this.state=DEAD;
	}
	
	
	//�жϷ������Ƿ�Խ��
	public abstract boolean flyingobjectout();
	
	
}
