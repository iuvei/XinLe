package com.xinle.lottery.material;

import android.util.Log;

import com.xinle.lottery.data.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩种计算注数
 * Created by ACE-PC on 2016/1/21.
 */
public class Calculation {
	private static final String TAG = Calculation.class.getSimpleName();

	private static Calculation instance = new Calculation();

	public Calculation() {

	}

	public static Calculation getInstance() {
		return instance;
	}

	/**
	 * 计算注数 十一选五
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public int calculationNote(List<Integer> oneNums, List<Integer> twoNums,
			List<Integer> threeNums, List<Integer> assembleNums,
							   Method methodkey) {
		int redC = 0;
		switch (methodkey.getNameEn()) {
		case "LTZX3":
			List<Integer> aIntersectsB = intersects(oneNums, twoNums);
			List<Integer> aIntersectsC = intersects(oneNums, threeNums);
			List<Integer> bIntersectsC = intersects(twoNums, threeNums);
			List<Integer> abc = intersects(oneNums, bIntersectsC);
			
			redC = oneNums.size() * twoNums.size() * threeNums.size()
					- oneNums.size() * bIntersectsC.size()
					- twoNums.size() * aIntersectsC.size()
					- threeNums.size() * aIntersectsB.size()
					+ 2 * abc.size();
			break;
		case "LTZU3":
			redC = C(assembleNums.size(), 3);
			break;
		case "LTZX2": // A*B-A∩B
			redC = oneNums.size() * twoNums.size()
					- methodJiaoji(oneNums, twoNums);
			break;
		case "LTZU2":
			redC = C(assembleNums.size(), 2);
			break;
		case "LTBDW":
			break;
		case "LTDWD":
			redC = oneNums.size() + twoNums.size() + threeNums.size();
			break;
		case "LTRX1":
			redC = C(assembleNums.size(), 1);
			break;
		case "LTRX2":
			redC = C(assembleNums.size(), 2);
			break;
		case "LTRX3":
			redC = C(assembleNums.size(), 3);
			break;
		case "LTRX4":
			redC = C(assembleNums.size(), 4);
			break;
		case "LTRX5":
			redC = C(assembleNums.size(), 5);
			break;
		case "LTRX6":
			redC = C(assembleNums.size(), 6);
			break;
		case "LTRX7":
			redC = C(assembleNums.size(), 7);
			break;
		case "LTRX8":
			redC = C(assembleNums.size(), 8);
			break;
		}
		return redC;
	}

	/**
	 * 计算注数 时时彩
	 * 
	 * @return
	 */
	public int calculationNote(ArrayList<Integer> wan,ArrayList<Integer> qian,ArrayList<Integer> bai,ArrayList<Integer> shi,ArrayList<Integer> ge,
							   ArrayList<Integer> assemble,
							   ArrayList<Integer> single,
							   ArrayList<Integer> cipher,
							   ArrayList<Integer> unusualBai,ArrayList<Integer> unusualShi,ArrayList<Integer> unusualGe,String methodkey) {
		int redC = 0;
		switch (methodkey){
			case "YXZX":    //后一直选
				redC = ge.size();
				break;
			case "WXDW":    //五星定位
				redC = wan.size() + qian.size() + bai.size() + shi.size() + ge.size();
				break;
			case "EXZX":    //后二直选
				redC = shi.size() * ge.size();
				break;
			case "EXZUX":   //后二组选
				redC = C(assemble.size(), 2);
				break;
			case "EXBD":    //后二包点
				redC = Bits.getName(cipher,"Two","BD");
				break;
			case "EXLX":    //后二连选
				redC = shi.size() * ge.size()+C(ge.size(),1);
				break;
			case "EXHZ":    //后二和值
				redC = Bits.getName(cipher,"Two","HZ");
				break;
			case "SXZX":    //后三直选
				redC = bai.size() * shi.size() * ge.size();
				break;
			case "SXZS":    //后三组三
				redC = C(assemble.size(), 2)*2;
				break;
			case "SXZL":    //后三组六
				redC = C(assemble.size(), 3);
				break;
			case "SXHHZX":  //后三混合组选
				break;
			case "SXLX":    //后三连选
				redC = bai.size() * shi.size() * ge.size() + shi.size() * ge.size() + C(ge.size(),1);
				break;
			case "SXBD":    //后三包点
				redC = Bits.getName(cipher,"Two","BD");
				break;
			case "SXHZ":    //后三和值
				redC=Bits.getName(cipher,"Three","HZ");
				break;
			case "QEZUX":   //前二组选
				redC = C(assemble.size(), 2);
				break;
			case "QELX":    //前二连选
				redC = wan.size() * qian.size()+C(qian.size(),1);
				break;
			case "QEBD":    //前二包点
				redC=Bits.getName(cipher,"Two","BD");
				break;
			case "QEZX":    //前二直选
				redC = wan.size() * qian.size();
				break;
			case "QEHZ":    //前二和值
				redC=Bits.getName(cipher,"Two","HZ");
				break;
			case "QSZS":    //前三组三
				redC = C(assemble.size(), 2) * 2;
				break;
			case "QSZL":    //前三组六
				redC = C(assemble.size(), 3);
				break;
			case "QSLX":    //前三连选
				redC = wan.size() * qian.size() * bai.size() + qian.size() * bai.size() + C(bai.size(),1);
				break;
			case "QSBD":    //前三包点
				redC=Bits.getName(cipher,"Three","BD");
				break;
			case "QSHHZX":  //前三混合组选
				break;
			case "QSZX":    //前三直选
				redC = wan.size() * qian.size() * bai.size();
				break;
			case "QSHZ":    //前三和值
				redC=Bits.getName(cipher,"Three","HZ");
				break;
			case "ZSZX":    //中三直选
				redC = qian.size() * bai.size() * shi.size();
				break;
			case "ZSHHZX":  //中三混合组选
				break;
			case "ZSZS":    //中三组三
				redC = C(assemble.size(), 2) * 2;
				break;
			case "ZSZL":    //中三组六
				redC = C(assemble.size(), 3);
				break;
			case "ZSLX":    //中三连选
				redC = qian.size() * bai.size() * shi.size() + bai.size() * shi.size() + C(shi.size(),1);
				break;
			case "ZSBD":    //中三包点
				redC=Bits.getName(cipher,"Three","BD");
				break;
			case "ZSHZ":    //中三和值
				redC=Bits.getName(cipher,"Three","HZ");
				break;
			case "SIXZX":   //后四直选
				redC = qian.size() * bai.size() * shi.size()*ge.size();
				break;
			case "QSIZX":   //前四直选
				redC = wan.size() * qian.size() * bai.size() * shi.size();
				break;
			case "ZUX4":    //后四组选4
				List<Integer> intersects4 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),1)-C(intersects4.size(),1);
				break;
			case "ZUX24":   //后四组选24
				redC = C(assemble.size(),4);
				break;
			case "ZUX12":   //后四组选12
				List<Integer> intersects12 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),2)-C(intersects12.size(),1)*C(single.size()-1,1);
				break;
			case "ZUX6":    //后四组选6
				redC = C(assemble.size(),2);
				break;
			case "ZUX120":  //组选120
				redC = C(assemble.size(),5);
				break;
			case "ZUX5":    //组选5
				List<Integer> intersects5 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),1)-C(intersects5.size(),1);
				break;
			case "ZUX60":   //组选60
				List<Integer> intersects60 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),3)-C(intersects60.size(),1)*C(single.size()-1,2);
				break;
			case "WXZX":    //五星直选
				redC = wan.size() * qian.size() * bai.size() * shi.size()*ge.size();
				break;
			case "ZUX30":   //组选30
				List<Integer> intersects30 = intersects(assemble, single);
				redC = C(assemble.size(),2)*C(single.size(),1) -C(intersects30.size(),2)*C(2,1)-C(intersects30.size(),1)*C(assemble.size()-intersects30.size(),1) ;
				break;
			case "WXLX":    //五星连选
				redC = wan.size() * qian.size() * bai.size() * shi.size() *ge.size()
						+ bai.size() * shi.size() * ge.size()
						+ shi.size() * ge.size()
						+ C(ge.size(),1);
				break;
			case "ZUX20":   //组选20
				List<Integer> intersects20 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),2)-C(intersects20.size(),1)*C(single.size()-1,1);
				break;
			case "ZUX10":   //组选10
				List<Integer> intersects10 = intersects(assemble, single);
				redC = C(assemble.size(),1)*C(single.size(),1)-C(intersects10.size(),1);
				break;
			case "REZX":    //任二直选
				/*List<Integer> unionsA = unions(wan, qian);
				List<Integer> unionsB = unions(unionsA, bai);
				List<Integer> unionsC = unions(unionsB, shi);
				List<Integer> unionsD = unions(unionsC, ge);
				redC =C(wan.size(),2);*/

				redC =wan.size()*qian.size() + wan.size()*bai.size() + wan.size()*shi.size() + wan.size()*ge.size()
				+qian.size()*bai.size() + qian.size()*shi.size() + qian.size()*ge.size()
				+bai.size()*shi.size() + bai.size()*ge.size()
				+shi.size()*ge.size();
				break;
			case "RSZX":    //任三直选
				redC =wan.size()*qian.size()*bai.size() + wan.size()*qian.size()*shi.size() + wan.size()*qian.size()*ge.size()
					+wan.size()*bai.size()*shi.size() + wan.size() * bai.size()* ge.size() + wan.size() *shi.size() * ge.size()
					+qian.size()*bai.size()*shi.size() + qian.size() * bai.size()*ge.size()+ qian.size() * shi.size()* ge.size()
					+bai.size() *shi.size()*ge.size();
				break;
			case "WXSMBDW": //五星三码不定位
				redC = C(assemble.size(),3);
				break;
			case "WXEMBDW": //五星二码不定位
				redC = C(assemble.size(),2);
				break;
			case "WXYMBDW": //五星一码不定位
				redC = C(assemble.size(),1);
				break;
			case "SXEMBDW": //四星二码不定位
				redC = C(assemble.size(),2);
				break;
			case "SXYMBDW": //四星一码不定位
				redC = C(assemble.size(),1);
				break;
			case "QSEMBDW": //前三二码不定位
				redC = C(assemble.size(),2);
				break;
			case "QSYMBDW": //前三一码不定位
				redC = C(assemble.size(),1);
				break;
			case "ZSEMBDW": //中三二码不定位
				redC = C(assemble.size(),2);
				break;
			case "ZSYMBDW": //中三一码不定位
				redC = C(assemble.size(),1);
				break;
			case "EMBDW":   //后三二码不定位
				redC = C(assemble.size(), 2);
				break;
			case "YMBDW":   //后三一码不定位
				redC = C(assemble.size(), 1);
				break;
			case "YFFS":    //一帆风顺
				redC = C(assemble.size(), 1);
				break;
			case "HSCS":    //好事成双
				redC = C(assemble.size(), 1);
				break;
			case "SXBX":    //三星报喜
				redC = C(assemble.size(), 1);
				break;
			case "SJFC":    //四季发财
				redC = C(assemble.size(), 1);
				break;
			case "EXDXDS":  //后二大小单双
				redC = C(assemble.size(), 1);
				break;
			case "SXDXDS":  //后三大小单双
				redC = C(assemble.size(), 1);
				break;
		}

		return redC;
	}

	/**
	 * 计算一个数的阶乘
	 * 
	 * @param num
	 * @return
	 */
	private long factorial(int num) {
		// num=7 7*6*5...*1

		if (num > 1) {
			return num * factorial(num - 1);
		} else if (num == 1 || num == 0) {
			return 1;
		} else {
			throw new IllegalArgumentException("num >= 0");
		}
	}

	// 阶乘
	private int P(int n) {
		if (n < 0) {
			return 0;
		}

		if (n == 0) {
			return 1;
		}

		int rlt = 1;
		int i = 1;
		while (i < n) {
			rlt *= (i + 1);
			i++;
		}
		return rlt;
	}

	// 排列
	private int A(int n, int m) {
		if (n < m) {
			return 0;
		}
		return P(n) / P(n - m);
	}

	// 组合
	private int C(int n, int m) {
		if (n < m) {
			return 0;
		}
		return A(n, m) / P(m);
	}

	// 交集
	private List<Integer> intersects(List<Integer> arrayFirst, List<Integer> arraySecond) {
		List<Integer> intersectSet = new ArrayList<Integer>(arrayFirst);
		intersectSet.retainAll(arraySecond);
		return intersectSet;
	}

	private int methodJiaoji(List<Integer> arrayFirst, List<Integer> arraySecond) {
		List<Integer> newArray = new ArrayList<Integer>();

		for (int i = 0; i < arrayFirst.size(); i++) {
			for (int j = 0; j < arraySecond.size(); j++) {
				if (arraySecond.get(j).equals(arrayFirst.get(i))) {
					newArray.add(arraySecond.get(j));
				}
			}
		}
		return newArray.size();
	}

	/**
	 * 从n个数字中选择m个数字
	 * @param a
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public List combine(String[] a,int m) {
		int n = a.length;
		if(m>n){
			Log.i(TAG,"错误！数组a中只有" + n + "个元素。" + m + "大于" + 2 + "!!!");
		}

		List result = new ArrayList();

		int[] bs = new int[n];
		for(int i=0;i<n;i++){
			bs[i]=0;
		}
		//初始化
		for(int i=0;i<m;i++){
			bs[i]=1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		//首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do{
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(print(bs,a,m));

			for(int i=0;i<n-1;i++){
				if(bs[i]==1 && bs[i+1]==0 ){
					bs[i]=0;
					bs[i+1]=1;
					pos = i;
					break;
				}
			}
			//将左边的1全部移动到数组的最左边

			for(int i=0;i<pos;i++){
				if(bs[i]==1){
					sum++;
				}
			}
			for(int i=0;i<pos;i++){
				if(i<sum){
					bs[i]=1;
				}else{
					bs[i]=0;
				}
			}

			//检查是否所有的1都移动到了最右边
			for(int i= n-m;i<n;i++){
				if(bs[i]==0){
					tempFlag = false;
					break;
				}
			}
			flag = tempFlag == false;

		}while(flag);
		result.add(print(bs,a,m));

		return result;
	}

	private String[] print(int[] bs,String[] a,int m){
		String[] result = new String[m];
		int pos= 0;
		for(int i=0;i<bs.length;i++){
			if(bs[i]==1){
				result[pos]=a[i];
				pos++;
			}
		}
		return result ;
	}

	private enum Bits {
		Two_BD_Bits0(0, 1), Two_BD_Bits1(1, 1), Two_BD_Bits2(2, 2), Two_BD_Bits3(3, 2), Two_BD_Bits4(4, 3), Two_BD_Bits5(5, 3), Two_BD_Bits6(6, 4),
		Two_BD_Bits7(7, 4), Two_BD_Bits8(8, 5), Two_BD_Bits9(9, 5), Two_BD_Bits10(10, 5), Two_BD_Bits11(11, 4), Two_BD_Bits12(12, 4), Two_BD_Bits13(13, 3),
		Two_BD_Bits14(14, 3), Two_BD_Bits15(15, 2), Two_BD_Bits16(16, 2), Two_BD_Bits17(17, 1), Two_BD_Bits18(18, 1),

		Two_HZ_Bits0(0, 1), Two_HZ_Bits1(1, 2), Two_HZ_Bits2(2, 3), Two_HZ_Bits3(3, 4), Two_HZ_Bits4(4, 5), Two_HZ_Bits5(5, 6), Two_HZ_Bits6(6, 7),
		Two_HZ_Bits7(7, 8), Two_HZ_Bits8(8, 9), Two_HZ_Bits9(9, 10), Two_HZ_Bits10(10, 9), Two_HZ_Bits11(11, 8), Two_HZ_Bits12(12, 7), Two_HZ_Bits13(13, 6),
		Two_HZ_Bits14(14, 5), Two_HZ_Bits15(15, 4), Two_HZ_Bits16(16, 3), Two_HZ_Bits17(17, 2), Two_HZ_Bits18(18, 1),

		Three_BD_Bits0(0, 1), Three_BD_Bits1(1, 1), Three_BD_Bits2(2, 2), Three_BD_Bits3(3, 2), Three_BD_Bits4(4, 3), Three_BD_Bits5(5, 3), Three_BD_Bits6(6, 4),
		Three_BD_Bits7(7, 4), Three_BD_Bits8(8, 5), Three_BD_Bits9(9, 5), Three_BD_Bits10(10, 5), Three_BD_Bits11(11, 4), Three_BD_Bits12(12, 4), Three_BD_Bits13(13, 3),
		Three_BD_Bits14(14, 3), Three_BD_Bits15(15, 2), Three_BD_Bits16(16, 2), Three_BD_Bits17(17, 1), Three_BD_Bits18(18, 1),Three_BD_Bits19(19, 3),
		Three_BD_Bits20(20, 2), Three_BD_Bits21(21, 2), Three_BD_Bits22(22, 1), Three_BD_Bits23(23, 1),
		Three_BD_Bits24(24, 3), Three_BD_Bits25(25, 2), Three_BD_Bits26(26, 2), Three_BD_Bits27(27, 1),

		Three_HZ_Bits0(0, 1), Three_HZ_Bits1(1, 2), Three_HZ_Bits2(2, 3), Three_HZ_Bits3(3, 4), Three_HZ_Bits4(4, 5), Three_HZ_Bits5(5, 6), Three_HZ_Bits6(6, 7),
		Three_HZ_Bits7(7, 8), Three_HZ_Bits8(8, 9), Three_HZ_Bits9(9, 10), Three_HZ_Bits10(10, 9), Three_HZ_Bits11(11, 8), Three_HZ_Bits12(12, 7), Three_HZ_Bits13(13, 6),
		Three_HZ_Bits14(14, 5), Three_HZ_Bits15(15, 4), Three_HZ_Bits16(16, 3), Three_HZ_Bits17(17, 2), Three_HZ_Bits18(18, 1),Three_HZ_Bits19(19, 3),
		Three_HZ_Bits20(20, 2), Three_HZ_Bits21(21, 2), Three_HZ_Bits22(22, 1), Three_HZ_Bits23(23, 1),
		Three_HZ_Bits24(24, 3), Three_HZ_Bits25(25, 2), Three_HZ_Bits26(26, 2), Three_HZ_Bits27(27, 1);
		// 成员变量
		private int name;
		private int index;
		// 构造方法
		Bits(int name, int index) {
			this.name = name;
			this.index = index;
		}
		// 普通方法
		public static int getName(ArrayList<Integer> name,String place,String types) {
			int estimate=0;
			for (Integer n : name){
				for (Bits c : Bits.values()){
					String a[] = c.toString().split("_");
					switch (a[0]) {
						case "Two":
							if (a[0].equals(place)&&a[1].equals(types)) {
								if (c.getName() == n) {
									estimate += c.index;
								}
							}
							break;
						case "Three":
							if (a[0].equals(place)&&a[1].equals(types)) {
								if (c.getName() == n) {
									estimate += c.index;
								}
							}
							break;
					}
				}
			}

			return estimate;
		}
		// get set 方法
		public int getName() {
			return name;
		}
		public void setName(int name) {
			this.name = name;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
	}

	public static void main(String[ ] arg){
		int dsd=instance.C(9,1);
		System.out.print(dsd);

		int zrs=instance.C(13, 3);
		System.out.print("组合："+zrs);
	}
}
