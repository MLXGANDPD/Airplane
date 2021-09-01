package com.ykt.ykt2020OPP.shortDay10;

import java.awt.image.BufferedImage;

/*
 * Ӣ�ۻ�
 */
public class Hero extends FlyingObject{
	
	private int doubleFire;//����
	private int life;//��
	public static BufferedImage[] images;
	
	//��ʼ����̬��Դ
	static{
		//��ȡӢ�ۻ�ͼƬ
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

	
	public void step() {//�ƶ�
		
		
	}
	//Ӣ�ۻ��ƶ���������x��y�ƶ����ƶ�
	
	//���ݲ�ͬ��״̬����ȡ��ͬ��״̬
	public void herostep(int x,int y){
		this.x=x-this.width/2;//���x�����ȥ��ǰ��ȵ�һ�룬���ǵ�ǰ����x������
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
	
	
	
	//Ӣ�ۻ������ӵ�
	public Bullet[] heroshoot(){
		int xstep=this.width/4;
		int ystep=10;
		if(doubleFire==0){//����������1/2����
			Bullet[] bullet=new Bullet[1];
			bullet[0]=new Bullet(this.x+2*xstep,this.y-ystep);
			return bullet;
			
		}else{//˫��������1/4����3/4����
			Bullet[] bullet=new Bullet[2];
			bullet[0]=new Bullet(this.x+1*xstep,this.y-ystep);
			bullet[1]=new Bullet(this.x+3*xstep,this.y-ystep);
			doubleFire-=2;//����һ���ӵ�������ֵ��2
			return bullet;
		}
	}
	//Ӣ�ۻ�����
		public void sublife(){
			life--;
		}
		
	//Ӣ�ۻ��������
	public void cleaedoublefire(){
		doubleFire=0;
	}
	
	//��ȡӢ�ۻ�����
	public int getlife(){
		return life;
	}
	
	public int doublefire(){
		return doubleFire;
	}
	
	//����Ӣ�ۻ���
	public void addlife(){
		life++;
	}
	
	//���ӻ���ֵ
	public void adddoublefire(){
		doubleFire+=40;
	}


	//Ӣ�ۻ���Խ��
	public boolean flyingobjectout() {
		return false;//��Խ��
	}
}
