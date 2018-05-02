package cn.bd.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	protected T model;

	@Override
	public T getModel() {
		return model;
	}
	//构造器
	public BaseAction(){
		// 构造子类Action对象 ，获取继承父类型的泛型，然后实例化
		// AreaAction extends BaseAction<Area>
		// 需要获取BaseAction<Area>中的Area，然后实例化
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) genericSuperclass;
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
		try {
			model=clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			//输错错误信息
			System.out.println("baseAction初始化失败");
			e.printStackTrace();
		}
	}
	protected void pushValueToStack(Object object){
		ActionContext.getContext().getValueStack().push(object);
	}
	//分页信息
	protected Integer page;
	protected Integer rows;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	protected void pushPageDataToValueStack(Page<T> pageData){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(map);
	}
}
