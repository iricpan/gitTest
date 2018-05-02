package cn.bd.action;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.criteria.*;

import cn.bd.action.base.BaseAction;
import cn.bd.entity.Courier;
import cn.bd.entity.Standard;
import cn.bd.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class CourierAction extends BaseAction<Courier> {
	@Autowired
	private CourierService courierService;
	//添加取派员
	@Action(value="courier_add",results={@Result(type="redirect",location="./pages/base/courier.html")})
	public String add(){
		courierService.save(model);
		return SUCCESS;
	}
	/*private Integer page;
	private Integer rows;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}*/

	//分页查询
	@Action(value="courier_pageQuery",results={@Result(type="json")})
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);

		//带条件查询
		Specification<Courier> spec = new Specification<Courier>() {
			/**
			 * 传递：
			 * 		Root<Courier> root：（连接语句的时候需要字段，获取字段的名称）代表Criteria查询的根对象，Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似
			 * 		CriteriaQuery<?> query： （简单的查询可以使用CriteriaQuery）代表一个specific的顶层查询对象，它包含着查询的各个部分，比如：select 、from、where、group by、order by等
			 * 		CriteriaBuilder cb：（复杂的查询可以使用CriteriaBuilder构建）用来构建CritiaQuery的构建器对象
			 * 返回：Predicate：封装查询条件
			 * 
			 */
			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				//定义谓词,添加条件
				Predicate predicate=null;
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getCourierNum())){
					predicate=cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());//// 相当于条件：courierNum = gh001;
					list.add(predicate);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					predicate=cb.like(root.get("company").as(String.class),"%"+model.getCompany()+"%");//相当于条件：company like %北京%;
					list.add(predicate);
				}
				if(StringUtils.isNotBlank(model.getType())){
					predicate=cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					list.add(predicate);
				}
				//定义多表之间的连接关系
				Join<Courier, Standard> join = root.join("standard", JoinType.INNER);//相当于inner join t_courier c on t_standard s
				if(model.getStandard()!=null&&(model.getStandard().getId()!=null&&model.getStandard().getId()!=0)){
					predicate = cb.equal(join.get("id").as(Integer.class),model.getStandard().getId());
					list.add(predicate);
				}
				//判断条件并将条件添加到谓词
				if(list!=null&&list.size()>0){
					Predicate p[] = new Predicate[list.size()];//// 构建一个数组，长度条件的长度
					predicate=cb.and(list.toArray(p));//将查询条件从集合中的值转成数组中的值
				}
				return predicate;
			}
		};
		//条件查询
		Page<Courier> couriers = courierService.findPageQueryCourierByCondition(spec,pageable);
		//不带条件查询
//		Page<Courier> couriers = courierService.pageQuery(pageable);
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", couriers.getTotalElements());
		map.put("rows", couriers.getContent());
		ActionContext.getContext().getValueStack().push(map);*/
		this.pushPageDataToValueStack(couriers);
		return SUCCESS;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	//删除快递员
	@Action(value="courier_delete",results={@Result(type="redirect",location="./pages/base/courier.html")})
	public String delete(){
		courierService.delete(ids);
		return SUCCESS;
	}
	//恢复快递员
	@Action(value="courier_restore",results={@Result(type="redirect",location="./pages/base/courier.html")})
	public String restore(){
		courierService.restore(ids);
		return SUCCESS;
	}
}
