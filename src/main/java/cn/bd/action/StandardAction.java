package cn.bd.action;

import cn.bd.action.base.BaseAction;
import cn.bd.entity.Standard;
import cn.bd.service.IStandardService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class StandardAction extends BaseAction<Standard> {
	@Autowired
	private IStandardService standardService;
	//保存取派标准
	@Action(value="standard_save",results={@Result(type="redirect",location="./pages/base/standard.html")})
	public String save(){
		System.out.println("保存成功");
		standardService.save(model);
		return SUCCESS;
	}
	
	@Action(value="standard_validateName",results={@Result(type="json")})
	public String validateName(){
		List<Standard> list = standardService.findByName(model.getName());
		if(list!=null&&list.size()>0){
//			ActionContext.getContext().getValueStack().push(false);
			//使用baseAction中的方法
			this.pushValueToStack(false);
		}else{
//			ActionContext.getContext().getValueStack().push(true);
			//使用baseAction的方法
			this.pushValueToStack(true);
		}
		System.out.println("验证");
		return SUCCESS;
	}
	
	//分页查询
	@Action(value="standard_pageQuery",results={@Result(type="json")})
	public String pageQuery(){
		//定义分页的pageable
		Pageable pageable = new PageRequest(page-1,rows);
		Page<Standard> standards = standardService.findPagedata(pageable);
		this.pushPageDataToValueStack(standards);
		return SUCCESS;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	//删除派送标准
	@Action(value="standard_delete",results={@Result(type="redirect",location="./pages/base/standard.html")})
	public String delete(){
		standardService.delete(ids);
		return SUCCESS;
	}
	//查询所有
	@Action(value="standard_findAll",results={@Result(type="json")})
	public String findAll(){
		List<Standard> list = standardService.findAll();
//		ActionContext.getContext().getValueStack().push(list);
		//使用baseAction方法放入值栈
		this.pushValueToStack(list);
		return SUCCESS;
	}
	
}
