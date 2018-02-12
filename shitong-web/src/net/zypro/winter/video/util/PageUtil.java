package net.zypro.winter.video.util;

public class PageUtil {
private int totalCount;   //总的记录数
private int everyPage;    //每一页的记录数
private int beginIndex;    //开始查询起点
private int currentPage;    //当前页面
private int prePage;       //前一页
private int nextPage;     //下一页
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
