//package net.zypro.winter.video.action;
//
//
//import java.util.List;
//
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.convention.annotation.Action;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.opensymphony.xwork2.ActionSupport;
//
//import net.zypro.winter.video.bean.Comment;
//import net.zypro.winter.video.bean.User;
//import net.zypro.winter.video.bean.Video;
//import net.zypro.winter.video.service.CommentService;
//
//public class CommentAction extends ActionSupport {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private Comment comment;
//	private Video video;
//	public Video getVideo() {
//		return video;
//	}
//
//	public void setVideo(Video video) {
//		this.video = video;
//	}
//
//	public Comment getComment() {
//		return comment;
//	}
//
//	public void setComment(Comment comment) {
//		this.comment = comment;
//	}
//	@Action(value="/comment/edit")
//	public void editComment() throws Exception{   //编辑视频评论
//		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
//		CommentService commentService=new CommentService();
//		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
//		comment.setUser(user);       //设置外键
//		comment.setVideo(video);
//		commentService.addComment(comment); //调用业务逻辑组件，将该评论保存在数据库
//		JSONObject json=new JSONObject();
//		json.put("status", "success");
//		json.put("message","用户评论成功" );
//		ServletActionContext.getResponse().getWriter().println(json.toString());	
//	}
//	@Action(value="/comment/delete")
//   public void deleteComment() throws Exception{
//	   ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
//	   CommentService commentService=new CommentService();
//	   commentService.deleteComment(comment.getId());
//	   JSONObject json=new JSONObject();
//	   json.put("status", "success");
//	   json.put("message","用户删除评论成功" );
//	   ServletActionContext.getResponse().getWriter().println(json.toString());	
//   }
//	@Action(value="/comment/list")
//   public void listComment() throws Exception{
//	   ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8"); 
//	   CommentService commentService=new CommentService();
//	   List<Comment> comments=commentService.findAll();   //返回数据库
//	   JSONArray jsonArray=new JSONArray(comments);
//	   ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
//   }
//}
