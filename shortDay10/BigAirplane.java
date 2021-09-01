package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * ��л�
 */
public class BigAirplane extends FlyingObject implements Score{
	
	private int speed;//�ٶ�
	public static BufferedImage img;
	public static BufferedImage[] images;
	
	//��ʼ����̬��Դ
	static{
		//��ȡ���ŵ�ͼƬ
		img=loadImage("bigAriplane.png");
		//��ȡ������6��ͼƬ(��ըЧ��ͼ)
		images=new BufferedImage[6];
		for(int i=0;i<images.length;i++){
			images[i]=loadImage("boms"+i+".png");
		}
	}
	
	
	public BigAirplane(){
		super(94,84);
		this.speed=2;
	}
	//�ƶ�
	public void step(){
		this.y+=speed;
		//System.out.println("��л��ƶ���"+y);
	}
	
	
	
	//���ݲ�ͬ��״̬����ȡ��ͬ��״̬
		private int index=0;
		public BufferedImage getImage() {
			if(islife()){//������ţ����ػ��ŵ�״̬
				return img;
			}else if(isdead()){//���������������6�ű�ըЧ��ͼ
				BufferedImage image=images[index++];
				if(index==images.length){//��ըЧ��ͼ�л����
					state=REMOVE;//�ı�Ϊ�Ƴ�״̬
				}
				return image;
			}
			return null;
		}
		
		//��л��÷�
		public int getScore() {
			
			return Score.THREE;
		}
		
		
		//�жϴ�л��Ƿ�Խ��
		public boolean flyingobjectout() {
			return this.y>World.HEIGHT;//Խ��
		}
}
