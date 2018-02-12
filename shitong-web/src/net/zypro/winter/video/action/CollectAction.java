//package net.zypro.winter.video.action;
//
//
//
//
//import java.util.List;
//
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.convention.annotation.Action;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import net.zypro.winter.video.bean.Collect;
//import net.zypro.winter.video.bean.User;
//import net.zypro.winter.video.bean.Video;
//import net.zypro.winter.video.service.CollectService;
//
//
//
//import com.opensymphony.xwork2.ActionSupport;
//
//public class CollectAction extends ActionSupport {
// /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//private Collect collect;
// private Video video;
//
//public Video getVideo() {
//	return video;
//}
//
//public void setVideo(Video video) {
//	this.video = video;
//}
//
//public Collect getCollect() {
//	return collect;
//}
//
//public void setCollect(Collect collect) {
//	this.collect = collect;
//}
//@Action(value="/collect/edit")
//public void editCollect() throws Exception{
//	collect=new Collect();
//	ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
//	CollectService collectService=new CollectService();
//	User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
//	collect.setUser(user);     //设置外键
//	collect.setVideo(video);
//	collectService.addCollect(collect); //调用业务逻辑组件，将该收藏记录保存在数据库
//	JSONObject json=new JSONObject();
//	json.put("status", "success");
//	json.put("message","用户收藏成功" );
//	ServletActionContext.getResponse().getWriter().println(json.toString());	
//}
//@Action(value="/collect/delete")
//public void deleteComment() throws Exception{
//	   ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
//	   CollectService collectService=new CollectService();
//	   collectService.deleteCollect(collect.getId());
//	   JSONObject json=new JSONObject();
//	   json.put("status", "success");
//	   json.put("message","用户删除收藏记录成功" );
//	   ServletActionContext.getResponse().getWriter().println(json.toString());	
//}
//@Action(value="/collect/list")
//public void listCollect() throws Exception{
//	 ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8"); 
//	   CollectService collectService=new CollectService();
//	   List< Collect> collects=collectService.findAll();   //返回数据库
//	   JSONArray jsonArray=new JSONArray(collects);
//	   ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
//}
//}
