package net.zypro.winter.video.util;

public class PageUtil {
private int totalCount;   //�ܵļ�¼��
private int everyPage;    //ÿһҳ�ļ�¼��
private int beginIndex;    //��ʼ��ѯ���
private int currentPage;    //��ǰҳ��
private int prePage;       //ǰһҳ
private int nextPage;     //��һҳ
public int getTotalCount() {
	return totalCount;
}
public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
}
public int getEveryPage() {
	return everyPage;
}
public void setEveryPage(int everyPage) {
	this.everyPage = everyPage;
}
public int getBeginIndex() {
	return beginIndex;
}
public void setBeginIndex(int beginIndex) {
	this.beginIndex = beginIndex;
}
public int getCurrentPage(){
	currentPage=(beginIndex/everyPage)+1;
	return currentPage;
}
public int getPrePage(){
	if(beginIndex<everyPage){
		prePage=beginIndex;
	}
	else{
		prePage=beginIndex-everyPage;
	}
	return prePage;
}
public int getNextPage(){
	if(beginIndex+everyPage>totalCount){
		nextPage=beginIndex;
	}
	else{
		nextPage=beginIndex+everyPage;
	}
	return nextPage;
}
}
