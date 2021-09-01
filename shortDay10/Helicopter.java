package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * ֱ����
 */
public class Helicopter extends FlyingObject implements Award{
	private int xspeed;
	private int yspeed;
	public static BufferedImage img;
	public static BufferedImage[] images;
	private int type;//��������
	
	//��ʼ����̬��Դ
	static{
		//��ȡ���ŵ�ͼƬ
		img=loadImage("Helicopter.png");
		//��ȡ������6��ͼƬ(��ըЧ��ͼ)
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
	
	
	//�ƶ�
	public void step(){
		this.y+=yspeed;
		this.x+=xspeed;
		//��x�����ȴ��ڴ��ڿ�Ȼ���С�ڴ��ڿ�ȣ�����x�ٶȱ�Ϊ��ֵ��
		if(x>=World.WIDTH-this.width||x<=0){
			xspeed*=-1;
		}
		//System.out.println("ֱ����x�����ƶ���"+x+"ֱ����y�����ƶ���"+y);
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


		//ֱ������ȡ����
		public int getaward() {
			// TODO Auto-generated method stub
			return type;
		}
		
		
		//�ж�ֱ�����Ƿ�Խ��
		public boolean flyingobjectout() {
			return this.y>World.HEIGHT;//Խ��
		}
}
