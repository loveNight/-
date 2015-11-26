package king;

import java.awt.Color;
import java.util.Random;

/**
 * ���������AI
 * ������setAI()���ٵ���getAIX()��getAIY()
 */
public class AI{
	private Color[][] chess; //�����������
	private Color myColor; //AI��ִ������ɫ
	private Color antiColor;//�Է���Ҳ������ң���ִ����ɫ
	private int bestX, bestY; //��������λ��
	private int effectiveX1, effectiveY1, effectiveX2, effectiveY2; //���̵���Чλ�ã�һ�����������ӷ�Χ��һȦ�ľ���
	private Color[] colorArr = new Color[]{Color.black, Color.white};
	private Random rand = new Random(); //�����������
//	int antiBlockX1 = -1, antiBlockY1 = -1; //��ס�ҷ����ӵĶԷ��������꣬-1��ʾ������
//	int antiBlockX2 = -1, antiBlockY2 = -1; //��ס�ҷ����ӵĶԷ��������꣬-1��ʾ������
	
	
	/**
	 * ���뵱ǰ���������������ִ��ɫ
	 * ���ڳ������ȴ���AI��ʵ�����ٸ��ݲ�ͬ��chess����������һ������
	 * �����ʺ�ʹ�ù�����
	 */
	public boolean setAI(Color[][] chess, Color myColor){
		this.chess = chess;
		this.myColor = myColor;
		this.antiColor = myColor == Color.black?Color.white:Color.black;
		findEffectiveArea();
		findBestPosition();
		return true;
	}
	
	/**
	 * ���ؼ�����X����
	 */
	public int getAIX(){
		return bestX;
	}
	
	/**
	 * ���ؼ�����Y����
	 */
	public int getAIY(){
		return bestY;
	}
	
	/**
	 * �ҳ����̵���Ч��Χ
	 */
	private void findEffectiveArea(){
		int offset; //���Ƶ�Ȧ��
		//�ȶ�effectiveX1�ȸ���ֵ��������
		for(int i = 0; i <= 14; i++){
			for(int j = 0; j <= 14; j++){
				if(chess[i][j] != null){ //��λ�������ӣ��ſ�ʼ�ж�
					//�������������ӷ�Χ���еľ���
					effectiveX1 = i;
					effectiveY1 = j;
					effectiveX2 = i;
					effectiveY2 = j;
				}
			}			
		}
		for(int i = 0; i <= 14; i++){
			for(int j = 0; j <= 14; j++){
				if(chess[i][j] != null){ //��λ�������ӣ��ſ�ʼ�ж�
					//�������������ӷ�Χ���еľ���
					effectiveX1 = effectiveX1>i?i:effectiveX1;
					effectiveY1 = effectiveY1>j?j:effectiveY1;
					effectiveX2 = effectiveX2<i?i:effectiveX2;
					effectiveY2 = effectiveY2<j?j:effectiveY2;
				}
			}			
		}
		offset = 2;
		effectiveX1 = (effectiveX1-offset)>=0?effectiveX1-offset:effectiveX1;
		effectiveY1 = (effectiveY1-offset)>=0?effectiveY1-offset:effectiveY1;
		effectiveX2 = (effectiveX2+offset)<=14?effectiveX2+offset:effectiveX2;
		effectiveY2 = (effectiveY2+offset)<=14?effectiveY2+offset:effectiveY2;
	}
	
	/**
	 * �ҳ�����ʵ�����λ��
	 */
	private void findBestPosition(){
		int tempScore = 0;//�ݴ���������Ա���
		int realGetScore; //���淵��ֵ
		bestX = effectiveX1;//��ʼ����������
		bestY = effectiveY1;
		for(int x = effectiveX1; x <= effectiveX2; x++){
			for(int y = effectiveY1; y <= effectiveY2; y++){
				if(chess[x][y] == null ){ //�˴����ӣ����ж�
					if(tempScore < getScore(x, y)){
						bestX = x;
						bestY = y;
						tempScore = getScore(x, y);
					}					
				}
			}
		}
		//�������Ϊ0�������ʺ�ѡ�����������
		if (tempScore == 0) {
			bestX = effectiveX1 + rand.nextInt(effectiveX2+1-effectiveX1);
			bestY = effectiveY1 + rand.nextInt(effectiveY2+1-effectiveY1);
		}
	}
	
	/**
	 * ĳλ���Ƿ���ָ����ɫ������
	 * �߽�Ҳ�ܶ�ס�ѷ����ӣ���з����ӵ�Ч
	 * 1Ϊ�ѷ���-1Ϊ�Է���߽磬0Ϊ������
	 */
	private int contains(int x, int y, Color color){
		if(x < 0 || x > 14 || y < 0 || y > 14){
			return -1; 
		}
		else if(chess[x][y] == color){
			return 1;
		}
		else if(chess[x][y] == null ){
			return 0;
		}
		else{
			return -1;
		}
	}
	
	/**
	 * ���ĳλ�õĵ÷�
	 * �����ĸ���������������ٵ�����ַ���
	 */
	private int getScore(int x, int y){
		int tempX = x;//�������x�����Դ���λ
		int tempY = y;
		int count = 0;//ͬһ��ֱ����������ͬɫ������
		int block = 0;//�������Ӿ�ͷ������
		int totalScore = 0;//���֣����ڷ���
		//�ȼ������
		//�ѷ���з���Ҫ���
		for(Color color:colorArr){
			//���жϺ���
			x = tempX;
			y = tempY;
			count = 0;
			block = 0;
			while(contains(x-1, y, color) == 1){ //ͬɫ���ӣ����������
				x--;
				count++;
			}
			if(contains(x-1, y, color) == -1){ //������ס�������
				block++;
			}
			x = tempX;//��λ�����¿�ʼ
			y = tempY;
			while(contains(x+1, y, color) == 1){
				x++;
				count++;
			}
			if(contains(x+1, y, color) == -1){
				block++;
			}
			count++;
			totalScore += countToScore(count, block, color);
			//���ж�����
			x = tempX;
			y = tempY;
			count = 0;
			block = 0;
			while(contains(x, y-1, color) == 1){
				y--;
				count++;
			}
			if(contains(x, y-1, color) == -1){
				block++;
			}
			x = tempX;
			y = tempY;
			while(contains(x, y+1, color) == 1){
				y++;
				count++;
			}
			if(contains(x, y+1, color) == -1){
				block++;
			}
			count++;//ǰ��δ�����ʼλ�ã��˴�����
			totalScore += countToScore(count, block, color);
			//���ж����ϵ�����
			x = tempX;
			y = tempY;
			count = 0;
			block = 0;
			while(contains(x-1, y-1, color) == 1){
				x--;
				y--;
				count++;
			}
			if(contains(x-1, y-1, color) == -1){
				block++;
			}
			x = tempX;
			y = tempY;
			while (contains(x+1, y+1, color) == 1) {
				x++;
				y++;
				count++;		
			}
			if(contains(x+1, y+1, color) == -1){
				block++;
			}
			count++;
			totalScore += countToScore(count, block, color);
			//���ж����µ�����
			x = tempX;
			y = tempY;
			count = 0;
			block = 0;
			while(contains(x-1, y+1, color) == 1){
				x--;
				y++;
				count++;
			}
			if(contains(x-1, y+1, color) == -1){
				block++;
			}
			x = tempX;
			y = tempY;
			while(contains(x+1, y-1, color) == 1){
				x++;
				y--;
				count++;
			}
			if(contains(x+1, y-1, color) == -1){
				block++;
			}
			count++;
			totalScore += countToScore(count, block, color);				
		}
		return totalScore;
	}
	
	/**
	 * ������������ɷ���
	 */
	private int countToScore(int count, int block, Color color){
		if(block == 2) return 0; //returnֱ�ӽ��������������治��Ҫelse��
		//���水�ݸ��˳��Ʒ�
		if(count == 5 && color == myColor) return 1000000000; //��һ���ѷ����壬ִ֮
		if(count == 5 && color == antiColor) return 100000000; //��һ�����ֳ��壬��֮
		if(count == 4 && color == myColor && block == 0) return 10000000; //��һ���ѷ����ģ�ִ֮
		if(count == 4 && color == myColor && block == 1) return 1000000; //��һ���ѷ����ģ�ִ֮
		if(count == 4 && color == antiColor && block == 0) return 100000; //��һ�����ֻ��ģ���֮
		if(count == 3 && color == myColor && block == 0) return 10000; //��һ���ѷ�������ִ֮
		if(count == 4 && color == antiColor && block == 1) return 1000; //��һ�����ֳ��ģ���֮  ******���ֳ��ģ��ѷ����Զ�ʵ���һλ�£����뷨δʵ��
		if(count == 3 && color == myColor && block == 1) return 100; //��һ���ѷ�������һͷ���£���ִ֮
		if(count == 2 && color == myColor && block == 0) return 10; //��һ���ѷ������ִ֮
		if(count == 2 && color == myColor && block == 1) return 1; //��һ���ѷ������ִ֮
		//***********ͬһ��λ�ã����ֵĸ�λ�õ÷�Ҳ���Լӳɵ��ѷ��÷��ϣ����뷨δʵ��
		return 0; //ʣ�����һ�ɷ���0;
	}
}
